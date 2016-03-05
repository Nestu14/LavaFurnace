// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import java.util.Map;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.Chunk;
import org.bukkit.block.Furnace;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class FurnaceHelper
{
    private LavaFurnace plugin;
    private DataWriter dataWriter;
    
    public FurnaceHelper(final LavaFurnace instance) {
        this.plugin = instance;
        this.dataWriter = this.plugin.datawriter;
    }
    
    public int createFurnace(final String in_creator, final String in_world, final byte in_facing, final int in_blockX, final int in_blockY, final int in_blockZ) {
        int index = -1;
        for (int i = 0; i < this.dataWriter.lfObject.size(); ++i) {
            if (in_creator.equals(this.dataWriter.lfObject.get(i).creator) && in_world.equals(this.dataWriter.lfObject.get(i).world) && this.dataWriter.lfObject.get(i).facing == in_facing && this.dataWriter.lfObject.get(i).X == in_blockX && this.dataWriter.lfObject.get(i).Y == in_blockY && this.dataWriter.lfObject.get(i).Z == in_blockZ) {
                index = i;
            }
        }
        if (index == -1) {
            final FurnaceObject fo = new FurnaceObject(in_creator, in_world, in_facing, in_blockX, in_blockY, in_blockZ, 0);
            this.putInFurnaceBlockMap(fo);
            this.dataWriter.lfObject.add(fo);
            for (int j = 0; j < this.dataWriter.lfObject.size(); ++j) {
                if (in_creator.equals(this.dataWriter.lfObject.get(j).creator) && in_world.equals(this.dataWriter.lfObject.get(j).world) && this.dataWriter.lfObject.get(j).facing == in_facing && this.dataWriter.lfObject.get(j).X == in_blockX && this.dataWriter.lfObject.get(j).Y == in_blockY && this.dataWriter.lfObject.get(j).Z == in_blockZ) {
                    index = j;
                }
            }
        }
        return index;
    }
    
    public int findFurnace(final String in_creator, final String in_world, final byte in_facing, final int in_blockX, final int in_blockY, final int in_blockZ) {
        int index = -1;
        for (int i = 0; i < this.dataWriter.lfObject.size(); ++i) {
            if (in_creator.equals(this.dataWriter.lfObject.get(i).creator) && in_world.equals(this.dataWriter.lfObject.get(i).world) && this.dataWriter.lfObject.get(i).facing == in_facing && this.dataWriter.lfObject.get(i).X == in_blockX && this.dataWriter.lfObject.get(i).Y == in_blockY && this.dataWriter.lfObject.get(i).Z == in_blockZ) {
                index = i;
            }
        }
        return index;
    }
    
    public int findFurnace(final String in_world, final byte in_facing, final int in_blockX, final int in_blockY, final int in_blockZ) {
        int index = -1;
        for (int i = 0; i < this.dataWriter.lfObject.size(); ++i) {
            if (in_world.equals(this.dataWriter.lfObject.get(i).world) && this.dataWriter.lfObject.get(i).facing == in_facing && this.dataWriter.lfObject.get(i).X == in_blockX && this.dataWriter.lfObject.get(i).Y == in_blockY && this.dataWriter.lfObject.get(i).Z == in_blockZ) {
                index = i;
            }
        }
        return index;
    }

    public boolean wasLavaPlacedInCrucible(FurnaceObject fo, int blockX, int blockY, int blockZ, BlockFace face, boolean log) {
        boolean b=wasLavaPlacedInCrucible(fo, blockX, blockY, blockZ, face);
        if (log) {
            Bukkit.getLogger().log(Level.INFO, "fo=({0},{1},{2}), fo.facing={3}", new Object[]{fo.X, fo.Y, fo.Z, fo.facing});
            Bukkit.getLogger().log(Level.INFO, "block=({0},{1},{2}), face={3}", new Object[]{blockX, blockY, blockZ, face});
            Bukkit.getLogger().log(Level.INFO, "BlockFace.UP={0}, equals={1}", new Object[]{BlockFace.UP, face.equals(BlockFace.UP)});
            Bukkit.getLogger().log(Level.INFO, "BlockFace.SOUTH={0}, equals={1}", new Object[]{BlockFace.SOUTH, face.equals(BlockFace.SOUTH)});
            Bukkit.getLogger().log(Level.INFO, "BlockFace.EAST={0}, equals={1}", new Object[]{BlockFace.EAST, face.equals(BlockFace.EAST)});
            Bukkit.getLogger().log(Level.INFO, "BlockFace.NORTH={0}, equals={1}", new Object[]{BlockFace.NORTH, face.equals(BlockFace.NORTH)});
            Bukkit.getLogger().log(Level.INFO, "BlockFace.WEST={0}, equals={1}", new Object[]{BlockFace.WEST, face.equals(BlockFace.WEST)});
            Bukkit.getLogger().log(Level.INFO, "wasLavaPlaced.. returns {0}", b);
        }
        return b;
    }

    public boolean wasLavaPlacedInCrucible(final FurnaceObject fo, final int blockX, final int blockY, final int blockZ, final BlockFace face) {
        final int fBlockX = fo.X;
        final int fBlockY = fo.Y;
        final int fBlockZ = fo.Z;
        final int LfFace = fo.facing;
        
        /* Seems that in spigot 1.9, Block is the block that lava is placed into,
           not the block adjacent to it that was clicked by the user. So the blockface
           isn't really relevant anymore. As i don't really understand all of this, i'll just
           leave the original code after my switch.
        */

        switch (LfFace) {
            case 2: return (fBlockX==blockX && fBlockY-1==blockY && fBlockZ+2==blockZ);
            case 3: return (fBlockX==blockX && fBlockY-1==blockY && fBlockZ-2==blockZ);
            case 4: return (fBlockX+2==blockX && fBlockY-1==blockY && fBlockZ==blockZ);
            case 5: return (fBlockX-2==blockX && fBlockY-1==blockY && fBlockZ==blockZ);
        }
        
        
        if (LfFace == 2) {
            return (fBlockX == blockX && fBlockY - 2 == blockY && fBlockZ + 2 == blockZ && face.equals((Object)BlockFace.UP))
                || (fBlockX - 1 == blockX && fBlockY - 1 == blockY && fBlockZ + 2 == blockZ && face.equals((Object)BlockFace.EAST))
                || (fBlockX + 1 == blockX && fBlockY - 1 == blockY && fBlockZ + 2 == blockZ && face.equals((Object)BlockFace.WEST)) 
                || (fBlockX == blockX && fBlockY - 1 == blockY && fBlockZ + 3 == blockZ && face.equals((Object)BlockFace.NORTH)) 
                || (fBlockX == blockX && fBlockY - 1 == blockY && fBlockZ + 1 == blockZ && face.equals((Object)BlockFace.SOUTH));
        }
        if (LfFace == 3) {
            return (fBlockX == blockX && fBlockY - 2 == blockY && fBlockZ - 2 == blockZ && face.equals((Object)BlockFace.UP))
                || (fBlockX + 1 == blockX && fBlockY - 1 == blockY && fBlockZ - 2 == blockZ && face.equals((Object)BlockFace.WEST)) 
                || (fBlockX - 1 == blockX && fBlockY - 1 == blockY && fBlockZ - 2 == blockZ && face.equals((Object)BlockFace.EAST))
                || (fBlockX == blockX && fBlockY - 1 == blockY && fBlockZ - 3 == blockZ && face.equals((Object)BlockFace.SOUTH)) 
                || (fBlockX == blockX && fBlockY - 1 == blockY && fBlockZ - 1 == blockZ && face.equals((Object)BlockFace.NORTH));
        }
        if (LfFace == 4) {
            return (fBlockX + 2 == blockX && fBlockY - 2 == blockY && fBlockZ == blockZ && face.equals((Object)BlockFace.UP))
                || (fBlockX + 2 == blockX && fBlockY - 1 == blockY && fBlockZ - 1 == blockZ && face.equals((Object)BlockFace.SOUTH))
                || (fBlockX + 2 == blockX && fBlockY - 1 == blockY && fBlockZ + 1 == blockZ && face.equals((Object)BlockFace.NORTH))
                || (fBlockX + 3 == blockX && fBlockY - 1 == blockY && fBlockZ == blockZ && face.equals((Object)BlockFace.WEST)) 
                || (fBlockX + 1 == blockX && fBlockY - 1 == blockY && fBlockZ == blockZ && face.equals((Object)BlockFace.EAST));
        }
        if (LfFace == 5) {
            return (fBlockX - 2 == blockX && fBlockY - 2 == blockY && fBlockZ == blockZ && face.equals((Object)BlockFace.UP))
                || (fBlockX - 2 == blockX && fBlockY - 1 == blockY && fBlockZ + 1 == blockZ && face.equals((Object)BlockFace.NORTH))
                || (fBlockX - 2 == blockX && fBlockY - 1 == blockY && fBlockZ - 1 == blockZ && face.equals((Object)BlockFace.SOUTH)) 
                || (fBlockX - 3 == blockX && fBlockY - 1 == blockY && fBlockZ == blockZ && face.equals((Object)BlockFace.EAST)) 
                || (fBlockX - 1 == blockX && fBlockY - 1 == blockY && fBlockZ == blockZ && face.equals((Object)BlockFace.WEST));
        }
        return false;
    }
    
    public boolean isBlockLavaInCrucible(final FurnaceObject fo, final int blockX, final int blockY, final int blockZ) {
        final int fBlockX = fo.X;
        final int fBlockY = fo.Y;
        final int fBlockZ = fo.Z;
        final int face = fo.facing;
        if (face == 2) {
            return fBlockX == blockX && fBlockY - 1 == blockY && fBlockZ + 2 == blockZ;
        }
        if (face == 3) {
            return fBlockX == blockX && fBlockY - 1 == blockY && fBlockZ - 2 == blockZ;
        }
        if (face == 4) {
            return fBlockX + 2 == blockX && fBlockY - 1 == blockY && fBlockZ == blockZ;
        }
        return face == 5 && (fBlockX - 2 == blockX && fBlockY - 1 == blockY && fBlockZ == blockZ);
    }
    
    private void setCrucibleFillLevel(final FurnaceObject fo) {
        final int power = fo.power;
        final Block block = this.getFurnaceCenter(fo);
        final int levelOne = (int)(0.16 * this.dataWriter.getFurnaceTimer());
        final int levelTwo = (int)(0.32 * this.dataWriter.getFurnaceTimer());
        final int levelThree = (int)(0.48 * this.dataWriter.getFurnaceTimer());
        final int levelFour = (int)(0.64 * this.dataWriter.getFurnaceTimer());
        final int levelFive = (int)(0.8 * this.dataWriter.getFurnaceTimer());
        final int levelSix = (int)(0.96 * this.dataWriter.getFurnaceTimer());
        if (power <= levelSix && power > levelFive) {
            block.setTypeId(11);
            block.setData((byte)1);
        }
        else if (power <= levelFive && power > levelFour) {
            block.setTypeId(11);
            block.setData((byte)2);
        }
        else if (power <= levelFour && power > levelThree) {
            block.setTypeId(11);
            block.setData((byte)3);
        }
        else if (power <= levelThree && power > levelTwo) {
            block.setTypeId(11);
            block.setData((byte)4);
        }
        else if (power <= levelTwo && power > levelOne) {
            block.setTypeId(11);
            block.setData((byte)5);
        }
        else if (power <= levelOne && power > 0) {
            block.setTypeId(11);
            block.setData((byte)6);
        }
    }
    
    public boolean isFurnace(final FurnaceObject fo) {
        final World world = this.plugin.getWorld(fo.world);
        return this.isFurnace(world, fo.X, fo.Y, fo.Z, (byte)fo.facing);
    }
    
    public boolean isFurnace(final World world, final int blockX, final int blockY, final int blockZ, final byte blockData) {
        this.dataWriter.setFdFacing(blockData);
        final int doorBlock = this.dataWriter.getDoorBlock();
        final int doorBlockData = this.dataWriter.getDoorBlockData();
        final int mBLayerOne = this.dataWriter.getmBLayerOne();
        final int mBLayerOneData = this.dataWriter.getmBLayerOneData();
        final int mBLayerTwo = this.dataWriter.getmBLayerTwo();
        final int mBLayerTwoData = this.dataWriter.getmBLayerTwoData();
        final int mBLayerThree = this.dataWriter.getmBLayerThree();
        final int mBLayerThreeData = this.dataWriter.getmBLayerThreeData();
        boolean layerOne = false;
        boolean layerTwo = false;
        boolean layerThree = false;
        final boolean beltTest = this.isFurnaceBelt(world, blockX, blockY, blockZ, blockData);
        if (blockData == 2) {
            if (world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX - 1, blockY, blockZ + 2).getTypeId() == doorBlock && world.getBlockAt(blockX - 1, blockY, blockZ + 2).getData() == doorBlockData && world.getBlockAt(blockX, blockY, blockZ + 2).getTypeId() == 0 && world.getBlockAt(blockX - 1, blockY, blockZ + 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ + 3).getData() == mBLayerOneData && world.getBlockAt(blockX, blockY, blockZ + 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX, blockY, blockZ + 3).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ + 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ + 3).getData() == mBLayerOneData) {
                layerOne = true;
                this.dataWriter.setFurnaceDetectionl1(true);
            }
            if (world.getBlockAt(blockX - 1, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == 61 && world.getBlockAt(blockX, blockY - 1, blockZ + 1).getData() == 2 && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getData() == mBLayerTwoData && world.getBlockAt(blockX, blockY - 1, blockZ + 2).getTypeId() == 0 && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getData() == mBLayerTwoData && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 3).getData() == mBLayerTwoData && world.getBlockAt(blockX, blockY - 1, blockZ + 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX, blockY - 1, blockZ + 3).getData() == mBLayerTwoData && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 3).getData() == mBLayerTwoData) {
                layerTwo = beltTest;
                this.dataWriter.setFurnaceDetectionl2(true);
            }
            layerThree = true;
            this.dataWriter.setFurnaceDetectionl3(true);
            for (int x = -1; x < 2; ++x) {
                for (int z = 1; z < 4; ++z) {
                    if (world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getTypeId() != mBLayerThree || world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getData() != mBLayerThreeData) {
                        layerThree = false;
                        this.dataWriter.setFurnaceDetectionl3(false);
                    }
                }
            }
            if (layerOne && layerTwo && layerThree) {
                return true;
            }
        }
        else if (blockData == 3) {
            if (world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ - 2).getTypeId() == doorBlock && world.getBlockAt(blockX + 1, blockY, blockZ - 2).getData() == doorBlockData && world.getBlockAt(blockX, blockY, blockZ - 2).getTypeId() == 0 && world.getBlockAt(blockX + 1, blockY, blockZ - 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ - 3).getData() == mBLayerOneData && world.getBlockAt(blockX, blockY, blockZ - 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX, blockY, blockZ - 3).getData() == mBLayerOneData && world.getBlockAt(blockX - 1, blockY, blockZ - 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ - 3).getData() == mBLayerOneData) {
                layerOne = true;
                this.dataWriter.setFurnaceDetectionl1(true);
            }
            if (world.getBlockAt(blockX + 1, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == 61 && world.getBlockAt(blockX, blockY - 1, blockZ - 1).getData() == 3 && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getData() == mBLayerTwoData && world.getBlockAt(blockX, blockY - 1, blockZ - 2).getTypeId() == 0 && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getData() == mBLayerTwoData && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 3).getData() == mBLayerTwoData && world.getBlockAt(blockX, blockY - 1, blockZ - 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX, blockY - 1, blockZ - 3).getData() == mBLayerTwoData && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 3).getData() == mBLayerTwoData) {
                layerTwo = beltTest;
                this.dataWriter.setFurnaceDetectionl2(true);
            }
            layerThree = true;
            this.dataWriter.setFurnaceDetectionl3(true);
            for (int x = -1; x < 2; ++x) {
                for (int z = -1; z > -4; --z) {
                    if (world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getTypeId() != mBLayerThree || world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getData() != mBLayerThreeData) {
                        layerThree = false;
                        this.dataWriter.setFurnaceDetectionl3(false);
                    }
                }
            }
            if (layerOne && layerTwo && layerThree) {
                return true;
            }
        }
        else if (blockData == 4) {
            if (world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX + 2, blockY, blockZ + 1).getTypeId() == doorBlock && world.getBlockAt(blockX + 2, blockY, blockZ + 1).getData() == doorBlockData && world.getBlockAt(blockX + 2, blockY, blockZ).getTypeId() == 0 && world.getBlockAt(blockX + 3, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 3, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX + 3, blockY, blockZ).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 3, blockY, blockZ).getData() == mBLayerOneData && world.getBlockAt(blockX + 3, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 3, blockY, blockZ - 1).getData() == mBLayerOneData) {
                layerOne = true;
                this.dataWriter.setFurnaceDetectionl1(true);
            }
            if (world.getBlockAt(blockX + 1, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == 61 && world.getBlockAt(blockX + 1, blockY - 1, blockZ).getData() == 4 && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 2, blockY - 1, blockZ).getTypeId() == 0 && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 3, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 3, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 3, blockY - 1, blockZ).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 3, blockY - 1, blockZ).getData() == mBLayerTwoData && world.getBlockAt(blockX + 3, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 3, blockY - 1, blockZ - 1).getData() == mBLayerTwoData) {
                layerTwo = beltTest;
                this.dataWriter.setFurnaceDetectionl2(true);
            }
            layerThree = true;
            this.dataWriter.setFurnaceDetectionl3(true);
            for (int x = 1; x < 4; ++x) {
                for (int z = -1; z < 2; ++z) {
                    if (world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getTypeId() != mBLayerThree || world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getData() != mBLayerThreeData) {
                        layerThree = false;
                        this.dataWriter.setFurnaceDetectionl3(false);
                    }
                }
            }
            if (layerOne && layerTwo && layerThree) {
                return true;
            }
        }
        else if (blockData == 5) {
            if (world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX - 1, blockY, blockZ).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ).getData() == mBLayerOneData && world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX - 2, blockY, blockZ - 1).getTypeId() == doorBlock && world.getBlockAt(blockX - 2, blockY, blockZ - 1).getData() == doorBlockData && world.getBlockAt(blockX - 2, blockY, blockZ).getTypeId() == 0 && world.getBlockAt(blockX - 3, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 3, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX - 3, blockY, blockZ).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 3, blockY, blockZ).getData() == mBLayerOneData && world.getBlockAt(blockX - 3, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 3, blockY, blockZ + 1).getData() == mBLayerOneData) {
                layerOne = true;
                this.dataWriter.setFurnaceDetectionl1(true);
            }
            if (world.getBlockAt(blockX - 1, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == 61 && world.getBlockAt(blockX - 1, blockY - 1, blockZ).getData() == 5 && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 2, blockY - 1, blockZ).getTypeId() == 0 && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 3, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 3, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 3, blockY - 1, blockZ).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 3, blockY - 1, blockZ).getData() == mBLayerTwoData && world.getBlockAt(blockX - 3, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 3, blockY - 1, blockZ + 1).getData() == mBLayerTwoData) {
                layerTwo = beltTest;
                this.dataWriter.setFurnaceDetectionl2(true);
            }
            layerThree = true;
            this.dataWriter.setFurnaceDetectionl3(true);
            for (int x = -1; x > -4; --x) {
                for (int z = -1; z < 2; ++z) {
                    if (world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getTypeId() != mBLayerThree || world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getData() != mBLayerThreeData) {
                        layerThree = false;
                        this.dataWriter.setFurnaceDetectionl3(false);
                    }
                }
            }
            if (layerOne && layerTwo && layerThree) {
                if (this.dataWriter.getLFDebug() == 3) {
                    LavaFurnace.LOGGER.info("df method -> unpowered & facing=" + blockData + " layer1=" + layerOne + " layer2=" + layerTwo + " layer3=" + layerThree);
                }
                return true;
            }
        }
        if (this.dataWriter.getLFDebug() == 3) {
            LavaFurnace.LOGGER.info("df method -> No furnace detected=" + blockData + " layer1=" + layerOne + " layer2=" + layerTwo + " layer3=" + layerThree);
        }
        return false;
    }
    
    public boolean isFurnacePowered(final FurnaceObject fo) {
        final int blockX = fo.X;
        final int blockY = fo.Y;
        final int blockZ = fo.Z;
        final byte blockData = (byte)fo.facing;
        final World world = this.plugin.getWorld(fo.world);
        final int doorBlock = this.dataWriter.getDoorBlock();
        final int doorBlockData = this.dataWriter.getDoorBlockData();
        final int mBLayerOne = this.dataWriter.getmBLayerOne();
        final int mBLayerOneData = this.dataWriter.getmBLayerOneData();
        final int mBLayerTwo = this.dataWriter.getmBLayerTwo();
        final int mBLayerTwoData = this.dataWriter.getmBLayerTwoData();
        final int mBLayerThree = this.dataWriter.getmBLayerThree();
        final int mBLayerThreeData = this.dataWriter.getmBLayerThreeData();
        boolean layerOne = false;
        boolean layerTwo = false;
        boolean layerThree = false;
        final boolean beltTest = this.isFurnaceBelt(world, blockX, blockY, blockZ, blockData);
        if (blockData == 2) {
            if (world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ + 1).getData() == mBLayerOneData && ((world.getBlockAt(blockX - 1, blockY, blockZ + 2).getTypeId() == doorBlock && world.getBlockAt(blockX - 1, blockY, blockZ + 2).getData() == doorBlockData && (world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == 62 || world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == 61)) || (world.getBlockAt(blockX, blockY, blockZ + 2).getTypeId() == doorBlock && world.getBlockAt(blockX, blockY, blockZ + 2).getData() == doorBlockData && (world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == 62 || world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == 61))) && world.getBlockAt(blockX - 1, blockY, blockZ + 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ + 3).getData() == mBLayerOneData && world.getBlockAt(blockX, blockY, blockZ + 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX, blockY, blockZ + 3).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ + 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ + 3).getData() == mBLayerOneData) {
                layerOne = true;
            }
            if (world.getBlockAt(blockX - 1, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && (world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == 62 || world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == 61) && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getData() == mBLayerTwoData && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getData() == mBLayerTwoData && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 3).getData() == mBLayerTwoData && world.getBlockAt(blockX, blockY - 1, blockZ + 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX, blockY - 1, blockZ + 3).getData() == mBLayerTwoData && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 3).getData() == mBLayerTwoData && (((world.getBlockAt(blockX, blockY - 1, blockZ + 2).getTypeId() == 11 || world.getBlockAt(blockX, blockY - 1, blockZ + 2).getTypeId() == 10) && world.getBlockAt(blockX, blockY, blockZ + 2).getTypeId() == doorBlock && world.getBlockAt(blockX, blockY, blockZ + 2).getData() == doorBlockData) || (world.getBlockAt(blockX, blockY - 1, blockZ + 2).getTypeId() == 0 && world.getBlockAt(blockX - 1, blockY, blockZ + 2).getTypeId() == doorBlock && world.getBlockAt(blockX - 1, blockY, blockZ + 2).getData() == doorBlockData))) {
                layerTwo = beltTest;
            }
            layerThree = true;
            this.dataWriter.setFurnaceDetectionl3(true);
            for (int x = -1; x < 2; ++x) {
                for (int z = 1; z < 4; ++z) {
                    if (world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getTypeId() != mBLayerThree || world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getData() != mBLayerThreeData) {
                        layerThree = false;
                        this.dataWriter.setFurnaceDetectionl3(false);
                    }
                }
            }
            if (layerOne && layerTwo && layerThree) {
                return true;
            }
        }
        else if (blockData == 3) {
            if (world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ - 1).getData() == mBLayerOneData && ((world.getBlockAt(blockX + 1, blockY, blockZ - 2).getTypeId() == doorBlock && (world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == 62 || world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == 61)) || (world.getBlockAt(blockX, blockY, blockZ - 2).getTypeId() == doorBlock && world.getBlockAt(blockX, blockY, blockZ - 2).getData() == doorBlockData && (world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == 62 || world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == 61))) && world.getBlockAt(blockX + 1, blockY, blockZ - 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX, blockY, blockZ - 3).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ - 3).getTypeId() == mBLayerOne) {
                layerOne = true;
            }
            if (world.getBlockAt(blockX + 1, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && (world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == 62 || world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == 61) && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getData() == mBLayerTwoData && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getData() == mBLayerTwoData && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 3).getData() == mBLayerTwoData && world.getBlockAt(blockX, blockY - 1, blockZ - 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX, blockY - 1, blockZ - 3).getData() == mBLayerTwoData && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 3).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 3).getData() == mBLayerTwoData && (((world.getBlockAt(blockX, blockY - 1, blockZ - 2).getTypeId() == 11 || world.getBlockAt(blockX, blockY - 1, blockZ - 2).getTypeId() == 10) && world.getBlockAt(blockX, blockY, blockZ - 2).getTypeId() == doorBlock && world.getBlockAt(blockX, blockY, blockZ - 2).getData() == doorBlockData) || (world.getBlockAt(blockX, blockY - 1, blockZ - 2).getTypeId() == 0 && world.getBlockAt(blockX + 1, blockY, blockZ - 2).getTypeId() == doorBlock && world.getBlockAt(blockX + 1, blockY, blockZ - 2).getData() == doorBlockData))) {
                layerTwo = beltTest;
            }
            layerThree = true;
            this.dataWriter.setFurnaceDetectionl3(true);
            for (int x = -1; x < 2; ++x) {
                for (int z = -1; z > -4; --z) {
                    if (world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getTypeId() != mBLayerThree || world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getData() != mBLayerThreeData) {
                        layerThree = false;
                        this.dataWriter.setFurnaceDetectionl3(false);
                    }
                }
            }
            if (layerOne && layerTwo && layerThree) {
                return true;
            }
        }
        else if (blockData == 4) {
            if (world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ).getData() == mBLayerOneData && world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 1, blockY, blockZ - 1).getData() == mBLayerOneData && ((world.getBlockAt(blockX + 2, blockY, blockZ + 1).getTypeId() == doorBlock && world.getBlockAt(blockX + 2, blockY, blockZ + 1).getData() == doorBlockData && (world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == 62 || world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == 61)) || (world.getBlockAt(blockX + 2, blockY, blockZ).getTypeId() == doorBlock && world.getBlockAt(blockX + 2, blockY, blockZ).getData() == doorBlockData && (world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == 62 || world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == 61))) && world.getBlockAt(blockX + 3, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 3, blockY, blockZ + 1).getData() == mBLayerOneData && world.getBlockAt(blockX + 3, blockY, blockZ).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 3, blockY, blockZ).getData() == mBLayerOneData && world.getBlockAt(blockX + 3, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX + 3, blockY, blockZ - 1).getData() == mBLayerOneData) {
                layerOne = true;
            }
            if (world.getBlockAt(blockX + 1, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && (world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == 62 || world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == 61) && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 3, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 3, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX + 3, blockY - 1, blockZ).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 3, blockY - 1, blockZ).getData() == mBLayerTwoData && world.getBlockAt(blockX + 3, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX + 3, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && (((world.getBlockAt(blockX + 2, blockY - 1, blockZ).getTypeId() == 11 || world.getBlockAt(blockX + 2, blockY - 1, blockZ).getTypeId() == 10) && world.getBlockAt(blockX + 2, blockY, blockZ).getTypeId() == doorBlock && world.getBlockAt(blockX + 2, blockY, blockZ).getData() == doorBlockData) || (world.getBlockAt(blockX + 2, blockY - 1, blockZ).getTypeId() == 0 && world.getBlockAt(blockX + 2, blockY, blockZ + 1).getTypeId() == doorBlock && world.getBlockAt(blockX + 2, blockY, blockZ + 1).getData() == doorBlockData))) {
                layerTwo = beltTest;
            }
            layerThree = true;
            this.dataWriter.setFurnaceDetectionl3(true);
            for (int x = 1; x < 4; ++x) {
                for (int z = -1; z < 2; ++z) {
                    if (world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getTypeId() != mBLayerThree || world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getData() != mBLayerThreeData) {
                        layerThree = false;
                        this.dataWriter.setFurnaceDetectionl3(false);
                    }
                }
            }
            if (layerOne && layerTwo && layerThree) {
                return true;
            }
        }
        else if (blockData == 5) {
            if (world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX - 1, blockY, blockZ).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ).getData() == mBLayerOneData && world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 1, blockY, blockZ + 1).getData() == mBLayerOneData && ((world.getBlockAt(blockX - 2, blockY, blockZ - 1).getTypeId() == doorBlock && (world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == 62 || world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == 61)) || (world.getBlockAt(blockX - 2, blockY, blockZ).getTypeId() == doorBlock && world.getBlockAt(blockX - 2, blockY, blockZ).getData() == doorBlockData && (world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == 62 || world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == 61))) && world.getBlockAt(blockX - 3, blockY, blockZ - 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 3, blockY, blockZ - 1).getData() == mBLayerOneData && world.getBlockAt(blockX - 3, blockY, blockZ).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 3, blockY, blockZ).getData() == mBLayerOneData && world.getBlockAt(blockX - 3, blockY, blockZ + 1).getTypeId() == mBLayerOne && world.getBlockAt(blockX - 3, blockY, blockZ + 1).getData() == mBLayerOneData) {
                layerOne = true;
            }
            if (world.getBlockAt(blockX - 1, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && (world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == 62 || world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == 61) && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 3, blockY - 1, blockZ - 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 3, blockY - 1, blockZ - 1).getData() == mBLayerTwoData && world.getBlockAt(blockX - 3, blockY - 1, blockZ).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 3, blockY - 1, blockZ).getData() == mBLayerTwoData && world.getBlockAt(blockX - 3, blockY - 1, blockZ + 1).getTypeId() == mBLayerTwo && world.getBlockAt(blockX - 3, blockY - 1, blockZ + 1).getData() == mBLayerTwoData && (((world.getBlockAt(blockX - 2, blockY - 1, blockZ).getTypeId() == 11 || world.getBlockAt(blockX - 2, blockY - 1, blockZ).getTypeId() == 10) && world.getBlockAt(blockX - 2, blockY, blockZ).getTypeId() == doorBlock && world.getBlockAt(blockX - 2, blockY, blockZ).getData() == doorBlockData) || (world.getBlockAt(blockX - 2, blockY - 1, blockZ).getTypeId() == 0 && world.getBlockAt(blockX - 2, blockY, blockZ - 1).getTypeId() == doorBlock && world.getBlockAt(blockX - 2, blockY, blockZ - 1).getData() == doorBlockData))) {
                layerTwo = beltTest;
            }
            layerThree = true;
            this.dataWriter.setFurnaceDetectionl3(true);
            for (int x = -1; x > -4; --x) {
                for (int z = -1; z < 2; ++z) {
                    if (world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getTypeId() != mBLayerThree || world.getBlockAt(blockX + x, blockY - 2, blockZ + z).getData() != mBLayerThreeData) {
                        layerThree = false;
                        this.dataWriter.setFurnaceDetectionl3(false);
                    }
                }
            }
            if (layerOne && layerTwo && layerThree) {
                if (this.dataWriter.getLFDebug() == 3) {
                    LavaFurnace.LOGGER.info("dfp method -> powered & facing=" + blockData + " layer1=" + layerOne + " layer2=" + layerTwo + " layer3=" + layerThree);
                }
                return true;
            }
        }
        if (this.dataWriter.getLFDebug() == 3) {
            LavaFurnace.LOGGER.info("dfp method -> powered & facing=" + blockData + " layer1=" + layerOne + " layer2=" + layerTwo + " layer3=" + layerThree);
        }
        return false;
    }
    
    public boolean resetGlassDoor(final FurnaceObject fo) {
        final int doorBlock = this.dataWriter.getDoorBlock();
        final int doorBlockData = this.dataWriter.getDoorBlockData();
        final World world = this.plugin.getWorld(fo.world);
        final int fblockX = fo.X;
        final int fblockY = fo.Y;
        final int fblockZ = fo.Z;
        boolean doorReset = false;
        if (fo.facing == 2) {
            if (doorBlock == 44 || doorBlock == 43) {
                if (world.getBlockAt(fblockX - 1, fblockY, fblockZ + 2).getTypeId() == 0 && world.getBlockAt(fblockX, fblockY, fblockZ + 2).getTypeId() == doorBlock && world.getBlockAt(fblockX, fblockY, fblockZ + 2).getData() == doorBlockData) {
                    world.getBlockAt(fblockX - 1, fblockY, fblockZ + 2).setTypeIdAndData(doorBlock, (byte)doorBlockData, true);
                    world.getBlockAt(fblockX, fblockY, fblockZ + 2).setTypeId(0);
                    world.getBlockAt(fblockX, fblockY - 1, fblockZ + 2).setTypeId(0);
                    doorReset = true;
                }
            }
            else if (world.getBlockAt(fblockX - 1, fblockY, fblockZ + 2).getTypeId() == 0 && world.getBlockAt(fblockX, fblockY, fblockZ + 2).getTypeId() == doorBlock && world.getBlockAt(fblockX + 1, fblockY, fblockZ + 2).getTypeId() == 0) {
                world.getBlockAt(fblockX - 1, fblockY, fblockZ + 2).setTypeId(doorBlock);
                world.getBlockAt(fblockX, fblockY, fblockZ + 2).setTypeId(0);
                world.getBlockAt(fblockX, fblockY - 1, fblockZ + 2).setTypeId(0);
                doorReset = true;
            }
        }
        if (fo.facing == 3) {
            if (doorBlock == 44 || doorBlock == 43) {
                if (world.getBlockAt(fblockX + 1, fblockY, fblockZ - 2).getTypeId() == 0 && world.getBlockAt(fblockX, fblockY, fblockZ - 2).getTypeId() == doorBlock && world.getBlockAt(fblockX, fblockY, fblockZ - 2).getData() == doorBlockData) {
                    world.getBlockAt(fblockX + 1, fblockY, fblockZ - 2).setTypeIdAndData(doorBlock, (byte)doorBlockData, true);
                    world.getBlockAt(fblockX, fblockY, fblockZ - 2).setTypeId(0);
                    world.getBlockAt(fblockX, fblockY - 1, fblockZ - 2).setTypeId(0);
                    doorReset = true;
                }
            }
            else if (world.getBlockAt(fblockX + 1, fblockY, fblockZ - 2).getTypeId() == 0 && world.getBlockAt(fblockX, fblockY, fblockZ - 2).getTypeId() == doorBlock && world.getBlockAt(fblockX + 1, fblockY, fblockZ - 2).getTypeId() == 0) {
                world.getBlockAt(fblockX + 1, fblockY, fblockZ - 2).setTypeId(doorBlock);
                world.getBlockAt(fblockX, fblockY, fblockZ - 2).setTypeId(0);
                world.getBlockAt(fblockX, fblockY - 1, fblockZ - 2).setTypeId(0);
                doorReset = true;
            }
        }
        if (fo.facing == 4) {
            if (doorBlock == 44 || doorBlock == 43) {
                if (world.getBlockAt(fblockX + 2, fblockY, fblockZ + 1).getTypeId() == 0 && world.getBlockAt(fblockX + 2, fblockY, fblockZ).getTypeId() == doorBlock && world.getBlockAt(fblockX + 2, fblockY, fblockZ).getData() == doorBlockData) {
                    world.getBlockAt(fblockX + 2, fblockY, fblockZ + 1).setTypeIdAndData(doorBlock, (byte)doorBlockData, true);
                    world.getBlockAt(fblockX + 2, fblockY, fblockZ).setTypeId(0);
                    world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ).setTypeId(0);
                    doorReset = true;
                }
            }
            else if (world.getBlockAt(fblockX + 2, fblockY, fblockZ + 1).getTypeId() == 0 && world.getBlockAt(fblockX + 2, fblockY, fblockZ).getTypeId() == doorBlock && world.getBlockAt(fblockX + 2, fblockY, fblockZ + 1).getTypeId() == 0) {
                world.getBlockAt(fblockX + 2, fblockY, fblockZ + 1).setTypeId(doorBlock);
                world.getBlockAt(fblockX + 2, fblockY, fblockZ).setTypeId(0);
                world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ).setTypeId(0);
                doorReset = true;
            }
        }
        if (fo.facing == 5) {
            if (doorBlock == 44 || doorBlock == 43) {
                if (world.getBlockAt(fblockX - 2, fblockY, fblockZ - 1).getTypeId() == 0 && world.getBlockAt(fblockX - 2, fblockY, fblockZ).getTypeId() == doorBlock && world.getBlockAt(fblockX - 2, fblockY, fblockZ).getData() == doorBlockData) {
                    world.getBlockAt(fblockX - 2, fblockY, fblockZ - 1).setTypeIdAndData(doorBlock, (byte)doorBlockData, true);
                    world.getBlockAt(fblockX - 2, fblockY, fblockZ).setTypeId(0);
                    world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ).setTypeId(0);
                    doorReset = true;
                }
            }
            else if (world.getBlockAt(fblockX - 2, fblockY, fblockZ - 1).getTypeId() == 0 && world.getBlockAt(fblockX - 2, fblockY, fblockZ).getTypeId() == doorBlock && world.getBlockAt(fblockX - 2, fblockY, fblockZ - 1).getTypeId() == 0) {
                world.getBlockAt(fblockX - 2, fblockY, fblockZ - 1).setTypeId(doorBlock);
                world.getBlockAt(fblockX - 2, fblockY, fblockZ).setTypeId(0);
                world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ).setTypeId(0);
                doorReset = true;
            }
        }
        this.resetFurnace(fo);
        return doorReset;
    }
    
    private boolean isFurnaceBelt(final World world, final int blockX, final int blockY, final int blockZ, final byte blockData) {
        final int beltBlock = this.dataWriter.getBeltBlocks();
        final int beltBlockData = this.dataWriter.getBeltBlockData();
        if (beltBlock != 0) {
            if (blockData == 2) {
                this.dataWriter.setFdFacing(2);
                if (this.isStairBlock(beltBlock)) {
                    if (world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ).getData() == 2 && world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ).getData() == 2 && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getData() == 0 && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getData() == 1 && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getData() == 0 && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getData() == 1 && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 3).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 3).getData() == 0 && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 3).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 3).getData() == 1 && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 4).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 4).getData() == 3 && world.getBlockAt(blockX, blockY - 1, blockZ + 4).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ + 4).getData() == 3 && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 4).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 4).getData() == 3) {
                        this.dataWriter.setFurnaceDetectionbl(true);
                        return true;
                    }
                }
                else if (world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ).getData() == beltBlockData && world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ).getData() == beltBlockData && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getData() == beltBlockData && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getData() == beltBlockData && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getData() == beltBlockData && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getData() == beltBlockData && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 3).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 3).getData() == beltBlockData && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 3).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 3).getData() == beltBlockData && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 4).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 4).getData() == beltBlockData && world.getBlockAt(blockX, blockY - 1, blockZ + 4).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ + 4).getData() == beltBlockData && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 4).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 4).getData() == beltBlockData) {
                    this.dataWriter.setFurnaceDetectionbl(true);
                    return true;
                }
            }
            if (blockData == 3) {
                this.dataWriter.setFdFacing(3);
                if (this.isStairBlock(beltBlock)) {
                    if (world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ).getData() == 3 && world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ).getData() == 3 && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getData() == 1 && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getData() == 0 && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getData() == 1 && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getData() == 0 && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 3).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 3).getData() == 1 && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 3).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 3).getData() == 0 && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 4).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 4).getData() == 2 && world.getBlockAt(blockX, blockY - 1, blockZ - 4).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ - 4).getData() == 2 && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 4).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 4).getData() == 2) {
                        this.dataWriter.setFurnaceDetectionbl(true);
                        return true;
                    }
                }
                else if (world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ).getData() == beltBlockData && world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ).getData() == beltBlockData && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getData() == beltBlockData && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getData() == beltBlockData && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getData() == beltBlockData && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getData() == beltBlockData && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 3).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 3).getData() == beltBlockData && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 3).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 3).getData() == beltBlockData && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 4).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 4).getData() == beltBlockData && world.getBlockAt(blockX, blockY - 1, blockZ - 4).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ - 4).getData() == beltBlockData && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 4).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 4).getData() == beltBlockData) {
                    this.dataWriter.setFurnaceDetectionbl(true);
                    return true;
                }
            }
            if (blockData == 4) {
                this.dataWriter.setFdFacing(4);
                if (beltBlock == 53 || beltBlock == 67 || beltBlock == 108 || beltBlock == 109 || beltBlock == 114) {
                    if (world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ + 1).getData() == 0 && world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ - 1).getData() == 0 && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getData() == 3 && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getData() == 2 && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getData() == 3 && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getData() == 2 && world.getBlockAt(blockX + 3, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 3, blockY - 1, blockZ + 2).getData() == 3 && world.getBlockAt(blockX + 3, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 3, blockY - 1, blockZ - 2).getData() == 2 && world.getBlockAt(blockX + 4, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX + 4, blockY - 1, blockZ + 1).getData() == 1 && world.getBlockAt(blockX + 4, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX + 4, blockY - 1, blockZ).getData() == 1 && world.getBlockAt(blockX + 4, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX + 4, blockY - 1, blockZ - 1).getData() == 1) {
                        this.dataWriter.setFurnaceDetectionbl(true);
                        return true;
                    }
                }
                else if (world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ + 1).getData() == beltBlockData && world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ - 1).getData() == beltBlockData && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getData() == beltBlockData && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getData() == beltBlockData && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getData() == beltBlockData && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getData() == beltBlockData && world.getBlockAt(blockX + 3, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 3, blockY - 1, blockZ + 2).getData() == beltBlockData && world.getBlockAt(blockX + 3, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX + 3, blockY - 1, blockZ - 2).getData() == beltBlockData && world.getBlockAt(blockX + 4, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX + 4, blockY - 1, blockZ + 1).getData() == beltBlockData && world.getBlockAt(blockX + 4, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX + 4, blockY - 1, blockZ).getData() == beltBlockData && world.getBlockAt(blockX + 4, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX + 4, blockY - 1, blockZ - 1).getData() == beltBlockData) {
                    this.dataWriter.setFurnaceDetectionbl(true);
                    return true;
                }
            }
            if (blockData == 5) {
                this.dataWriter.setFdFacing(5);
                if (this.isStairBlock(beltBlock)) {
                    if (world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ - 1).getData() == 1 && world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ + 1).getData() == 1 && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getData() == 2 && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getData() == 3 && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getData() == 2 && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getData() == 3 && world.getBlockAt(blockX - 3, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 3, blockY - 1, blockZ - 2).getData() == 2 && world.getBlockAt(blockX - 3, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 3, blockY - 1, blockZ + 2).getData() == 3 && world.getBlockAt(blockX - 4, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX - 4, blockY - 1, blockZ - 1).getData() == 0 && world.getBlockAt(blockX - 4, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX - 4, blockY - 1, blockZ).getData() == 0 && world.getBlockAt(blockX - 4, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX - 4, blockY - 1, blockZ + 1).getData() == 0) {
                        this.dataWriter.setFurnaceDetectionbl(true);
                        return true;
                    }
                }
                else if (world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ - 1).getData() == beltBlockData && world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX, blockY - 1, blockZ + 1).getData() == beltBlockData && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getData() == beltBlockData && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getData() == beltBlockData && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getData() == beltBlockData && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getData() == beltBlockData && world.getBlockAt(blockX - 3, blockY - 1, blockZ - 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 3, blockY - 1, blockZ - 2).getData() == beltBlockData && world.getBlockAt(blockX - 3, blockY - 1, blockZ + 2).getTypeId() == beltBlock && world.getBlockAt(blockX - 3, blockY - 1, blockZ + 2).getData() == beltBlockData && world.getBlockAt(blockX - 4, blockY - 1, blockZ - 1).getTypeId() == beltBlock && world.getBlockAt(blockX - 4, blockY - 1, blockZ - 1).getData() == beltBlockData && world.getBlockAt(blockX - 4, blockY - 1, blockZ).getTypeId() == beltBlock && world.getBlockAt(blockX - 4, blockY - 1, blockZ).getData() == beltBlockData && world.getBlockAt(blockX - 4, blockY - 1, blockZ + 1).getTypeId() == beltBlock && world.getBlockAt(blockX - 4, blockY - 1, blockZ + 1).getData() == beltBlockData) {
                    this.dataWriter.setFurnaceDetectionbl(true);
                    return true;
                }
            }
            return false;
        }
        this.dataWriter.setFurnaceDetectionbl(true);
        return true;
    }
    
    public boolean isFurnaceUpper(final FurnaceObject fo, final int blockX, final int blockY, final int blockZ) {
        final int facing = fo.facing;
        if (facing == 2) {
            final int start_X = fo.X + 1;
            final int start_Z = fo.Z + 3;
            for (int x = start_X; x > start_X - 3; --x) {
                for (int z = start_Z; z > start_Z - 3; --z) {
                    if (blockX == x && blockY == fo.Y && blockZ == z) {
                        return true;
                    }
                }
            }
        }
        else if (facing == 3) {
            final int start_X = fo.X - 1;
            final int start_Z = fo.Z - 3;
            for (int x = start_X; x < start_X + 3; ++x) {
                for (int z = start_Z; z < start_Z + 3; ++z) {
                    if (blockX == x && blockY == fo.Y && blockZ == z) {
                        return true;
                    }
                }
            }
        }
        else if (facing == 4) {
            final int start_X = fo.X + 3;
            final int start_Z = fo.Z + 1;
            for (int x = start_X; x > start_X - 3; --x) {
                for (int z = start_Z; z > start_Z - 3; --z) {
                    if (blockX == x && blockY == fo.Y && blockZ == z) {
                        return true;
                    }
                }
            }
        }
        else if (facing == 5) {
            final int start_X = fo.X - 3;
            final int start_Z = fo.Z - 1;
            for (int x = start_X; x < start_X + 3; ++x) {
                for (int z = start_Z; z < start_Z + 3; ++z) {
                    if (blockX == x && blockY == fo.Y && blockZ == z) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean detectFurnaceByBlock(final FurnaceObject fo, final int blockX, final int blockY, final int blockZ) {
        final int fblockX = fo.X;
        final int fblockY = fo.Y;
        final int fblockZ = fo.Z;
        boolean layerOne = false;
        boolean layerTwo = false;
        boolean layerThree = false;
        if (fo.facing == 2) {
            if ((fblockX - 1 == blockX && fblockY == blockY && fblockZ + 1 == blockZ) || (fblockX == blockX && fblockY == blockY && fblockZ + 1 == blockZ) || (fblockX + 1 == blockX && fblockY == blockY && fblockZ + 1 == blockZ) || (fblockX - 1 == blockX && fblockY == blockY && fblockZ + 2 == blockZ) || (fblockX == blockX && fblockY == blockY && fblockZ + 2 == blockZ && fo.power != 0) || (fblockX - 1 == blockX && fblockY == blockY && fblockZ + 3 == blockZ) || (fblockX == blockX && fblockY == blockY && fblockZ + 3 == blockZ) || (fblockX + 1 == blockX && fblockY == blockY && fblockZ + 3 == blockZ)) {
                layerOne = true;
            }
            if ((fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ + 3 == blockZ) || (fblockX == blockX && fblockY - 1 == blockY && fblockZ + 3 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ + 3 == blockZ)) {
                layerTwo = true;
            }
            if (this.dataWriter.getBeltBlocks() != 0 && ((fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ + 3 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ + 3 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ + 4 == blockZ) || (fblockX == blockX && fblockY - 1 == blockY && fblockZ + 4 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ + 4 == blockZ))) {
                layerTwo = true;
            }
            if ((fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ) || (fblockX == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ) || (fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ) || (fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ + 2 == blockZ) || (fblockX == blockX && fblockY - 2 == blockY && fblockZ + 2 == blockZ) || (fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ + 2 == blockZ) || (fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ + 3 == blockZ) || (fblockX == blockX && fblockY - 2 == blockY && fblockZ + 3 == blockZ) || (fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ + 3 == blockZ)) {
                layerThree = true;
            }
            if (!layerOne && !layerTwo) {}
        }
        else if (fo.facing == 3) {
            if ((fblockX + 1 == blockX && fblockY == blockY && fblockZ - 1 == blockZ) || (fblockX == blockX && fblockY == blockY && fblockZ - 1 == blockZ) || (fblockX - 1 == blockX && fblockY == blockY && fblockZ - 1 == blockZ) || (fblockX + 1 == blockX && fblockY == blockY && fblockZ - 2 == blockZ) || (fblockX == blockX && fblockY == blockY && fblockZ - 2 == blockZ && fo.power != 0) || (fblockX + 1 == blockX && fblockY == blockY && fblockZ - 3 == blockZ) || (fblockX == blockX && fblockY == blockY && fblockZ - 3 == blockZ) || (fblockX - 1 == blockX && fblockY == blockY && fblockZ - 3 == blockZ)) {
                layerOne = true;
            }
            if ((fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ - 3 == blockZ) || (fblockX == blockX && fblockY - 1 == blockY && fblockZ - 3 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ - 3 == blockZ)) {
                layerTwo = true;
            }
            if (this.dataWriter.getBeltBlocks() != 0 && ((fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ - 3 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ - 3 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ - 4 == blockZ) || (fblockX == blockX && fblockY - 1 == blockY && fblockZ - 4 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ - 4 == blockZ))) {
                layerTwo = true;
            }
            if ((fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ) || (fblockX == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ) || (fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ) || (fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ - 2 == blockZ) || (fblockX == blockX && fblockY - 2 == blockY && fblockZ - 2 == blockZ) || (fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ - 2 == blockZ) || (fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ - 3 == blockZ) || (fblockX == blockX && fblockY - 2 == blockY && fblockZ - 3 == blockZ) || (fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ - 3 == blockZ)) {
                layerThree = true;
            }
            if (!layerOne && !layerTwo) {}
        }
        else if (fo.facing == 4) {
            if ((fblockX + 1 == blockX && fblockY == blockY && fblockZ + 1 == blockZ) || (fblockX + 1 == blockX && fblockY == blockY && fblockZ == blockZ) || (fblockX + 1 == blockX && fblockY == blockY && fblockZ - 1 == blockZ) || (fblockX + 2 == blockX && fblockY == blockY && fblockZ + 1 == blockZ) || (fblockX + 2 == blockX && fblockY == blockY && fblockZ == blockZ && fo.power != 0) || (fblockX + 3 == blockX && fblockY == blockY && fblockZ + 1 == blockZ) || (fblockX + 3 == blockX && fblockY == blockY && fblockZ == blockZ) || (fblockX + 3 == blockX && fblockY == blockY && fblockZ - 1 == blockZ)) {
                layerOne = true;
            }
            if ((fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX + 3 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX + 3 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX + 3 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ)) {
                layerTwo = true;
            }
            if (this.dataWriter.getBeltBlocks() != 0 && ((fblockX == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX + 1 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX + 2 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX + 3 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX + 3 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX + 4 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX + 4 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX + 4 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ))) {
                layerTwo = true;
            }
            if ((fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ) || (fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ == blockZ) || (fblockX + 1 == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ) || (fblockX + 2 == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ) || (fblockX + 2 == blockX && fblockY - 2 == blockY && fblockZ == blockZ) || (fblockX + 2 == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ) || (fblockX + 3 == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ) || (fblockX + 3 == blockX && fblockY - 2 == blockY && fblockZ == blockZ) || (fblockX + 3 == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ)) {
                layerThree = true;
            }
            if (!layerOne && !layerTwo) {}
        }
        else if (fo.facing == 5) {
            if ((fblockX - 1 == blockX && fblockY == blockY && fblockZ - 1 == blockZ) || (fblockX - 1 == blockX && fblockY == blockY && fblockZ == blockZ) || (fblockX - 1 == blockX && fblockY == blockY && fblockZ + 1 == blockZ) || (fblockX - 2 == blockX && fblockY == blockY && fblockZ - 1 == blockZ) || (fblockX - 2 == blockX && fblockY == blockY && fblockZ == blockZ && fo.power != 0) || (fblockX - 3 == blockX && fblockY == blockY && fblockZ - 1 == blockZ) || (fblockX - 3 == blockX && fblockY == blockY && fblockZ == blockZ) || (fblockX - 3 == blockX && fblockY == blockY && fblockZ + 1 == blockZ)) {
                layerOne = true;
            }
            if ((fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX - 3 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX - 3 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX - 3 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ)) {
                layerTwo = true;
            }
            if (this.dataWriter.getBeltBlocks() != 0 && ((fblockX == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX - 1 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX - 2 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX - 3 == blockX && fblockY - 1 == blockY && fblockZ - 2 == blockZ) || (fblockX - 3 == blockX && fblockY - 1 == blockY && fblockZ + 2 == blockZ) || (fblockX - 4 == blockX && fblockY - 1 == blockY && fblockZ - 1 == blockZ) || (fblockX - 4 == blockX && fblockY - 1 == blockY && fblockZ == blockZ) || (fblockX - 4 == blockX && fblockY - 1 == blockY && fblockZ + 1 == blockZ))) {
                layerTwo = true;
            }
            if ((fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ) || (fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ == blockZ) || (fblockX - 1 == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ) || (fblockX - 2 == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ) || (fblockX - 2 == blockX && fblockY - 2 == blockY && fblockZ == blockZ) || (fblockX - 2 == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ) || (fblockX - 3 == blockX && fblockY - 2 == blockY && fblockZ - 1 == blockZ) || (fblockX - 3 == blockX && fblockY - 2 == blockY && fblockZ == blockZ) || (fblockX - 3 == blockX && fblockY - 2 == blockY && fblockZ + 1 == blockZ)) {
                layerThree = true;
            }
            if (layerOne || !layerTwo) {}
        }
        if (this.dataWriter.getLFDebug() == 3) {
            LavaFurnace.LOGGER.info("dfbb Method -> facing=" + fo.facing + " layer1=" + layerOne + " layer2=" + layerTwo + " layer3=" + layerThree);
        }
        return layerOne || layerTwo || layerThree;
    }
    
    public void furnaceBornFX(final FurnaceObject fo) {
        final int fblockX = fo.X;
        final int fblockY = fo.Y;
        final int fblockZ = fo.Z;
        final World world = this.plugin.getWorld(fo.world);
        if (fo.facing == 2) {
            final Location l = world.getBlockAt(fblockX, fblockY - 1, fblockZ + 2).getLocation();
            l.getWorld().strikeLightningEffect(l);
        }
        else if (fo.facing == 3) {
            final Location l = world.getBlockAt(fblockX, fblockY - 1, fblockZ - 2).getLocation();
            l.getWorld().strikeLightningEffect(l);
        }
        else if (fo.facing == 4) {
            final Location l = world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ).getLocation();
            l.getWorld().strikeLightningEffect(l);
        }
        else if (fo.facing == 5) {
            final Location l = world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ).getLocation();
            l.getWorld().strikeLightningEffect(l);
        }
    }
    
    public Furnace getFurnace(final FurnaceObject fo) {
        if (fo.world != null && fo.world.length() != 0) {
            final int fblockX = fo.X;
            final int fblockY = fo.Y;
            final int fblockZ = fo.Z;
            final World world = this.plugin.getWorld(fo.world);
            if (fo.facing == 2) {
                final int typeId = world.getBlockAt(fblockX, fblockY - 1, fblockZ + 1).getTypeId();
                if (typeId == 61 || typeId == 62) {
                    return (Furnace)world.getBlockAt(fblockX, fblockY - 1, fblockZ + 1).getState();
                }
            }
            else if (fo.facing == 3) {
                final int typeId = world.getBlockAt(fblockX, fblockY - 1, fblockZ - 1).getTypeId();
                if (typeId == 61 || typeId == 62) {
                    return (Furnace)world.getBlockAt(fblockX, fblockY - 1, fblockZ - 1).getState();
                }
            }
            else if (fo.facing == 4) {
                final int typeId = world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ).getTypeId();
                if (typeId == 61 || typeId == 62) {
                    return (Furnace)world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ).getState();
                }
            }
            else if (fo.facing == 5) {
                final int typeId = world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ).getTypeId();
                if (typeId == 61 || typeId == 62) {
                    return (Furnace)world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ).getState();
                }
            }
        }
        return null;
    }
    
    private void resetFurnace(final FurnaceObject fo) {
        if (fo.world != null && fo.world.length() != 0) {
            final int fblockX = fo.X;
            final int fblockY = fo.Y;
            final int fblockZ = fo.Z;
            final World world = this.plugin.getWorld(fo.world);
            if (fo.facing == 2) {
                if (world.getBlockAt(fblockX, fblockY - 1, fblockZ + 1).getTypeId() == 62) {
                    final Furnace cf = (Furnace)world.getBlockAt(fblockX, fblockY - 1, fblockZ + 1).getState();
                    if (cf.getBurnTime() >= 0) {
                        cf.setBurnTime((short)1);
                    }
                }
                if (world.getBlockAt(fblockX, fblockY - 1, fblockZ + 2).getTypeId() == 10 || world.getBlockAt(fblockX, fblockY - 1, fblockZ + 2).getTypeId() == 11) {
                    world.getBlockAt(fblockX, fblockY - 1, fblockZ + 2).setTypeId(0);
                }
            }
            else if (fo.facing == 3) {
                if (world.getBlockAt(fblockX, fblockY - 1, fblockZ - 1).getTypeId() == 62) {
                    final Furnace cf = (Furnace)world.getBlockAt(fblockX, fblockY - 1, fblockZ - 1).getState();
                    if (cf.getBurnTime() >= 0) {
                        cf.setBurnTime((short)1);
                    }
                }
                if (world.getBlockAt(fblockX, fblockY - 1, fblockZ - 2).getTypeId() == 10 || world.getBlockAt(fblockX, fblockY - 1, fblockZ - 2).getTypeId() == 11) {
                    world.getBlockAt(fblockX, fblockY - 1, fblockZ - 2).setTypeId(0);
                }
            }
            else if (fo.facing == 4) {
                if (world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ).getTypeId() == 62) {
                    final Furnace cf = (Furnace)world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ).getState();
                    if (cf.getBurnTime() >= 0) {
                        cf.setBurnTime((short)1);
                    }
                }
                if (world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ).getTypeId() == 10 || world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ).getTypeId() == 11) {
                    world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ).setTypeId(0);
                }
            }
            else if (fo.facing == 5) {
                if (world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ).getTypeId() == 62) {
                    final Furnace cf = (Furnace)world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ).getState();
                    if (cf.getBurnTime() >= 0) {
                        cf.setBurnTime((short)1);
                    }
                }
                if (world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ).getTypeId() == 10 || world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ).getTypeId() == 11) {
                    world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ).setTypeId(0);
                }
            }
        }
    }
    
    public boolean getChunkLoaded(final FurnaceObject fo) {
        if (fo.world != null && fo.world.length() != 0) {
            final World world = this.plugin.getWorld(fo.world);
            final int fblockX = fo.X;
            final int fblockY = fo.Y;
            final int fblockZ = fo.Z;
            final int facing = fo.facing;
            Chunk chunkOne = null;
            Chunk chunkTwo = null;
            Chunk chunkThree = null;
            Chunk chunkFour = null;
            Chunk chunkFive = null;
            Chunk chunkSix = null;
            Chunk chunkSeven = null;
            Chunk chunkEight = null;
            boolean lockOne = false;
            boolean lockTwo = false;
            boolean lockThree = false;
            boolean lockFour = false;
            boolean lockFive = false;
            boolean lockSix = false;
            boolean lockSeven = false;
            boolean lockEight = false;
            if (facing == 2) {
                chunkOne = world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ).getChunk();
                chunkTwo = world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ).getChunk();
                chunkThree = world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ + 1).getChunk();
                chunkFour = world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ + 1).getChunk();
                chunkFive = world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ + 3).getChunk();
                chunkSix = world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ + 3).getChunk();
                chunkSeven = world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ + 4).getChunk();
                chunkEight = world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ + 4).getChunk();
            }
            else if (facing == 3) {
                chunkOne = world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ).getChunk();
                chunkTwo = world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ).getChunk();
                chunkThree = world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ - 1).getChunk();
                chunkFour = world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ - 1).getChunk();
                chunkFive = world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ - 3).getChunk();
                chunkSix = world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ - 3).getChunk();
                chunkSeven = world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ - 4).getChunk();
                chunkEight = world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ - 4).getChunk();
            }
            else if (facing == 4) {
                chunkOne = world.getBlockAt(fblockX, fblockY - 1, fblockZ - 1).getChunk();
                chunkTwo = world.getBlockAt(fblockX, fblockY - 1, fblockZ + 1).getChunk();
                chunkThree = world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ - 2).getChunk();
                chunkFour = world.getBlockAt(fblockX + 1, fblockY - 1, fblockZ + 2).getChunk();
                chunkFive = world.getBlockAt(fblockX + 3, fblockY - 1, fblockZ - 2).getChunk();
                chunkSix = world.getBlockAt(fblockX + 3, fblockY - 1, fblockZ + 2).getChunk();
                chunkSeven = world.getBlockAt(fblockX + 4, fblockY - 1, fblockZ - 1).getChunk();
                chunkEight = world.getBlockAt(fblockX + 4, fblockY - 1, fblockZ + 1).getChunk();
            }
            else if (facing == 5) {
                chunkOne = world.getBlockAt(fblockX, fblockY - 1, fblockZ - 1).getChunk();
                chunkTwo = world.getBlockAt(fblockX, fblockY - 1, fblockZ + 1).getChunk();
                chunkThree = world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ - 2).getChunk();
                chunkFour = world.getBlockAt(fblockX - 1, fblockY - 1, fblockZ + 2).getChunk();
                chunkFive = world.getBlockAt(fblockX - 3, fblockY - 1, fblockZ - 2).getChunk();
                chunkSix = world.getBlockAt(fblockX - 3, fblockY - 1, fblockZ + 2).getChunk();
                chunkSeven = world.getBlockAt(fblockX - 4, fblockY - 1, fblockZ - 1).getChunk();
                chunkEight = world.getBlockAt(fblockX - 4, fblockY - 1, fblockZ + 1).getChunk();
            }
            if (world.isChunkLoaded(chunkOne)) {
                lockOne = true;
            }
            if (world.isChunkLoaded(chunkTwo)) {
                lockTwo = true;
            }
            if (world.isChunkLoaded(chunkThree)) {
                lockThree = true;
            }
            if (world.isChunkLoaded(chunkFour)) {
                lockFour = true;
            }
            if (world.isChunkLoaded(chunkFive)) {
                lockFive = true;
            }
            if (world.isChunkLoaded(chunkSix)) {
                lockSix = true;
            }
            if (world.isChunkLoaded(chunkSeven)) {
                lockSeven = true;
            }
            if (world.isChunkLoaded(chunkEight)) {
                lockEight = true;
            }
            return lockOne && lockTwo && lockThree && lockFour && lockFive && lockSix && lockSeven && lockEight;
        }
        return false;
    }
    
    public boolean isValidLayerOneBlocks(final int typeId, final int dataId) {
        return typeId == 1 || typeId == 4 || typeId == 7 || typeId == 14 || typeId == 15 || typeId == 16 || typeId == 20 || typeId == 21 || typeId == 22 || typeId == 24 || typeId == 41 || typeId == 42 || (typeId == 43 && (dataId == 0 || dataId == 1 || dataId == 3 || dataId == 4 || dataId == 5 || dataId == 6 || dataId == 7 || dataId == 8 || dataId == 9 || dataId == 15)) || (typeId == 44 && (dataId == 0 || dataId == 1 || dataId == 3 || dataId == 4 || dataId == 5 || dataId == 6 || dataId == 7 || dataId == 8 || dataId == 9 || dataId == 15)) || typeId == 45 || typeId == 48 || typeId == 49 || typeId == 56 || typeId == 57 || typeId == 82 || typeId == 88 || typeId == 89 || (typeId == 98 && (dataId == 0 || dataId == 1 || dataId == 2 || dataId == 3)) || typeId == 112 || typeId == 121 || typeId == 129 || typeId == 133 || typeId == 153 || typeId == 155 || typeId == 159 || typeId == 172;
    }
    
    public boolean isValidLayerTwoThreeBlocks(final int typeId, final int dataId) {
        return typeId == 1 || typeId == 4 || typeId == 7 || typeId == 14 || typeId == 15 || typeId == 16 || typeId == 20 || typeId == 21 || typeId == 22 || typeId == 24 || typeId == 41 || typeId == 42 || (typeId == 43 && (dataId == 0 || dataId == 1 || dataId == 3 || dataId == 4 || dataId == 5 || dataId == 6 || dataId == 7 || dataId == 8 || dataId == 9 || dataId == 15)) || typeId == 45 || typeId == 48 || typeId == 49 || typeId == 56 || typeId == 57 || typeId == 82 || typeId == 88 || typeId == 89 || (typeId == 98 && (dataId == 0 || dataId == 1 || dataId == 2 || dataId == 3)) || typeId == 112 || typeId == 121 || typeId == 129 || typeId == 133 || typeId == 153 || typeId == 155 || typeId == 159 || typeId == 172;
    }
    
    private boolean isStairBlock(final int typeId) {
        return typeId == 53 || typeId == 67 || typeId == 108 || typeId == 109 || typeId == 114 || typeId == 128 || typeId == 156;
    }
    
    public boolean isValidBeltBlocks(final int typeId) {
        return typeId == 0 || typeId == 44 || typeId == 53 || typeId == 67 || typeId == 108 || typeId == 109 || typeId == 114 || typeId == 128 || typeId == 156;
    }
    
    private void furnaceGovernor(final FurnaceObject fo) {
        final Furnace cf = this.getFurnace(fo);
        if (cf == null) {
            return;
        }
        if (fo.power > 32768) {
            final int t = cf.getBurnTime() - 32767;
            fo.power -= Math.abs(t);
            cf.setBurnTime((short)32767);
        }
        else {
            fo.power = cf.getBurnTime();
        }
    }
    
    private void updateSign(final FurnaceObject fo) {
        final Sign sign = (Sign)new Location(this.plugin.getWorld(fo.world), (double)fo.X, (double)fo.Y, (double)fo.Z).getBlock().getState();
        if (sign instanceof Sign) {
            sign.setLine(3, this.getPowerString(fo.power));
            final BlockState sgn = sign.getBlock().getState();
            sgn.update(true);
            sign.update(true);
        }
    }
    
    private String getPowerString(final int power) {
        final StringBuffer powerString = new StringBuffer();
        if (power > 0 && this.dataWriter.isShowFuelLevel()) {
            final double percent = power / this.dataWriter.getFurnaceTimer() * 100.0;
            powerString.append("§e" + (int)percent + "% Lava");
        }
        return powerString.toString();
    }
    
    public Block getFurnaceCenter(final FurnaceObject fo) {
        if (fo.world != null && fo.world.length() != 0) {
            final int fblockX = fo.X;
            final int fblockY = fo.Y;
            final int fblockZ = fo.Z;
            Block block = null;
            final World world = this.plugin.getWorld(fo.world);
            if (fo.facing == 2) {
                block = world.getBlockAt(fblockX, fblockY - 1, fblockZ + 2);
            }
            else if (fo.facing == 3) {
                block = world.getBlockAt(fblockX, fblockY - 1, fblockZ - 2);
            }
            else if (fo.facing == 4) {
                block = world.getBlockAt(fblockX + 2, fblockY - 1, fblockZ);
            }
            else if (fo.facing == 5) {
                block = world.getBlockAt(fblockX - 2, fblockY - 1, fblockZ);
            }
            return block;
        }
        return null;
    }
    
    public boolean initFurnace(final FurnaceObject fo, final World world) {
        final String worldName = world.getName();
        final int facing = fo.facing;
        final int doorBlock = this.dataWriter.getDoorBlock();
        final int doorBlockData = this.dataWriter.getDoorBlockData();
        if (facing == 2 && worldName.equals(fo.world)) {
            final Block block = world.getBlockAt(fo.X, fo.Y - 1, fo.Z + 1);
            Furnace furnace = (Furnace)block.getState();
            final HashMap<Block, ItemStack[]> furnaceInventory = new HashMap<Block, ItemStack[]>();
            furnaceInventory.put(block, furnace.getInventory().getContents());
            furnace.getInventory().clear();
            world.getBlockAt(fo.X - 1, fo.Y, fo.Z + 2).setTypeId(0);
            if (doorBlock == 44 || doorBlock == 43) {
                world.getBlockAt(fo.X, fo.Y, fo.Z + 2).setTypeIdAndData(doorBlock, (byte)doorBlockData, true);
            }
            else {
                world.getBlockAt(fo.X, fo.Y, fo.Z + 2).setTypeId(doorBlock);
            }
            world.getBlockAt(fo.X, fo.Y - 1, fo.Z + 1).setTypeId(62);
            world.getBlockAt(fo.X, fo.Y - 1, fo.Z + 1).setData((byte)2);
            furnace = (Furnace)world.getBlockAt(fo.X, fo.Y - 1, fo.Z + 1).getState();
            furnace.getInventory().setContents((ItemStack[])furnaceInventory.get(furnace.getBlock()));
            fo.furnaceInit = true;
            fo.power = -1;
            this.startLavaFurnace(fo);
            return true;
        }
        if (facing == 3 && worldName.equals(fo.world)) {
            final Block block = world.getBlockAt(fo.X, fo.Y - 1, fo.Z - 1);
            Furnace furnace = (Furnace)block.getState();
            final HashMap<Block, ItemStack[]> furnaceInventory = new HashMap<Block, ItemStack[]>();
            furnaceInventory.put(block, furnace.getInventory().getContents());
            furnace.getInventory().clear();
            world.getBlockAt(fo.X + 1, fo.Y, fo.Z - 2).setTypeId(0);
            if (doorBlock == 44 || doorBlock == 43) {
                world.getBlockAt(fo.X, fo.Y, fo.Z - 2).setTypeIdAndData(doorBlock, (byte)doorBlockData, true);
            }
            else {
                world.getBlockAt(fo.X, fo.Y, fo.Z - 2).setTypeId(doorBlock);
            }
            world.getBlockAt(fo.X, fo.Y - 1, fo.Z - 1).setTypeId(62);
            world.getBlockAt(fo.X, fo.Y - 1, fo.Z - 1).setData((byte)3);
            furnace = (Furnace)world.getBlockAt(fo.X, fo.Y - 1, fo.Z - 1).getState();
            furnace.getInventory().setContents((ItemStack[])furnaceInventory.get(furnace.getBlock()));
            fo.furnaceInit = true;
            fo.power = -1;
            this.startLavaFurnace(fo);
            return true;
        }
        if (facing == 4 && worldName.equals(fo.world)) {
            final Block block = world.getBlockAt(fo.X + 1, fo.Y - 1, fo.Z);
            Furnace furnace = (Furnace)block.getState();
            final HashMap<Block, ItemStack[]> furnaceInventory = new HashMap<Block, ItemStack[]>();
            furnaceInventory.put(block, furnace.getInventory().getContents());
            furnace.getInventory().clear();
            world.getBlockAt(fo.X + 2, fo.Y, fo.Z + 1).setTypeId(0);
            if (doorBlock == 44 || doorBlock == 43) {
                world.getBlockAt(fo.X + 2, fo.Y, fo.Z).setTypeIdAndData(doorBlock, (byte)doorBlockData, true);
            }
            else {
                world.getBlockAt(fo.X + 2, fo.Y, fo.Z).setTypeId(doorBlock);
            }
            world.getBlockAt(fo.X + 1, fo.Y - 1, fo.Z).setTypeId(62);
            world.getBlockAt(fo.X + 1, fo.Y - 1, fo.Z).setData((byte)4);
            furnace = (Furnace)world.getBlockAt(fo.X + 1, fo.Y - 1, fo.Z).getState();
            furnace.getInventory().setContents((ItemStack[])furnaceInventory.get(furnace.getBlock()));
            fo.furnaceInit = true;
            fo.power = -1;
            this.startLavaFurnace(fo);
            return true;
        }
        if (facing == 5 && worldName.equals(fo.world)) {
            final Block block = world.getBlockAt(fo.X - 1, fo.Y - 1, fo.Z);
            Furnace furnace = (Furnace)block.getState();
            final HashMap<Block, ItemStack[]> furnaceInventory = new HashMap<Block, ItemStack[]>();
            furnaceInventory.put(block, furnace.getInventory().getContents());
            furnace.getInventory().clear();
            world.getBlockAt(fo.X - 2, fo.Y, fo.Z - 1).setTypeId(0);
            if (doorBlock == 44 || doorBlock == 43) {
                world.getBlockAt(fo.X - 2, fo.Y, fo.Z).setTypeIdAndData(doorBlock, (byte)doorBlockData, true);
            }
            else {
                world.getBlockAt(fo.X - 2, fo.Y, fo.Z).setTypeId(doorBlock);
            }
            world.getBlockAt(fo.X - 1, fo.Y - 1, fo.Z).setTypeId(62);
            world.getBlockAt(fo.X - 1, fo.Y - 1, fo.Z).setData((byte)5);
            furnace = (Furnace)world.getBlockAt(fo.X - 1, fo.Y - 1, fo.Z).getState();
            furnace.getInventory().setContents((ItemStack[])furnaceInventory.get(furnace.getBlock()));
            fo.furnaceInit = true;
            fo.power = -1;
            this.startLavaFurnace(fo);
            return true;
        }
        return false;
    }
    
    public void startLavaFurnace(final FurnaceObject fo) {
        final Furnace cf = this.getFurnace(fo);
        if (cf == null) {
            return;
        }
        final int blockX = cf.getLocation().getBlockX();
        final int blockY = cf.getLocation().getBlockY();
        final int blockZ = cf.getLocation().getBlockZ();
        final World world = this.plugin.getWorld(fo.world);
        if (!this.dataWriter.isInfiniteTimer() && this.dataWriter.getFurnaceTimer() < 32768) {
            cf.setBurnTime((short)this.dataWriter.getFurnaceTimer());
        }
        else {
            cf.setBurnTime((short)32767);
            fo.power = this.dataWriter.getFurnaceTimer();
        }
        fo.furnaceInit = false;
        this.getFurnaceCenter(fo).setType(Material.STATIONARY_LAVA);
        if (this.isCompatModeNeeded()) {
            final ItemStack item = new ItemStack(327, 1);
            final FurnaceBurnEvent furnaceBurnEvent = new FurnaceBurnEvent(world.getBlockAt(blockX, blockY, blockZ), item, this.dataWriter.getFurnaceTimer());
            this.plugin.getServer().getPluginManager().callEvent((Event)furnaceBurnEvent);
            if (furnaceBurnEvent.isCancelled()) {
                this.resetGlassDoor(fo);
                new RedStoneHelper(this.plugin).resetFurnaceLever(fo);
                return;
            }
        }
        new RedStoneHelper(this.plugin).updateLevers(fo);
    }
    
    public void maintainLavaFurnace(final FurnaceObject fo) {
        final Furnace cf = this.getFurnace(fo);
        if (cf == null) {
            return;
        }
        final int typeId = this.getFurnaceCenter(fo).getTypeId();
        if (!this.isFurnaceEmpty(fo) || !this.plugin.datawriter.isSmartFurnace()) {
            if (this.dataWriter.isInfiniteTimer() && (typeId == 10 || typeId == 11)) {
                cf.setBurnTime((short)32767);
            }
            else if (this.dataWriter.getFurnaceTimer() > 32767 && (typeId == 10 || typeId == 11) && fo.power != -2) {
                this.furnaceGovernor(fo);
            }
            if (fo.power != -2 && this.dataWriter.getFurnaceTimer() < 32768) {
                fo.power = cf.getBurnTime();
            }
            if (cf.getBurnTime() == 0 && fo.power != -2) {
                this.resetGlassDoor(fo);
                fo.power = -2;
            }
            else if (cf.getBurnTime() >= 0 && (typeId == 10 || typeId == 11)) {
                this.setCrucibleFillLevel(fo);
            }
        }
        else if (this.plugin.datawriter.isSmartFurnace()) {
            if (this.dataWriter.isInfiniteTimer() && (typeId == 10 || typeId == 11)) {
                cf.setBurnTime((short)32767);
            }
            else if (this.dataWriter.getFurnaceTimer() > 32767 && (typeId == 10 || typeId == 11)) {
                if (fo.power != -2) {
                    cf.setBurnTime((short)32767);
                }
            }
            else if (fo.power >= 0 && fo.power < 32768) {
                cf.setBurnTime((short)fo.power);
            }
            if (fo.power == -1 && this.dataWriter.getFurnaceTimer() < 32768) {
                fo.power = cf.getBurnTime();
            }
            if (cf.getBurnTime() == 0 && fo.power != -2) {
                this.resetGlassDoor(fo);
                fo.power = -2;
            }
            else if (cf.getBurnTime() >= 0 && (typeId == 10 || typeId == 11)) {
                this.setCrucibleFillLevel(fo);
            }
        }
        new RedStoneHelper(this.plugin).updateLevers(fo);
        this.updateSign(fo);
    }
    
    private boolean isFurnaceEmpty(final FurnaceObject fo) {
        final Furnace furn = this.getFurnace(fo);
        final ChestHelper ch = new ChestHelper(this.plugin, fo);
        final Inventory fInv = ch.getFurnaceInventory();
        final int fSIId = this.getTypeId(fInv.getItem(0));
        return fSIId == 0 || !furn.getChunk().isLoaded();
    }
    
    public boolean isLavaPowered(final FurnaceObject fo) {
        final Block lavaBlock = this.getFurnaceCenter(fo);
        return lavaBlock != null && lavaBlock.isLiquid();
    }
    
    private boolean isCompatModeNeeded() {
        for (final String pName : this.dataWriter.pluginNames) {
            if (this.plugin.getServer().getPluginManager().getPlugin(pName) != null) {
                return true;
            }
        }
        return false;
    }
    
    public int getDamageValue(final ItemStack item) {
        if (item == null) {
            return -1;
        }
        return item.getDurability();
    }
    
    public int getDataValue(final ItemStack item) {
        if (item == null) {
            return -1;
        }
        return item.getData().getData();
    }
    
    public int getTypeId(final ItemStack item) {
        if (item == null) {
            return 0;
        }
        return item.getTypeId();
    }
    
    public int getMaxStackSize(final ItemStack item) {
        if (item == null) {
            return 0;
        }
        return item.getMaxStackSize();
    }
    
    public int getAmount(final ItemStack item) {
        if (item == null) {
            return 0;
        }
        return item.getAmount();
    }
    
    public void debugLevelSix(final World world, final int blockX, final int blockY, final int blockZ, final int facing2) {
        String L1built = null;
        String L2built = null;
        String L3built = null;
        String Bbuilt = null;
        L1built = String.valueOf(world.getBlockAt(blockX, blockY, blockZ).getTypeId()) + " ";
        L3built = (L2built = "");
        if (facing2 == 2) {
            for (int y = 0; y > -3; --y) {
                for (int z = 1; z < 4; ++z) {
                    for (int x = -1; x < 2; ++x) {
                        if (y == 0) {
                            L1built = String.valueOf(L1built) + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getTypeId() + ":" + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getData() + " ";
                        }
                        else if (y == -1) {
                            L2built = String.valueOf(L2built) + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getTypeId() + ":" + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getData() + " ";
                        }
                        else if (y == -2) {
                            L3built = String.valueOf(L3built) + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getTypeId() + ":" + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getData() + " ";
                        }
                    }
                }
            }
            Bbuilt = String.valueOf(world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId()) + ":" + world.getBlockAt(blockX - 1, blockY - 1, blockZ).getData() + " " + world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId() + ":" + world.getBlockAt(blockX + 1, blockY - 1, blockZ).getData() + " " + world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getTypeId() + ":" + world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1).getData() + " " + world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getTypeId() + ":" + world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1).getData() + " " + world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getTypeId() + ":" + world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getData() + " " + world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getTypeId() + ":" + world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getData() + " " + world.getBlockAt(blockX - 2, blockY - 1, blockZ + 3).getTypeId() + ":" + world.getBlockAt(blockX - 2, blockY - 1, blockZ + 3).getData() + " " + world.getBlockAt(blockX + 2, blockY - 1, blockZ + 3).getTypeId() + ":" + world.getBlockAt(blockX + 2, blockY - 1, blockZ + 3).getData() + " " + world.getBlockAt(blockX - 1, blockY - 1, blockZ + 4).getTypeId() + ":" + world.getBlockAt(blockX - 1, blockY - 1, blockZ + 4).getData() + " " + world.getBlockAt(blockX, blockY - 1, blockZ + 4).getTypeId() + ":" + world.getBlockAt(blockX, blockY - 1, blockZ + 4).getData() + " " + world.getBlockAt(blockX + 1, blockY - 1, blockZ + 4).getTypeId() + ":" + world.getBlockAt(blockX + 1, blockY - 1, blockZ + 4).getData();
        }
        else if (facing2 == 3) {
            for (int y = 0; y > -3; --y) {
                for (int z = -1; z > -4; --z) {
                    for (int x = -1; x < 2; ++x) {
                        if (y == 0) {
                            L1built = String.valueOf(L1built) + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getTypeId() + ":" + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getData() + " ";
                        }
                        else if (y == -1) {
                            L2built = String.valueOf(L2built) + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getTypeId() + ":" + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getData() + " ";
                        }
                        else if (y == -2) {
                            L3built = String.valueOf(L3built) + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getTypeId() + ":" + world.getBlockAt(blockX + x, blockY + y, blockZ + z).getData() + " ";
                        }
                    }
                }
            }
            Bbuilt = String.valueOf(world.getBlockAt(blockX + 1, blockY - 1, blockZ).getTypeId()) + ":" + world.getBlockAt(blockX + 1, blockY - 1, blockZ).getData() + " " + world.getBlockAt(blockX - 1, blockY - 1, blockZ).getTypeId() + ":" + world.getBlockAt(blockX - 1, blockY - 1, blockZ).getData() + " " + world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getTypeId() + ":" + world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1).getData() + " " + world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getTypeId() + ":" + world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1).getData() + " " + world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getTypeId() + ":" + world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getData() + " " + world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getTypeId() + ":" + world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getData() + " " + world.getBlockAt(blockX + 2, blockY - 1, blockZ - 3).getTypeId() + ":" + world.getBlockAt(blockX + 2, blockY - 1, blockZ - 3).getData() + " " + world.getBlockAt(blockX - 2, blockY - 1, blockZ - 3).getTypeId() + ":" + world.getBlockAt(blockX - 2, blockY - 1, blockZ - 3).getData() + " " + world.getBlockAt(blockX + 1, blockY - 1, blockZ - 4).getTypeId() + ":" + world.getBlockAt(blockX + 1, blockY - 1, blockZ - 4).getData() + " " + world.getBlockAt(blockX, blockY - 1, blockZ - 4).getTypeId() + ":" + world.getBlockAt(blockX, blockY - 1, blockZ - 4).getData() + " " + world.getBlockAt(blockX - 1, blockY - 1, blockZ - 4).getTypeId() + ":" + world.getBlockAt(blockX - 1, blockY - 1, blockZ - 4).getData();
        }
        else if (facing2 == 4) {
            for (int y = 0; y > -3; --y) {
                for (int x2 = 1; x2 < 4; ++x2) {
                    for (int z2 = -1; z2 < 2; ++z2) {
                        if (y == 0) {
                            L1built = String.valueOf(L1built) + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getTypeId() + ":" + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getData() + " ";
                        }
                        else if (y == -1) {
                            L2built = String.valueOf(L2built) + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getTypeId() + ":" + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getData() + " ";
                        }
                        else if (y == -2) {
                            L3built = String.valueOf(L3built) + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getTypeId() + ":" + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getData() + " ";
                        }
                    }
                }
            }
            Bbuilt = String.valueOf(world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId()) + ":" + world.getBlockAt(blockX, blockY - 1, blockZ + 1).getData() + " " + world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId() + ":" + world.getBlockAt(blockX, blockY - 1, blockZ - 1).getData() + " " + world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getTypeId() + ":" + world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2).getData() + " " + world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getTypeId() + ":" + world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2).getData() + " " + world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getTypeId() + ":" + world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2).getData() + " " + world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getTypeId() + ":" + world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2).getData() + " " + world.getBlockAt(blockX + 3, blockY - 1, blockZ + 2).getTypeId() + ":" + world.getBlockAt(blockX + 3, blockY - 1, blockZ + 2).getData() + " " + world.getBlockAt(blockX + 3, blockY - 1, blockZ - 2).getTypeId() + ":" + world.getBlockAt(blockX + 3, blockY - 1, blockZ - 2).getData() + " " + world.getBlockAt(blockX + 4, blockY - 1, blockZ + 1).getTypeId() + ":" + world.getBlockAt(blockX + 4, blockY - 1, blockZ + 1).getData() + " " + world.getBlockAt(blockX + 4, blockY - 1, blockZ).getTypeId() + ":" + world.getBlockAt(blockX + 4, blockY - 1, blockZ).getData() + " " + world.getBlockAt(blockX + 4, blockY - 1, blockZ - 1).getTypeId() + ":" + world.getBlockAt(blockX + 4, blockY - 1, blockZ - 1).getData();
        }
        else if (facing2 == 5) {
            for (int y = 0; y > -3; --y) {
                for (int x2 = -1; x2 > -4; --x2) {
                    for (int z2 = -1; z2 < 2; ++z2) {
                        if (y == 0) {
                            L1built = String.valueOf(L1built) + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getTypeId() + ":" + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getData() + " ";
                        }
                        else if (y == -1) {
                            L2built = String.valueOf(L2built) + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getTypeId() + ":" + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getData() + " ";
                        }
                        else if (y == -2) {
                            L3built = String.valueOf(L3built) + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getTypeId() + ":" + world.getBlockAt(blockX + x2, blockY + y, blockZ + z2).getData() + " ";
                        }
                    }
                }
            }
            Bbuilt = String.valueOf(world.getBlockAt(blockX, blockY - 1, blockZ - 1).getTypeId()) + ":" + world.getBlockAt(blockX, blockY - 1, blockZ - 1).getData() + " " + world.getBlockAt(blockX, blockY - 1, blockZ + 1).getTypeId() + ":" + world.getBlockAt(blockX, blockY - 1, blockZ + 1).getData() + " " + world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getTypeId() + ":" + world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2).getData() + " " + world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getTypeId() + ":" + world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2).getData() + " " + world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getTypeId() + ":" + world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2).getData() + " " + world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getTypeId() + ":" + world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2).getData() + " " + world.getBlockAt(blockX - 3, blockY - 1, blockZ - 2).getTypeId() + ":" + world.getBlockAt(blockX - 3, blockY - 1, blockZ - 2).getData() + " " + world.getBlockAt(blockX - 3, blockY - 1, blockZ + 2).getTypeId() + ":" + world.getBlockAt(blockX - 3, blockY - 1, blockZ + 2).getData() + " " + world.getBlockAt(blockX - 4, blockY - 1, blockZ - 1).getTypeId() + ":" + world.getBlockAt(blockX - 4, blockY - 1, blockZ - 1).getData() + " " + world.getBlockAt(blockX - 4, blockY - 1, blockZ).getTypeId() + ":" + world.getBlockAt(blockX - 4, blockY - 1, blockZ).getData() + " " + world.getBlockAt(blockX - 4, blockY - 1, blockZ + 1).getTypeId() + ":" + world.getBlockAt(blockX - 4, blockY - 1, blockZ + 1).getData();
        }
        LavaFurnace.LOGGER.info("***************************");
        LavaFurnace.LOGGER.info("***LavaFurnace Debug L6 ***");
        LavaFurnace.LOGGER.info("***************************");
        LavaFurnace.LOGGER.info("facing= " + facing2);
        LavaFurnace.LOGGER.info("Level 1= " + L1built);
        LavaFurnace.LOGGER.info("Level 2= " + L2built);
        LavaFurnace.LOGGER.info("Belt = " + Bbuilt);
        LavaFurnace.LOGGER.info("Level 3= " + L3built);
        LavaFurnace.LOGGER.info("***************************");
    }
    
    public FurnaceObject findFurnaceFromProductionChest(final String user, final Block chest) {
        final World world = chest.getWorld();
        final int chestX = chest.getLocation().getBlockX();
        final int chestY = chest.getLocation().getBlockY();
        final int chestZ = chest.getLocation().getBlockZ();
        final int facing = chest.getData();
        int furnaceX = chestX;
        final int furnaceY = chestY + 1;
        int furnaceZ = chestZ;
        if (facing == 2) {
            --furnaceX;
            if (world.getBlockAt(furnaceX, chestY, furnaceZ).getType().equals((Object)Material.CHEST)) {
                furnaceX -= 2;
            }
            else {
                --furnaceX;
            }
        }
        else if (facing == 3) {
            ++furnaceX;
            if (world.getBlockAt(furnaceX, chestY, furnaceZ).getType().equals((Object)Material.CHEST)) {
                furnaceX += 2;
            }
            else {
                ++furnaceX;
            }
        }
        else if (facing == 4) {
            ++furnaceZ;
            if (world.getBlockAt(furnaceX, chestY, furnaceZ).getType().equals((Object)Material.CHEST)) {
                furnaceZ += 2;
            }
            else {
                ++furnaceZ;
            }
        }
        else if (facing == 5) {
            --furnaceZ;
            if (world.getBlockAt(furnaceX, chestY, furnaceZ).getType().equals((Object)Material.CHEST)) {
                furnaceZ -= 2;
            }
            else {
                --furnaceZ;
            }
        }
        final int index = this.findFurnace(user, world.getName(), (byte)facing, furnaceX, furnaceY, furnaceZ);
        return (index != -1) ? this.plugin.datawriter.lfObject.get(index) : null;
    }
    
    public Block getDoubleChestBlock(final Block block) {
        final BlockFace[] surchest = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
        BlockFace[] array;
        for (int length = (array = surchest).length, i = 0; i < length; ++i) {
            final BlockFace face = array[i];
            final Block otherHalf = block.getRelative(face);
            if (otherHalf.getType().equals((Object)block.getType())) {
                return otherHalf;
            }
        }
        return block;
    }
    
    public FurnaceObject chestPlacedFurnaceCheck(final Block chest) {
        final Block northNorth = chest.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getRelative(BlockFace.NORTH);
        final Block southSouth = chest.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getRelative(BlockFace.SOUTH);
        final Block westWest = chest.getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getRelative(BlockFace.WEST);
        final Block eastEast = chest.getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getRelative(BlockFace.EAST);
        FurnaceObject fo = this.dataWriter.furnaceBlockMap.get(northNorth);
        if (fo != null && northNorth.getType().equals((Object)Material.WALL_SIGN)) {
            return fo;
        }
        fo = this.dataWriter.furnaceBlockMap.get(southSouth);
        if (fo != null && southSouth.getType().equals((Object)Material.WALL_SIGN)) {
            return fo;
        }
        fo = this.dataWriter.furnaceBlockMap.get(westWest);
        if (fo != null && westWest.getType().equals((Object)Material.WALL_SIGN)) {
            return fo;
        }
        fo = this.dataWriter.furnaceBlockMap.get(eastEast);
        if (fo != null && eastEast.getType().equals((Object)Material.WALL_SIGN)) {
            return fo;
        }
        if (this.dataWriter.getBeltBlocks() == 0) {
            final Block north = chest.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH);
            final Block south = chest.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH);
            final Block east = chest.getRelative(BlockFace.UP).getRelative(BlockFace.EAST);
            final Block west = chest.getRelative(BlockFace.UP).getRelative(BlockFace.WEST);
            fo = this.dataWriter.furnaceBlockMap.get(north);
            if (fo != null && north.getType().equals((Object)Material.WALL_SIGN)) {
                return fo;
            }
            fo = this.dataWriter.furnaceBlockMap.get(south);
            if (fo != null && south.getType().equals((Object)Material.WALL_SIGN)) {
                return fo;
            }
            fo = this.dataWriter.furnaceBlockMap.get(east);
            if (fo != null && east.getType().equals((Object)Material.WALL_SIGN)) {
                return fo;
            }
            fo = this.dataWriter.furnaceBlockMap.get(west);
            if (fo != null && west.getType().equals((Object)Material.WALL_SIGN)) {
                return fo;
            }
        }
        return null;
    }
    
    public void putInFurnaceBlockMap(final FurnaceObject fo) {
        final World world = this.plugin.getWorld(fo.world);
        final int blockX = fo.X;
        final int blockY = fo.Y;
        final int blockZ = fo.Z;
        if (world == null) {
            return;
        }
        final Map<Block, FurnaceObject> fbm = this.dataWriter.furnaceBlockMap;
        fbm.put(world.getBlockAt(fo.X, fo.Y, fo.Z), fo);
        final int facing = fo.facing;
        if (facing == 2) {
            final int start_X = fo.X + 1;
            final int start_Y = fo.Y;
            final int start_Z = fo.Z + 3;
            for (int y = start_Y; y > start_Y - 3; --y) {
                for (int x = start_X; x > start_X - 3; --x) {
                    for (int z = start_Z; z > start_Z - 3; --z) {
                        fbm.put(world.getBlockAt(x, y, z), fo);
                    }
                }
            }
            if (this.dataWriter.getBeltBlocks() != 0) {
                fbm.put(world.getBlockAt(blockX - 1, blockY - 1, blockZ), fo);
                fbm.put(world.getBlockAt(blockX + 1, blockY - 1, blockZ), fo);
                fbm.put(world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1), fo);
                fbm.put(world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1), fo);
                fbm.put(world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2), fo);
                fbm.put(world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2), fo);
                fbm.put(world.getBlockAt(blockX - 2, blockY - 1, blockZ + 3), fo);
                fbm.put(world.getBlockAt(blockX + 2, blockY - 1, blockZ + 3), fo);
                fbm.put(world.getBlockAt(blockX - 1, blockY - 1, blockZ + 4), fo);
                fbm.put(world.getBlockAt(blockX, blockY - 1, blockZ + 4), fo);
                fbm.put(world.getBlockAt(blockX + 1, blockY - 1, blockZ + 4), fo);
            }
        }
        else if (facing == 3) {
            final int start_X = fo.X - 1;
            final int start_Y = fo.Y;
            final int start_Z = fo.Z - 3;
            for (int y = start_Y; y > start_Y - 3; --y) {
                for (int x = start_X; x < start_X + 3; ++x) {
                    for (int z = start_Z; z < start_Z + 3; ++z) {
                        fbm.put(world.getBlockAt(x, y, z), fo);
                    }
                }
            }
            if (this.dataWriter.getBeltBlocks() != 0) {
                fbm.put(world.getBlockAt(blockX + 1, blockY - 1, blockZ), fo);
                fbm.put(world.getBlockAt(blockX - 1, blockY - 1, blockZ), fo);
                fbm.put(world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1), fo);
                fbm.put(world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1), fo);
                fbm.put(world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2), fo);
                fbm.put(world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2), fo);
                fbm.put(world.getBlockAt(blockX + 2, blockY - 1, blockZ - 3), fo);
                fbm.put(world.getBlockAt(blockX - 2, blockY - 1, blockZ - 3), fo);
                fbm.put(world.getBlockAt(blockX + 1, blockY - 1, blockZ - 4), fo);
                fbm.put(world.getBlockAt(blockX, blockY - 1, blockZ - 4), fo);
                fbm.put(world.getBlockAt(blockX - 1, blockY - 1, blockZ - 4), fo);
            }
        }
        else if (facing == 4) {
            final int start_X = fo.X + 3;
            final int start_Y = fo.Y;
            final int start_Z = fo.Z + 1;
            for (int y = start_Y; y > start_Y - 3; --y) {
                for (int x = start_X; x > start_X - 3; --x) {
                    for (int z = start_Z; z > start_Z - 3; --z) {
                        fbm.put(world.getBlockAt(x, y, z), fo);
                    }
                }
            }
            if (this.dataWriter.getBeltBlocks() != 0) {
                fbm.put(world.getBlockAt(blockX, blockY - 1, blockZ + 1), fo);
                fbm.put(world.getBlockAt(blockX, blockY - 1, blockZ - 1), fo);
                fbm.put(world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2), fo);
                fbm.put(world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2), fo);
                fbm.put(world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2), fo);
                fbm.put(world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2), fo);
                fbm.put(world.getBlockAt(blockX + 3, blockY - 1, blockZ + 2), fo);
                fbm.put(world.getBlockAt(blockX + 3, blockY - 1, blockZ - 2), fo);
                fbm.put(world.getBlockAt(blockX + 4, blockY - 1, blockZ + 1), fo);
                fbm.put(world.getBlockAt(blockX + 4, blockY - 1, blockZ), fo);
                fbm.put(world.getBlockAt(blockX + 4, blockY - 1, blockZ - 1), fo);
            }
        }
        else if (facing == 5) {
            final int start_X = fo.X - 3;
            final int start_Y = fo.Y;
            final int start_Z = fo.Z - 1;
            for (int y = start_Y; y > start_Y - 3; --y) {
                for (int x = start_X; x < start_X + 3; ++x) {
                    for (int z = start_Z; z < start_Z + 3; ++z) {
                        fbm.put(world.getBlockAt(x, y, z), fo);
                    }
                }
            }
            if (this.dataWriter.getBeltBlocks() != 0) {
                fbm.put(world.getBlockAt(blockX, blockY - 1, blockZ - 1), fo);
                fbm.put(world.getBlockAt(blockX, blockY - 1, blockZ + 1), fo);
                fbm.put(world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2), fo);
                fbm.put(world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2), fo);
                fbm.put(world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2), fo);
                fbm.put(world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2), fo);
                fbm.put(world.getBlockAt(blockX - 3, blockY - 1, blockZ - 2), fo);
                fbm.put(world.getBlockAt(blockX - 3, blockY - 1, blockZ + 2), fo);
                fbm.put(world.getBlockAt(blockX - 4, blockY - 1, blockZ - 1), fo);
                fbm.put(world.getBlockAt(blockX - 4, blockY - 1, blockZ), fo);
                fbm.put(world.getBlockAt(blockX - 4, blockY - 1, blockZ + 1), fo);
            }
        }
    }
    
    public void remFromFurnaceBlockMap(final FurnaceObject fo) {
        final World world = this.plugin.getWorld(fo.world);
        final int blockX = fo.X;
        final int blockY = fo.Y;
        final int blockZ = fo.Z;
        if (world == null) {
            return;
        }
        final Map<Block, FurnaceObject> fbm = this.dataWriter.furnaceBlockMap;
        fbm.remove(world.getBlockAt(fo.X, fo.Y, fo.Z));
        final int facing = fo.facing;
        if (facing == 2) {
            final int start_X = fo.X + 1;
            final int start_Y = fo.Y;
            final int start_Z = fo.Z + 3;
            for (int y = start_Y; y > start_Y - 3; --y) {
                for (int x = start_X; x > start_X - 3; --x) {
                    for (int z = start_Z; z > start_Z - 3; --z) {
                        fbm.remove(world.getBlockAt(x, y, z));
                    }
                }
            }
            if (this.dataWriter.getBeltBlocks() != 0) {
                fbm.remove(world.getBlockAt(blockX - 1, blockY - 1, blockZ));
                fbm.remove(world.getBlockAt(blockX + 1, blockY - 1, blockZ));
                fbm.remove(world.getBlockAt(blockX - 2, blockY - 1, blockZ + 1));
                fbm.remove(world.getBlockAt(blockX + 2, blockY - 1, blockZ + 1));
                fbm.remove(world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2));
                fbm.remove(world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2));
                fbm.remove(world.getBlockAt(blockX - 2, blockY - 1, blockZ + 3));
                fbm.remove(world.getBlockAt(blockX + 2, blockY - 1, blockZ + 3));
                fbm.remove(world.getBlockAt(blockX - 1, blockY - 1, blockZ + 4));
                fbm.remove(world.getBlockAt(blockX, blockY - 1, blockZ + 4));
                fbm.remove(world.getBlockAt(blockX + 1, blockY - 1, blockZ + 4));
            }
        }
        else if (facing == 3) {
            final int start_X = fo.X - 1;
            final int start_Y = fo.Y;
            final int start_Z = fo.Z - 3;
            for (int y = start_Y; y > start_Y - 3; --y) {
                for (int x = start_X; x < start_X + 3; ++x) {
                    for (int z = start_Z; z < start_Z + 3; ++z) {
                        fbm.remove(world.getBlockAt(x, y, z));
                    }
                }
            }
            if (this.dataWriter.getBeltBlocks() != 0) {
                fbm.remove(world.getBlockAt(blockX + 1, blockY - 1, blockZ));
                fbm.remove(world.getBlockAt(blockX - 1, blockY - 1, blockZ));
                fbm.remove(world.getBlockAt(blockX + 2, blockY - 1, blockZ - 1));
                fbm.remove(world.getBlockAt(blockX - 2, blockY - 1, blockZ - 1));
                fbm.remove(world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2));
                fbm.remove(world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2));
                fbm.remove(world.getBlockAt(blockX + 2, blockY - 1, blockZ - 3));
                fbm.remove(world.getBlockAt(blockX - 2, blockY - 1, blockZ - 3));
                fbm.remove(world.getBlockAt(blockX + 1, blockY - 1, blockZ - 4));
                fbm.remove(world.getBlockAt(blockX, blockY - 1, blockZ - 4));
                fbm.remove(world.getBlockAt(blockX - 1, blockY - 1, blockZ - 4));
            }
        }
        else if (facing == 4) {
            final int start_X = fo.X + 3;
            final int start_Y = fo.Y;
            final int start_Z = fo.Z + 1;
            for (int y = start_Y; y > start_Y - 3; --y) {
                for (int x = start_X; x > start_X - 3; --x) {
                    for (int z = start_Z; z > start_Z - 3; --z) {
                        fbm.remove(world.getBlockAt(x, y, z));
                    }
                }
            }
            if (this.dataWriter.getBeltBlocks() != 0) {
                fbm.remove(world.getBlockAt(blockX, blockY - 1, blockZ + 1));
                fbm.remove(world.getBlockAt(blockX, blockY - 1, blockZ - 1));
                fbm.remove(world.getBlockAt(blockX + 1, blockY - 1, blockZ + 2));
                fbm.remove(world.getBlockAt(blockX + 1, blockY - 1, blockZ - 2));
                fbm.remove(world.getBlockAt(blockX + 2, blockY - 1, blockZ + 2));
                fbm.remove(world.getBlockAt(blockX + 2, blockY - 1, blockZ - 2));
                fbm.remove(world.getBlockAt(blockX + 3, blockY - 1, blockZ + 2));
                fbm.remove(world.getBlockAt(blockX + 3, blockY - 1, blockZ - 2));
                fbm.remove(world.getBlockAt(blockX + 4, blockY - 1, blockZ + 1));
                fbm.remove(world.getBlockAt(blockX + 4, blockY - 1, blockZ));
                fbm.remove(world.getBlockAt(blockX + 4, blockY - 1, blockZ - 1));
            }
        }
        else if (facing == 5) {
            final int start_X = fo.X - 3;
            final int start_Y = fo.Y;
            final int start_Z = fo.Z - 1;
            for (int y = start_Y; y > start_Y - 3; --y) {
                for (int x = start_X; x < start_X + 3; ++x) {
                    for (int z = start_Z; z < start_Z + 3; ++z) {
                        fbm.remove(world.getBlockAt(x, y, z));
                    }
                }
            }
            if (this.dataWriter.getBeltBlocks() != 0) {
                fbm.remove(world.getBlockAt(blockX, blockY - 1, blockZ - 1));
                fbm.remove(world.getBlockAt(blockX, blockY - 1, blockZ + 1));
                fbm.remove(world.getBlockAt(blockX - 1, blockY - 1, blockZ - 2));
                fbm.remove(world.getBlockAt(blockX - 1, blockY - 1, blockZ + 2));
                fbm.remove(world.getBlockAt(blockX - 2, blockY - 1, blockZ - 2));
                fbm.remove(world.getBlockAt(blockX - 2, blockY - 1, blockZ + 2));
                fbm.remove(world.getBlockAt(blockX - 3, blockY - 1, blockZ - 2));
                fbm.remove(world.getBlockAt(blockX - 3, blockY - 1, blockZ + 2));
                fbm.remove(world.getBlockAt(blockX - 4, blockY - 1, blockZ - 1));
                fbm.remove(world.getBlockAt(blockX - 4, blockY - 1, blockZ));
                fbm.remove(world.getBlockAt(blockX - 4, blockY - 1, blockZ + 1));
            }
        }
    }
}
