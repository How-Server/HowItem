package tw.iehow.util.apply;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionEffect {
    public static void add(LivingEntity livingEntity, StatusEffect statusEffect, int duration, int amplifier){
        livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, duration, amplifier, false, false));
    }

    public static void remove(LivingEntity livingEntity, StatusEffect statusEffect){
        livingEntity.removeStatusEffect(statusEffect);
    }
}
