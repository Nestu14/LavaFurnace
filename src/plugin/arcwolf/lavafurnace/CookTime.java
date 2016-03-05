// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Furnace;
import org.bukkit.ChatColor;
import java.util.Iterator;

public class CookTime
{
    private LavaFurnace plugin;
    private FurnaceHelper furnacehelper;
    private UserCookTimeHelper usercooktimehelper;
    
    public CookTime(final LavaFurnace instance) {
        this.plugin = instance;
        this.furnacehelper = this.plugin.furnaceHelper;
        this.usercooktimehelper = this.plugin.usercooktimehelper;
    }
    
    public void process() {
        try {
            for (final FurnaceObject fo : this.plugin.datawriter.lfObject) {
                if (!this.furnacehelper.isFurnace(fo)) {
                    if (!this.furnacehelper.isFurnacePowered(fo)) {
                        continue;
                    }
                }
                try {
                    final int id = this.usercooktimehelper.findUser(fo.creator);
                    if (id == -1) {
                        this.modifyCookTime(fo);
                    }
                    else {
                        this.modifyCookTime(fo, id);
                    }
                }
                catch (Exception e) {
                    if (this.plugin.datawriter.getLFDebug() != 5) {
                        continue;
                    }
                    final StringBuffer buff = new StringBuffer();
                    for (int i = 0; i < fo.toString().length(); ++i) {
                        buff.append((fo.toString().charAt(i) == LavaFurnace.SEPERATOR) ? ", " : fo.toString().charAt(i));
                    }
                    LavaFurnace.LOGGER.info("LavaFurnace: cook time error in-> \n" + (Object)buff + "\n Exception caught is: \n" + e);
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e2) {
            if (this.plugin.datawriter.getLFDebug() == 5) {
                LavaFurnace.LOGGER.info("LavaFurnace: cook time error in furnace Exception caught is: \n" + e2);
            }
        }
    }
    
    private void modifyCookTime(final FurnaceObject fo) {
        final Furnace cf = this.plugin.furnaceHelper.getFurnace(fo);
        if (cf == null) {
            if (this.plugin.datawriter.getLFDebug() == 7) {
                System.out.println("+---+");
                System.out.println("LavaFurnace: Furnace Was NULL! " + fo.creator + ": " + fo.X + ", " + fo.Y + ", " + fo.Z + " in " + fo.world);
                System.out.println("LavaFurnace: modifyCookTime(fo)");
                System.out.println("+---+");
            }
            return;
        }
        final int itemID = this.getTypeId(cf.getInventory().getItem(0));
        if (this.getTypeId(cf.getInventory().getItem(1)) == 0 && itemID != 0 && this.plugin.furnaceHelper.isLavaPowered(fo)) {
            final double multiplier = this.plugin.datawriter.getCookTimeMultiplier();
            double newCookTime = cf.getCookTime();
            if (newCookTime < multiplier) {
                if (this.plugin.datawriter.getLFDebug() == 7) {
                    System.out.println("+---+");
                    this.plugin.getServer().getConsoleSender().sendMessage(plugin.getMessage("cooktime.advanced"));
                    System.out.println("For furnace " + fo.creator + ": " + fo.X + ", " + fo.Y + ", " + fo.Z + " in " + fo.world);
                    System.out.println("CookTime < multiplier " + newCookTime + " " + multiplier + " cooking " + cf.getInventory().getItem(0).getType());
                    System.out.println("LavaFurnace: modifyCookTime(fo)");
                    System.out.println("+---+");
                }
                newCookTime += multiplier;
                cf.setCookTime((short)newCookTime);
            }
            else if (this.plugin.datawriter.getLFDebug() == 7) {
                System.out.println("+---+");
                this.plugin.getServer().getConsoleSender().sendMessage(plugin.getMessage("cooktime.normal"));
                System.out.println("For furnace " + fo.creator + ": " + fo.X + ", " + fo.Y + ", " + fo.Z + " in " + fo.world);
                System.out.println("CookTime > multiplier " + newCookTime + " " + multiplier + " cooking " + cf.getInventory().getItem(0).getType());
                System.out.println("LavaFurnace: modifyCookTime(fo)");
                System.out.println("+---+");
            }
        }
        else if (this.plugin.datawriter.getLFDebug() == 7) {
            System.out.println("+---+");
            System.out.println("LavaFurnace: Furnace " + fo.creator + ": " + fo.X + ", " + fo.Y + ", " + fo.Z + " in " + fo.world);
            System.out.println("Inventory Slot 1 = " + this.getTypeId(cf.getInventory().getItem(1)) + " Inventory Slot 0 = " + this.getTypeId(cf.getInventory().getItem(0)) + " lavaPowered? " + this.plugin.furnaceHelper.isLavaPowered(fo));
            System.out.println("LavaFurnace: modifyCookTime(fo)");
            System.out.println("+---+");
        }
    }
    
    private void modifyCookTime(final FurnaceObject fo, final int userIndex) {
        final Furnace cf = this.plugin.furnaceHelper.getFurnace(fo);
        if (cf == null) {
            if (this.plugin.datawriter.getLFDebug() == 7) {
                System.out.println("+---+");
                System.out.println("LavaFurnace: Furnace Was NULL! " + fo.creator + ": " + fo.X + ", " + fo.Y + ", " + fo.Z + " in " + fo.world);
                System.out.println("LavaFurnace: modifyCookTime(fo, userIndex)");
                System.out.println("+---+");
            }
            return;
        }
        final int itemID = this.getTypeId(cf.getInventory().getItem(0));
        if (this.getTypeId(cf.getInventory().getItem(1)) == 0 && itemID != 0 && this.plugin.furnaceHelper.isLavaPowered(fo)) {
            final double multiplier = this.usercooktimehelper.getCookTimeMultiplier(userIndex, itemID);
            double newCookTime = cf.getCookTime();
            if (newCookTime < multiplier) {
                if (this.plugin.datawriter.getLFDebug() == 7) {
                    System.out.println("+---+");
                    this.plugin.getServer().getConsoleSender().sendMessage(plugin.getMessage("cooktime.advanced"));
                    System.out.println("For furnace " + fo.creator + ": " + fo.X + ", " + fo.Y + ", " + fo.Z + " in " + fo.world);
                    System.out.println("CookTime < multiplier " + newCookTime + " " + multiplier + " cooking " + cf.getInventory().getItem(0).getType());
                    System.out.println("LavaFurnace: modifyCookTime(fo, userIndex)");
                    System.out.println("+---+");
                }
                newCookTime += multiplier;
                cf.setCookTime((short)newCookTime);
            }
            else if (this.plugin.datawriter.getLFDebug() == 7) {
                System.out.println("+---+");
                this.plugin.getServer().getConsoleSender().sendMessage(plugin.getMessage("cooktime.normal"));
                System.out.println("For furnace " + fo.creator + ": " + fo.X + ", " + fo.Y + ", " + fo.Z + " in " + fo.world);
                System.out.println("CookTime > multiplier " + newCookTime + " " + multiplier + " cooking " + cf.getInventory().getItem(0).getType());
                System.out.println("LavaFurnace: modifyCookTime(fo, userIndex)");
                System.out.println("+---+");
            }
        }
        else if (this.plugin.datawriter.getLFDebug() == 7) {
            System.out.println("+---+");
            System.out.println("LavaFurnace: Furnace " + fo.creator + ": " + fo.X + ", " + fo.Y + ", " + fo.Z + " in " + fo.world);
            System.out.println("Inventory Slot 1 = " + this.getTypeId(cf.getInventory().getItem(1)) + " Inventory Slot 0 = " + this.getTypeId(cf.getInventory().getItem(0)) + " lavaPowered? " + this.plugin.furnaceHelper.isLavaPowered(fo));
            System.out.println("LavaFurnace: modifyCookTime(fo, userIndex)");
            System.out.println("+---+");
        }
    }
    
    private int getTypeId(final ItemStack item) {
        if (item == null) {
            return 0;
        }
        return item.getTypeId();
    }
}
