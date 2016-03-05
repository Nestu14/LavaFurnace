// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import org.bukkit.command.Command;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.command.CommandSender;
import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.CalculableType;
import org.bukkit.entity.Player;
import org.bukkit.World;
import java.util.Iterator;
import org.bukkit.event.Listener;
import plugin.arcwolf.lavafurnace.Listeners.LFEntityListener;
import plugin.arcwolf.lavafurnace.Listeners.LFPlayerListener;
import plugin.arcwolf.lavafurnace.Listeners.LFBlockListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import com.nijikokun.bukkit.Permissions.Permissions;
import net.milkbowl.vault.permission.Permission;
import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Server;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class LavaFurnace extends JavaPlugin
{
    public static final Logger LOGGER;
    public static final Character SEPERATOR;
    private Server server;
    private BukkitScheduler scheduler;
    public DataWriter datawriter;
    public FurnaceHelper furnaceHelper;
    public ChestProcessing chestprocessing;
    public UserCookTimeHelper usercooktimehelper;
    public CommandHandler commandHandler;
    public CookTime cookTimeProcessing;
    private GroupManager groupManager;
    private Permission vaultPerms;
    private Permissions permissionsPlugin;
    private PermissionsEx permissionsExPlugin;
    private de.bananaco.bpermissions.imp.Permissions bPermissions;
    private PluginDescriptionFile pdfFile;
    private PluginManager pm;
    private String pluginName;
    private boolean permissionsEr;
    private boolean permissionsSet;
    public static Plugin plugin;
    
    static {
        LOGGER = Logger.getLogger("Minecraft.LavaFurnace");
        SEPERATOR = '\u001f';
    }
    
    public LavaFurnace() {
        this.permissionsEr = false;
        this.permissionsSet = false;
    }
    
    public void onEnable() {
        LavaFurnace.plugin = (Plugin)this;
        this.server = this.getServer();
        this.scheduler = this.server.getScheduler();
        this.pdfFile = this.getDescription();
        this.pluginName = this.pdfFile.getName();
        this.pm = this.server.getPluginManager();
        this.datawriter = new DataWriter(this);
        this.furnaceHelper = new FurnaceHelper(this);
        this.chestprocessing = new ChestProcessing(this);
        this.usercooktimehelper = new UserCookTimeHelper(this);
        this.commandHandler = new CommandHandler(this);
        this.cookTimeProcessing = new CookTime(this);
        final LFBlockListener blockListener = new LFBlockListener(this);
        final LFPlayerListener playerListener = new LFPlayerListener(this);
        final LFEntityListener entityListener = new LFEntityListener(this);
        try {
            this.datawriter.setVersion(Double.parseDouble(this.pdfFile.getVersion()));
        }
        catch (NumberFormatException e) {
            this.datawriter.setVersion(this.getCorrectVersion(this.pdfFile.getVersion()));
        }
        catch (Exception e2) {
            this.datawriter.setVersion(0.0);
        }
        this.pm.registerEvents((Listener)blockListener, (Plugin)this);
        this.pm.registerEvents((Listener)playerListener, (Plugin)this);
        this.pm.registerEvents((Listener)entityListener, (Plugin)this);
        this.datawriter.init();
        this.datawriter.reload();
        this.scheduler.scheduleSyncRepeatingTask((Plugin)this, (Runnable)new FurnaceScanner(this), 20L, 20L);
        this.getPermissionsPlugin();
        LavaFurnace.LOGGER.info(String.valueOf(this.pdfFile.getName()) + " version " + this.datawriter.getVersion() + " is enabled!");
    }
    
    private double getCorrectVersion(final String version) {
        final StringBuffer rebuilt = new StringBuffer();
        for (int index = 0; index < version.length(); ++index) {
            final char test = version.charAt(index);
            if ((test > '/' && test < ':') || (test == '.' && !rebuilt.toString().contains("."))) {
                rebuilt.append(test);
            }
        }
        return Double.valueOf(rebuilt.toString().equals("") ? 0.0 : Double.parseDouble(rebuilt.toString()));
    }
    
    public void onDisable() {
        final int count = this.datawriter.saveDatabase();
        final int count2 = this.datawriter.saveCookTimeDatabase();
        if (count != -1) {
            LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + " saved " + count + " furnace(s)");
        }
        if (count2 != -1) {
            LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + " saved " + count2 + " user(s)");
        }
        this.scheduler.cancelTasks((Plugin)this);
        for (final FurnaceObject fo : this.datawriter.lfObject) {
            this.furnaceHelper.remFromFurnaceBlockMap(fo);
        }
        LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + " version " + this.pdfFile.getVersion() + " is disabled!");
    }
    
    public World getWorld(final String worldname) {
        return this.server.getWorld(worldname);
    }
    
    public boolean playerCanUseCommand(final Player player, final String command) {
        this.getPermissionsPlugin();
        if (this.datawriter.isFreeforall()) {
            return true;
        }
        if (this.vaultPerms != null) {
            if (this.datawriter.getLFDebug() == 4) {
                final String pName = player.getName();
                final String gName = this.vaultPerms.getPrimaryGroup(player);
                final Boolean permissions = this.vaultPerms.has(player, command);
                LavaFurnace.LOGGER.info("Vault permissions, group for '" + pName + "' = " + gName);
                LavaFurnace.LOGGER.info("Permission for " + command + " is " + permissions);
            }
            return this.vaultPerms.has(player, command);
        }
        if (this.groupManager != null) {
            if (this.datawriter.getLFDebug() == 4) {
                final String pName = player.getName();
                final String gName = this.groupManager.getWorldsHolder().getWorldData(player.getWorld().getName()).getPermissionsHandler().getGroup(player.getName());
                final Boolean permissions = this.groupManager.getWorldsHolder().getWorldPermissions(player).has(player, command);
                LavaFurnace.LOGGER.info("group for '" + pName + "' = " + gName);
                LavaFurnace.LOGGER.info("Permission for " + command + " is " + permissions);
                LavaFurnace.LOGGER.info("");
                LavaFurnace.LOGGER.info("permissions available to '" + pName + "' = " + this.groupManager.getWorldsHolder().getWorldData(player.getWorld().getName()).getGroup(gName).getPermissionList());
            }
            return this.groupManager.getWorldsHolder().getWorldPermissions(player).has(player, command);
        }
        if (this.permissionsPlugin != null) {
            if (this.datawriter.getLFDebug() == 4) {
                final String pName = player.getName();
                final String wName = player.getWorld().getName();
                final String gName2 = Permissions.Security.getGroup(wName, pName);
                final Boolean permissions2 = Permissions.Security.permission(player, command);
                LavaFurnace.LOGGER.info("Niji permissions, group for '" + pName + "' = " + gName2);
                LavaFurnace.LOGGER.info("Permission for " + command + " is " + permissions2);
            }
            return Permissions.Security.permission(player, command);
        }
        if (this.permissionsExPlugin != null) {
            if (this.datawriter.getLFDebug() == 4) {
                final String pName = player.getName();
                final String wName = player.getWorld().getName();
                final String[] gNameA = PermissionsEx.getUser(player).getGroupsNames(wName);
                final StringBuffer gName3 = new StringBuffer();
                String[] array;
                for (int length = (array = gNameA).length, i = 0; i < length; ++i) {
                    final String groups = array[i];
                    gName3.append(String.valueOf(groups) + " ");
                }
                final Boolean permissions3 = PermissionsEx.getPermissionManager().has(player, command);
                LavaFurnace.LOGGER.info("PermissionsEx permissions, group for '" + pName + "' = " + gName3.toString());
                LavaFurnace.LOGGER.info("Permission for " + command + " is " + permissions3);
            }
            return PermissionsEx.getPermissionManager().has(player, command);
        }
        if (this.bPermissions != null) {
            if (this.datawriter.getLFDebug() == 4) {
                final String pName = player.getName();
                final String wName = player.getWorld().getName();
                final String[] gNameA = ApiLayer.getGroups(wName, CalculableType.USER, pName);
                final StringBuffer gName3 = new StringBuffer();
                String[] array2;
                for (int length2 = (array2 = gNameA).length, j = 0; j < length2; ++j) {
                    final String groups = array2[j];
                    gName3.append(String.valueOf(groups) + " ");
                }
                final Boolean permissions3 = this.bPermissions.has((CommandSender)player, command);
                LavaFurnace.LOGGER.info("bPermissions, group for '" + pName + "' = " + (Object)gName3);
                LavaFurnace.LOGGER.info("bPermission for " + command + " is " + permissions3);
            }
            return this.bPermissions.has((CommandSender)player, command);
        }
        if (this.server.getPluginManager().getPlugin("PermissionsBukkit") != null && player.hasPermission(command)) {
            if (this.datawriter.getLFDebug() == 4) {
                LavaFurnace.LOGGER.info("Bukkit Permissions " + command + " " + player.hasPermission(command));
            }
            return true;
        }
        if (this.permissionsEr && (player.isOp() || player.hasPermission(command))) {
            if (this.datawriter.getLFDebug() == 4) {
                LavaFurnace.LOGGER.info("Unknown permissions plugin " + command + " " + player.hasPermission(command));
            }
            return true;
        }
        if (this.datawriter.getLFDebug() == 4 && this.permissionsEr) {
            LavaFurnace.LOGGER.info("Unknown permissions plugin " + command + " " + player.hasPermission(command));
        }
        return false;
    }
    
    private void getPermissionsPlugin() {
        if (this.server.getPluginManager().getPlugin("Vault") != null) {
            final RegisteredServiceProvider<Permission> rsp = (RegisteredServiceProvider<Permission>)this.getServer().getServicesManager().getRegistration((Class)Permission.class);
            if (!this.permissionsSet) {
                LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + ": Vault detected, permissions enabled...");
                this.permissionsSet = true;
            }
            this.vaultPerms = (Permission)rsp.getProvider();
        }
        else if (this.server.getPluginManager().getPlugin("GroupManager") != null) {
            final Plugin p = this.server.getPluginManager().getPlugin("GroupManager");
            if (!this.permissionsSet) {
                LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + ": GroupManager detected, permissions enabled...");
                this.permissionsSet = true;
            }
            this.groupManager = (GroupManager)p;
        }
        else if (this.server.getPluginManager().getPlugin("Permissions") != null) {
            final Plugin p = this.server.getPluginManager().getPlugin("Permissions");
            if (!this.permissionsSet) {
                LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + ": Permissions detected, permissions enabled...");
                this.permissionsSet = true;
            }
            this.permissionsPlugin = (Permissions)p;
        }
        else if (this.server.getPluginManager().getPlugin("PermissionsBukkit") != null) {
            if (!this.permissionsSet) {
                LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + ": Bukkit permissions detected, permissions enabled...");
                this.permissionsSet = true;
            }
        }
        else if (this.server.getPluginManager().getPlugin("PermissionsEx") != null) {
            final Plugin p = this.server.getPluginManager().getPlugin("PermissionsEx");
            if (!this.permissionsSet) {
                LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + ": PermissionsEx detected, permissions enabled...");
                this.permissionsSet = true;
            }
            this.permissionsExPlugin = (PermissionsEx)p;
        }
        else if (this.server.getPluginManager().getPlugin("bPermissions") != null) {
            final Plugin p = this.server.getPluginManager().getPlugin("bPermissions");
            if (!this.permissionsSet) {
                LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + ": bPermissions detected, permissions enabled...");
                this.permissionsSet = true;
            }
            this.bPermissions = (de.bananaco.bpermissions.imp.Permissions)p;
        }
        else if (!this.permissionsEr) {
            LavaFurnace.LOGGER.info(String.valueOf(this.pluginName) + ": No known permissions detected, Using Server OPs");
            this.permissionsEr = true;
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] split) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player)sender;
            return this.commandHandler.inGame(cmd, commandLabel, split, player);
        }
        if (this.datawriter.isEnableConsoleCommands()) {
            return this.commandHandler.inConsole(sender, cmd, commandLabel, split);
        }
        sender.sendMessage("Console commands are disabled. Enable them in the config file");
        return true;
    }
}
