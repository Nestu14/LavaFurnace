// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace.Listeners;

import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import plugin.arcwolf.lavafurnace.ChestHelper;
import org.bukkit.ChatColor;
import org.bukkit.event.block.Action;
import org.bukkit.Material;
import plugin.arcwolf.lavafurnace.FurnaceObject;
import org.bukkit.event.player.PlayerInteractEvent;
import plugin.arcwolf.lavafurnace.DataWriter;
import plugin.arcwolf.lavafurnace.FurnaceHelper;
import plugin.arcwolf.lavafurnace.LavaFurnace;
import org.bukkit.event.Listener;

public class LFPlayerListener implements Listener
{
    private LavaFurnace plugin;
    private FurnaceHelper furnacehelper;
    private DataWriter dataWriter;
    
    public LFPlayerListener(final LavaFurnace instance) {
        this.plugin = instance;
        this.furnacehelper = this.plugin.furnaceHelper;
        this.dataWriter = this.plugin.datawriter;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Block blockClicked = event.getClickedBlock();
        if (blockClicked != null) {
            final String playerName = event.getPlayer().getName();
            FurnaceObject fo = this.dataWriter.furnaceBlockMap.get(blockClicked);
            if (fo == null && blockClicked.getType().equals((Object)Material.CHEST)) {
                fo = this.furnacehelper.chestPlacedFurnaceCheck(blockClicked);
                if (fo == null) {
                    fo = this.furnacehelper.chestPlacedFurnaceCheck(this.furnacehelper.getDoubleChestBlock(blockClicked));
                }
            }
            if (fo == null) {
                return;
            }
            final Player player = event.getPlayer();
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (blockClicked.getTypeId() == 61 || blockClicked.getTypeId() == 62) {
                    if ((fo.creator.equals(playerName) && this.plugin.playerCanUseCommand(player, "lavafurnace.player.use")) || this.plugin.playerCanUseCommand(player, "lavafurnace.admin.use")) {
                        return;
                    }
                    player.sendMessage(plugin.getMessage("user.feedback.doorblocked"));
                    event.setCancelled(true);
                }
                else if (blockClicked.getTypeId() == 54 && this.plugin.datawriter.isProductChests() && new ChestHelper(this.plugin, fo).isChestPair() && !this.plugin.datawriter.isFreeforallchests() && !this.plugin.playerCanUseCommand(player, "lavafurnace.chests")) {
                    player.sendMessage(plugin.getMessage("user.feedback.lidblocked"));
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBucketEmpty(final PlayerBucketEmptyEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final FurnaceObject fo = this.dataWriter.furnaceBlockMap.get(event.getBlockClicked());
        if (fo == null) {
            return;
        }
        final Player player = event.getPlayer();
        final String playerName = event.getPlayer().getName();
        final int blockX = event.getBlockClicked().getLocation().getBlockX();
        final int blockY = event.getBlockClicked().getLocation().getBlockY();
        final int blockZ = event.getBlockClicked().getLocation().getBlockZ();
        final int facing = fo.facing;
        final BlockFace face = event.getBlockFace();
        final World world = player.getWorld();
        //if (event.getBucket().getId() == 327) {
        if (event.getBucket() == Material.LAVA_BUCKET) {
            if (this.plugin.datawriter.getLFDebug() == 2) {
                player.sendMessage(plugin.getMessage("user.feedback.debugincrucible", ""+facing, 
                        ""+furnacehelper.wasLavaPlacedInCrucible(fo, blockX, blockY, blockZ, face)));
            }
            if (!this.furnacehelper.wasLavaPlacedInCrucible(fo, blockX, blockY, blockZ, face)) {
                player.sendMessage(plugin.getMessage("user.feedback.badplaceforlava"));
                event.setCancelled(true);
            }
            else if (
                    (fo.creator.equals(playerName) && plugin.playerCanUseCommand(player, "lavafurnace.player.fuel"))
                 || this.plugin.playerCanUseCommand(player, "lavafurnace.admin.fuel")
            ) {
                plugin.furnaceHelper.initFurnace(fo, world);
            }
            else {
                player.sendMessage(plugin.getMessage("user.feedback.nopermsforlava"));
                event.setCancelled(true);
            }
        }
        else if (event.getBucket() == Material.WATER_BUCKET) {
            player.sendMessage(plugin.getMessage("user.feedback.badplaceforwater"));
            event.setCancelled(true);
        }
    }
}
