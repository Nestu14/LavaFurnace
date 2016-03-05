// 
// Decompiled by Procyon v0.5.30
// 

package plugin.arcwolf.lavafurnace;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import org.bukkit.block.Block;
import java.util.Map;
import java.util.List;

public class DataWriter
{
    private double Version;
    private static final int CONFIG_OPTIONS = 25;
    LavaFurnace plugin;
    public final List<FurnaceObject> lfObject;
    public final List<UserCookTimes> lfCook;
    public final List<LavaFurnaceUserGroups> lfUserGroups;
    public final List<Integer> customSmeltables;
    public final List<Integer> customFuel;
    public final List<String> pluginNames;
    public Map<Block, FurnaceObject> furnaceBlockMap;
    private String oldFurnacesFile;
    private String oldCooktimeFile;
    private String furnacesFile;
    private String cooktimeFile;
    private String configFile;
    private String userGroups;
    private File pluginDirectory;
    private File oldFurnaceDatabase;
    private File oldCookTimeDatabase;
    private File furnaceDatabase;
    private File cookTimeDatabase;
    private File configData;
    private File userGroupData;
    private List<String> erMsg;
    private int furnaceTimer;
    private boolean infiniteTimer;
    private boolean productChests;
    private int maxForges;
    private int LFDebug;
    private int loadTimer;
    private boolean freeForAll;
    private boolean freeForAllChests;
    private boolean useLargeChests;
    private boolean sourceChestFuel;
    private boolean smartFurnace;
    private int cookTimeMultiplier;
    private boolean explosionProof;
    private boolean pistonProtect;
    private boolean enableConsoleCommands;
    private boolean enderProtect;
    private boolean showFuelLevel;
    private int mBLayerOne;
    private int mBLayerOneData;
    private int mBLayerTwo;
    private int mBLayerTwoData;
    private int mBLayerThree;
    private int mBLayerThreeData;
    private int beltBlocks;
    private int beltBlockData;
    private int doorBlock;
    private int doorBlockData;
    private boolean furnaceDetectionl1;
    private boolean furnaceDetectionl2;
    private boolean furnaceDetectionl3;
    private boolean furnaceDetectionbl;
    private int fdFacing;
    private String customSmeltablesString;
    private String customFuelString;
    private int userGroupCount;
    
    public DataWriter(final LavaFurnace instance) {
        this.lfObject = new ArrayList<FurnaceObject>();
        this.lfCook = new ArrayList<UserCookTimes>();
        this.lfUserGroups = new ArrayList<LavaFurnaceUserGroups>();
        this.customSmeltables = new ArrayList<Integer>();
        this.customFuel = new ArrayList<Integer>();
        this.pluginNames = new ArrayList<String>();
        this.furnaceBlockMap = new HashMap<Block, FurnaceObject>();
        this.oldFurnacesFile = "furnaces.dat";
        this.oldCooktimeFile = "cooktimes.dat";
        this.furnacesFile = "furnaces.bin";
        this.cooktimeFile = "cooktimes.bin";
        this.configFile = "config.ini";
        this.userGroups = "usergroups.ini";
        this.erMsg = new ArrayList<String>();
        this.furnaceTimer = 102400;
        this.infiniteTimer = false;
        this.productChests = false;
        this.maxForges = 1;
        this.LFDebug = 0;
        this.loadTimer = 0;
        this.freeForAll = false;
        this.freeForAllChests = false;
        this.useLargeChests = true;
        this.sourceChestFuel = false;
        this.smartFurnace = true;
        this.cookTimeMultiplier = 1;
        this.explosionProof = true;
        this.pistonProtect = false;
        this.enableConsoleCommands = false;
        this.enderProtect = false;
        this.showFuelLevel = false;
        this.mBLayerOne = 49;
        this.mBLayerOneData = 0;
        this.mBLayerTwo = 49;
        this.mBLayerTwoData = 0;
        this.mBLayerThree = 49;
        this.mBLayerThreeData = 0;
        this.beltBlocks = 67;
        this.beltBlockData = 0;
        this.doorBlock = 20;
        this.doorBlockData = 0;
        this.furnaceDetectionl1 = false;
        this.furnaceDetectionl2 = false;
        this.furnaceDetectionl3 = false;
        this.furnaceDetectionbl = false;
        this.fdFacing = 0;
        this.customSmeltablesString = "";
        this.customFuelString = "";
        this.userGroupCount = 0;
        this.plugin = instance;
        this.pluginDirectory = this.plugin.getDataFolder();
        this.oldFurnaceDatabase = new File(this.pluginDirectory, this.oldFurnacesFile);
        this.oldCookTimeDatabase = new File(this.pluginDirectory, this.oldCooktimeFile);
        this.furnaceDatabase = new File(this.pluginDirectory, this.furnacesFile);
        this.cookTimeDatabase = new File(this.pluginDirectory, this.cooktimeFile);
        this.configData = new File(this.pluginDirectory, this.configFile);
        this.userGroupData = new File(this.pluginDirectory, this.userGroups);
    }
    
    public void init() {
        boolean initialized = false;
        if (initialized) {
            return;
        }
        initialized = true;
        if (!this.pluginDirectory.exists()) {
            LavaFurnace.LOGGER.info("LavaFurnace folder does not exist - creating it... ");
            final boolean chk = this.pluginDirectory.mkdir();
            if (chk) {
                LavaFurnace.LOGGER.info("Successfully created folder.");
            }
            else {
                LavaFurnace.LOGGER.info("Unable to create folder!");
            }
        }
        if (!this.oldFurnaceDatabase.exists() && !this.furnaceDatabase.exists()) {
            FileWriter fwriter = null;
            BufferedWriter writer = null;
            try {
                LavaFurnace.LOGGER.info("LavaFurnace Furnace Data File does not exist - creating it... ");
                fwriter = new FileWriter(this.furnaceDatabase);
                writer = new BufferedWriter(fwriter);
                writer.write("#Do Not Edit This File\r\n");
                writer.write("#\r\n");
            }
            catch (Exception e) {
                LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while creating " + this.furnacesFile, e);
            }
            finally {
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        LavaFurnace.LOGGER.info("Successfully created New Furnace Data File.");
                    }
                    catch (IOException ex) {}
                }
                if (fwriter != null) {
                    try {
                        fwriter.close();
                    }
                    catch (IOException ex2) {}
                }
            }
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                    LavaFurnace.LOGGER.info("Successfully created New Furnace Data File.");
                }
                catch (IOException ex3) {}
            }
            if (fwriter != null) {
                try {
                    fwriter.close();
                }
                catch (IOException ex4) {}
            }
        }
        else if (this.oldFurnaceDatabase.exists() && !this.furnaceDatabase.exists()) {
            this.convertFurnaceDatabase();
        }
        if (!this.oldCookTimeDatabase.exists() && !this.cookTimeDatabase.exists()) {
            FileWriter fwriter = null;
            BufferedWriter writer = null;
            try {
                LavaFurnace.LOGGER.info("LavaFurnace Cook Time Data File does not exist - creating it...");
                fwriter = new FileWriter(this.cookTimeDatabase);
                writer = new BufferedWriter(fwriter);
                writer.write("#Do Not Edit This File\r\n");
                writer.write("#\r\n");
            }
            catch (Exception e) {
                LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while creating " + this.cooktimeFile, e);
            }
            finally {
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        LavaFurnace.LOGGER.info("Successfully created New Cook Time Data File.");
                    }
                    catch (IOException ex5) {}
                }
                if (fwriter != null) {
                    try {
                        fwriter.close();
                    }
                    catch (IOException ex6) {}
                }
            }
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                    LavaFurnace.LOGGER.info("Successfully created New Cook Time Data File.");
                }
                catch (IOException ex7) {}
            }
            if (fwriter != null) {
                try {
                    fwriter.close();
                }
                catch (IOException ex8) {}
            }
        }
        else if (this.oldCookTimeDatabase.exists() && !this.cookTimeDatabase.exists()) {
            this.convertCookTimeDatabase();
        }
        if (!this.configData.exists()) {
            LavaFurnace.LOGGER.info("LavaFurnace Config File does not exist - creating it...");
            this.writeConfig();
        }
        if (!this.userGroupData.exists()) {
            FileWriter fwriter = null;
            BufferedWriter writer = null;
            try {
                fwriter = new FileWriter(this.userGroupData);
                writer = new BufferedWriter(fwriter);
                LavaFurnace.LOGGER.info("LavaFurnace UserGroups File does not exist - creating it...");
                writer.write("# User Groups are entered in the following format:\r\n");
                writer.write("#\r\n");
                writer.write("# groupname,furnacequantity\r\n");
                writer.write("#\r\n");
                writer.write("#EX:\r\n");
                writer.write("# somegroup,2\r\n");
                writer.write("# anothergroup,5\r\n");
                writer.write("#\r\n");
                writer.write("# The permissions would then be: \r\n");
                writer.write("# lavafurnace.furnacelimit.somegroup\r\n");
                writer.write("# &\r\n");
                writer.write("# lavafurnace.furnacelimit.anothergroup\r\n");
                writer.write("#\r\n");
                writer.write("# \"somegroup\" members would only be able to create 2 furnaces\r\n");
                writer.write("# while \"anothergroup\" members would be able to create 5\r\n");
                writer.write("#\r\n");
                writer.write("default,5\r\n");
            }
            catch (Exception e) {
                LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while creating " + this.userGroups, e);
            }
            finally {
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        LavaFurnace.LOGGER.info("Successfully created New User Group Data File.");
                    }
                    catch (IOException ex9) {}
                }
                if (fwriter != null) {
                    try {
                        fwriter.close();
                    }
                    catch (IOException ex10) {}
                }
            }
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                    LavaFurnace.LOGGER.info("Successfully created New User Group Data File.");
                }
                catch (IOException ex11) {}
            }
            if (fwriter != null) {
                try {
                    fwriter.close();
                }
                catch (IOException ex12) {}
            }
        }
    }
    
    private void convertFurnaceDatabase() {
        String line = "";
        final StringBuffer updatedString = new StringBuffer();
        FileWriter fwriter = null;
        BufferedWriter writer = null;
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.oldFurnaceDatabase));
            while ((line = reader.readLine()) != null) {
                if (!line.contains("#")) {
                    updatedString.append(String.valueOf(line.replaceAll(",", LavaFurnace.SEPERATOR.toString())) + "\r\n");
                }
                else {
                    updatedString.append(String.valueOf(line) + "\r\n");
                }
            }
            reader.close();
            final File backupOldFile = new File(this.pluginDirectory, "furnaces.bak");
            if (!this.oldFurnaceDatabase.renameTo(backupOldFile)) {
                LavaFurnace.LOGGER.warning("LavaFurnace, " + this.oldFurnaceDatabase.getAbsolutePath() + " Data File could not be renamed! ");
                LavaFurnace.LOGGER.warning("Rename or delete file manually! ");
            }
            fwriter = new FileWriter(this.furnaceDatabase);
            writer = new BufferedWriter(fwriter);
            writer.write(updatedString.toString());
            fwriter.flush();
            writer.flush();
            writer.close();
            fwriter.close();
            LavaFurnace.LOGGER.info("LavaFurnace Furnace Data File successfully updated... ");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        catch (Exception e3) {
            e3.printStackTrace();
        }
    }
    
    private void convertCookTimeDatabase() {
        String line = "";
        StringBuffer updatedString = new StringBuffer();
        FileWriter fwriter = null;
        BufferedWriter writer = null;
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.oldCookTimeDatabase));
            updatedString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                if (!line.contains("#")) {
                    final int lineCounter = line.split(",").length;
                    if (lineCounter != 16) {
                        for (int i = lineCounter; i < 16; ++i) {
                            line = String.valueOf(line) + ",1";
                        }
                    }
                    updatedString.append(String.valueOf(line.replaceAll(",", LavaFurnace.SEPERATOR.toString())) + "\r\n");
                }
                else {
                    updatedString.append(String.valueOf(line) + "\r\n");
                }
            }
            reader.close();
            final File backupOldFile = new File(this.pluginDirectory, "cooktimes.bak");
            if (!this.oldCookTimeDatabase.renameTo(backupOldFile)) {
                LavaFurnace.LOGGER.warning("LavaFurnace, " + this.oldCookTimeDatabase.getAbsolutePath() + " Data File could not be renamed! ");
                LavaFurnace.LOGGER.warning("Rename or delete file manually! ");
            }
            fwriter = new FileWriter(this.cookTimeDatabase);
            writer = new BufferedWriter(fwriter);
            writer.write(updatedString.toString());
            fwriter.flush();
            writer.flush();
            writer.close();
            fwriter.close();
            LavaFurnace.LOGGER.info("LavaFurnace CookTimes Data File successfully updated... ");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        catch (Exception e3) {
            e3.printStackTrace();
        }
    }
    
    private boolean resetConfig() {
        try {
            final File f = this.configData;
            if (!f.exists()) {
                return false;
            }
            if (!f.canWrite()) {
                return false;
            }
            final boolean success = f.delete();
            if (!success) {
                return false;
            }
            this.writeConfig();
        }
        catch (Exception e) {
            LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while deleting config " + this.configFile, e);
            return false;
        }
        return true;
    }
    
    private void writeConfig() {
        FileWriter fwriter = null;
        BufferedWriter writer = null;
        try {
            fwriter = new FileWriter(this.configData);
            writer = new BufferedWriter(fwriter);
            writer.write("Version=" + this.Version + "\r\n");
            writer.write("Config created: " + getDateTime() + "\r\n");
            writer.write("#\r\n");
            writer.write("# Lava time from 1 to 2147483647 \r\n");
            writer.write("# Coal = 1600  LavaBucket = 20000\r\n");
            writer.write("#\r\n");
            writer.write("lava_furnace_timer=" + this.furnaceTimer + "\r\n");
            writer.write("#\r\n");
            writer.write("# 1 to 10 times normal speed\r\n");
            writer.write("#\r\n");
            writer.write("cook_timer=" + this.cookTimeMultiplier + "\r\n");
            writer.write("#\r\n");
            writer.write("infinite_lava=" + this.infiniteTimer + "\r\n");
            writer.write("#\r\n");
            writer.write("max_player_furnaces=" + this.maxForges + "\r\n");
            writer.write("#\r\n");
            writer.write("allow_production_chests=" + this.productChests + "\r\n");
            writer.write("#\r\n");
            writer.write("allow_freeforall=" + this.freeForAll + "\r\n");
            writer.write("#\r\n");
            writer.write("allow_freeforall_chests=" + this.freeForAllChests + "\r\n");
            writer.write("#\r\n");
            writer.write("allow_source_chest_fuel=" + this.sourceChestFuel + "\r\n");
            writer.write("#\r\n");
            writer.write("use_large_chests=" + this.useLargeChests + "\r\n");
            writer.write("#\r\n");
            writer.write("explosion_proof=" + this.explosionProof + "\r\n");
            writer.write("#\r\n");
            writer.write("piston_protection=" + this.pistonProtect + "\r\n");
            writer.write("#\r\n");
            writer.write("enderman_protection=" + this.enderProtect + "\r\n");
            writer.write("#\r\n");
            writer.write("console_commands=" + this.enableConsoleCommands + "\r\n");
            writer.write("#\r\n");
            writer.write("sign_show_fuel_level=" + this.showFuelLevel + "\r\n");
            writer.write("#\r\n");
            writer.write("use_smartFurnace=" + this.smartFurnace + "\r\n");
            writer.write("#\r\n");
            writer.write("#Custom Smeltables are comma seperated integers\r\n");
            writer.write("#Ex: custom_smeltables=1,225,10,19\r\n");
            writer.write("#\r\n");
            writer.write("custom_smeltables=" + this.customSmeltablesString + "\r\n");
            writer.write("#\r\n");
            writer.write("#Custom fuel are comma seperated integers\r\n");
            writer.write("#Ex: custom_fuel=268,269,270,271,290\r\n");
            writer.write("custom_fuel=" + this.customFuelString + "\r\n");
            writer.write("#\r\n");
            writer.write("################################\r\n");
            writer.write("#    Furnace Block Settings    #\r\n");
            writer.write("################################\r\n");
            writer.write("layer_one_blocks=" + this.mBLayerOne + ":" + this.mBLayerOneData + "\r\n");
            writer.write("layer_two_blocks=" + this.mBLayerTwo + ":" + this.mBLayerTwoData + "\r\n");
            writer.write("layer_three_blocks=" + this.mBLayerThree + ":" + this.mBLayerThreeData + "\r\n");
            writer.write("belt_blocks=" + this.beltBlocks + ":" + this.beltBlockData + "\r\n");
            writer.write("door_block=" + this.doorBlock + ":" + this.doorBlockData + "\r\n");
            writer.write("################################\r\n");
            writer.write("#\r\n");
            writer.write("# Debug levels 1 to 6\r\n");
            writer.write("#\r\n");
            writer.write("Debug=" + this.LFDebug + "\r\n");
            writer.write("#\r\n");
            writer.write("#A comma seperated list of plugins LavaFurnace should attempt to be compatable with.\r\n");
            writer.write("#The official name for a plugin is found in its plugin.yml file inside the plugin Jar.\r\n");
            writer.write("#Look for a field called name: PluginName\r\n");
            writer.write("#Ex: Compatability=SomePlugin,AnotherPlugin,YetAnotherPlugin\r\n");
            writer.write("#\r\n");
            writer.write("Compatibility=\r\n");
        }
        catch (Exception e) {
            LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while creating " + this.configFile, e);
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                    LavaFurnace.LOGGER.info("Successfully created New Config File.");
                }
                catch (IOException e2) {
                    LavaFurnace.LOGGER.log(Level.SEVERE, "LavaFurnace error " + e2);
                }
            }
            if (fwriter != null) {
                try {
                    fwriter.close();
                }
                catch (IOException e2) {
                    LavaFurnace.LOGGER.log(Level.SEVERE, "LavaFurnace error " + e2);
                }
            }
            return;
        }
        finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                    LavaFurnace.LOGGER.info("Successfully created New Config File.");
                }
                catch (IOException e2) {
                    LavaFurnace.LOGGER.log(Level.SEVERE, "LavaFurnace error " + e2);
                }
            }
            if (fwriter != null) {
                try {
                    fwriter.close();
                }
                catch (IOException e2) {
                    LavaFurnace.LOGGER.log(Level.SEVERE, "LavaFurnace error " + e2);
                }
            }
        }
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
                LavaFurnace.LOGGER.info("Successfully created New Config File.");
            }
            catch (IOException e2) {
                LavaFurnace.LOGGER.log(Level.SEVERE, "LavaFurnace error " + e2);
            }
        }
        if (fwriter != null) {
            try {
                fwriter.close();
            }
            catch (IOException e2) {
                LavaFurnace.LOGGER.log(Level.SEVERE, "LavaFurnace error " + e2);
            }
        }
    }
    
    public void deleteUser(final String userName) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.cookTimeDatabase));
            String line = "";
            final StringBuffer text = new StringBuffer();
            boolean foundIt = false;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(userName)) {
                    text.append(String.valueOf(line) + "\r\n");
                }
                else {
                    if (foundIt) {
                        continue;
                    }
                    foundIt = true;
                }
            }
            reader.close();
            final FileWriter fwriter = new FileWriter(this.cookTimeDatabase);
            final BufferedWriter writer = new BufferedWriter(fwriter);
            writer.write(text.toString());
            writer.flush();
            writer.close();
            fwriter.close();
        }
        catch (Exception e) {
            LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while deleting objects in " + this.cooktimeFile, e);
        }
    }
    
    public void writeUser(final UserCookTimes userCookTimes) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.cookTimeDatabase));
            String line = "";
            String newline = "";
            final StringBuffer text = new StringBuffer();
            boolean foundIt = false;
            newline = userCookTimes.toString();
            while ((line = reader.readLine()) != null) {
                if (!line.contains(userCookTimes.userName)) {
                    text.append(String.valueOf(line) + "\r\n");
                }
                else {
                    if (foundIt) {
                        continue;
                    }
                    foundIt = true;
                    text.append(String.valueOf(newline) + "\r\n");
                }
            }
            if (!foundIt) {
                text.append(String.valueOf(newline) + "\r\n");
            }
            reader.close();
            final FileWriter fwriter = new FileWriter(this.cookTimeDatabase);
            final BufferedWriter writer = new BufferedWriter(fwriter);
            writer.write(text.toString());
            writer.flush();
            writer.close();
            fwriter.close();
        }
        catch (Exception e1) {
            LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while editing Users in " + this.cooktimeFile, e1);
        }
    }
    
    public void deleteFurnace(final FurnaceObject fo) {
        this.plugin.furnaceHelper.remFromFurnaceBlockMap(fo);
        final int X = fo.X;
        final int Y = fo.Y;
        final int Z = fo.Z;
        final int facing = fo.facing;
        final String creator = fo.creator;
        final String world = fo.world;
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.furnaceDatabase));
            String line = "";
            final StringBuffer text = new StringBuffer();
            boolean foundIt = false;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(String.valueOf(creator) + LavaFurnace.SEPERATOR + world + LavaFurnace.SEPERATOR + facing + LavaFurnace.SEPERATOR + X + LavaFurnace.SEPERATOR + Y + LavaFurnace.SEPERATOR + Z)) {
                    text.append(String.valueOf(line) + "\r\n");
                }
                else {
                    if (foundIt) {
                        continue;
                    }
                    foundIt = true;
                }
            }
            reader.close();
            final FileWriter fwriter = new FileWriter(this.furnaceDatabase);
            final BufferedWriter writer = new BufferedWriter(fwriter);
            writer.write(text.toString());
            writer.flush();
            writer.close();
            fwriter.close();
        }
        catch (Exception e1) {
            LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while deleting objects in " + this.furnacesFile, e1);
        }
    }
    
    public void writeFurnace(final FurnaceObject fo) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.furnaceDatabase));
            String line = "";
            String newline = "";
            final StringBuffer text = new StringBuffer();
            boolean foundIt = false;
            newline = fo.toString();
            while ((line = reader.readLine()) != null) {
                if (!line.contains(String.valueOf(fo.creator) + LavaFurnace.SEPERATOR + fo.world + LavaFurnace.SEPERATOR + fo.facing + LavaFurnace.SEPERATOR + fo.X + LavaFurnace.SEPERATOR + fo.Y + LavaFurnace.SEPERATOR + fo.Z)) {
                    text.append(String.valueOf(line) + "\r\n");
                }
                else {
                    if (foundIt) {
                        continue;
                    }
                    foundIt = true;
                    text.append(String.valueOf(newline) + "\r\n");
                }
            }
            if (!foundIt) {
                text.append(String.valueOf(newline) + "\r\n");
            }
            reader.close();
            final FileWriter fwriter = new FileWriter(this.furnaceDatabase);
            final BufferedWriter writer = new BufferedWriter(fwriter);
            writer.write(text.toString());
            writer.flush();
            writer.close();
            fwriter.close();
        }
        catch (Exception e1) {
            LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while editing furnaces in " + this.furnacesFile, e1);
        }
    }
    
    public void reload() {
        int furnaceCount = 0;
        int configCount = 0;
        int userCount = 0;
        if (this.furnaceDatabase.exists()) {
            Scanner scanner = null;
            Label_0184: {
                try {
                    scanner = new Scanner(this.furnaceDatabase);
                    while (scanner.hasNextLine()) {
                        final String line = scanner.nextLine();
                        if (!line.startsWith("#")) {
                            if (line.length() == 0) {
                                continue;
                            }
                            final FurnaceObject lf = new FurnaceObject(line);
                            this.lfObject.add(lf);
                            ++furnaceCount;
                            this.plugin.furnaceHelper.putInFurnaceBlockMap(lf);
                        }
                    }
                }
                catch (Exception e) {
                    LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while reading " + this.furnacesFile, e);
                    break Label_0184;
                }
                finally {
                    if (scanner != null) {
                        scanner.close();
                    }
                }
                if (scanner != null) {
                    scanner.close();
                }
            }
            LavaFurnace.LOGGER.log(Level.INFO, "LavaFurnace loaded: " + furnaceCount + " Furnace(s)");
        }
        if (this.cookTimeDatabase.exists()) {
            Scanner scanner = null;
            Label_0382: {
                try {
                    scanner = new Scanner(this.cookTimeDatabase);
                    while (scanner.hasNextLine()) {
                        final String line = scanner.nextLine();
                        if (!line.startsWith("#")) {
                            if (line.length() == 0) {
                                continue;
                            }
                            final UserCookTimes ucf = new UserCookTimes(line);
                            this.lfCook.add(ucf);
                            ++userCount;
                        }
                    }
                }
                catch (Exception e) {
                    LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while reading " + this.cooktimeFile, e);
                    break Label_0382;
                }
                finally {
                    if (scanner != null) {
                        scanner.close();
                    }
                }
                if (scanner != null) {
                    scanner.close();
                }
            }
            LavaFurnace.LOGGER.log(Level.INFO, "LavaFurnace loaded: " + userCount + " Custom Cook Time User(s)");
        }
        if (this.userGroupData.exists()) {
            Scanner scanner = null;
            Label_0726: {
                try {
                    scanner = new Scanner(this.userGroupData);
                    int lineCount = 1;
                    String line2 = "";
                    String groupName = "";
                    while (scanner.hasNextLine()) {
                        line2 = scanner.nextLine();
                        if (!line2.startsWith("#")) {
                            if (line2.isEmpty()) {
                                continue;
                            }
                            final String[] split = line2.split(",");
                            if (split.length == 2) {
                                groupName = split[0];
                                int furnaceAmount = 0;
                                try {
                                    furnaceAmount = Integer.parseInt(split[1]);
                                    final LavaFurnaceUserGroups lfug = new LavaFurnaceUserGroups(groupName, furnaceAmount);
                                    this.lfUserGroups.add(lfug);
                                    ++this.userGroupCount;
                                }
                                catch (NumberFormatException e4) {
                                    LavaFurnace.LOGGER.log(Level.SEVERE, "Error while reading LavaFurnace " + this.userGroups + " on line " + lineCount);
                                }
                            }
                            else {
                                LavaFurnace.LOGGER.log(Level.SEVERE, "Error while reading LavaFurnace " + this.userGroups + " on line " + lineCount);
                            }
                            ++lineCount;
                        }
                    }
                }
                catch (Exception e) {
                    LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while reading " + this.userGroups, e);
                    break Label_0726;
                }
                finally {
                    if (scanner != null) {
                        scanner.close();
                    }
                }
                if (scanner != null) {
                    scanner.close();
                }
            }
            LavaFurnace.LOGGER.log(Level.INFO, "LavaFurnace loaded: " + this.userGroupCount + " User Group(s)");
        }
        Label_4377: {
            if (this.configData.exists()) {
                Scanner scanner2 = null;
                try {
                    scanner2 = new Scanner(this.configData);
                    while (scanner2.hasNextLine()) {
                        final String line3 = scanner2.nextLine();
                        if (!line3.startsWith("#")) {
                            if (line3.length() == 0) {
                                continue;
                            }
                            if (line3.startsWith("Version=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        final double v = Double.parseDouble(split2[1]);
                                        if (v != this.Version) {
                                            this.erMsg.add("LavaFurnace Incorrect Config Version, updating config...");
                                        }
                                        ++configCount;
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Incorrect Config Version, updating config...");
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Incorrect Config Version, updating config...");
                                }
                            }
                            if (line3.startsWith("lava_furnace_timer=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.furnaceTimer = Integer.parseInt(split2[1]);
                                        if (this.furnaceTimer < 1) {
                                            this.erMsg.add("LavaFurnace Timer in config wrong. Default value used");
                                            this.furnaceTimer = 102400;
                                        }
                                        ++configCount;
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Timer in config wrong. Default value used");
                                        this.furnaceTimer = 102400;
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Timer in config wrong. Default value used");
                                    this.furnaceTimer = 102400;
                                }
                            }
                            if (line3.startsWith("cook_timer=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.cookTimeMultiplier = Math.abs(Integer.parseInt(split2[1]));
                                        if (this.cookTimeMultiplier == 0 || this.cookTimeMultiplier > 10) {
                                            throw new Exception();
                                        }
                                        ++configCount;
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Cook Time in config wrong. Default value used");
                                        this.cookTimeMultiplier = 1;
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Cook Time in config wrong. Default value used");
                                    this.cookTimeMultiplier = 1;
                                }
                            }
                            if (line3.startsWith("infinite_lava=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.infiniteTimer = Boolean.parseBoolean(split2[1]);
                                        if (this.infiniteTimer) {
                                            this.furnaceTimer = 102400;
                                        }
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Infinite Lava in config wrong. Default value used");
                                        this.infiniteTimer = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Infinite Lava in config wrong. Default value used");
                                    this.infiniteTimer = false;
                                }
                            }
                            if (line3.startsWith("max_player_furnaces=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.maxForges = Integer.parseInt(split2[1]);
                                        if (this.maxForges < 0) {
                                            this.erMsg.add("LavaFurnace Max Furnace amount in config wrong. Default value used");
                                            this.maxForges = 1;
                                        }
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Max Furnace amount in config wrong. Default value used");
                                        this.maxForges = 1;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Max Furnace amount in config wrong. Default value used");
                                    this.maxForges = 1;
                                }
                            }
                            if (line3.startsWith("allow_production_chests=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.productChests = Boolean.parseBoolean(split2[1]);
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Production Chests in config wrong. Default value used");
                                        this.productChests = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Production Chests in config wrong. Default value used");
                                    this.productChests = false;
                                }
                            }
                            if (line3.startsWith("allow_freeforall=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.freeForAll = Boolean.parseBoolean(split2[1]);
                                        if (this.freeForAll) {
                                            LavaFurnace.LOGGER.info("Permissions turned off - Using Free-For-All Mode");
                                        }
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Free For All in config wrong. Default value used");
                                        this.freeForAll = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Free For All in config wrong. Default value used");
                                    this.freeForAll = false;
                                }
                            }
                            if (line3.startsWith("allow_freeforall_chests=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        if (!this.freeForAll && Boolean.parseBoolean(split2[1])) {
                                            this.freeForAllChests = Boolean.parseBoolean(split2[1]);
                                            LavaFurnace.LOGGER.info("Permissions turned off - Using Free-For-All Chest Mode");
                                        }
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Free For All Chests in config wrong. Default value used");
                                        this.freeForAllChests = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Free For All Chests in config wrong. Default value used");
                                    this.freeForAllChests = false;
                                }
                            }
                            if (line3.startsWith("allow_source_chest_fuel=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.sourceChestFuel = Boolean.parseBoolean(split2[1]);
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Source Chest Fuel in config wrong. Default value used");
                                        this.sourceChestFuel = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Source Chest Fuel in config wrong. Default value used");
                                    this.sourceChestFuel = false;
                                }
                            }
                            if (line3.startsWith("use_large_chests=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        if (!this.useLargeChests && Boolean.parseBoolean(split2[1])) {
                                            this.useLargeChests = Boolean.parseBoolean(split2[1]);
                                        }
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Use Large Chests in config wrong. Default value used");
                                        this.useLargeChests = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Use Large Chests in config wrong. Default value used");
                                    this.useLargeChests = false;
                                }
                            }
                            if (line3.startsWith("explosion_proof=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.explosionProof = Boolean.parseBoolean(split2[1]);
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Explosion Proof in config wrong. Default value used");
                                        this.explosionProof = true;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Explosion Proof in config wrong. Default value used");
                                    this.explosionProof = true;
                                }
                            }
                            if (line3.startsWith("piston_protection=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.pistonProtect = Boolean.parseBoolean(split2[1]);
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Piston Protection in config wrong. Default value used");
                                        this.pistonProtect = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Piston Protection in config wrong. Default value used");
                                    this.pistonProtect = false;
                                }
                            }
                            if (line3.startsWith("enderman_protection=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.enderProtect = Boolean.parseBoolean(split2[1]);
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Enderman Protection in config wrong. Default value used");
                                        this.enderProtect = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Enderman Protection in config wrong. Default value used");
                                    this.enderProtect = false;
                                }
                            }
                            if (line3.startsWith("console_commands=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.enableConsoleCommands = Boolean.parseBoolean(split2[1]);
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Console Commands in config wrong. Default value used");
                                        this.enableConsoleCommands = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Console Commands in config wrong. Default value used");
                                    this.enableConsoleCommands = false;
                                }
                            }
                            if (line3.startsWith("sign_show_fuel_level=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.showFuelLevel = Boolean.parseBoolean(split2[1]);
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Show Sign Fuel Level in config wrong. Default value used");
                                        this.showFuelLevel = false;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Show Sign Fuel Level in config wrong. Default value used");
                                    this.showFuelLevel = false;
                                }
                            }
                            if (line3.startsWith("use_smartFurnace=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.smartFurnace = Boolean.parseBoolean(split2[1]);
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Use Smart Furnace in config wrong. Default value used");
                                        this.smartFurnace = true;
                                    }
                                    ++configCount;
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Use Smart Furnace in config wrong. Default value used");
                                    this.smartFurnace = true;
                                }
                            }
                            if (line3.startsWith("custom_smeltables=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    final String[] values = split2[1].split(",");
                                    try {
                                        for (int i = 0; i < values.length; ++i) {
                                            this.customSmeltables.add(Math.abs(Integer.parseInt(values[i])));
                                        }
                                        ++configCount;
                                    }
                                    catch (Exception e6) {
                                        this.customSmeltables.clear();
                                        this.erMsg.add("LavaFurnace Custom Smeltables in config wrong. Default value used");
                                    }
                                }
                                else {
                                    ++configCount;
                                }
                            }
                            if (line3.startsWith("custom_fuel=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    final String[] values = split2[1].split(",");
                                    try {
                                        for (int i = 0; i < values.length; ++i) {
                                            this.customFuel.add(Math.abs(Integer.parseInt(values[i])));
                                        }
                                        ++configCount;
                                    }
                                    catch (Exception e6) {
                                        this.customFuel.clear();
                                        this.erMsg.add("LavaFurnace Custom Fuel in config wrong. Default value used");
                                    }
                                }
                                else {
                                    ++configCount;
                                }
                            }
                            if (line3.startsWith("layer_one_blocks=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    final String[] secondSplit = split2[1].split(":");
                                    if (secondSplit.length == 2) {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidLayerOneBlocks(Integer.parseInt(secondSplit[0]), Integer.parseInt(secondSplit[1]))) {
                                                this.mBLayerOne = Integer.parseInt(secondSplit[0]);
                                                this.mBLayerOneData = Integer.parseInt(secondSplit[1]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Layer One Blocks in config wrong. Default value used");
                                                this.mBLayerOne = 49;
                                                this.mBLayerOneData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Layer One Blocks in config wrong. Default value used");
                                            this.mBLayerOne = 49;
                                            this.mBLayerOneData = 0;
                                        }
                                    }
                                    else {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidLayerOneBlocks(Integer.parseInt(secondSplit[0]), 0)) {
                                                this.mBLayerOne = Integer.parseInt(secondSplit[0]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Layer One Blocks in config wrong. Default value used");
                                                this.mBLayerOne = 49;
                                                this.mBLayerOneData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Layer One Blocks in config wrong. Default value used");
                                            this.mBLayerOne = 49;
                                            this.mBLayerOneData = 0;
                                        }
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Layer One Blocks in config wrong. Default value used");
                                    this.mBLayerOne = 49;
                                    this.mBLayerOneData = 0;
                                }
                            }
                            if (line3.startsWith("layer_two_blocks=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    final String[] secondSplit = split2[1].split(":");
                                    if (secondSplit.length == 2) {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidLayerTwoThreeBlocks(Integer.parseInt(secondSplit[0]), Integer.parseInt(secondSplit[1]))) {
                                                this.mBLayerTwo = Integer.parseInt(secondSplit[0]);
                                                this.mBLayerTwoData = Integer.parseInt(secondSplit[1]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Layer Two Blocks in config wrong. Default value used");
                                                this.mBLayerTwo = 49;
                                                this.mBLayerTwoData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Layer Two Blocks in config wrong. Default value used");
                                            this.mBLayerTwo = 49;
                                            this.mBLayerTwoData = 0;
                                        }
                                    }
                                    else {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidLayerTwoThreeBlocks(Integer.parseInt(secondSplit[0]), 0)) {
                                                this.mBLayerTwo = Integer.parseInt(secondSplit[0]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Layer Two Blocks in config wrong. Default value used");
                                                this.mBLayerTwo = 49;
                                                this.mBLayerTwoData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Layer Two Blocks in config wrong. Default value used");
                                            this.mBLayerTwo = 49;
                                            this.mBLayerTwoData = 0;
                                        }
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Layer Two Blocks in config wrong. Default value used");
                                    this.mBLayerTwo = 49;
                                    this.mBLayerTwoData = 0;
                                }
                            }
                            if (line3.startsWith("layer_three_blocks=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    final String[] secondSplit = split2[1].split(":");
                                    if (secondSplit.length == 2) {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidLayerTwoThreeBlocks(Integer.parseInt(secondSplit[0]), Integer.parseInt(secondSplit[1]))) {
                                                this.mBLayerThree = Integer.parseInt(secondSplit[0]);
                                                this.mBLayerThreeData = Integer.parseInt(secondSplit[1]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Layer Three Blocks in config wrong. Default value used");
                                                this.mBLayerThree = 49;
                                                this.mBLayerThreeData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Layer Three Blocks in config wrong. Default value used");
                                            this.mBLayerThree = 49;
                                            this.mBLayerThreeData = 0;
                                        }
                                    }
                                    else {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidLayerTwoThreeBlocks(Integer.parseInt(secondSplit[0]), 0)) {
                                                this.mBLayerThree = Integer.parseInt(secondSplit[0]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Layer Three Blocks in config wrong. Default value used");
                                                this.mBLayerThree = 49;
                                                this.mBLayerThreeData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Layer Three Blocks in config wrong. Default value used");
                                            this.mBLayerThree = 49;
                                            this.mBLayerThreeData = 0;
                                        }
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Layer Three Blocks in config wrong. Default value used");
                                    this.mBLayerThree = 49;
                                    this.mBLayerThreeData = 0;
                                }
                            }
                            if (line3.startsWith("belt_blocks=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    final String[] secondSplit = split2[1].split(":");
                                    if (secondSplit.length == 2) {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidBeltBlocks(Integer.parseInt(secondSplit[0]))) {
                                                this.beltBlocks = Integer.parseInt(secondSplit[0]);
                                                this.beltBlockData = Integer.parseInt(secondSplit[1]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Belt Blocks in config wrong. Default value used");
                                                this.beltBlocks = 67;
                                                this.beltBlockData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Belt Blocks in config wrong. Default value used");
                                            this.beltBlocks = 67;
                                            this.beltBlockData = 0;
                                        }
                                    }
                                    else {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidBeltBlocks(Integer.parseInt(secondSplit[0]))) {
                                                this.beltBlocks = Integer.parseInt(secondSplit[0]);
                                                this.beltBlockData = 0;
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Belt Blocks in config wrong. Default value used");
                                                this.beltBlocks = 67;
                                                this.beltBlockData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Belt Blocks in config wrong. Default value used");
                                            this.beltBlocks = 67;
                                            this.beltBlockData = 0;
                                        }
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Belt Blocks in config wrong. Default value used");
                                    this.beltBlocks = 67;
                                    this.beltBlockData = 0;
                                }
                            }
                            if (line3.startsWith("door_block=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    final String[] secondSplit = split2[1].split(":");
                                    if (secondSplit.length == 2) {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidLayerOneBlocks(Integer.parseInt(secondSplit[0]), Integer.parseInt(secondSplit[1]))) {
                                                this.doorBlock = Integer.parseInt(secondSplit[0]);
                                                this.doorBlockData = Integer.parseInt(secondSplit[1]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Door Block in config wrong. Default value used");
                                                this.doorBlock = 20;
                                                this.doorBlockData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Door Block in config wrong. Default value used");
                                            this.doorBlock = 20;
                                            this.doorBlockData = 0;
                                        }
                                    }
                                    else {
                                        try {
                                            if (this.plugin.furnaceHelper.isValidLayerOneBlocks(Integer.parseInt(secondSplit[0]), 0)) {
                                                this.doorBlock = Integer.parseInt(secondSplit[0]);
                                            }
                                            else {
                                                this.erMsg.add("LavaFurnace Door Block in config wrong. Default value used");
                                                this.doorBlock = 20;
                                                this.doorBlockData = 0;
                                            }
                                            ++configCount;
                                        }
                                        catch (Exception e5) {
                                            this.erMsg.add("LavaFurnace Door Block in config wrong. Default value used");
                                            this.doorBlock = 20;
                                            this.doorBlockData = 0;
                                        }
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Door Block in config wrong. Default value used");
                                    this.doorBlock = 20;
                                    this.doorBlockData = 0;
                                }
                            }
                            if (line3.startsWith("Debug=")) {
                                final String[] split2 = line3.split("=");
                                if (split2.length == 2) {
                                    try {
                                        this.LFDebug = Integer.parseInt(split2[1]);
                                        if (this.LFDebug < 0) {
                                            this.erMsg.add("LavaFurnace Debug in config wrong. Default value used");
                                            this.LFDebug = 0;
                                        }
                                        ++configCount;
                                    }
                                    catch (Exception e5) {
                                        this.erMsg.add("LavaFurnace Debug in config wrong. Default value used");
                                        this.LFDebug = 0;
                                    }
                                }
                                else {
                                    this.erMsg.add("LavaFurnace Debug in config wrong. Default value used");
                                    this.LFDebug = 0;
                                }
                            }
                            if (!line3.startsWith("Compatibility=")) {
                                continue;
                            }
                            final String[] split2 = line3.split("=");
                            if (split2.length == 2) {
                                final String[] values = split2[1].split(",");
                                this.pluginNames.add("UnlimitedLava");
                                this.pluginNames.add("DwarfForge");
                                try {
                                    for (int i = 0; i < values.length; ++i) {
                                        this.pluginNames.add(values[i].trim());
                                    }
                                    ++configCount;
                                }
                                catch (Exception e6) {
                                    this.customFuel.clear();
                                    this.erMsg.add("LavaFurnace Compatability in config wrong. Default values used");
                                }
                            }
                            else {
                                ++configCount;
                            }
                        }
                    }
                    if (this.erMsg.size() == 0) {
                        LavaFurnace.LOGGER.info("LavaFurnace config file finished loading...");
                    }
                    else {
                        LavaFurnace.LOGGER.info("LavaFurnace config file finished loading with " + this.erMsg.size() + " errors.");
                    }
                }
                catch (FileNotFoundException e2) {
                    LavaFurnace.LOGGER.log(Level.SEVERE, "Exception while reading " + this.configFile, e2);
                    break Label_4377;
                }
                finally {
                    if (scanner2 != null) {
                        scanner2.close();
                    }
                }
                if (scanner2 != null) {
                    scanner2.close();
                }
            }
            else {
                this.erMsg.add("LavaFurnace config file Not Found");
            }
        }
        if (this.erMsg.size() > 0 || configCount != 25) {
            for (final String e3 : this.erMsg) {
                LavaFurnace.LOGGER.log(Level.SEVERE, e3);
            }
            if (this.resetConfig()) {
                LavaFurnace.LOGGER.log(Level.INFO, "Config file repaired");
            }
            else {
                LavaFurnace.LOGGER.log(Level.SEVERE, "Config file could not be repaired!");
            }
        }
        this.erMsg.clear();
    }
    
    private static String getDateTime() {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date();
        return dateFormat.format(date);
    }
    
    public int saveDatabase() {
        if (this.furnaceDatabase.exists()) {
            int count = 0;
            for (int i = 0; i < this.lfObject.size(); ++i) {
                this.writeFurnace(this.lfObject.get(i));
                ++count;
            }
            return count;
        }
        return -1;
    }
    
    public int saveCookTimeDatabase() {
        if (this.cookTimeDatabase.exists()) {
            int count = 0;
            for (int i = 0; i < this.lfCook.size(); ++i) {
                this.writeUser(this.lfCook.get(i));
                ++count;
            }
            return count;
        }
        return -1;
    }
    
    public void incrementLoadTimer() {
        ++this.loadTimer;
        if (this.loadTimer > 100) {
            this.loadTimer = 100;
        }
    }
    
    public int getFurnaceTimer() {
        return this.furnaceTimer;
    }
    
    public void setFurnaceTimer(final int furnaceTimer) {
        this.furnaceTimer = furnaceTimer;
    }
    
    public boolean isInfiniteTimer() {
        return this.infiniteTimer;
    }
    
    public void setInfiniteTimer(final boolean infiniteTimer) {
        this.infiniteTimer = infiniteTimer;
    }
    
    public boolean isProductChests() {
        return this.productChests;
    }
    
    public void setProductChests(final boolean productChests) {
        this.productChests = productChests;
    }
    
    public int getMaxForges() {
        return this.maxForges;
    }
    
    public void setMaxForges(final int maxForges) {
        this.maxForges = maxForges;
    }
    
    public int getLFDebug() {
        return this.LFDebug;
    }
    
    public void setLFDebug(final int lFDebug) {
        this.LFDebug = lFDebug;
    }
    
    public int getLoadTimer() {
        return this.loadTimer;
    }
    
    public void setLoadTimer(final int loadTimer) {
        this.loadTimer = loadTimer;
    }
    
    public boolean isFreeforall() {
        return this.freeForAll;
    }
    
    public void setFreeforall(final boolean freeforall) {
        this.freeForAll = freeforall;
    }
    
    public boolean isFreeforallchests() {
        return this.freeForAllChests;
    }
    
    public void setFreeforallchests(final boolean freeforallchests) {
        this.freeForAllChests = freeforallchests;
    }
    
    public boolean isUselargechests() {
        return this.useLargeChests;
    }
    
    public void setUselargechests(final boolean uselargechests) {
        this.useLargeChests = uselargechests;
    }
    
    public boolean isSourceChestFuel() {
        return this.sourceChestFuel;
    }
    
    public int getCookTimeMultiplier() {
        return (int)Math.floor(200.0 - 10.0 / this.cookTimeMultiplier * 20.0);
    }
    
    public void setCookTimeMultiplier(final int cookTimeMultiplier) {
        this.cookTimeMultiplier = cookTimeMultiplier;
    }
    
    public boolean isExplosionProof() {
        return this.explosionProof;
    }
    
    public void setExplosionProof(final boolean explosionProof) {
        this.explosionProof = explosionProof;
    }
    
    public boolean isPistonprotect() {
        return this.pistonProtect;
    }
    
    public void setPistonprotect(final boolean pistonprotect) {
        this.pistonProtect = pistonprotect;
    }
    
    public boolean isEnableConsoleCommands() {
        return this.enableConsoleCommands;
    }
    
    public void setEnableConsoleCommands(final boolean enableConsoleCommands) {
        this.enableConsoleCommands = enableConsoleCommands;
    }
    
    public int getmBLayerOne() {
        return this.mBLayerOne;
    }
    
    public int getmBLayerOneData() {
        return this.mBLayerOneData;
    }
    
    public int getmBLayerTwo() {
        return this.mBLayerTwo;
    }
    
    public int getmBLayerTwoData() {
        return this.mBLayerTwoData;
    }
    
    public int getmBLayerThree() {
        return this.mBLayerThree;
    }
    
    public int getmBLayerThreeData() {
        return this.mBLayerThreeData;
    }
    
    public int getBeltBlocks() {
        return this.beltBlocks;
    }
    
    public int getBeltBlockData() {
        return this.beltBlockData;
    }
    
    public int getDoorBlock() {
        return this.doorBlock;
    }
    
    public int getDoorBlockData() {
        return this.doorBlockData;
    }
    
    public boolean isFurnaceDetectionl1() {
        return this.furnaceDetectionl1;
    }
    
    public void setFurnaceDetectionl1(final boolean furnaceDetectionl1) {
        this.furnaceDetectionl1 = furnaceDetectionl1;
    }
    
    public boolean isFurnaceDetectionl2() {
        return this.furnaceDetectionl2;
    }
    
    public void setFurnaceDetectionl2(final boolean furnaceDetectionl2) {
        this.furnaceDetectionl2 = furnaceDetectionl2;
    }
    
    public boolean isFurnaceDetectionl3() {
        return this.furnaceDetectionl3;
    }
    
    public void setFurnaceDetectionl3(final boolean furnaceDetectionl3) {
        this.furnaceDetectionl3 = furnaceDetectionl3;
    }
    
    public boolean isFurnaceDetectionbl() {
        return this.furnaceDetectionbl;
    }
    
    public void setFurnaceDetectionbl(final boolean furnaceDetectionbl) {
        this.furnaceDetectionbl = furnaceDetectionbl;
    }
    
    public int getFdFacing() {
        return this.fdFacing;
    }
    
    public void setFdFacing(final int fdFacing) {
        this.fdFacing = fdFacing;
    }
    
    public void setVersion(final double version) {
        this.Version = version;
    }
    
    public double getVersion() {
        return this.Version;
    }
    
    public boolean isShowFuelLevel() {
        return this.showFuelLevel;
    }
    
    public boolean isEnderProtect() {
        return this.enderProtect;
    }
    
    public boolean isSmartFurnace() {
        return this.smartFurnace;
    }
}
