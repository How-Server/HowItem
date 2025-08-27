package tw.iehow.howitem.items.omamori;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class PurpleOmamori {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getOffHandStack(), Items.FLOWER_BANNER_PATTERN,1337001)) {
            PotionEffect.remove(player, StatusEffects.HUNGER);
            PotionEffect.remove(player, StatusEffects.DARKNESS);
            PotionEffect.remove(player, StatusEffects.POISON);
            PotionEffect.remove(player, StatusEffects.WITHER);
        }
    }
}
