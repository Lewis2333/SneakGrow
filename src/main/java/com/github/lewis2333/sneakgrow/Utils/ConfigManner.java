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
    public static boolean Is_GrowCommon;
    public static boolean Shift_Enabled;
    public static int Shift_Time;
    public static int GrowNecessary_Count;
    public static int GrowCommon_Count;
    public static int Player_Choice;


    public static String Successful;
    public static String Successful_2;
    public static String Failed;
    public static String Failed_2;
    public static String Failed_3;
    public static String errormode;
    public static String FinishTime;
    public static String Reload;

    public static void testConfig(){
        File file = new File(Main.plugin.getDataFolder(), "Settings.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(file);
        //settings.yml
        testConfig_2(filec,"SneakGrow.Center",5,null,false,0.0,false,false,false);
        testConfig_2(filec,"SneakGrow.Delay",30,null,false,0.0,false,false,false);
        testConfig_2(filec,"SneakGrow.Success_Rate",0,null,false,0.5,false,false,true);
        testConfig_2(filec,"SneakGrow.Max_Count",5,null,false,0.0,false,false,false);
        testConfig_2(filec,"SneakGrow.Player_Choice",0,null,true,0.0,false,true,false);
        testConfig_2(filec,"SneakGrow.LuckMode.enabled",0,null,false,0.0,false,true,false);
        testConfig_2(filec,"SneakGrow.LuckMode.GrowNecessary_Count",7,null,false,0.0,false,false,false);
        testConfig_2(filec,"SneakGrow.CommonMode.enabled",0,null,true,0.0,false,true,false);
        testConfig_2(filec,"SneakGrow.CommonMode.GrowCommon_Count",5,null,false,0.0,false,false,false);
        testConfig_2(filec,"SneakGrow.CommonMode.Shift_Enabled",0,null,true,0.0,false,true,false);
        testConfig_2(filec,"SneakGrow.CommonMode.Shift_Time",10,null,false,0.0,false,false,false);

        File file_ = new File(Main.plugin.getDataFolder(), "Message.yml");
        FileConfiguration filec_ = YamlConfiguration.loadConfiguration(file_);
        //Message.yml
        testConfig_2(filec_,"Successful",0,"&b[SneakGrow] &7尝试催熟了你的树苗~",false,0.0,true,false,false);
        testConfig_2(filec_,"Successful_2",0,"&b[SneakGrow] &7成功催熟了你的树苗！",false,0.0,true,false,false);
        testConfig_2(filec_,"FinishTime",0,"&b[SneakGrow] &7你现在可以催熟啦~ 最大数量%max_count%次！",false,0.0,true,false,false);
        testConfig_2(filec_,"Failed",0,"&b[SneakGrow] &c哎呀~ 尝试催熟失败了呢.",false,0.0,true,false,false);
        testConfig_2(filec_,"Failed_2",0, "&b[SneakGrow] &c附近的树好像已经成长了呢~",false,0.0,true,false,false);
        testConfig_2(filec_,"Failed_3",0,"&b[SneakGrow] &c&l哎呀呀~ 你已经催熟%grow_count%次啦，请一会再来试试吧！",false,0.0,true,false,false);
        testConfig_2(filec_,"Reload",0,"&b[SneakGrow] &c重载配置文件成功~",false,0.0,true,false,false);
        testConfig_2(filec_,"errormode",0,"§b[SneakGrow] §c错误！不得设置为两个催熟模式 插件已关闭！",false,0.0,true,false,false);
        testConfig_2(filec_,"HelpMessage.0",0,"============§b[SneakGrow]§f============",false,0.0,true,false,false);
        testConfig_2(filec_,"HelpMessage.1",0," ► §7/SneakGrow help 获取插件帮助",false,0.0,true,false,false);
        testConfig_2(filec_,"HelpMessage.2",0," ",false,0.0,true,false,false);
        testConfig_2(filec_,"HelpMessage.3",0," ► §7/SneakGrow reload 重载配置文件",false,0.0,true,false,false);
        testConfig_2(filec_,"HelpMessage.4",0,"============§b[@By Lewis]§f============",false,0.0,true,false,false);

        try {
            filec.save(file);
            filec_.save(file_);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
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
        Is_GrowCommon = filec.getBoolean("SneakGrow.CommonMode.enabled");
        Shift_Enabled = filec.getBoolean("SneakGrow.CommonMode.Shift_Enabled");
        Shift_Time = filec.getInt("SneakGrow.CommonMode.Shift_Time");
        Is_GrowNecessary = filec.getBoolean("SneakGrow.LuckMode.enabled");
        GrowCommon_Count = filec.getInt("SneakGrow.CommonMode.GrowCommon_Count");
        GrowNecessary_Count = filec.getInt("SneakGrow.LuckMode.GrowNecessary_Count");
        Player_Choice = filec.getInt("SneakGrow.Player_Choice");

    }

    public static void loadMessage() {
        File file = new File(Main.plugin.getDataFolder(), "Message.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(file);
        Successful = filec.getString("Successful");
        Successful = Successful.replace("&", "§");
        Successful_2 = filec.getString("Successful_2");
        Successful_2 = Successful_2.replace("&", "§");
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
        errormode = filec.getString("errormode");
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
    public static void testConfig_2(FileConfiguration filec,String path,int a,String b,Boolean c,Double d,Boolean isString,Boolean isBoolean,Boolean isDouble){
        if(!isString && !isBoolean && !isDouble){
            if(!filec.contains(path)){
                filec.set(path,a);
                Main.plugin.getLogger().info("检测到"+path+"节点消失，已自动生成！");
            }
        }
        if(isString) {
            if (!filec.contains(path)) {
                filec.set(path,b);
                Main.plugin.getLogger().info("检测到"+path+"节点消失，已自动生成！");
            }
        }
        if(isBoolean){
            if (!filec.contains(path)) {
                filec.set(path,c);
                Main.plugin.getLogger().info("检测到"+path+"节点消失，已自动生成！");
            }
        }
        if(isDouble){
            if (!filec.contains(path)) {
                filec.set(path,d);
                Main.plugin.getLogger().info("检测到"+path+"节点消失，已自动生成！");
            }
        }
    }
}
