package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.DimensionCheck;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowClown {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337021) && DimensionCheck.isVanillaWorld(player)){
            if (SlotCheck.isValid(player.getOffHandStack(),  Items.FLOWER_BANNER_PATTERN, 1337016)){
                PotionEffect.add(player, StatusEffects.LEVITATION,10,0);
            }
            if (SlotCheck.isValid(player.getMainHandStack(), Items.FLOWER_BANNER_PATTERN, 1337016)){
                PotionEffect.add(player, StatusEffects.SLOW_FALLING,10,0);
            }
        }
    }
}
