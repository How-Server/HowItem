package tw.iehow.util.check;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class EffectCheck {
    public static void HERO_OF_THE_VILLAGE(PlayerEntity player, Entity entity){
        if(entity.getType() == EntityType.VILLAGER && entity.getCommandTags().contains("official")){
            player.removeStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
        }
    }
}
