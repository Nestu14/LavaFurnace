// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

public class UserCookTimes
{
    public String userName;
    public int ironore;
    public int goldore;
    public int sand;
    public int cobblestone;
    public int rawporkchop;
    public int clay;
    public int rawfish;
    public int wood;
    public int cactus;
    public int clayblock;
    public int diamondore;
    public int rawbeef;
    public int rawchicken;
    public int lapisore;
    public int redstoneore;
    public int coalore;
    public int rawpotato;
    public int emeraldore;
    public int netherrack;
    public int netherquartz;
    
    public UserCookTimes(final String in_userName, final int in_IronOre, final int in_GoldOre, final int in_DiamondOre, final int in_Cobblestone, final int in_Clay, final int in_Sand, final int in_Wood, final int in_Cactus, final int in_RawPorkchop, final int in_RawFish, final int in_RawBeef, final int in_RawChicken, final int in_LapisOre, final int in_RedstoneOre, final int in_coalore, final int in_rawpotato, final int in_emeraldore, final int in_netherrack, final int in_netherquartz, final int in_Clayblock) {
        this.userName = in_userName;
        this.ironore = in_IronOre;
        this.goldore = in_GoldOre;
        this.diamondore = in_DiamondOre;
        this.cobblestone = in_Cobblestone;
        this.clay = in_Clay;
        this.sand = in_Sand;
        this.wood = in_Wood;
        this.cactus = in_Cactus;
        this.clayblock = in_Clayblock;
        this.rawporkchop = in_RawPorkchop;
        this.rawfish = in_RawFish;
        this.rawbeef = in_RawBeef;
        this.rawchicken = in_RawChicken;
        this.lapisore = in_LapisOre;
        this.redstoneore = in_RedstoneOre;
        this.coalore = in_coalore;
        this.rawpotato = in_rawpotato;
        this.emeraldore = in_emeraldore;
        this.netherrack = in_netherrack;
        this.netherquartz = in_netherquartz;
    }
    
    public UserCookTimes(final String in_String) {
        final String[] split = in_String.split(LavaFurnace.SEPERATOR.toString());
        this.userName = split[0];
        this.ironore = Integer.parseInt(split[1]);
        this.goldore = Integer.parseInt(split[2]);
        this.diamondore = Integer.parseInt(split[3]);
        this.cobblestone = Integer.parseInt(split[4]);
        this.clay = Integer.parseInt(split[5]);
        this.sand = Integer.parseInt(split[6]);
        this.wood = Integer.parseInt(split[7]);
        this.cactus = Integer.parseInt(split[8]);
        this.rawporkchop = Integer.parseInt(split[9]);
        this.rawfish = Integer.parseInt(split[10]);
        this.rawbeef = Integer.parseInt(split[11]);
        this.rawchicken = Integer.parseInt(split[12]);
        this.lapisore = Integer.parseInt(split[13]);
        this.redstoneore = Integer.parseInt(split[14]);
        this.coalore = Integer.parseInt(split[15]);
        switch (split.length) {
            case 16: {
                this.rawpotato = 1;
                this.emeraldore = 1;
                this.netherrack = 1;
                this.netherquartz = 1;
                this.clayblock = 1;
                break;
            }
            case 17: {
                this.rawpotato = Integer.parseInt(split[16]);
                this.emeraldore = 1;
                this.netherrack = 1;
                this.netherquartz = 1;
                this.clayblock = 1;
                break;
            }
            case 18: {
                this.rawpotato = Integer.parseInt(split[16]);
                this.emeraldore = Integer.parseInt(split[17]);
                this.netherrack = 1;
                this.netherquartz = 1;
                this.clayblock = 1;
                break;
            }
            case 20: {
                this.rawpotato = Integer.parseInt(split[16]);
                this.emeraldore = Integer.parseInt(split[17]);
                this.netherrack = Integer.parseInt(split[18]);
                this.netherquartz = Integer.parseInt(split[19]);
                this.clayblock = 1;
                break;
            }
            default: {
                this.rawpotato = Integer.parseInt(split[16]);
                this.emeraldore = Integer.parseInt(split[17]);
                this.netherrack = Integer.parseInt(split[18]);
                this.netherquartz = Integer.parseInt(split[19]);
                this.clayblock = Integer.parseInt(split[20]);
                break;
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.userName);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.ironore);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.goldore);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.diamondore);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.cobblestone);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.clay);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.sand);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.wood);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.cactus);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.rawporkchop);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.rawfish);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.rawbeef);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.rawchicken);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.lapisore);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.redstoneore);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.coalore);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.rawpotato);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.emeraldore);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.netherrack);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.netherquartz);
        builder.append(LavaFurnace.SEPERATOR);
        builder.append(this.clayblock);
        return builder.toString();
    }
}
