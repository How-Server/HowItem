package tw.iehow.howitem.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.UUID;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerDeath {

    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    public void customTotem(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        UUID playerUuid = player.getUuid();
        long cd = CooldownManager.get(playerUuid, CooldownType.TOTEM);
        if (cd > 0) return;

        // vip totem
        if (SlotCheck.isValid(player.getStackInHand(Hand.OFF_HAND),Items.SKULL_BANNER_PATTERN, 1337025)
        || SlotCheck.isValid(player.getStackInHand(Hand.OFF_HAND),Items.SKULL_BANNER_PATTERN, 1, 20)){
            player.setHealth(1.0f);
            ci.cancel();
            applyTotem(player, 300);
        } // admin & gold totem
        else if (SlotCheck.isValid(player.getStackInHand(Hand.OFF_HAND),Items.SKULL_BANNER_PATTERN, 1337031, 1337036)){
            player.setHealth(1.0f);
            ci.cancel();
            applyTotem(player, 1200);
        }
    }

    @Unique
    private void applyTotem(ServerPlayerEntity player, long cd) {
        PotionEffect.add(player, StatusEffects.REGENERATION,100,2);
        PotionEffect.add(player, StatusEffects.ABSORPTION,100,1);
        PlayerSound.play(player, SoundEvents.ITEM_TOTEM_USE, 0.6f, 1.0f);
        PlayerParticle.show(player, ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0, player.getZ(), 1.5F, 1.5F, 1.5F, 0.1f, 50);
        CooldownManager.set(player.getUuid(), CooldownType.TOTEM, cd);
    }
}