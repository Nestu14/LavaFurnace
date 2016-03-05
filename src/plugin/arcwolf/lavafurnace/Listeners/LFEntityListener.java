// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace.Listeners;

import org.bukkit.entity.Enderman;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityExplodeEvent;
import plugin.arcwolf.lavafurnace.DataWriter;
import plugin.arcwolf.lavafurnace.LavaFurnace;
import org.bukkit.event.Listener;

public class LFEntityListener implements Listener
{
    private LavaFurnace plugin;
    private DataWriter dataWriter;
    
    public LFEntityListener(final LavaFurnace instance) {
        this.plugin = instance;
        this.dataWriter = this.plugin.datawriter;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (this.dataWriter.isExplosionProof()) {
            for (final Block expBlock : event.blockList()) {
                if (this.dataWriter.furnaceBlockMap.containsKey(expBlock)) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityChangeBlock(final EntityChangeBlockEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (this.plugin.datawriter.isEnderProtect() && event.getEntity() instanceof Enderman && this.dataWriter.furnaceBlockMap.containsKey(event.getBlock())) {
            event.setCancelled(true);
        }
    }
}
