// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import org.bukkit.inventory.Inventory;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Lever;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class RedStoneHelper
{
    private LavaFurnace plugin;
    
    public RedStoneHelper(final LavaFurnace plugin) {
        this.plugin = plugin;
    }
    
    public void updateLevers(final FurnaceObject fo) {
        this.updateFurnaceLever(fo);
        if (this.plugin.datawriter.isProductChests() && new ChestHelper(this.plugin, fo).isChestPair()) {
            this.updateSupplyLever(fo);
            this.updateProcessedLever(fo);
        }
    }
    
    public void resetFurnaceLever(final FurnaceObject fo) {
        final Block block = this.locatedFurnaceLever(fo);
        this.flipLever(block, fo, false);
    }
    
    private void updateFurnaceLever(final FurnaceObject fo) {
        final Block block = this.locatedFurnaceLever(fo);
        this.flipLever(block, fo, fo.power > 0);
    }
    
    private void updateSupplyLever(final FurnaceObject fo) {
        final Block block = this.locatedSupplyLever(fo);
        this.flipLever(block, fo, this.isSupplied(fo));
    }
    
    private void updateProcessedLever(final FurnaceObject fo) {
        final Block block = this.locatedProcessedLever(fo);
        this.flipLever(block, fo, this.isProcessed(fo));
    }
    
    private void flipLever(final Block block, final FurnaceObject fo, final boolean power) {
        if (block != null && block.getType() == Material.LEVER) {
            final BlockState blockState = block.getState();
            final Lever lever = (Lever)blockState.getData();
            lever.setPowered(power);
            blockState.setData((MaterialData)lever);
            blockState.update();
        }
    }
    
    private Block locatedFurnaceLever(final FurnaceObject fo) {
        final World world = this.plugin.getWorld(fo.world);
        if (fo.facing == 2) {
            return world.getBlockAt(fo.X, fo.Y - 2, fo.Z + 4);
        }
        if (fo.facing == 3) {
            return world.getBlockAt(fo.X, fo.Y - 2, fo.Z - 4);
        }
        if (fo.facing == 4) {
            return world.getBlockAt(fo.X + 4, fo.Y - 2, fo.Z);
        }
        if (fo.facing == 5) {
            return world.getBlockAt(fo.X - 4, fo.Y - 2, fo.Z);
        }
        return null;
    }
    
    private Block locatedProcessedLever(final FurnaceObject fo) {
        final World world = this.plugin.getWorld(fo.world);
        if (fo.facing == 2) {
            return world.getBlockAt(fo.X - 2, fo.Y - 2, fo.Z + 1);
        }
        if (fo.facing == 3) {
            return world.getBlockAt(fo.X + 2, fo.Y - 2, fo.Z - 1);
        }
        if (fo.facing == 4) {
            return world.getBlockAt(fo.X + 1, fo.Y - 2, fo.Z + 2);
        }
        if (fo.facing == 5) {
            return world.getBlockAt(fo.X - 1, fo.Y - 2, fo.Z - 2);
        }
        return null;
    }
    
    private Block locatedSupplyLever(final FurnaceObject fo) {
        final World world = this.plugin.getWorld(fo.world);
        if (fo.facing == 2) {
            return world.getBlockAt(fo.X + 2, fo.Y - 2, fo.Z + 1);
        }
        if (fo.facing == 3) {
            return world.getBlockAt(fo.X - 2, fo.Y - 2, fo.Z - 1);
        }
        if (fo.facing == 4) {
            return world.getBlockAt(fo.X + 1, fo.Y - 2, fo.Z - 2);
        }
        if (fo.facing == 5) {
            return world.getBlockAt(fo.X - 1, fo.Y - 2, fo.Z + 2);
        }
        return null;
    }
    
    private boolean isSupplied(final FurnaceObject fo) {
        final ChestHelper ch = new ChestHelper(this.plugin, fo);
        final Inventory[] inv = { ch.getSingleSupplyChest(), ch.getDoubleSupplyChest() };
        return !ch.isEmpty(inv);
    }
    
    public boolean isProcessed(final FurnaceObject fo) {
        final ChestHelper ch = new ChestHelper(this.plugin, fo);
        final Inventory[] inv = { ch.getSingleProcessChest(), ch.getDoubleProcessChest() };
        return !ch.isEmpty(inv);
    }
}
