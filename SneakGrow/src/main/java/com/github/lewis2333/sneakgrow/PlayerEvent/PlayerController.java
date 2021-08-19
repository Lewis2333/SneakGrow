package com.github.lewis2333.sneakgrow.PlayerEvent;

import com.github.lewis2333.sneakgrow.Main;
import com.github.lewis2333.sneakgrow.Utils.ConfigManner;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;

import static com.github.lewis2333.sneakgrow.Utils.ConfigManner.*;

/**
 * @author Lewis
 */
public class PlayerController implements Listener {
    public static HashMap<Player,Integer> count = new HashMap<Player,Integer>();
    public static HashMap<Player,Integer> growNecessary_Count = new HashMap<Player,Integer>();
    public static HashMap<Player,Boolean> alreadyTime = new HashMap<Player,Boolean>();
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        // 进行初始化数值
        if(!growNecessary_Count.containsKey(player)){
            growNecessary_Count.put(player,0);
        }
        if(!count.containsKey(player)){
            count.put(player,0);
        }
        if(!alreadyTime.containsKey(player)){
            alreadyTime.put(player,false);
        }
        if(event.isSneaking()){
            growPlant(player);
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
        if(randomSuccessul(player)){
            if(count.get(player) <  Max_Count){
                block.getWorld().playEffect(block.getLocation(), Effect.VILLAGER_PLANT_GROW, 0);
                block.applyBoneMeal(BlockFace.UP);
                count.put(player,count.get(player)+1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Successful));
                if(Is_GrowNecessary && growNecessary_Count.get(player) < GrowNecessary_Count){
                    growNecessary_Count.put(player,growNecessary_Count.get(player)+1);
                }else if(Is_GrowNecessary && growNecessary_Count.get(player) == GrowNecessary_Count){
                    growNecessary_Count.put(player,0);
                }
                return true;
            }else {
                Failed_3 = Failed_3.replace("%grow_count%",""+count.get(player));
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Failed_3));
                //计时
                isTime(player);
                return false;
            }
        }else{
            block.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 0);
        }
        return false;
    }
    public static Boolean randomSuccessul(Player player){
        Double i = 0.0;
        Random r = new Random();
        i = r.nextDouble();
        if(count.get(player) <  Max_Count ){
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


}

