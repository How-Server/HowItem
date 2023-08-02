package tw.iehow.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static tw.iehow.SlotCheck.isValid;

@Mixin(PlayerEntity.class)
public class HandHeldEffect {
    private boolean absorptionEffect = false;

    //Log for CD
    private final Map<UUID, Long> cooldown = new HashMap<>();

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        //Get player info
        PlayerEntity player = ((PlayerEntity)(Object)this);
        ItemStack offHand = ((PlayerEntity)(Object)this).getStackInHand(Hand.OFF_HAND);
        UUID playerUuid = player.getUuid();

        //Timestamp for CD
        long lastUsedTime = cooldown.getOrDefault(playerUuid, 0L);
        long currentTime = player.getWorld().getTime();
        int cooldownTime = 300;

        //Detect the usage of absorption
        float absorptionAmount = player.getAbsorptionAmount();

        //HowItem:blue_omamori
        if (isValid(offHand,"minecraft:flower_banner_pattern",1337001)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 10, 1, false, false));
        }

        //HowItem:purple_omamori
        if (isValid(offHand,"minecraft:flower_banner_pattern",1337002)) {
            player.removeStatusEffect(StatusEffects.HUNGER);
            player.removeStatusEffect(StatusEffects.DARKNESS);
            player.removeStatusEffect(StatusEffects.POISON);
            player.removeStatusEffect(StatusEffects.WITHER);
        }

        //HowItem:red_omamori
        if (isValid(offHand,"minecraft:totem_of_undying",1337001)) {
            if (currentTime - lastUsedTime >= cooldownTime && !absorptionEffect) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, -1, 3, false, false));
                absorptionAmount = 1.0F;
                absorptionEffect = true;
            }
        }
        if (absorptionEffect && (!isValid(offHand,"minecraft:totem_of_undying",1337001) || absorptionAmount == 0.0F)){
            player.removeStatusEffect(StatusEffects.ABSORPTION);
            absorptionEffect = false;
            cooldown.put(playerUuid, currentTime);
        }

        //HowItem:blue_omamori
        if (isValid(offHand,"minecraft:skull_banner_pattern",1337001)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 25, 1, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 25, 1, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 25, 1, false, false));
        }
    }
}

