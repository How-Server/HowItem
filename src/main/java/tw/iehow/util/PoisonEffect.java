package tw.iehow.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PoisonEffect {
    public static void add(LivingEntity livingEntity, StatusEffect statusEffect, int duration, int amplifier){
        livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, duration, amplifier, false, false));
    }
}
