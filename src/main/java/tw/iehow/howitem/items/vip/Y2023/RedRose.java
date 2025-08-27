package tw.iehow.howitem.items.vip.Y2023;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class RedRose {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getOffHandStack(), Items.FLOWER_BANNER_PATTERN,1337028)
            || SlotCheck.isValid(player.getOffHandStack(), Items.SKULL_BANNER_PATTERN, 1337029)) {
            long cd = CooldownManager.get(player.getUuid(), CooldownType.TOY);
            if (cd == 0) {
                PotionEffect.add(player, StatusEffects.REGENERATION, 80, 1);
                PlayerParticle.show(player, ParticleTypes.HEART, player.getX(), player.getY() + 1.0, player.getZ(), 0.5F, 0.5F, 0.5F, 1, 5);
                CooldownManager.set(player.getUuid(), CooldownType.TOY, 200);
            }else {
                PlayerActionBar.showCD(player, cd);
            }
        }
    }
}
