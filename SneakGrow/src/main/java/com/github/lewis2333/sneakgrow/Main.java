package com.github.lewis2333.sneakgrow;

import com.github.lewis2333.sneakgrow.Command.Cmd;
import com.github.lewis2333.sneakgrow.PlayerEvent.PlayerController;
import com.github.lewis2333.sneakgrow.Utils.ConfigManner;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import static com.github.lewis2333.sneakgrow.Utils.ConfigManner.loadConfig;
import static com.github.lewis2333.sneakgrow.Utils.ConfigManner.testConfig;
import static com.github.lewis2333.sneakgrow.Utils.GrowMode.modeController;


/**
 * @author Lewis
 */
public final class Main extends JavaPlugin {

    public static JavaPlugin plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Bukkit.getPluginCommand("SneakGrow").setExecutor(new Cmd());
        Bukkit.getPluginManager().registerEvents(new PlayerController(),this);
        saveResource("Settings.yml",false);
        saveResource("Message.yml",false);
        testConfig();
        loadConfig();
        modeController();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
