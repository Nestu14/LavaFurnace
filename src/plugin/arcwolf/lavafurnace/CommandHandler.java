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
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.RED + "You do not have permissions for:");
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
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.WHITE + "User " + ChatColor.GREEN + split[0] + ChatColor.WHITE + " added to list.");
            }
            else {
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.WHITE + "User " + ChatColor.GREEN + split[0] + ChatColor.WHITE + " already in list.");
            }
        }
        else if (cmdname.equalsIgnoreCase("lfrem")) {
            if (!this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lfrem")) {
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.RED + "You do not have permissions for:");
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
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.WHITE + "User " + ChatColor.GREEN + split[0] + ChatColor.WHITE + " not in list.");
            }
            else {
                this.plugin.datawriter.deleteUser(split[0]);
                this.plugin.datawriter.lfCook.remove(id);
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.WHITE + "User " + ChatColor.GREEN + split[0] + ChatColor.WHITE + " removed from list.");
            }
        }
        else if (cmdname.equalsIgnoreCase("lfset")) {
            if (!this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lfset")) {
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.RED + "You do not have permissions for:");
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
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.RED + "Multiplier " + ChatColor.GREEN + split[2] + ChatColor.WHITE + " is not valid");
            }
            if (itemId != -1) {
                if (userId != -1) {
                    if (this.plugin.usercooktimehelper.setCookTimeMultiplier(userId, itemId, multiplier)) {
                        this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(userId));
                        player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.GREEN + split[0] + "'s" + ChatColor.WHITE + " item " + ChatColor.GREEN + split[1] + ChatColor.WHITE + " now smelts at " + ChatColor.GREEN + split[2] + "x");
                    }
                    else {
                        this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(userId));
                        player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.GREEN + split[2] + "x" + ChatColor.WHITE + " is not a valid multiplier.");
                    }
                }
                else {
                    player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.WHITE + "User " + ChatColor.GREEN + split[0] + ChatColor.WHITE + " not in list.");
                }
            }
            else {
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.WHITE + "Item " + ChatColor.GREEN + split[1] + ChatColor.WHITE + " is not a valid item.");
            }
        }
        else if (cmdname.equalsIgnoreCase("lflist")) {
            if (split.length == 1 && this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lflist")) {
                final int userId2 = this.plugin.usercooktimehelper.findUser(split[0]);
                if (userId2 != -1) {
                    player.sendMessage("User: " + ChatColor.GREEN + split[0]);
                    this.showLFList((CommandSender)player, userId2);
                }
                else {
                    player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.WHITE + "User " + ChatColor.GREEN + split[0] + ChatColor.WHITE + " not in list.");
                }
            }
            else {
                if (split.length != 0 || !this.plugin.playerCanUseCommand(player, "lavafurnace.player.lflist")) {
                    player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.RED + "You do not have permissions for:");
                    return false;
                }
                final int userId2 = this.plugin.usercooktimehelper.findUser(player.getName());
                if (userId2 != -1) {
                    player.sendMessage("User: " + ChatColor.GREEN + player.getName());
                    this.showLFList((CommandSender)player, userId2);
                }
                else {
                    player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.WHITE + "User " + ChatColor.GREEN + player.getName() + ChatColor.WHITE + " not in list.");
                }
            }
        }
        else if (cmdname.equalsIgnoreCase("lfreload")) {
            if (!this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lfreload")) {
                player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.RED + "You do not have permissions for:");
                return false;
            }
            this.plugin.datawriter.reload();
            player.sendMessage(ChatColor.GREEN + "LavaFurnace: " + ChatColor.GOLD + "plugin files reloaded. Check console for info.");
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
                sender.sendMessage("LavaFurnace: User " + split[0] + " added to list.");
            }
            else {
                sender.sendMessage("LavaFurnace: User " + split[0] + " already in list.");
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
                sender.sendMessage("LavaFurnace: User " + split[0] + " not in list.");
            }
            else {
                this.plugin.datawriter.deleteUser(split[0]);
                this.plugin.datawriter.lfCook.remove(id);
                sender.sendMessage("LavaFurnace: User " + split[0] + " removed from list.");
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
                sender.sendMessage("LavaFurnace: Multiplier " + split[2] + " is not valid");
            }
            if (itemId != -1) {
                if (userId != -1) {
                    if (this.plugin.usercooktimehelper.setCookTimeMultiplier(userId, itemId, multiplier)) {
                        this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(userId));
                        sender.sendMessage("LavaFurnace: " + split[0] + "'s item " + split[1] + " smelting at " + split[2] + "x");
                    }
                    else {
                        this.plugin.datawriter.writeUser(this.plugin.datawriter.lfCook.get(userId));
                        sender.sendMessage("LavaFurnace: " + split[2] + "x is not a valid speed.");
                    }
                }
                else {
                    sender.sendMessage("LavaFurnace: User " + split[0] + " not in list.");
                }
            }
            else {
                sender.sendMessage("LavaFurnace: Item " + split[1] + " is not a valid item.");
            }
        }
        else if (cmdname.equalsIgnoreCase("lflist")) {
            if (split.length != 1) {
                return false;
            }
            final int userId2 = this.plugin.usercooktimehelper.findUser(split[0]);
            if (userId2 != -1) {
                sender.sendMessage("User: " + ChatColor.YELLOW + split[0]);
                this.showLFList(sender, userId2);
            }
            else {
                sender.sendMessage("LavaFurnace: User " + ChatColor.YELLOW + split[0] + ChatColor.RESET + " not in list.");
            }
        }
        else if (cmdname.equalsIgnoreCase("lfreload")) {
            this.plugin.datawriter.reload();
            sender.sendMessage(ChatColor.GREEN + "LavaFurnace: " + ChatColor.GOLD + "plugin files reloaded.");
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
