package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.concurrent.atomic.AtomicInteger;

public class HeartGlasses {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337026)){
            heartShoot(player);
        }
    }

    private static void heartShoot(PlayerEntity player) {
        AtomicInteger distance = new AtomicInteger(0);
        Vec3d direction = player.getRotationVector();
        for (int i = 0; i < 16; i++) {
            double x = player.getX() + direction.x * i;
            double y = player.getY() + player.getEyeHeight(player.getPose()) + direction.y * i;
            double z = player.getZ() + direction.z * i;
            BlockPos blockPos = new BlockPos((int) x, (int) y, (int) z);
            if (!player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.AIR) && !player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.LIGHT)) return;

            int finalI = i;
            player.getWorld().getEntitiesByType(
                    TypeFilter.instanceOf(LivingEntity.class),
                    new Box(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1),
                    entity -> entity != player
            ).forEach(entity -> {
                if (entity.isPlayer()) { PlayerEntity targetPlayer = (PlayerEntity) entity;
                    if (targetPlayer.isCreative() || targetPlayer.isSpectator()) return;
                    if (!targetPlayer.hasStatusEffect(StatusEffects.REGENERATION)){
                        distance.set(finalI);
                    }
                }
            });
            if (distance.get() > 0) break;
        }
        if (distance.get() > 0){
            for (int i = 0; i < distance.get() + 1; i++) {
                double x = player.getX() + direction.x * i;
                double y = player.getY() + player.getEyeHeight(player.getPose()) + direction.y * i;
                double z = player.getZ() + direction.z * i;
                if (i % 2 == 0 && i > 0) PlayerParticle.show(player, ParticleTypes.HEART, x, y, z, 0.1f, 0.1f, 0.1f, 0.1f, 5);
                player.getWorld().getEntitiesByType(
                        TypeFilter.instanceOf(LivingEntity.class),
                        new Box(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1),
                        entity -> entity != player
                ).forEach(entity -> {
                    if (entity.isPlayer()) { PlayerEntity player1 = (PlayerEntity) entity;
                        if (player1.isCreative() || player1.isSpectator()) return;
                        PotionEffect.add(player1, StatusEffects.REGENERATION, 300, 0);
                        PlayerParticle.show(player1, ParticleTypes.HEART, player1.getX(), player1.getY() + 1.0, player1.getZ(), 1.0F, 0.5F, 1.0F, 0.1F, 8);
                        PlayerActionBar.showText(player1, "您收到來自 " + player.getName().getLiteralString() + "內心的愛意！", Formatting.GOLD);
                        PlayerActionBar.showText(player, "您對 " + player1.getName().getLiteralString() + " 投射了內心的愛意！", Formatting.GOLD);
                    }
                });
            }
        }
    }
}
