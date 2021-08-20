package com.github.lewis2333.sneakgrow.PlayerEvent;

import com.github.lewis2333.sneakgrow.Main;
import com.github.lewis2333.sneakgrow.Utils.GrowMode;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;

import static com.github.lewis2333.sneakgrow.Utils.ConfigManner.*;
import static com.github.lewis2333.sneakgrow.Utils.GrowMode.initialization;

/**
 * @author Lewis
 */
public class PlayerController implements Listener {
    public static HashMap<Player,Integer> count = new HashMap<Player,Integer>();
    public static HashMap<Player,Integer> growNecessary_Count = new HashMap<Player,Integer>();
    public static HashMap<Player,Integer> growCommon_Count = new HashMap<Player,Integer>();
    public static HashMap<Player,Boolean> alreadyTime = new HashMap<Player,Boolean>();
    public static HashMap<Player,Boolean> alreadyCommonTime = new HashMap<Player,Boolean>();
    public static HashMap<Player,Integer> treeLocation = new HashMap<Player,Integer>();
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        initialization(player);
        if(event.isSneaking()){
            if(!GrowMode.Iserror){
                growPlant(player);
            }else{
                player.spigot().sendMessage(ChatMessageType.SYSTEM, new TextComponent(errormode));
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§b[SneakGrow] §4插件异常，请尝试联系服务器管理员"));
            }
        }

    }
    public void growPlant(Player player){
        Block block;
        block = player.getTargetBlock(null,Center);
        growSapling(player,block);
    }
    public static boolean growSapling(Player player , Block block) {
        if (!(block.getBlockData().clone() instanceof Sapling)) {
            return false;
        }
        if(!player.hasPermission("SneakGrow.use")){
            player.sendMessage("§b[SneakGrow] §c你没有权限使用此插件~");
            return false;
        }
        if(randomSuccessul(player)){
            if(count.get(player) <  Max_Count){
                if(luckMode(player,block)){
                    return true;
                }
                if(commonMode(player,block)){
                    return true;
                }
            }else {
                Failed_3 = Failed_3.replace("%grow_count%",""+count.get(player));
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Failed_3));
                //计时
                isTime(player);
                return false;
            }
        }
        if(Is_GrowNecessary){
            count.put(player,count.get(player)+1);
        }
        return false;
    }
    public static Boolean randomSuccessul(Player player){
        Double i = 0.0;
        Random r = new Random();
        i = r.nextDouble();
        if(count.get(player) <  Max_Count){
            if(i > Success_Rate){
                if(Is_GrowNecessary && growNecessary_Count.get(player) < GrowNecessary_Count){
                    growNecessary_Count.put(player,growNecessary_Count.get(player)+1);
                }else if(Is_GrowNecessary && growNecessary_Count.get(player) == GrowNecessary_Count){
                    return true;
                }
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Failed));
                return false;
            }else{
                return true;
            }
        }else {
            Failed_3 = Failed_3.replace("%grow_count%",""+count.get(player));
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Failed_3));
            //计时
            isTime(player);
            return false;
        }
    }
    public static boolean luckMode(Player player,Block block){
        if(Is_GrowNecessary && growNecessary_Count.get(player) == GrowNecessary_Count){
            for(;;){
                if ((block.getBlockData().clone() instanceof Sapling)) {
                    block.getWorld().playEffect(block.getLocation(), Effect.VILLAGER_PLANT_GROW, 0);
                    block.applyBoneMeal(BlockFace.UP);
                }
                else{
                    break;
                }
            }
            growNecessary_Count.put(player,0);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Successful_2));
            return true;
        }
        else if(Is_GrowNecessary && growNecessary_Count.get(player) < GrowNecessary_Count){
            growNecessary_Count.put(player,growNecessary_Count.get(player)+1);
            block.getWorld().playEffect(block.getLocation(), Effect.VILLAGER_PLANT_GROW, 0);
            block.applyBoneMeal(BlockFace.UP);
            count.put(player,count.get(player)+1);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Successful));
        }
        return false;
    }
    public static boolean commonMode(Player player,Block block){
        treeLocation.put(player,block.getLocation().getBlockX()+block.getLocation().getBlockZ()/99);
        if(Shift_Enabled){
            isCommonTime(player,block);
        }
        if(Is_GrowCommon && growCommon_Count.get(player) == (GrowCommon_Count - 1)){
            for(;;){
                if ((block.getBlockData().clone() instanceof Sapling)) {
                    block.getWorld().playEffect(block.getLocation(), Effect.VILLAGER_PLANT_GROW, 0);
                    block.applyBoneMeal(BlockFace.UP);
                }else{
                    break;
                }
            }
            count.put(player,0);
            growCommon_Count.put(player,0);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Successful_2));
            return true;
        }
        else if(Is_GrowCommon && growCommon_Count.get(player) < GrowCommon_Count){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Successful));
            count.put(player,count.get(player)+1);
            growCommon_Count.put(player,growCommon_Count.get(player)+1);
            return true;
        }
        return false;
    }
    public static void isTime(Player player){
        if(!alreadyTime.get(player)){
            alreadyTime.put(player,true);
            new BukkitRunnable(){
                @Override
                public void run() {
                    alreadyTime.put(player,false);
                    count.put(player,0);
                    FinishTime = FinishTime.replace("%max_count%",""+Max_Count);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(FinishTime));
                }
            }.runTaskLaterAsynchronously(Main.plugin, Delay * 20);
        }
    }
    public static void isCommonTime(Player player,Block block){
        if(!alreadyCommonTime.get(player)){
            alreadyCommonTime.put(player,true);
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(treeLocation.get(player) == block.getLocation().getBlockX()+block.getLocation().getBlockZ()/99){
                        growCommon_Count.put(player,0);
                    }
                    alreadyCommonTime.put(player,false);
                }
            }.runTaskLaterAsynchronously(Main.plugin, Shift_Time * 20);
        }
}

}

