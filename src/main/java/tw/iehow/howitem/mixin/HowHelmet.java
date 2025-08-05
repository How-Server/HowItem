package tw.iehow.howitem.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class HowHelmet {
    @ModifyVariable(
            method = "damage",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )

    private float modifyDamage(float amount, ServerWorld world, DamageSource source) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof PlayerEntity && SlotCheck.isValid(self.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337061)) {
            if (source.isOf(DamageTypes.MAGIC) ||
                    source.isOf(DamageTypes.DRAGON_BREATH) ||
                    source.isOf(DamageTypes.WITHER) ||
                    source.isOf(DamageTypes.INDIRECT_MAGIC) ||
                    source.isOf(DamageTypes.SONIC_BOOM)) {
                float reduceRate = 0.1f + new Random().nextFloat() * 0.4f;
                return amount * (1.0f - reduceRate);
            }
        }return amount;
    }
}