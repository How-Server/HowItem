package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowSweater {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.CHEST), Items.NETHERITE_CHESTPLATE, Identifier.of("minecraft:sweater_how"))) {
            PotionEffect.add(player, StatusEffects.HASTE, 10, 1);
        }
    }
}
