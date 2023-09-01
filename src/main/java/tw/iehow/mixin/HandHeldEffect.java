package tw.iehow.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static tw.iehow.util.PlayerParticle.showParticle;
import static tw.iehow.util.SlotCheck.isValid;
import static tw.iehow.util.PlayerTitle.showTitle;

@Mixin(PlayerEntity.class)
public class HandHeldEffect {
    @Unique
    private boolean absorptionEffect = false;

    //Log for CD
    @Unique
    private final Map<UUID, Long> cooldown = new HashMap<>();

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        //Get player info
        PlayerEntity player = ((PlayerEntity)(Object)this);
        ServerPlayerEntity ServerPlayer = (ServerPlayerEntity) player;
        ItemStack offHand = ((PlayerEntity)(Object)this).getStackInHand(Hand.OFF_HAND);

        //Timestamp for CD
        UUID playerUuid = player.getUuid();
        long lastUsedTime = cooldown.getOrDefault(playerUuid, 0L);
        long currentTime = player.getWorld().getTime();
        long interval = currentTime - lastUsedTime;

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
            if (interval >= 300 && !absorptionEffect) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, -1, 3, false, false));
                absorptionAmount = 1.0F;
                absorptionEffect = true;
            }else {
                showTitle(ServerPlayer, 300 - interval);
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

        //HowItem:chinese_valentines_2023/red_rose
        if (isValid(offHand,"minecraft:flower_banner_pattern",1337028)) {
            if (interval >= 200) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 80, 2, false, false));
                showParticle(ServerPlayer, ParticleTypes.HEART, player.getX(), player.getY() + 1.0, player.getZ(), 0.5F, 0.5F, 0.5F, 1, 5);
                cooldown.put(playerUuid, currentTime);
            }else {
                showTitle(ServerPlayer, 200 - interval);
            }
        }
    }
}

