package tw.iehow.howitem.items.omamori;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class BlackOmamori {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getOffHandStack(), Items.SKULL_BANNER_PATTERN,1337001)) {
            PotionEffect.add(player, StatusEffects.SLOWNESS, 25, 1);
            PotionEffect.add(player, StatusEffects.WEAKNESS, 25, 1);
            PotionEffect.add(player, StatusEffects.BLINDNESS, 25, 1);
        }
    }
}
