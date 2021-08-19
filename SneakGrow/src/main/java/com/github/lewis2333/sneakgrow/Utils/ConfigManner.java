package com.github.lewis2333.sneakgrow.Utils;


import com.github.lewis2333.sneakgrow.Main;
import com.github.lewis2333.sneakgrow.PlayerEvent.PlayerController;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lewis
 */
public class ConfigManner {
    public static int Center;
    public static int Delay;
    public static int Grow_Max;
    public static double Success_Rate;
    public static int Max_Count;
    public static boolean Is_GrowNecessary;
    public static int GrowNecessary_Count;

    public static String Successful;
    public static String Failed;
    public static String Failed_2;
    public static String Failed_3;
    public static String FinishTime;
    public static String Reload;

    public static void reloadConfig() {
        File file = new File(Main.plugin.getDataFolder(), "Settings.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(file);
        try {
            filec.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadConfig();

    }

    public static void loadConfig() {
        loadSettings();
        loadMessage();
    }

    public static void loadSettings() {
        File file = new File(Main.plugin.getDataFolder(), "Settings.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(file);
        Center = filec.getInt("SneakGrow.Center");
        Delay = filec.getInt("SneakGrow.Delay");
        Grow_Max = filec.getInt("SneakGrow.Grow_Max");
        Success_Rate = filec.getDouble("SneakGrow.Success_Rate");
        Max_Count = filec.getInt("SneakGrow.Max_Count");
        Is_GrowNecessary = filec.getBoolean("SneakGrow.Is_GrowNecessary");
        GrowNecessary_Count = filec.getInt("SneakGrow.GrowNecessary_Count");

    }

    public static void loadMessage() {
        File file = new File(Main.plugin.getDataFolder(), "Message.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(file);
        Successful = filec.getString("Successful");
        Successful = Successful.replace("&", "§");
        Failed = filec.getString("Failed");
        Failed = Failed.replace("&", "§");
        Failed_2 = filec.getString("Failed_2");
        Failed_2 = Failed_2.replace("&", "§");
        Failed_3 = filec.getString("Failed_3");
        Failed_3 = Failed_3.replace("&", "§");
        FinishTime = filec.getString("FinishTime");
        FinishTime = FinishTime.replace("&", "§");
        Reload = filec.getString("Reload");
        Reload = Reload.replace("&", "§");
    }

    public static List<String> loadHelpMessage() {
        File file = new File(Main.plugin.getDataFolder(), "Message.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection filecc = filec.getConfigurationSection("HelpMessage");
        List<String> newHelpMessage = new ArrayList<>();
        for (String key : filecc.getKeys(false)){
            newHelpMessage.add(filec.getString("HelpMessage."+key));
        }
        return newHelpMessage;
    }
}
