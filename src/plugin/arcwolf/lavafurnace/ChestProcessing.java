// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import java.util.ArrayList;
import org.bukkit.World;
import org.bukkit.material.MaterialData;
import org.bukkit.inventory.Inventory;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

public class ChestProcessing
{
    private LavaFurnace plugin;
    
    public ChestProcessing(final LavaFurnace instance) {
        this.plugin = instance;
    }
    
    public void processChests(final FurnaceObject fo) {
        final Furnace furn = this.plugin.furnaceHelper.getFurnace(fo);
        if (furn == null) {
            return;
        }
        final ChestHelper ch = new ChestHelper(this.plugin, fo);
        final Inventory scInv = ch.getSingleSupplyChest();
        final Inventory pcInv = ch.getSingleProcessChest();
        final Inventory fInv = ch.getFurnaceInventory();
        final Inventory scInv2 = ch.getDoubleSupplyChest();
        final Inventory pcInv2 = ch.getDoubleProcessChest();
        final boolean largeChest = this.plugin.datawriter.isUselargechests();
        final boolean chestDetected = ch.isChestDetected();
        final boolean fuelFromChest = this.plugin.datawriter.isSourceChestFuel();
        int fSA = this.getAmount(fInv.getItem(0));
        final int fFA = this.getAmount(fInv.getItem(1));
        final int fPA = this.getAmount(fInv.getItem(2));
        int fmaxSS = Math.abs(this.getMaxStackSize(fInv.getItem(0)));
        final int fSIId = this.getTypeId(fInv.getItem(0));
        final int fFIId = this.getTypeId(fInv.getItem(1));
        int burnTime = 0;
        burnTime = furn.getBurnTime();
        if (fuelFromChest && fo.power < 1 && fSA > 0 && fFA == 0 && burnTime <= 0 && fPA == 0) {
            int index = this.getSourceChestFuelIndex(scInv, fFIId);
            if (index != -1) {
                final int itemCount = this.getAmount(scInv.getItem(index));
                final ItemStack scItem = scInv.getItem(index);
                final int typeId = this.getTypeId(scItem);
                if (itemCount == 1) {
                    if (typeId == 327) {
                        scInv.setItem(index, new ItemStack(325, 1));
                        this.fuelFurnaceWithLava(fo, furn);
                    }
                    else if (typeId == 10 || typeId == 11) {
                        scInv.clear(index);
                        this.fuelFurnaceWithLava(fo, furn);
                    }
                    else {
                        fInv.setItem(1, scItem);
                        scInv.clear(index);
                    }
                }
                else if (itemCount > 1) {
                    if (typeId == 10 || typeId == 11) {
                        this.fuelFurnaceWithLava(fo, furn);
                    }
                    else {
                        fInv.setItem(1, this.getSingleItem_ItemStack(scItem));
                    }
                    scItem.setAmount(itemCount - 1);
                }
            }
            else if (largeChest && chestDetected) {
                index = this.getSourceChestFuelIndex(scInv2, fFIId);
                if (index != -1) {
                    final int itemCount = this.getAmount(scInv2.getItem(index));
                    final ItemStack scItem = scInv2.getItem(index);
                    final int typeId = this.getTypeId(scItem);
                    if (itemCount == 1) {
                        if (typeId == 327) {
                            scInv2.setItem(index, new ItemStack(325, 1));
                            this.fuelFurnaceWithLava(fo, furn);
                        }
                        else if (typeId == 10 || typeId == 11) {
                            scInv2.clear(index);
                            this.fuelFurnaceWithLava(fo, furn);
                        }
                        else {
                            fInv.setItem(1, scItem);
                            scInv2.clear(index);
                        }
                    }
                    else if (itemCount > 1) {
                        if (typeId == 10 || typeId == 11) {
                            this.fuelFurnaceWithLava(fo, furn);
                        }
                        else {
                            fInv.setItem(1, this.getSingleItem_ItemStack(scItem));
                        }
                        scItem.setAmount(itemCount - 1);
                    }
                }
            }
        }
        if (fSA == 0 && this.isValidOre(fSIId)) {
            int index = this.getSourceChestIndex(scInv, fSIId);
            if (index != -1) {
                fInv.setItem(0, scInv.getItem(index));
                scInv.clear(index);
                fSA = this.getAmount(fInv.getItem(0));
            }
            else if (largeChest && chestDetected) {
                index = this.getSourceChestIndex(scInv2, fSIId);
                if (index != -1) {
                    fInv.setItem(0, scInv2.getItem(index));
                    scInv2.clear(index);
                    fSA = this.getAmount(fInv.getItem(0));
                }
            }
        }
        else if (fSA < fmaxSS && this.isValidOre(fSIId)) {
            int index = this.getSourceChestIndex(scInv, fSIId);
            int startItemCount = 0;
            int itemCount2 = 0;
            if (index != -1) {
                startItemCount = this.getAmount(scInv.getItem(index));
                if (startItemCount > 0) {
                    for (int i = 0; i <= startItemCount; ++i) {
                        fSA = this.getAmount(fInv.getItem(0));
                        fmaxSS = Math.abs(this.getMaxStackSize(fInv.getItem(0)));
                        itemCount2 = this.getAmount(scInv.getItem(index));
                        if (fSA == fmaxSS) {
                            break;
                        }
                        fInv.getItem(0).setAmount(fSA + 1);
                        if (itemCount2 - 1 == 0) {
                            scInv.clear(index);
                            break;
                        }
                        scInv.getItem(index).setAmount(itemCount2 - 1);
                    }
                }
            }
            else if (largeChest && chestDetected) {
                index = this.getSourceChestIndex(scInv2, fSIId);
                startItemCount = 0;
                itemCount2 = 0;
                if (index != -1) {
                    startItemCount = this.getAmount(scInv2.getItem(index));
                    if (startItemCount > 0) {
                        for (int i = 0; i <= startItemCount; ++i) {
                            fSA = this.getAmount(fInv.getItem(0));
                            fmaxSS = Math.abs(this.getMaxStackSize(fInv.getItem(0)));
                            itemCount2 = this.getAmount(scInv2.getItem(index));
                            if (fSA == fmaxSS) {
                                break;
                            }
                            fInv.getItem(0).setAmount(fSA + 1);
                            if (itemCount2 - 1 == 0) {
                                scInv2.clear(index);
                                break;
                            }
                            scInv2.getItem(index).setAmount(itemCount2 - 1);
                        }
                    }
                }
            }
        }
        if (this.getTypeId(fInv.getItem(2)) != 0) {
            int itemCount3 = 0;
            int maxStackSize = 0;
            int fStartItemCount = 0;
            int fItemCount = 0;
            for (int j = 0; j < pcInv.getSize(); ++j) {
                if (this.getTypeId(pcInv.getItem(j)) == this.getTypeId(fInv.getItem(2)) && this.getDataValue(pcInv.getItem(j)) == this.getDataValue(fInv.getItem(2))) {
                    itemCount3 = this.getAmount(pcInv.getItem(j));
                    maxStackSize = Math.abs(this.getMaxStackSize(pcInv.getItem(j)));
                    if (itemCount3 < maxStackSize && this.getTypeId(pcInv.getItem(j)) != 0) {
                        pcInv.getItem(j).setAmount(itemCount3 + 1);
                        if (this.getAmount(fInv.getItem(2)) == 1) {
                            fInv.clear(2);
                        }
                        else {
                            fStartItemCount = this.getAmount(fInv.getItem(2));
                            for (int k = 0; k <= fStartItemCount; ++k) {
                                fItemCount = this.getAmount(fInv.getItem(2));
                                itemCount3 = this.getAmount(pcInv.getItem(j));
                                if (itemCount3 == maxStackSize) {
                                    break;
                                }
                                if (fItemCount - 1 == 0) {
                                    fInv.clear(2);
                                    break;
                                }
                                pcInv.getItem(j).setAmount(itemCount3 + 1);
                                fInv.getItem(2).setAmount(fItemCount - 1);
                            }
                        }
                    }
                }
            }
            if (pcInv.firstEmpty() != -1 && this.getTypeId(fInv.getItem(2)) != 0) {
                pcInv.setItem(pcInv.firstEmpty(), fInv.getItem(2));
                fInv.clear(2);
            }
            if (largeChest && chestDetected && this.getTypeId(fInv.getItem(2)) != 0) {
                itemCount3 = 0;
                maxStackSize = 0;
                fStartItemCount = 0;
                fItemCount = 0;
                for (int j = 0; j < pcInv2.getSize(); ++j) {
                    if (this.getTypeId(pcInv2.getItem(j)) == this.getTypeId(fInv.getItem(2)) && this.getDataValue(pcInv.getItem(j)) == this.getDataValue(fInv.getItem(2))) {
                        itemCount3 = this.getAmount(pcInv2.getItem(j));
                        maxStackSize = Math.abs(this.getMaxStackSize(pcInv2.getItem(j)));
                        if (itemCount3 < maxStackSize && this.getTypeId(pcInv2.getItem(j)) != 0) {
                            pcInv2.getItem(j).setAmount(itemCount3 + 1);
                            if (this.getAmount(fInv.getItem(2)) == 1) {
                                fInv.clear(2);
                            }
                            else {
                                fStartItemCount = this.getAmount(fInv.getItem(2));
                                for (int k = 0; k <= fStartItemCount; ++k) {
                                    fItemCount = this.getAmount(fInv.getItem(2));
                                    itemCount3 = this.getAmount(pcInv2.getItem(j));
                                    if (itemCount3 == maxStackSize) {
                                        break;
                                    }
                                    if (fItemCount - 1 == 0) {
                                        fInv.clear(2);
                                        break;
                                    }
                                    pcInv2.getItem(j).setAmount(itemCount3 + 1);
                                    fInv.getItem(2).setAmount(fItemCount - 1);
                                }
                            }
                        }
                    }
                }
                if (pcInv2.firstEmpty() != -1 && this.getTypeId(fInv.getItem(2)) != 0) {
                    pcInv2.setItem(pcInv2.firstEmpty(), fInv.getItem(2));
                    fInv.clear(2);
                }
            }
        }
    }
    
    private ItemStack getSingleItem_ItemStack(final ItemStack scItem) {
        if (scItem == null) {
            return new ItemStack(0, 1);
        }
        final int itemId = this.getTypeId(scItem);
        final MaterialData itemData = scItem.getData();
        final short itemDamage = (short)this.getDamageValue(scItem);
        final ItemStack i = new ItemStack(itemId, 1, itemDamage);
        i.setData(itemData);
        return i;
    }
    
    private int getDamageValue(final ItemStack item) {
        return this.plugin.furnaceHelper.getDamageValue(item);
    }
    
    private int getDataValue(final ItemStack item) {
        return this.plugin.furnaceHelper.getDataValue(item);
    }
    
    private int getTypeId(final ItemStack item) {
        return this.plugin.furnaceHelper.getTypeId(item);
    }
    
    private int getMaxStackSize(final ItemStack item) {
        return this.plugin.furnaceHelper.getMaxStackSize(item);
    }
    
    private int getAmount(final ItemStack item) {
        return this.plugin.furnaceHelper.getAmount(item);
    }
    
    public boolean fuelFurnaceWithLava(final FurnaceObject fo, final Furnace furn) {
        final World world = this.plugin.getWorld(fo.world);
        return this.plugin.furnaceHelper.initFurnace(fo, world);
    }
    
    private int getSourceChestFuelIndex(final Inventory scInv, final int fFIId) {
        final List<Fuels> orderedFuels = new ArrayList<Fuels>(40);
        orderedFuels.add(new Fuels(scInv.first(Material.COAL), 263));
        orderedFuels.add(new Fuels(scInv.first(Material.LOG), 17));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD), 5));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD_STEP), 126));
        orderedFuels.add(new Fuels(scInv.first(Material.SAPLING), 6));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD_SWORD), 268));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD_SPADE), 269));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD_PICKAXE), 270));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD_AXE), 271));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD_HOE), 290));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD_PLATE), 72));
        orderedFuels.add(new Fuels(scInv.first(Material.STICK), 280));
        orderedFuels.add(new Fuels(scInv.first(Material.FENCE), 85));
        orderedFuels.add(new Fuels(scInv.first(Material.FENCE_GATE), 107));
        orderedFuels.add(new Fuels(scInv.first(Material.WOOD_STAIRS), 53));
        orderedFuels.add(new Fuels(scInv.first(Material.SPRUCE_WOOD_STAIRS), 134));
        orderedFuels.add(new Fuels(scInv.first(Material.BIRCH_WOOD_STAIRS), 135));
        orderedFuels.add(new Fuels(scInv.first(Material.JUNGLE_WOOD_STAIRS), 136));
        orderedFuels.add(new Fuels(scInv.first(Material.TRAP_DOOR), 96));
        orderedFuels.add(new Fuels(scInv.first(Material.WORKBENCH), 58));
        orderedFuels.add(new Fuels(scInv.first(Material.BOOKSHELF), 47));
        orderedFuels.add(new Fuels(scInv.first(Material.CHEST), 54));
        orderedFuels.add(new Fuels(scInv.first(Material.TRAPPED_CHEST), 146));
        orderedFuels.add(new Fuels(scInv.first(Material.DAYLIGHT_DETECTOR), 151));
        orderedFuels.add(new Fuels(scInv.first(Material.JUKEBOX), 84));
        orderedFuels.add(new Fuels(scInv.first(Material.NOTE_BLOCK), 25));
        orderedFuels.add(new Fuels(scInv.first(Material.HUGE_MUSHROOM_1), 99));
        orderedFuels.add(new Fuels(scInv.first(Material.HUGE_MUSHROOM_2), 100));
        orderedFuels.add(new Fuels(scInv.first(Material.BLAZE_ROD), 369));
        orderedFuels.add(new Fuels(scInv.first(Material.LAVA_BUCKET), 327));
        orderedFuels.add(new Fuels(scInv.first(Material.STATIONARY_LAVA), 11));
        orderedFuels.add(new Fuels(scInv.first(Material.COAL_BLOCK), 173));
        orderedFuels.add(new Fuels(scInv.first(Material.LAVA), 10));
        if (this.plugin.datawriter.customFuel.size() > 0) {
            for (int i = 0; i < this.plugin.datawriter.customFuel.size(); ++i) {
                final int tempSmelt = this.plugin.datawriter.customFuel.get(i);
                if (tempSmelt != 0) {
                    orderedFuels.add(new Fuels(scInv.first(tempSmelt), tempSmelt));
                }
            }
        }
        for (int j = 0; j < orderedFuels.size(); ++j) {
            if (orderedFuels.get(j).resourceChestIndex != -1 && (orderedFuels.get(j).furnaceFuelId == fFIId || fFIId == 0)) {
                return orderedFuels.get(j).resourceChestIndex;
            }
        }
        return -1;
    }
    
    private int getSourceChestIndex(final Inventory scInv, final int fSIId) {
        final List<Smeltables> unSortedSmeltables = new ArrayList<Smeltables>(30);
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.COBBLESTONE), 4));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.SAND), 12));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.GOLD_ORE), 14));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.IRON_ORE), 15));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.COAL_ORE), 16));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.LOG), 17));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.LAPIS_ORE), 21));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.DIAMOND_ORE), 56));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.REDSTONE_ORE), 73));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.CACTUS), 81));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.CLAY), 82));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.EMERALD_ORE), 129));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.PORK), 319));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.CLAY_BALL), 337));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.RAW_FISH), 349));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.RAW_BEEF), 363));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.RAW_CHICKEN), 365));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.POTATO_ITEM), 392));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.NETHERRACK), 87));
        unSortedSmeltables.add(new Smeltables(scInv.first(Material.QUARTZ_ORE), 153));
        if (this.plugin.datawriter.customSmeltables.size() > 0) {
            for (int i = 0; i < this.plugin.datawriter.customSmeltables.size(); ++i) {
                final int tempSmelt = this.plugin.datawriter.customSmeltables.get(i);
                if (tempSmelt != 0) {
                    unSortedSmeltables.add(new Smeltables(scInv.first(tempSmelt), tempSmelt));
                }
            }
        }
        final List<Smeltables> sortedSmeltables = this.sortSmeltables(unSortedSmeltables);
        for (int i = 0; i < sortedSmeltables.size(); ++i) {
            if (sortedSmeltables.get(i).resourceChestIndex != -1 && (sortedSmeltables.get(i).furnaceResourceId == fSIId || fSIId == 0)) {
                return sortedSmeltables.get(i).resourceChestIndex;
            }
        }
        return -1;
    }
    
    private List<Smeltables> sortSmeltables(final List<Smeltables> unSortedSmeltables) {
        for (int index = 0; index < unSortedSmeltables.size() - 1; ++index) {
            final int indexOfNextSmallest = this.indexOfSmallest(index, unSortedSmeltables, unSortedSmeltables.size());
            this.interchange(index, indexOfNextSmallest, unSortedSmeltables);
        }
        return unSortedSmeltables;
    }
    
    private int indexOfSmallest(final int startIndex, final List<Smeltables> unSortedSmeltables, final int size) {
        int min = unSortedSmeltables.get(startIndex).resourceChestIndex;
        int indexOfMin = startIndex;
        for (int index = startIndex + 1; index < size; ++index) {
            if (unSortedSmeltables.get(index).resourceChestIndex < min) {
                min = unSortedSmeltables.get(index).resourceChestIndex;
                indexOfMin = index;
            }
        }
        return indexOfMin;
    }
    
    private void interchange(final int i, final int j, final List<Smeltables> unSortedSmeltables) {
        final Smeltables temp = unSortedSmeltables.get(i);
        unSortedSmeltables.set(i, unSortedSmeltables.get(j));
        unSortedSmeltables.set(j, temp);
    }
    
    private boolean isValidOre(final int typeId) {
        if (typeId == 4 || typeId == 12 || typeId == 14 || typeId == 15 || typeId == 16 || typeId == 17 || typeId == 21 || typeId == 56 || typeId == 73 || typeId == 81 || typeId == 82 || typeId == 129 || typeId == 319 || typeId == 337 || typeId == 349 || typeId == 363 || typeId == 365 || typeId == 392 || typeId == 87 || typeId == 153 || typeId == 0) {
            return true;
        }
        for (final Integer customs : this.plugin.datawriter.customSmeltables) {
            if (customs == typeId) {
                return true;
            }
        }
        return false;
    }
    
    private class Smeltables
    {
        int resourceChestIndex;
        int furnaceResourceId;
        
        Smeltables(final int resourceChestIndex, final int furnaceResourceId) {
            this.resourceChestIndex = resourceChestIndex;
            this.furnaceResourceId = furnaceResourceId;
        }
    }
    
    private class Fuels
    {
        int resourceChestIndex;
        int furnaceFuelId;
        
        Fuels(final int resourceChestIndex, final int furnaceFuelId) {
            this.resourceChestIndex = resourceChestIndex;
            this.furnaceFuelId = furnaceFuelId;
        }
    }
}
