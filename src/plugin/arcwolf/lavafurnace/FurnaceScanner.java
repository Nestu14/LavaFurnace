// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import org.bukkit.World;
import java.util.Iterator;
import org.bukkit.block.Sign;

public class FurnaceScanner implements Runnable
{
    private LavaFurnace plugin;
    private FurnaceHelper furnaceHelper;
    private DataWriter dataWriter;
    
    public FurnaceScanner(final LavaFurnace instance) {
        this.plugin = instance;
        this.furnaceHelper = this.plugin.furnaceHelper;
        this.dataWriter = this.plugin.datawriter;
    }
    
    @Override
    public void run() {
        int index = 0;
        boolean furnaceStart = false;
        Sign sign = null;
        for (final FurnaceObject fo : this.dataWriter.lfObject) {
            furnaceStart = fo.furnaceInit;
            try {
                final World world = this.plugin.getWorld(fo.world);
                if (world.getBlockAt(fo.X, fo.Y, fo.Z).getTypeId() == 68) {
                    sign = (Sign)world.getBlockAt(fo.X, fo.Y, fo.Z).getState();
                    if (sign.getLine(0).contains("[LAVAFURNACE]") || sign.getLine(0).contains("[lavafurnace]")) {
                        sign.setLine(0, "");
                        sign.setLine(1, "&9[LAVAFURNACE]");
                        sign.setLine(1, sign.getLine(1).replaceFirst("&([0-9a-f])", "\\§$1"));
                        sign.update();
                    }
                }
                if (sign != null) {
                    if (sign.getLine(0).contains("[LAVAFURNACE]") || sign.getLine(0).contains("[lavafurnace]") || sign.getLine(1).contains("[LAVAFURNACE]") || sign.getLine(1).contains("[lavafurnace]")) {
                        if (this.furnaceHelper.isFurnace(fo) || this.furnaceHelper.isFurnacePowered(fo)) {
                            if (!furnaceStart || fo.power != -1) {
                                this.furnaceHelper.maintainLavaFurnace(fo);
                            }
                            if (this.dataWriter.isProductChests() && new ChestHelper(this.plugin, fo).isChestPair()) {
                                this.plugin.chestprocessing.processChests(fo);
                            }
                        }
                        else if (this.furnaceHelper.getChunkLoaded(fo) && this.dataWriter.getLoadTimer() > 5 && !this.furnaceHelper.resetGlassDoor(fo)) {
                            if (world.getBlockAt(fo.X, fo.Y, fo.Z).getTypeId() == 68) {
                                sign.setLine(0, "");
                                sign.setLine(1, "");
                                sign.setLine(2, "");
                                sign.setLine(3, "");
                                sign.update();
                            }
                            new RedStoneHelper(this.plugin).resetFurnaceLever(fo);
                            this.dataWriter.deleteFurnace(fo);
                            this.dataWriter.lfObject.remove(fo);
                            if (this.dataWriter.getLFDebug() == 3) {
                                LavaFurnace.LOGGER.info("furnace is broke deleted index=" + index + " facing=" + fo.facing + " X=" + fo.X + " Y=" + fo.Y + " Z=" + fo.Z + " world=" + fo.world);
                                break;
                            }
                            break;
                        }
                    }
                    else if (this.furnaceHelper.getChunkLoaded(fo) && this.dataWriter.getLoadTimer() > 5) {
                        this.furnaceHelper.resetGlassDoor(fo);
                        new RedStoneHelper(this.plugin).resetFurnaceLever(fo);
                        this.dataWriter.deleteFurnace(fo);
                        this.dataWriter.lfObject.remove(fo);
                        if (this.dataWriter.getLFDebug() == 3) {
                            LavaFurnace.LOGGER.info("sign text= " + sign.getLine(0));
                            LavaFurnace.LOGGER.info("sign wrong deleting " + index + " facing=" + fo.facing + " X=" + fo.X + " Y=" + fo.Y + " Z=" + fo.Z + " world=" + fo.world);
                            break;
                        }
                        break;
                    }
                }
                else if (this.furnaceHelper.getChunkLoaded(fo) && this.dataWriter.getLoadTimer() > 5) {
                    this.furnaceHelper.resetGlassDoor(fo);
                    new RedStoneHelper(this.plugin).resetFurnaceLever(fo);
                    this.dataWriter.deleteFurnace(fo);
                    this.dataWriter.lfObject.remove(fo);
                    if (this.dataWriter.getLFDebug() == 3) {
                        LavaFurnace.LOGGER.info("sign doesnt exist deleting " + index + " facing=" + fo.facing + " X=" + fo.X + " Y=" + fo.Y + " Z=" + fo.Z + " world=" + fo.world);
                        break;
                    }
                    break;
                }
                ++index;
                sign = null;
            }
            catch (Exception e) {
                if (this.dataWriter.getLFDebug() != 5) {
                    continue;
                }
                final StringBuffer buff = new StringBuffer();
                for (int i = 0; i < fo.toString().length(); ++i) {
                    buff.append((fo.toString().charAt(i) == LavaFurnace.SEPERATOR) ? ", " : fo.toString().charAt(i));
                }
                LavaFurnace.LOGGER.info("LavaFurnace: scanner error in-> \n" + (Object)buff + "\n Exception caught is: \n");
                e.printStackTrace();
            }
        }
        this.dataWriter.incrementLoadTimer();
        this.plugin.cookTimeProcessing.process();
    }
}
