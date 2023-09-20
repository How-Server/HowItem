package tw.iehow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import tw.iehow.util.check.EffectCheck;

public class UseEffect {
    public static void mainHand(PlayerEntity player, Entity entity){
        EffectCheck.HERO_OF_THE_VILLAGE(player, entity);
    }
}
