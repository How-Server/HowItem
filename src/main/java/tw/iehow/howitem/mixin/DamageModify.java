package tw.iehow.howitem.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tw.iehow.howitem.items.vip.Y2025.HowHelmet;

@Mixin(LivingEntity.class)
public abstract class DamageModify {
    // after claim check
    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyDamage(float amount, ServerWorld world, DamageSource source) {
        LivingEntity self = (LivingEntity) (Object) this;
        amount = HowHelmet.use(self, amount, source);
        return amount;
    }
}