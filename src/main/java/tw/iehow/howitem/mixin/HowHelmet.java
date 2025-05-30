package tw.iehow.howitem.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class HowHelmet {
    @Inject(
            method = "damage",
            at = @At("HEAD"),
            cancellable = true)
    private void onDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof PlayerEntity &&
                (source.isOf(DamageTypes.MAGIC) || source.isOf(DamageTypes.DRAGON_BREATH) || source.isOf(DamageTypes.WITHER)
                || source.isOf(DamageTypes.INDIRECT_MAGIC) || source.isOf(DamageTypes.SONIC_BOOM))){
            float reduceRate = 0.1f + new Random().nextFloat() * 0.4f;
            float reducedDamage = amount * reduceRate;
            self.setHealth(self.getHealth() - reducedDamage);
            cir.setReturnValue(true);
        }
    }
}