package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.Random;

public class HowRabbit {
    public static void apply(PlayerEntity player) {
        Random random = new Random();
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD),  Items.FLOWER_BANNER_PATTERN, 1337062)){
            PotionEffect.add(player, StatusEffects.JUMP_BOOST, 2, random.nextInt(5));
            if (random.nextInt(100) > 97
                    && (player.getSteppingBlockState().getBlock().equals(Blocks.AIR) || player.getSteppingBlockState().getBlock().equals(Blocks.LIGHT))
                    && !player.getAbilities().flying
                    && !player.hasStatusEffect(StatusEffects.INVISIBILITY)){
                PotionEffect.add(player, StatusEffects.INVISIBILITY, 200, 1);
                PlayerParticle.show(player, ParticleTypes.POOF, player.getX(), player.getY() + 0.8, player.getZ(), 1.6F, 1.0F, 1.6F, 0.001f, 50);
                PlayerSound.play(player, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.5F, 1.0F);
            }
        }
    }
}
