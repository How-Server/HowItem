package tw.iehow.howitem.items.pen;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.ClaimCheck;

import java.util.Random;

public class Pencil extends BaseHowItem {

    public Pencil() {
        super(Items.MOJANG_BANNER_PATTERN, 1337018);
    }

    private static void penShoot(PlayerEntity player, float damage, int range, boolean advanced, int particleCount) {
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
                PlayerParticle.show(player, ParticleTypes.SQUID_INK, x, y, z, 0.6f, 0.1f, 0.6f, 0.1f, particleCount);
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

    public void unsafeUse(PlayerEntity player, Hand hand) {
        long chargeCooldown = CooldownManager.get(player.getUuid(), CooldownType.PEN_CHARGED);
        if (chargeCooldown == 0) {
            penShoot(player, 3.0F, 8, false, 5);
            PlayerSound.onlyPlay(player, SoundEvents.BLOCK_CHERRY_SAPLING_STEP, 1.0f, 1.0f);
            PotionEffect.add(player, StatusEffects.SLOWNESS, 40, 1);
            PotionEffect.add(player, StatusEffects.WEAKNESS, 40, 1);
            CooldownManager.set(player.getUuid(), CooldownType.PEN_CHARGED, 40);
        }

        long cooldown = CooldownManager.get(player.getUuid(), CooldownType.PEN);
        if (cooldown == 0) {
            penShoot(player, 3.0F, 8, false, 2);
            PlayerSound.onlyPlay(player, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, 0.3f, 0.3f);
            CooldownManager.set(player.getUuid(), CooldownType.PEN, 4);
            PlayerActionBar.showCD(player, chargeCooldown);
        }

    }
}
