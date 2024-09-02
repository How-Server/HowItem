package tw.iehow.howitem.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerDeath {

    //Log for CD
    @Unique
    private final Map<UUID, Long> cooldown = new HashMap<>();

    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    public void playerDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        UUID playerUuid = player.getUuid();
        long lastUsedTime = cooldown.getOrDefault(playerUuid, 0L);
        long currentTime = player.getWorld().getTime();
        long interval = currentTime - lastUsedTime;
        //HowItem:totem
        if (isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 1337025)
        || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 1)
        || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 2)
        || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 3)
        || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 4)
        || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 5)){
            if (interval > 300){
                player.setHealth(1.0f);
                ci.cancel();
                PotionEffect.add(player, StatusEffects.REGENERATION,100,2);
                PotionEffect.add(player, StatusEffects.ABSORPTION,100,1);
                PlayerSound.play(player, SoundEvents.ITEM_TOTEM_USE, 0.6f, 1.0f);
                PlayerParticle.show(player, ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0, player.getZ(), 1.5F, 1.5F, 1.5F, 0.1f, 50);
                cooldown.put(playerUuid, currentTime);
            }
        } else if (isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 1337031)
                || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 1337032)
                || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 1337033)
                || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 1337034)
                || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 1337035)
                || isValid(player.getStackInHand(Hand.OFF_HAND), "minecraft:skull_banner_pattern", 1337036)){
            if (interval > 1200){
                player.setHealth(1.0f);
                ci.cancel();
                PotionEffect.add(player, StatusEffects.REGENERATION,100,2);
                PotionEffect.add(player, StatusEffects.ABSORPTION,100,1);
                PlayerSound.play(player, SoundEvents.ITEM_TOTEM_USE, 0.6f, 1.0f);
                PlayerParticle.show(player, ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0, player.getZ(), 1.5F, 1.5F, 1.5F, 0.1f, 50);
                cooldown.put(playerUuid, currentTime);
            }
        }
    }
}