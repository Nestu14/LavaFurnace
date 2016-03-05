// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;

public class CommandHandler
{
    private LavaFurnace plugin;
    
    public CommandHandler(final LavaFurnace instance) {
        this.plugin = instance;
    }
    
    public boolean inGame(final Command cmd, final String commandLabel, final String[] split, final Player player) {
        final String cmdname = cmd.getName().toLowerCase();
        if (cmdname.equalsIgnoreCase("lfadd")) {
            if (!this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lfadd")) {
                player.sendMessage(plugin.getMessage("error.permission", "admin.lfadd"));
                return false;
            }
            if (split.length == 0) {
                return false;
            }
            if (split.length != 1) {
                return false;
            }
            int id = this.plugin.usercooktimehelper.findUser(split[0]);
            if (id == -1) {
                id = this.plugin.usercooktimehelper.createUser(split[0]);
                this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(id));
                player.sendMessage(plugin.getMessage("user.list.added", split[0]));
            }
            else {
                player.sendMessage(plugin.getMessage("user.list.alreadyin", split[0]));
            }
        }
        else if (cmdname.equalsIgnoreCase("lfrem")) {
            if (!this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lfrem")) {
                player.sendMessage(plugin.getMessage("error.permission", "admin.lfrem"));
                return false;
            }
            if (split.length == 0) {
                return false;
            }
            if (split.length != 1) {
                return false;
            }
            final int id = this.plugin.usercooktimehelper.findUser(split[0]);
            if (id == -1) {
                player.sendMessage(plugin.getMessage("user.list.notin", split[0]));
            }
            else {
                this.plugin.datawriter.deleteUser(split[0]);
                this.plugin.datawriter.lfCook.remove(id);
                player.sendMessage(plugin.getMessage("user.list.removed", split[0]));
            }
        }
        else if (cmdname.equalsIgnoreCase("lfset")) {
            if (!this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lfset")) {
                player.sendMessage(plugin.getMessage("error.permission", "admin.lfset"));
                return false;
            }
            if (split.length != 3) {
                return false;
            }
            final int itemId = this.plugin.usercooktimehelper.getItemId(split[1]);
            final int userId = this.plugin.usercooktimehelper.findUser(split[0]);
            int multiplier = 1;
            try {
                multiplier = Integer.parseInt(split[2]);
            }
            catch (Exception e) {
                multiplier = 1;
                player.sendMessage(plugin.getMessage("user.feedback.multiplierinvalid", split[2]));
            }
            if (itemId != -1) {
                if (userId != -1) {
                    if (this.plugin.usercooktimehelper.setCookTimeMultiplier(userId, itemId, multiplier)) {
                        this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(userId));
                        player.sendMessage(plugin.getMessage("user.feedback.nowsmeltsat", split[0], split[1], split[2]));
                    }
                    else {
                        this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(userId));
                        player.sendMessage(plugin.getMessage("user.commands.multiplierinvalid", split[2]));
                    }
                }
                else {
                    player.sendMessage(plugin.getMessage("user.list.notin", split[0]));
                }
            }
            else {
                player.sendMessage(plugin.getMessage("user.feedback.invaliditem", split[1]));
            }
        }
        else if (cmdname.equalsIgnoreCase("lflist")) {
            if (split.length == 1 && this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lflist")) {
                final int userId2 = this.plugin.usercooktimehelper.findUser(split[0]);
                if (userId2 != -1) {
                    player.sendMessage(plugin.getMessage("user.list.header", split[0]));
                    this.showLFList((CommandSender)player, userId2);
                }
                else {
                    player.sendMessage(plugin.getMessage("user.list.notin", split[0]));
                }
            }
            else {
                if (split.length != 0 || !this.plugin.playerCanUseCommand(player, "lavafurnace.player.lflist")) {
                    player.sendMessage(plugin.getMessage("error.permission", "lavafurnace.player.lflist"));
                    return false;
                }
                final int userId2 = this.plugin.usercooktimehelper.findUser(player.getName());
                if (userId2 != -1) {
                    player.sendMessage(plugin.getMessage("user.list.header", player.getName()));
                    this.showLFList((CommandSender)player, userId2);
                }
                else {
                    player.sendMessage(plugin.getMessage("user.list.notin", player.getName()));
                }
            }
        }
        else if (cmdname.equalsIgnoreCase("lfreload")) {
            if (!this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lfreload")) {
                player.sendMessage(plugin.getMessage("error.permission", "lavafurnace.player.lfreload"));
                return false;
            }
            this.plugin.datawriter.reload();
            this.plugin.createAndLoadMessages();
            player.sendMessage(plugin.getMessage("user.feedback.reloaded"));
        }
        return true;
    }
    
    public boolean inConsole(final CommandSender sender, final Command cmd, final String commandLabel, final String[] split) {
        final String cmdname = cmd.getName().toLowerCase();
        if (cmdname.equalsIgnoreCase("lfadd")) {
            if (split.length == 0) {
                return false;
            }
            if (split.length != 1) {
                return false;
            }
            int id = this.plugin.usercooktimehelper.findUser(split[0]);
            if (id == -1) {
                id = this.plugin.usercooktimehelper.createUser(split[0]);
                this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(id));
                sender.sendMessage(plugin.getMessage("user.list.added", split[0]));
            }
            else {
                sender.sendMessage(plugin.getMessage("user.list.alreadyin", split[0]));
            }
        }
        else if (cmdname.equalsIgnoreCase("lfrem")) {
            if (split.length == 0) {
                return false;
            }
            if (split.length != 1) {
                return false;
            }
            final int id = this.plugin.usercooktimehelper.findUser(split[0]);
            if (id == -1) {
                sender.sendMessage(plugin.getMessage("user.list.notin", split[0]));
            }
            else {
                this.plugin.datawriter.deleteUser(split[0]);
                this.plugin.datawriter.lfCook.remove(id);
                sender.sendMessage(plugin.getMessage("user.list.removed", split[0]));
            }
        }
        else if (cmdname.equalsIgnoreCase("lfset")) {
            if (split.length != 3) {
                return false;
            }
            final int itemId = this.plugin.usercooktimehelper.getItemId(split[1]);
            final int userId = this.plugin.usercooktimehelper.findUser(split[0]);
            int multiplier = 1;
            try {
                multiplier = Integer.parseInt(split[2]);
            }
            catch (Exception e) {
                multiplier = 1;
                sender.sendMessage(plugin.getMessage("user.feedback.multiplierinvalid", split[2]));
            }
            if (itemId != -1) {
                if (userId != -1) {
                    if (this.plugin.usercooktimehelper.setCookTimeMultiplier(userId, itemId, multiplier)) {
                        this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(userId));
                        sender.sendMessage(plugin.getMessage("user.feedback.nowsmeltsat", split[0], split[1], split[2]));
                    }
                    else {
                        this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(userId));
                        sender.sendMessage(plugin.getMessage("user.feedback.invalidspeed", split[2]));
                    }
                }
                else {
                    sender.sendMessage(plugin.getMessage("user.list.notin", split[0]));
                }
            }
            else {
                sender.sendMessage(plugin.getMessage("user.feedback.invaliditem", split[1]));
            }
        }
        else if (cmdname.equalsIgnoreCase("lflist")) {
            if (split.length != 1) {
                return false;
            }
            final int userId2 = this.plugin.usercooktimehelper.findUser(split[0]);
            if (userId2 != -1) {
                sender.sendMessage(plugin.getMessage("user.list.header", split[0]));
                this.showLFList(sender, userId2);
            }
            else {
                sender.sendMessage(plugin.getMessage("user.list.notin", split[0]));
            }
        }
        else if (cmdname.equalsIgnoreCase("lfreload")) {
            this.plugin.datawriter.reload();
            sender.sendMessage(plugin.getMessage("user.feedback.reloaded"));
        }
        return true;
    }
    
    private void showLFList(final CommandSender sender, final int userId) {
        sender.sendMessage(ChatColor.WHITE + "NetherQuartz = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).netherquartz + "x" + ChatColor.WHITE + "   ClayBlock  = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).clayblock + "x" + ChatColor.WHITE + "   RawPork  = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).rawporkchop + "x");
        sender.sendMessage(ChatColor.WHITE + "CobbleStone  = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).cobblestone + "x" + ChatColor.WHITE + "   LapisOre   = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).lapisore + "x" + ChatColor.WHITE + "   Potato   = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).rawpotato + "x");
        sender.sendMessage(ChatColor.WHITE + "RedStoneOre  = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).redstoneore + "x" + ChatColor.WHITE + "   GoldOre    = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).goldore + "x" + ChatColor.WHITE + "   Cactus   = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).cactus + "x");
        sender.sendMessage(ChatColor.WHITE + "EmeraldOre   = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).emeraldore + "x" + ChatColor.WHITE + "   IronOre    = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).ironore + "x" + ChatColor.WHITE + "   Sand     = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).sand + "x");
        sender.sendMessage(ChatColor.WHITE + "DiamondOre   = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).diamondore + "x" + ChatColor.WHITE + "   CoalOre    = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).coalore + "x" + ChatColor.WHITE + "   Clay     = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).clay + "x");
        sender.sendMessage(ChatColor.WHITE + "NetherRack   = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).netherrack + "x" + ChatColor.WHITE + "   RawBeef    = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).rawbeef + "x" + ChatColor.WHITE + "   Logs     = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).wood + "x");
        sender.sendMessage(ChatColor.WHITE + "RawChicken   = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).rawchicken + "x" + ChatColor.WHITE + "   RawFish    = " + ChatColor.GREEN + this.plugin.datawriter.lfCook.get(userId).rawfish + "x");
    }
}
