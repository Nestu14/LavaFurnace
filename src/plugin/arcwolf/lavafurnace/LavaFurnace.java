// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.event.Listener;
import plugin.arcwolf.lavafurnace.Listeners.LFEntityListener;
import plugin.arcwolf.lavafurnace.Listeners.LFPlayerListener;
import plugin.arcwolf.lavafurnace.Listeners.LFBlockListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Server;
import java.util.logging.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
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
    private Permission vaultPerms;
    private PluginDescriptionFile pdfFile;
    private PluginManager pm;
    private String pluginName;
    private boolean permissionsEr;
    private boolean permissionsSet;
    public static Plugin plugin;
    
    YamlConfiguration messages;
    
    static {
        LOGGER = Logger.getLogger("Minecraft.LavaFurnace");
        SEPERATOR = '\u001f';
    }
    
    public LavaFurnace() {
        this.permissionsEr = false;
        this.permissionsSet = false;
    }
    
    @Override
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
        
        createAndLoadMessages();
        
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
        this.scheduler.scheduleSyncRepeatingTask((Plugin)this, 
                       (Runnable) new FurnaceScanner(this), 20L, 20L);
        this.getPermissionsPlugin();
        LavaFurnace.LOGGER.log(Level.INFO, "{0} version {1} is enabled!", 
                new Object[]{String.valueOf(this.pdfFile.getName()), this.datawriter.getVersion()});
    }
    
    private double getCorrectVersion(final String version) {
        final StringBuffer rebuilt = new StringBuffer();
        for (int index = 0; index < version.length(); ++index) {
            final char test = version.charAt(index);
            if ((test > '/' && test < ':') || (test == '.' && !rebuilt.toString().contains("."))) {
                rebuilt.append(test);
            }
        }
        return rebuilt.toString().equals("") ? 0.0 : Double.parseDouble(rebuilt.toString());
    }
    
    @Override
    public void onDisable() {
        final int count = this.datawriter.saveDatabase();
        final int count2 = this.datawriter.saveCookTimeDatabase();
        if (count != -1) {
            LavaFurnace.LOGGER.log(Level.INFO, "{0} saved {1} furnace(s)", new Object[]{String.valueOf(this.pluginName), count});
        }
        if (count2 != -1) {
            LavaFurnace.LOGGER.log(Level.INFO, "{0} saved {1} user(s)", new Object[]{String.valueOf(this.pluginName), count2});
        }
        this.scheduler.cancelTasks((Plugin)this);
        for (final FurnaceObject fo : this.datawriter.lfObject) {
            this.furnaceHelper.remFromFurnaceBlockMap(fo);
        }
        LavaFurnace.LOGGER.log(Level.INFO, "{0} version {1} is disabled!", new Object[]{String.valueOf(this.pluginName), this.pdfFile.getVersion()});
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
                LavaFurnace.LOGGER.log(Level.INFO, "Vault permissions, group for ''{0}'' = {1}", new Object[]{pName, gName});
                LavaFurnace.LOGGER.log(Level.INFO, "Permission for {0} is {1}", new Object[]{command, permissions});
            }
            return this.vaultPerms.has(player, command);
        }
        if (this.server.getPluginManager().getPlugin("PermissionsBukkit") != null && player.hasPermission(command)) {
            if (this.datawriter.getLFDebug() == 4) {
                LavaFurnace.LOGGER.log(Level.INFO, "Bukkit Permissions {0} {1}", new Object[]{command, player.hasPermission(command)});
            }
            return true;
        }
        if (this.permissionsEr && (player.isOp() || player.hasPermission(command))) {
            if (this.datawriter.getLFDebug() == 4) {
                LavaFurnace.LOGGER.log(Level.INFO, "Unknown permissions plugin {0} {1}", new Object[]{command, player.hasPermission(command)});
            }
            return true;
        }
        if (this.datawriter.getLFDebug() == 4 && this.permissionsEr) {
            LavaFurnace.LOGGER.log(Level.INFO, "Unknown permissions plugin {0} {1}", new Object[]{command, player.hasPermission(command)});
        }
        return false;
    }
    
    private void getPermissionsPlugin() {
        if (this.server.getPluginManager().getPlugin("Vault") != null) {
            final RegisteredServiceProvider<Permission> rsp = (RegisteredServiceProvider<Permission>)this.getServer().getServicesManager().getRegistration((Class)Permission.class);
            if (!this.permissionsSet) {
                LavaFurnace.LOGGER.log(Level.INFO, "{0}: Vault detected, permissions enabled...", String.valueOf(this.pluginName));
                this.permissionsSet = true;
            }
            this.vaultPerms = (Permission)rsp.getProvider();
        }
        else if (!this.permissionsEr) {
            LavaFurnace.LOGGER.log(Level.INFO, "{0}: No known permissions detected, Using Server OPs", String.valueOf(this.pluginName));
            this.permissionsEr = true;
        }
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] split) {
        Player player;
        if (sender instanceof Player) {
            player = (Player)sender;
            return this.commandHandler.inGame(cmd, commandLabel, split, player);
        }
        if (this.datawriter.isEnableConsoleCommands()) {
            return this.commandHandler.inConsole(sender, cmd, commandLabel, split);
        }
        sender.sendMessage(getMessage("cooktime.consoledisabled"));
        return true;
    }

    public boolean createAndLoadMessages() {
        File messageFile=new File(getDataFolder(), "messages.yml");
        if (!messageFile.exists()) {
            messageFile.getParentFile().mkdirs();
            copy(getResource("messages.yml"), messageFile);
        }
        YamlConfiguration tempMessages=new YamlConfiguration();
        try {
            tempMessages.load(messageFile);
            messages=tempMessages;
            return true;
        } catch (IOException | InvalidConfigurationException e) {
            LavaFurnace.LOGGER.log(Level.SEVERE, null, e);
            // In case of error, initialze messages, but don't overwrite them.
            if (messages==null)
                messages=tempMessages;
            return false;
        }
    }

    public String getMessage(String id, String... placeholders) {
        String result, formatted;
        if (messages==null)
            return "no messages loaded";
        if ((result=messages.getString(id))==null)
            return "No message defined for"+id;
        try {
            formatted=MessageFormat.format(result, (Object[]) placeholders);
            return formatted;
        } catch (IllegalArgumentException e) {
            return "Problem formatting "+id+" "+result+" with "+placeholders.length+" parameters ";
        }
    }
    
    private void copy(InputStream in, File file) {
        byte[] buf = new byte[1024];
        int len;
        try (OutputStream out = new FileOutputStream(file)) {
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
        } catch (IOException ex) {
            LavaFurnace.LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
