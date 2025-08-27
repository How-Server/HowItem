package tw.iehow.howitem.items.vip.Y2025;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.Random;

public class HowHelmet {
    public static float use(LivingEntity self, float amount, DamageSource source) {
        if (self instanceof PlayerEntity && SlotCheck.isValid(self.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337061)) {
            if (source.isOf(DamageTypes.MAGIC) ||
                    source.isOf(DamageTypes.DRAGON_BREATH) ||
                    source.isOf(DamageTypes.WITHER) ||
                    source.isOf(DamageTypes.INDIRECT_MAGIC) ||
                    source.isOf(DamageTypes.SONIC_BOOM)) {
                float reduceRate = 0.1f + new Random().nextFloat() * 0.4f;
                return amount * (1.0f - reduceRate);
            }
        }
        return amount;
    }
}
