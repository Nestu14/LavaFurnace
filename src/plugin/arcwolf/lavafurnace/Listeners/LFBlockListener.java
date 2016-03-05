// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace.Listeners;

import plugin.arcwolf.lavafurnace.LavaFurnaceUserGroups;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.World;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.Player;
import plugin.arcwolf.lavafurnace.ChestHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import plugin.arcwolf.lavafurnace.FurnaceObject;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonRetractEvent;
import plugin.arcwolf.lavafurnace.DataWriter;
import plugin.arcwolf.lavafurnace.FurnaceHelper;
import plugin.arcwolf.lavafurnace.LavaFurnace;
import org.bukkit.event.Listener;

public class LFBlockListener implements Listener
{
    private final LavaFurnace plugin;
    private final FurnaceHelper furnacehelper;
    private final DataWriter dataWriter;
    
    public LFBlockListener(final LavaFurnace instance) {
        this.plugin = instance;
        this.furnacehelper = this.plugin.furnaceHelper;
        this.dataWriter = this.plugin.datawriter;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPistonRetract(final BlockPistonRetractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (this.dataWriter.isPistonprotect() && event.isSticky() && this.dataWriter.furnaceBlockMap.containsKey(event.getRetractLocation().getBlock())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPistonExtend(final BlockPistonExtendEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (this.dataWriter.isPistonprotect()) {
            for (final Block b : event.getBlocks()) {
                if (this.dataWriter.furnaceBlockMap.containsKey(b)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDamage(final BlockDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Block damageBlock = event.getBlock();
        FurnaceObject fo = this.dataWriter.furnaceBlockMap.get(damageBlock);
        if (fo == null && damageBlock.getType().equals((Object)Material.CHEST)) {
            fo = this.furnacehelper.chestPlacedFurnaceCheck(damageBlock);
            if (fo == null) {
                fo = this.furnacehelper.chestPlacedFurnaceCheck(this.furnacehelper.getDoubleChestBlock(damageBlock));
            }
        }
        if (fo == null) {
            return;
        }
        final int blockX = damageBlock.getLocation().getBlockX();
        final int blockY = damageBlock.getLocation().getBlockY();
        final int blockZ = damageBlock.getLocation().getBlockZ();
        final String inWorld = event.getPlayer().getWorld().getName();
        final String playerName = event.getPlayer().getName();
        final Player player = event.getPlayer();
        if (!damageBlock.getType().equals((Object)Material.CHEST)) {
            if (fo.creator.equals(playerName) && this.plugin.playerCanUseCommand(player, "lavafurnace.player.destroy")) {
                return;
            }
            if (this.plugin.playerCanUseCommand(player, "lavafurnace.admin.destroy")) {
                return;
            }
            player.sendMessage(plugin.getMessage("user.feedback.furnaceguarded"));
            event.setCancelled(true);
        }
        else {
            if (!new ChestHelper(this.plugin, fo).isProductionChest(fo, blockX, blockY, blockZ, inWorld) || !this.dataWriter.isProductChests()) {
                return;
            }
            if (fo.creator.equals(player.getName())
            && this.plugin.playerCanUseCommand(player, "lavafurnace.chests")) {
                return;
            }
            if (this.plugin.playerCanUseCommand(player, "lavafurnace.admin.destroy")) {
                return;
            }
            player.sendMessage(plugin.getMessage("user.feedback.chestguarded"));
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Block brokeBlock = event.getBlock();
        FurnaceObject fo = this.dataWriter.furnaceBlockMap.get(brokeBlock);
        if (fo == null && brokeBlock.getType().equals((Object)Material.CHEST)) {
            fo = this.furnacehelper.chestPlacedFurnaceCheck(brokeBlock);
        }
        if (fo == null) {
            return;
        }
        final int blockX = brokeBlock.getLocation().getBlockX();
        final int blockY = brokeBlock.getLocation().getBlockY();
        final int blockZ = brokeBlock.getLocation().getBlockZ();
        final Player player = event.getPlayer();
        final String inWorld = player.getWorld().getName();
        if (brokeBlock.getType().equals((Object)Material.CHEST)) {
            if (new ChestHelper(this.plugin, fo).isProductionChest(fo, blockX, blockY, blockZ, inWorld) && this.dataWriter.isProductChests()) {
                if (new ChestHelper(this.plugin, fo).isChestPair()) {
                    player.sendMessage(plugin.getMessage("user.feedback.chestdestroyed"));
                }
            }
        }
        else {
            player.sendMessage(plugin.getMessage("user.feedback.furnacedestroyed"));
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Block blockPlaced = event.getBlock();
        final Block blockAgainst = event.getBlockAgainst();
        final Player player = event.getPlayer();
        final String playerName = player.getName();
        final World world = player.getWorld();
        final int blockX = blockPlaced.getLocation().getBlockX();
        final int blockY = blockPlaced.getLocation().getBlockY();
        final int blockZ = blockPlaced.getLocation().getBlockZ();
        final int aBlockX = blockAgainst.getLocation().getBlockX();
        final int aBlockY = blockAgainst.getLocation().getBlockY();
        final int aBlockZ = blockAgainst.getLocation().getBlockZ();
        FurnaceObject fo = this.dataWriter.furnaceBlockMap.get(blockPlaced);
        if (blockPlaced.getType().equals((Object)Material.CHEST) && fo == null) {
            fo = this.furnacehelper.chestPlacedFurnaceCheck(event.getBlock());
            if (fo == null) {
                fo = this.furnacehelper.chestPlacedFurnaceCheck(this.furnacehelper.getDoubleChestBlock(event.getBlock()));
            }
            if (fo == null) {
                return;
            }
            if (!this.plugin.playerCanUseCommand(player, "lavafurnace.chests") && this.dataWriter.isProductChests()) {
                player.sendMessage(plugin.getMessage("user.feedback.cannotplacethis"));
                player.sendMessage(plugin.getMessage("user.feedback.cannotplacechest"));
                event.setCancelled(true);
                return;
            }
        }
        if (fo == null) {
            return;
        }
        if (!this.furnacehelper.isFurnaceUpper(fo, aBlockX, aBlockY, aBlockZ) && !this.furnacehelper.isFurnaceUpper(fo, blockX, blockY, blockZ)) {
            if (this.furnacehelper.isBlockLavaInCrucible(fo, blockX, blockY, blockZ)) {
                if (event.getBlock().getTypeId() != 11 && event.getBlock().getTypeId() != 10) {
                    player.sendMessage(plugin.getMessage("user.feedback.cannotplacethis"));
                    player.sendMessage(plugin.getMessage("user.feedback.canonlyplacelava"));
                    event.setCancelled(true);
                    return;
                }
                if ((!fo.creator.equals(playerName) || !this.plugin.playerCanUseCommand(player, "lavafurnace.player.lavablockfuel")) && !this.plugin.playerCanUseCommand(player, "lavafurnace.admin.lavablockfuel")) {
                    player.sendMessage(plugin.getMessage("user.feedback.nopermsforlava"));
                    event.setCancelled(true);
                    return;
                }
                if (this.dataWriter.getLFDebug() <= 0) {
                    player.sendMessage(ChatColor.YELLOW + "LavaFurnace: " + ChatColor.RED + " Debugging disabled...");
                    event.setCancelled(true);
                    return;
                }
                if (this.plugin.furnaceHelper.initFurnace(fo, world)) {
                    return;
                }
            }
            return;
        }
        if (event.getBlock().getTypeId() == 8 || event.getBlock().getTypeId() == 9) {
            player.sendMessage(plugin.getMessage("user.feedback.badplaceforwater"));
            event.setCancelled(true);
            return;
        }
        if (event.getBlock().getTypeId() == 11 || event.getBlock().getTypeId() == 10) {
            player.sendMessage(plugin.getMessage("user.feedback.badplaceforlava"));
            event.setCancelled(true);
            return;
        }
        player.sendMessage(plugin.getMessage("user.feedback.nopermsforblock"));
        event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockFromTo(final BlockFromToEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (this.dataWriter.furnaceBlockMap.containsKey(event.getBlock()) || this.dataWriter.furnaceBlockMap.containsKey(event.getToBlock())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockForm(final BlockFormEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (this.dataWriter.furnaceBlockMap.containsKey(event.getBlock())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(final SignChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final String lavaFurnace = event.getLine(0).trim().toLowerCase();
        final int blockId = event.getBlock().getTypeId();
        if (lavaFurnace.equals("[lavafurnace]") && blockId == 68) {
            final World world = event.getPlayer().getWorld();
            final String playerName = event.getPlayer().getName();
            final Player player = event.getPlayer();
            final byte blockData = event.getBlock().getData();
            final int blockX = event.getBlock().getLocation().getBlockX();
            final int blockY = event.getBlock().getLocation().getBlockY();
            final int blockZ = event.getBlock().getLocation().getBlockZ();
            int count = 0;
            int forgeCount = this.dataWriter.getMaxForges();
            for (final FurnaceObject fo : this.dataWriter.lfObject) {
                if (fo.creator.equals(playerName) && world.getName().equals(fo.world)) {
                    ++count;
                }
            }
            for (final LavaFurnaceUserGroups lfug : this.plugin.datawriter.lfUserGroups) {
                if (this.plugin.playerCanUseCommand(player, "lavafurnace.furnacelimit." + lfug.getGroupName())) {
                    forgeCount = lfug.getFurnaceAmount();
                    break;
                }
            }
            if (this.furnacehelper.isFurnace(world, blockX, blockY, blockZ, blockData)) {
                if (this.plugin.playerCanUseCommand(player, "lavafurnace.player.build") || this.plugin.playerCanUseCommand(player, "lavafurnace.admin.build")) {
                    if (count < forgeCount || this.plugin.playerCanUseCommand(player, "lavafurnace.admin.maxforgeoverride")) {
                        event.setLine(0, "");
                        event.setLine(1, "&9[LAVAFURNACE]");
                        event.setLine(1, event.getLine(1).replaceFirst("&([0-9a-f])", "\\§$1"));
                        player.sendMessage(plugin.getMessage("user.feedback.completefurnace"));
                        final int id = this.furnacehelper.createFurnace(playerName, world.getName(), blockData, blockX, blockY, blockZ);
                        this.furnacehelper.furnaceBornFX(this.dataWriter.lfObject.get(id));
                        this.dataWriter.writeFurnace(this.dataWriter.lfObject.get(id));
                    }
                    else {
                        player.sendMessage(plugin.getMessage("user.feedback.toomanyfurnaces"));
                        if (this.dataWriter.getLFDebug() == 6 && this.plugin.playerCanUseCommand(player, "lavafurnace.admin.build")) {
                            player.sendMessage(plugin.getMessage("user.feedback.debugtoomanyfurnaces", ""+count, ""+forgeCount));
                        }
                        event.setLine(0, "");
                    }
                }
                else {
                    player.sendMessage(plugin.getMessage("user.feedback.nopermsforfurnaces"));
                    if (this.dataWriter.getLFDebug() == 6) {
                        player.sendMessage(plugin.getMessage("user.feedback.debugnopermsforfurnaces"));
                    }
                    event.setLine(0, "");
                }
            }
            else {
                player.sendMessage(plugin.getMessage("user.feedback.badfurnaceconstruction"));
                if (this.dataWriter.getLFDebug() == 6 && this.plugin.playerCanUseCommand(player, "lavafurnace.admin.build")) {
                    this.dataWriter.setFurnaceDetectionl1(false);
                    this.dataWriter.setFurnaceDetectionl2(false);
                    this.dataWriter.setFurnaceDetectionl3(false);
                    this.dataWriter.setFurnaceDetectionbl(false);
                    this.dataWriter.setFdFacing(0);
                    final boolean fTest = this.furnacehelper.isFurnace(world, blockX, blockY, blockZ, blockData);
                    player.sendMessage("Facing= " + ChatColor.GREEN + this.dataWriter.getFdFacing());
                    player.sendMessage("L1 = " + ChatColor.GREEN + this.dataWriter.isFurnaceDetectionl1() + ChatColor.WHITE + " L2 = " + ChatColor.GREEN + this.dataWriter.isFurnaceDetectionl2() + ChatColor.WHITE + " L3 = " + ChatColor.GREEN + this.dataWriter.isFurnaceDetectionl3() + ChatColor.WHITE + " belt = " + ChatColor.GREEN + this.dataWriter.isFurnaceDetectionbl() + ChatColor.WHITE + " returned= " + ChatColor.GREEN + fTest);
                    player.sendMessage("L1 Blocks= " + ChatColor.GREEN + this.dataWriter.getmBLayerOne() + ":" + ChatColor.GREEN + this.dataWriter.getmBLayerOneData() + ChatColor.WHITE + " Door Block= " + ChatColor.GREEN + this.dataWriter.getDoorBlock() + ":" + ChatColor.GREEN + this.dataWriter.getDoorBlockData());
                    player.sendMessage("L2 Blocks= " + ChatColor.GREEN + this.dataWriter.getmBLayerTwo() + ChatColor.WHITE + " Belt Blocks= " + ChatColor.GREEN + this.dataWriter.getBeltBlocks() + ":" + ChatColor.GREEN + this.dataWriter.getBeltBlockData());
                    player.sendMessage("L3 Blocks= " + ChatColor.GREEN + this.dataWriter.getmBLayerThree());
                    this.furnacehelper.debugLevelSix(world, blockX, blockY, blockZ, blockData);
                }
                event.setLine(0, "");
            }
        }
    }
}
