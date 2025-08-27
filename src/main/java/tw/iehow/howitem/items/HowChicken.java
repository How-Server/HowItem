package tw.iehow.howitem.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowChicken {
    public static void use(LivingEntity user, ItemStack stack, int remainingUseTicks) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
        if (SlotCheck.isValid(stack, Items.SKULL_BANNER_PATTERN, Identifier.of("minecraft:how_chicken")) && remainingUseTicks < 50) {
            playerEntity.stopUsingItem();
            playerEntity.getItemCooldownManager().set(stack, 40);
            PotionEffect.add(playerEntity, StatusEffects.SPEED, 100, 9);
            PlayerParticle.show(playerEntity, ParticleTypes.CLOUD, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), 1.2f, 1.2f, 1.2f, 0.001f, 60);
            PlayerSound.play(playerEntity, SoundEvents.ENTITY_CHICKEN_AMBIENT, 1.0f, 1.0f);
            playerEntity.getHungerManager().add(8,4);
        }
    }
}
