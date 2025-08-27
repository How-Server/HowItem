package tw.iehow.howitem.items.vip.Y2023;

import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowBucket {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getOffHandStack(), Items.SKULL_BANNER_PATTERN, 1337016)){
            if (player.getSteppingBlockState().getBlock() == Blocks.MAGMA_BLOCK){
                PotionEffect.add(player, StatusEffects.FIRE_RESISTANCE,5,1);
                PlayerParticle.show(player, ParticleTypes.SPLASH, player.getX(), player.getY(), player.getZ(), 1.6F, 0.8F, 1.6F, 0.4F, 240);
            }
            if (player.fallDistance >= 3.0F){
                BlockPos surfacePos = player.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, player.getBlockPos());
                double distanceToSurface = player.getY() - surfacePos.getY();

                if (distanceToSurface <= 3.0) {
                    PotionEffect.add(player, StatusEffects.SLOW_FALLING, 5, 1);
                    PlayerParticle.show(player, ParticleTypes.SPLASH, player.getX(), player.getY(), player.getZ(), 1.6F, 0.8F, 1.6F, 0.4F, 240);
                    PlayerSound.play(player, SoundEvents.ITEM_BUCKET_FILL, 1.0F, (float) (2.0F - (player.fallDistance / 16.0F) * 0.2F));
                }
            }
        }
    }
}
