package com.github.lewis2333.sneakgrow.Utils;
import com.github.lewis2333.sneakgrow.Main;
import org.bukkit.entity.Player;

import static com.github.lewis2333.sneakgrow.PlayerEvent.PlayerController.*;
import static com.github.lewis2333.sneakgrow.Utils.ConfigManner.*;
/**
 * @author Lewis
 */
public class GrowMode {
    public static boolean Iserror = false;
    public static void modeController(){
        if((Is_GrowCommon && Is_GrowNecessary))  {
            Is_GrowCommon = Is_GrowNecessary = false;
            Iserror = true;
            Main.plugin.getLogger().info(errormode);
        }
    }
    public static void initialization(Player player){
        // 进行初始化数值
        if(!growNecessary_Count.containsKey(player)){
            growNecessary_Count.put(player,0);
        }
        if(!growCommon_Count.containsKey(player)){
            growCommon_Count.put(player,0);
        }
        if(!count.containsKey(player)){
            count.put(player,0);
        }
        if(!alreadyTime.containsKey(player)){
            alreadyTime.put(player,false);
        }
        if(!alreadyCommonTime.containsKey(player)){
            alreadyCommonTime.put(player,false);
        }
        if(!treeLocation.containsKey(player)){
            treeLocation.put(player,null);
        }
    }
}
