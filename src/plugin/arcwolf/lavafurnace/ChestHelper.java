// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import org.bukkit.inventory.ItemStack;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.Inventory;

public class ChestHelper
{
    private FurnaceObject fo;
    private LavaFurnace plugin;
    private boolean chestDetected;
    
    public ChestHelper(final LavaFurnace plugin, final FurnaceObject fo) {
        this.chestDetected = false;
        this.fo = fo;
        this.plugin = plugin;
    }
    
    private Inventory[] getInventories() {
        final Inventory[] inventory = new Inventory[5];
        final int furnace_X = this.fo.X;
        final int furnace_Y = this.fo.Y;
        final int furnace_Z = this.fo.Z;
        final int furnace_face = this.fo.facing;
        boolean narrowLayout = false;
        final World world = this.plugin.getWorld(this.fo.world);
        final boolean largeChest = this.plugin.datawriter.isUselargechests();
        Furnace furnace = null;
        Chest supplyChest = null;
        Chest procChest = null;
        Chest supplyChest2 = null;
        Chest procChest2 = null;
        if (furnace_face == 2) {
            furnace = (Furnace)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 1).getState();
            if (this.plugin.datawriter.getBeltBlocks() != 0 && world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getState();
                procChest = (Chest)world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getState();
            }
            else if (world.getBlockAt(furnace_X + 1, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X - 1, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X + 1, furnace_Y - 1, furnace_Z).getState();
                procChest = (Chest)world.getBlockAt(furnace_X - 1, furnace_Y - 1, furnace_Z).getState();
                narrowLayout = true;
            }
            else if (world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getState();
                procChest = (Chest)world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getState();
            }
            if (largeChest) {
                if (narrowLayout) {
                    if (world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        this.chestDetected = true;
                        supplyChest2 = (Chest)world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getState();
                        procChest2 = (Chest)world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getState();
                    }
                }
                else if (world.getBlockAt(furnace_X + 3, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X - 3, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                    this.chestDetected = true;
                    supplyChest2 = (Chest)world.getBlockAt(furnace_X + 3, furnace_Y - 1, furnace_Z).getState();
                    procChest2 = (Chest)world.getBlockAt(furnace_X - 3, furnace_Y - 1, furnace_Z).getState();
                }
            }
        }
        else if (furnace_face == 3) {
            furnace = (Furnace)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 1).getState();
            if (this.plugin.datawriter.getBeltBlocks() != 0 && world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getState();
                procChest = (Chest)world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getState();
            }
            else if (world.getBlockAt(furnace_X + 1, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X - 1, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X - 1, furnace_Y - 1, furnace_Z).getState();
                procChest = (Chest)world.getBlockAt(furnace_X + 1, furnace_Y - 1, furnace_Z).getState();
                narrowLayout = true;
            }
            else if (world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getState();
                procChest = (Chest)world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getState();
            }
            if (largeChest) {
                if (narrowLayout) {
                    if (world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        this.chestDetected = true;
                        supplyChest2 = supplyChest;
                        final Chest temp = supplyChest = (Chest)world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getState();
                        procChest2 = (Chest)world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getState();
                    }
                }
                else if (world.getBlockAt(furnace_X - 3, furnace_Y - 1, furnace_Z).getTypeId() == 54 && world.getBlockAt(furnace_X + 3, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                    this.chestDetected = true;
                    supplyChest2 = supplyChest;
                    final Chest temp = supplyChest = (Chest)world.getBlockAt(furnace_X - 3, furnace_Y - 1, furnace_Z).getState();
                    procChest2 = (Chest)world.getBlockAt(furnace_X + 3, furnace_Y - 1, furnace_Z).getState();
                }
            }
        }
        else if (furnace_face == 4) {
            furnace = (Furnace)world.getBlockAt(furnace_X + 1, furnace_Y - 1, furnace_Z).getState();
            if (this.plugin.datawriter.getBeltBlocks() != 0 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getState();
                procChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getState();
            }
            else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 1).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 1).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 1).getState();
                procChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 1).getState();
                narrowLayout = true;
            }
            else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getState();
                procChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getState();
            }
            if (largeChest) {
                if (narrowLayout) {
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54) {
                        this.chestDetected = true;
                        supplyChest2 = supplyChest;
                        final Chest temp = supplyChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getState();
                        procChest2 = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getState();
                    }
                }
                else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 3).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 3).getTypeId() == 54) {
                    this.chestDetected = true;
                    supplyChest2 = supplyChest;
                    final Chest temp = supplyChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 3).getState();
                    procChest2 = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 3).getState();
                }
            }
        }
        else if (furnace_face == 5) {
            furnace = (Furnace)world.getBlockAt(furnace_X - 1, furnace_Y - 1, furnace_Z).getState();
            if (this.plugin.datawriter.getBeltBlocks() != 0 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getState();
                procChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getState();
            }
            else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 1).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 1).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 1).getState();
                procChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 1).getState();
                narrowLayout = true;
            }
            else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54) {
                supplyChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getState();
                procChest = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getState();
            }
            if (largeChest) {
                if (narrowLayout) {
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54) {
                        this.chestDetected = true;
                        supplyChest2 = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getState();
                        procChest2 = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getState();
                    }
                }
                else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 3).getTypeId() == 54 && world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 3).getTypeId() == 54) {
                    this.chestDetected = true;
                    supplyChest2 = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 3).getState();
                    procChest2 = (Chest)world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 3).getState();
                }
            }
        }
        inventory[0] = (Inventory)((furnace != null) ? furnace.getInventory() : null);
        inventory[1] = ((supplyChest != null) ? supplyChest.getInventory() : null);
        inventory[2] = ((supplyChest2 != null) ? supplyChest2.getInventory() : null);
        inventory[3] = ((procChest != null) ? procChest.getInventory() : null);
        inventory[4] = ((procChest2 != null) ? procChest2.getInventory() : null);
        return inventory;
    }
    
    public boolean isProductionChest(final FurnaceObject fo, final int x, final int y, final int z, final String in_world) {
        final int furnace_X = fo.X;
        final int furnace_Y = fo.Y;
        final int furnace_Z = fo.Z;
        final int furnace_face = fo.facing;
        final World e_world = this.plugin.getWorld(in_world);
        if (e_world != null) {
            if (furnace_face == 2 || furnace_face == 3) {
                if (e_world.getBlockAt(x, y, z).getTypeId() == 54) {
                    if (furnace_X + 2 == x && furnace_Y - 1 == y && furnace_Z == z && fo.world.equals(in_world)) {
                        return true;
                    }
                    if (furnace_X - 2 == x && furnace_Y - 1 == y && furnace_Z == z && fo.world.equals(in_world)) {
                        return true;
                    }
                }
                if (e_world.getBlockAt(x - 1, y, z).getTypeId() == 54 || e_world.getBlockAt(x + 1, y, z).getTypeId() == 54) {
                    if (e_world.getBlockAt(x, y, z).getTypeId() == 54) {
                        if (furnace_X + 3 == x && furnace_Y - 1 == y && furnace_Z == z && fo.world.equals(in_world)) {
                            return true;
                        }
                        if (furnace_X - 3 == x && furnace_Y - 1 == y && furnace_Z == z && fo.world.equals(in_world)) {
                            return true;
                        }
                        if (furnace_X + 1 == x && furnace_Y - 1 == y && furnace_Z == z && fo.world.equals(in_world) && e_world.getBlockAt(furnace_X - 1, y, z).getTypeId() == 54 && e_world.getBlockAt(furnace_X + 1, y, z).getTypeId() == 54) {
                            return true;
                        }
                        if (furnace_X - 1 == x && furnace_Y - 1 == y && furnace_Z == z && fo.world.equals(in_world) && e_world.getBlockAt(furnace_X - 1, y, z).getTypeId() == 54 && e_world.getBlockAt(furnace_X + 1, y, z).getTypeId() == 54) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            else if (furnace_face == 4 || furnace_face == 5) {
                if (e_world.getBlockAt(x, y, z).getTypeId() == 54) {
                    if (furnace_X == x && furnace_Y - 1 == y && furnace_Z - 2 == z && fo.world.equals(in_world)) {
                        return true;
                    }
                    if (furnace_X == x && furnace_Y - 1 == y && furnace_Z + 2 == z && fo.world.equals(in_world)) {
                        return true;
                    }
                }
                if (e_world.getBlockAt(x, y, z - 1).getTypeId() == 54 || e_world.getBlockAt(x, y, z + 1).getTypeId() == 54) {
                    if (e_world.getBlockAt(x, y, z).getTypeId() == 54) {
                        if (furnace_X == x && furnace_Y - 1 == y && furnace_Z - 3 == z && fo.world.equals(in_world)) {
                            return true;
                        }
                        if (furnace_X == x && furnace_Y - 1 == y && furnace_Z + 3 == z && fo.world.equals(in_world)) {
                            return true;
                        }
                        if (furnace_X == x && furnace_Y - 1 == y && furnace_Z - 1 == z && fo.world.equals(in_world) && e_world.getBlockAt(x, y, furnace_Z + 1).getTypeId() == 54 && e_world.getBlockAt(x, y, furnace_Z - 1).getTypeId() == 54) {
                            return true;
                        }
                        if (furnace_X == x && furnace_Y - 1 == y && furnace_Z + 1 == z && fo.world.equals(in_world) && e_world.getBlockAt(x, y, furnace_Z + 1).getTypeId() == 54 && e_world.getBlockAt(x, y, furnace_Z - 1).getTypeId() == 54) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            return false;
        }
        return false;
    }
    
    public boolean isChestPair() {
        if (this.fo.world != null) {
            final int furnace_X = this.fo.X;
            final int furnace_Y = this.fo.Y;
            final int furnace_Z = this.fo.Z;
            final int furnace_face = this.fo.facing;
            final World world = this.plugin.getWorld(this.fo.world);
            int chestLeft = 0;
            int chestRight = 0;
            int chestTotal = 0;
            if (furnace_face == 2) {
                if (this.plugin.datawriter.getBeltBlocks() != 0) {
                    if (world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    if (world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestRight = 1;
                    }
                }
                else {
                    if (world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    else if (world.getBlockAt(furnace_X + 1, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    if (world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestRight = 1;
                    }
                    else if (world.getBlockAt(furnace_X - 1, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestRight = 1;
                    }
                }
            }
            else if (furnace_face == 3) {
                if (this.plugin.datawriter.getBeltBlocks() != 0) {
                    if (world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    if (world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestRight = 1;
                    }
                }
                else {
                    if (world.getBlockAt(furnace_X - 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    else if (world.getBlockAt(furnace_X - 1, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    if (world.getBlockAt(furnace_X + 2, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestRight = 1;
                    }
                    else if (world.getBlockAt(furnace_X + 1, furnace_Y - 1, furnace_Z).getTypeId() == 54) {
                        chestRight = 1;
                    }
                }
            }
            else if (furnace_face == 4) {
                if (this.plugin.datawriter.getBeltBlocks() != 0) {
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54) {
                        chestRight = 1;
                    }
                }
                else {
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 1).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54) {
                        chestRight = 1;
                    }
                    else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 1).getTypeId() == 54) {
                        chestRight = 1;
                    }
                }
            }
            else if (furnace_face == 5) {
                if (this.plugin.datawriter.getBeltBlocks() != 0) {
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54) {
                        chestRight = 1;
                    }
                }
                else {
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 2).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z + 1).getTypeId() == 54) {
                        chestLeft = 1;
                    }
                    if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 2).getTypeId() == 54) {
                        chestRight = 1;
                    }
                    else if (world.getBlockAt(furnace_X, furnace_Y - 1, furnace_Z - 1).getTypeId() == 54) {
                        chestRight = 1;
                    }
                }
            }
            chestTotal = chestLeft + chestRight;
            return chestTotal == 2;
        }
        return false;
    }
    
    public Inventory getFurnaceInventory() {
        return this.getInventories()[0];
    }
    
    public Inventory getSingleSupplyChest() {
        return this.getInventories()[1];
    }
    
    public Inventory getDoubleSupplyChest() {
        return this.getInventories()[2];
    }
    
    public Inventory getSingleProcessChest() {
        return this.getInventories()[3];
    }
    
    public Inventory getDoubleProcessChest() {
        return this.getInventories()[4];
    }
    
    public boolean isEmpty(final Inventory[] inv) {
        for (int x = 0; x < inv.length; ++x) {
            if (inv[x] != null) {
                ItemStack[] contents;
                for (int length = (contents = inv[x].getContents()).length, j = 0; j < length; ++j) {
                    final ItemStack i = contents[j];
                    if (i != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public boolean isChestDetected() {
        return this.chestDetected;
    }
}
