// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import java.util.List;

public class UserCookTimeHelper
{
    private LavaFurnace plugin;
    private List<UserCookTimes> lfCook;
    
    public UserCookTimeHelper(final LavaFurnace instance) {
        this.plugin = instance;
        this.lfCook = this.plugin.datawriter.lfCook;
    }
    
    public int createUser(final String in_username) {
        int index = -1;
        if (index == -1) {
            this.lfCook.add(new UserCookTimes(in_username, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
            for (int i = 0; i < this.lfCook.size(); ++i) {
                if (this.lfCook.get(i).userName.equals(in_username)) {
                    index = i;
                }
            }
        }
        return index;
    }
    
    public int findUser(final String in_username) {
        int index = -1;
        for (int i = 0; i < this.lfCook.size(); ++i) {
            if (this.lfCook.get(i).userName.equals(in_username)) {
                index = i;
            }
        }
        return index;
    }
    
    public boolean setCookTimeMultiplier(final int userIndex, final int itemid, final int multiplier) {
        if (multiplier > 0 && multiplier < 11) {
            if (itemid == 4) {
                this.lfCook.get(userIndex).cobblestone = multiplier;
            }
            else if (itemid == 12) {
                this.lfCook.get(userIndex).sand = multiplier;
            }
            else if (itemid == 14) {
                this.lfCook.get(userIndex).goldore = multiplier;
            }
            else if (itemid == 15) {
                this.lfCook.get(userIndex).ironore = multiplier;
            }
            else if (itemid == 16) {
                this.lfCook.get(userIndex).coalore = multiplier;
            }
            else if (itemid == 17) {
                this.lfCook.get(userIndex).wood = multiplier;
            }
            else if (itemid == 21) {
                this.lfCook.get(userIndex).lapisore = multiplier;
            }
            else if (itemid == 56) {
                this.lfCook.get(userIndex).diamondore = multiplier;
            }
            else if (itemid == 73) {
                this.lfCook.get(userIndex).redstoneore = multiplier;
            }
            else if (itemid == 81) {
                this.lfCook.get(userIndex).cactus = multiplier;
            }
            else if (itemid == 82) {
                this.lfCook.get(userIndex).clayblock = multiplier;
            }
            else if (itemid == 129) {
                this.lfCook.get(userIndex).emeraldore = multiplier;
            }
            else if (itemid == 319) {
                this.lfCook.get(userIndex).rawporkchop = multiplier;
            }
            else if (itemid == 337) {
                this.lfCook.get(userIndex).clay = multiplier;
            }
            else if (itemid == 349) {
                this.lfCook.get(userIndex).rawfish = multiplier;
            }
            else if (itemid == 363) {
                this.lfCook.get(userIndex).rawbeef = multiplier;
            }
            else if (itemid == 365) {
                this.lfCook.get(userIndex).rawchicken = multiplier;
            }
            else if (itemid == 392) {
                this.lfCook.get(userIndex).rawpotato = multiplier;
            }
            if (itemid == 87) {
                this.lfCook.get(userIndex).netherrack = multiplier;
            }
            if (itemid == 153) {
                this.lfCook.get(userIndex).netherquartz = multiplier;
            }
            return true;
        }
        return false;
    }
    
    public double getCookTimeMultiplier(final int userIndex, final int itemid) {
        if (itemid == 4) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).cobblestone);
        }
        if (itemid == 12) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).sand);
        }
        if (itemid == 14) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).goldore);
        }
        if (itemid == 15) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).ironore);
        }
        if (itemid == 16) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).coalore);
        }
        if (itemid == 17) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).wood);
        }
        if (itemid == 21) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).lapisore);
        }
        if (itemid == 56) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).diamondore);
        }
        if (itemid == 73) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).redstoneore);
        }
        if (itemid == 81) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).cactus);
        }
        if (itemid == 82) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).clayblock);
        }
        if (itemid == 129) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).emeraldore);
        }
        if (itemid == 319) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).rawporkchop);
        }
        if (itemid == 337) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).clay);
        }
        if (itemid == 349) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).rawfish);
        }
        if (itemid == 363) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).rawbeef);
        }
        if (itemid == 365) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).rawchicken);
        }
        if (itemid == 392) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).rawpotato);
        }
        if (itemid == 87) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).netherrack);
        }
        if (itemid == 153) {
            return this.cookTimeMultiplier(this.lfCook.get(userIndex).netherquartz);
        }
        return 1.0;
    }
    
    public int getItemId(final String item_Name) {
        if (item_Name.equalsIgnoreCase("cobblestone") || item_Name.equalsIgnoreCase("cobble") || item_Name.equals("4") || item_Name.equals("04")) {
            return 4;
        }
        if (item_Name.equalsIgnoreCase("sand") || item_Name.equals("12")) {
            return 12;
        }
        if (item_Name.equalsIgnoreCase("goldore") || item_Name.equals("14")) {
            return 14;
        }
        if (item_Name.equalsIgnoreCase("ironore") || item_Name.equals("15")) {
            return 15;
        }
        if (item_Name.equalsIgnoreCase("coalore") || item_Name.equals("16")) {
            return 16;
        }
        if (item_Name.equalsIgnoreCase("log") || item_Name.equalsIgnoreCase("logs") || item_Name.equals("17")) {
            return 17;
        }
        if (item_Name.equalsIgnoreCase("lapisore") || item_Name.equalsIgnoreCase("lapislazuliore") || item_Name.equals("21")) {
            return 21;
        }
        if (item_Name.equalsIgnoreCase("diamondore") || item_Name.equals("56")) {
            return 56;
        }
        if (item_Name.equalsIgnoreCase("restoneore") || item_Name.equals("73")) {
            return 73;
        }
        if (item_Name.equalsIgnoreCase("cactus") || item_Name.equals("81")) {
            return 81;
        }
        if (item_Name.equalsIgnoreCase("clayblock") || item_Name.equals("82")) {
            return 82;
        }
        if (item_Name.equalsIgnoreCase("emeraldore") || item_Name.equals("129")) {
            return 129;
        }
        if (item_Name.equalsIgnoreCase("pork") || item_Name.equalsIgnoreCase("rawpork") || item_Name.equalsIgnoreCase("porkchop") || item_Name.equalsIgnoreCase("rawporkchop") || item_Name.equals("319")) {
            return 319;
        }
        if (item_Name.equalsIgnoreCase("clay") || item_Name.equals("337")) {
            return 337;
        }
        if (item_Name.equalsIgnoreCase("fish") || item_Name.equalsIgnoreCase("rawfish") || item_Name.equals("349")) {
            return 349;
        }
        if (item_Name.equalsIgnoreCase("beef") || item_Name.equalsIgnoreCase("rawbeef") || item_Name.equals("363")) {
            return 363;
        }
        if (item_Name.equalsIgnoreCase("chicken") || item_Name.equalsIgnoreCase("rawchicken") || item_Name.equals("365")) {
            return 365;
        }
        if (item_Name.equalsIgnoreCase("potato") || item_Name.equalsIgnoreCase("rawpotato") || item_Name.equals("392")) {
            return 392;
        }
        if (item_Name.equalsIgnoreCase("netherrack") || item_Name.equals("87")) {
            return 87;
        }
        if (item_Name.equalsIgnoreCase("netherquartz") || item_Name.equalsIgnoreCase("netherquartzore") || item_Name.equals("153")) {
            return 153;
        }
        return -1;
    }
    
    private int cookTimeMultiplier(final int itemMultiplier) {
        return (int)Math.floor(200.0 - 10.0 / itemMultiplier * 20.0);
    }
}
