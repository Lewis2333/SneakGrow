package com.github.lewis2333.sneakgrow.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.github.lewis2333.sneakgrow.Utils.ConfigManner.*;
import static com.github.lewis2333.sneakgrow.Utils.GrowMode.modeController;

/**
 * @author Lewis
 */
public class Cmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player){
            if(player.hasPermission("SneakGrow.use")){
                if(args.length==0){
                    GetHelpMessage(player);
                    return true;
                }else if(args[0].equalsIgnoreCase("help")){
                    GetHelpMessage(player);
                    return true;
                }else if(args[0].equalsIgnoreCase("reload")){
                    if(player.hasPermission("SneakGrow.admin")){
                        testConfig();
                        reloadConfig();
                        modeController();
                        player.sendMessage(Reload);
                        return true;
                    }else{
                        player.sendMessage("§b[SneakGrow] §c你没有权限使用这个指令~");
                    }
                }
            }else{
                player.sendMessage("§b[SneakGrow] §c你没有权限使用这个指令~");
                return true;
            }

        }else {
            sender.sendMessage("§b[SneakGrow] §c你必须作为玩家执行此命令~!");
            return true;
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabMessage = new ArrayList<>();
        tabMessage.add("help");
        tabMessage.add("reload");
        return tabMessage;
    }
    public void GetHelpMessage(Player player){
        for(String a : loadHelpMessage()){
            player.sendMessage(a);
        }

    }
}
