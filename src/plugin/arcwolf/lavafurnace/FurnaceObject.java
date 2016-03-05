// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

public class FurnaceObject
{
    public String creator;
    public String world;
    public int facing;
    public int X;
    public int Y;
    public int Z;
    public int power;
    public boolean furnaceInit;
    
    public FurnaceObject(final String in_Creator, final String in_World, final byte in_Facing, final int in_X, final int in_Y, final int in_Z, final int in_Power) {
        this.creator = "";
        this.world = "";
        this.furnaceInit = false;
        this.creator = in_Creator;
        this.world = in_World;
        this.facing = in_Facing;
        this.X = in_X;
        this.Y = in_Y;
        this.Z = in_Z;
        this.power = in_Power;
    }
    
    public FurnaceObject(final String in_String) {
        this.creator = "";
        this.world = "";
        this.furnaceInit = false;
        final String[] split = in_String.split(LavaFurnace.SEPERATOR.toString());
        if (split.length == 7) {
            this.creator = split[0];
            this.world = split[1];
            this.facing = Byte.parseByte(split[2]);
            this.X = Integer.parseInt(split[3]);
            this.Y = Integer.parseInt(split[4]);
            this.Z = Integer.parseInt(split[5]);
            this.power = Integer.parseInt(split[6]);
        }
        else {
            LavaFurnace.LOGGER.info("LavaFurnace: Error parsing Lava Furnace database entry");
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.creator);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.world);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.facing);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.X);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.Y);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.Z);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.power);
        return builder.toString();
    }
}
