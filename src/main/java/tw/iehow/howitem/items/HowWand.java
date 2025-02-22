package tw.iehow.howitem.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.ClaimCheck;

public class HowWand extends BaseHowItem {

    public HowWand() {
        super(Items.FLOWER_BANNER_PATTERN, 1337080);
    }

    public void unsafeUse(PlayerEntity player, Hand hand) {
        long chargeCooldown = CooldownManager.get(player.getUuid(), CooldownType.TOY);
        if (chargeCooldown > 0){
            PlayerActionBar.showCD(player, chargeCooldown);
            return;
        }
        CooldownManager.set(player.getUuid(), CooldownType.TOY, 20);
        Vec3d direction = player.getRotationVector();
        PlayerSound.play(player, SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST.value(), 0.5f, 1.0f);
        for (int i = 0; i < 24; i++) {
            double x = player.getX() + direction.x * i;
            double y = player.getY() + player.getEyeHeight(player.getPose()) + direction.y * i;
            double z = player.getZ() + direction.z * i;

            BlockPos blockPos = new BlockPos((int) x, (int) y, (int) z);

            if (!player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.AIR) && !player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.LIGHT)) {
                return;
            }

            if (i > 2 && i % 2 == 0) {
                PlayerParticle.show(player, ParticleTypes.SCULK_CHARGE_POP, x, y, z, 0.6f, 0.1f, 0.6f, 0.1f, 10);
            }else{
                PlayerParticle.show(player, ParticleTypes.SOUL, x, y, z, 0.6f, 0.1f, 0.6f, 0.1f, 5);
            }

            int finalI = i;
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

                Vec3d pullDirection = player.getPos().subtract(entity.getPos()).normalize();
                if (finalI > 6) entity.setVelocity(pullDirection.x * 3, pullDirection.y, pullDirection.z * 3);
                else entity.setVelocity(pullDirection.x, pullDirection.y, pullDirection.z);
                entity.velocityModified = true;

                PlayerSound.onlyPlay(player, SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 0.2f, 0.94f);
                PotionEffect.add(entity, StatusEffects.GLOWING, 40, 0);
            });
        }
    }
}
