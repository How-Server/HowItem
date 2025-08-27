package tw.iehow.howitem.items.pen;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;

public class FountainPen extends BaseHowItem {

    public FountainPen() {
        super(Items.MOJANG_BANNER_PATTERN, 1337019);
    }

    public void unsafeUse(PlayerEntity player, Hand hand) {
        long chargeCooldown = CooldownManager.get(player.getUuid(), CooldownType.PEN_CHARGED);
        if (chargeCooldown == 0) {
            PenShoot.apply(player, 6.0F, 16, true, ParticleTypes.SQUID_INK, 20);
            PlayerSound.onlyPlay(player, SoundEvents.ENTITY_SQUID_SQUIRT, 1.0f, 1.0f);
            PotionEffect.add(player, StatusEffects.SLOWNESS, 40, 1);
            PotionEffect.add(player, StatusEffects.WEAKNESS, 40, 1);
            CooldownManager.set(player.getUuid(), CooldownType.PEN_CHARGED, 40);

        }

        long cooldown = CooldownManager.get(player.getUuid(), CooldownType.PEN);
        if (cooldown == 0) {
            PenShoot.apply(player, 6.0F, 8, false, ParticleTypes.SQUID_INK, 2);
            PlayerSound.onlyPlay(player, SoundEvents.BLOCK_HANGING_SIGN_WAXED_INTERACT_FAIL, 1.0f, 1.0f);
            CooldownManager.set(player.getUuid(), CooldownType.PEN, 4);
            PlayerActionBar.showCD(player, chargeCooldown);
        }

    }
}
