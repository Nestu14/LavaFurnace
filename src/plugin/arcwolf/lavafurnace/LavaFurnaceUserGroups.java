// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

public class LavaFurnaceUserGroups
{
    String groupName;
    int furnaceAmount;
    
    public LavaFurnaceUserGroups(final String groupName, final int furnaceAmount) {
        this.groupName = "";
        this.groupName = groupName;
        this.furnaceAmount = furnaceAmount;
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    
    public int getFurnaceAmount() {
        return this.furnaceAmount;
    }
}
