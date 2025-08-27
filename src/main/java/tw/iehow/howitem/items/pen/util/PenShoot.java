package tw.iehow.howitem.items.pen.util;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.ClaimCheck;

import java.util.Random;

public class PenShoot {
        public static void apply(PlayerEntity player, float damage, int range, boolean advanced, ParticleEffect effect, int particleCount) {
        Random random = new Random();
        Vec3d direction = player.getRotationVector();
        for (int i = 0; i < range; i++) {
            double x = player.getX() + direction.x * i;
            double y = player.getY() + player.getEyeHeight(player.getPose()) + direction.y * i;
            double z = player.getZ() + direction.z * i;

            BlockPos blockPos = new BlockPos((int) x, (int) y, (int) z);

            if (!player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.AIR) && !player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.LIGHT)) {
                return;
            }

            if (i > 2 && i % 2 == 0) {
                PlayerParticle.show(player, effect, x, y, z, 0.6f, 0.1f, 0.6f, 0.1f, particleCount);
            }

            player.getWorld().getEntitiesByType(
                    TypeFilter.instanceOf(LivingEntity.class),
                    new Box(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1),
                    entity -> entity != player
            ).forEach(entity -> {
                if (entity.isPlayer()) {
                    PlayerEntity player1 = (PlayerEntity) entity;
                    if (player1.isCreative() || player1.isSpectator() || ClaimCheck.canPvP(player, player.getWorld()))
                        return;
                }
                entity.damage((ServerWorld) player.getWorld(), player.getDamageSources().mobProjectile(player, player), damage);
                PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), 0.5f, 0.94f);
                if (!advanced) {
                    return;
                }

                PotionEffect.add(entity, StatusEffects.GLOWING, 40, 0);
                if (random.nextInt(100) >= 10) {
                    return;
                }

                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, player.getWorld());
                lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
                player.getWorld().spawnEntity(lightning);
            });
        }
    }
}