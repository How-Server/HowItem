package tw.iehow.howitem.items.vip.Y2025;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.check.ClaimCheck;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.List;
import java.util.Random;

public class HowPants {
    public static void apply(PlayerEntity player) {
        Random random = new Random();
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.LEGS), Items.NETHERITE_LEGGINGS, Identifier.of("minecraft:how_red_pants"))){
            if (ClaimCheck.interactEntity(player)) return;
            List<LivingEntity> passiveEntities = ((ServerPlayerEntity) player).getWorld().getEntitiesByClass(LivingEntity.class,
                    player.getBoundingBox().expand(16), entity -> entity instanceof PassiveEntity
            );
            for (LivingEntity entity : passiveEntities) {
                if (entity instanceof TameableEntity tameable && ((tameable.isTamed() && tameable.getOwner() != player) || tameable.isSitting())) continue;
                if (entity.isSilent() || entity.isSleeping()) continue;
                if (entity instanceof HoglinEntity) continue;

                if (entity instanceof MobEntity mob) {
                    if (mob instanceof MerchantEntity || mob instanceof AxolotlEntity) {
                        mob.getNavigation().startMovingTo(player, 0.5);
                    }else mob.getNavigation().startMovingTo(player, 1.2);
                }
                if (random.nextInt(100) > 70) PlayerParticle.show(entity, ParticleTypes.HEART, entity.getX(), entity.getY() + 0.8, entity.getZ(), 0.5F, 0.5F, 0.5F, 0.001f, 1);
            }
        }
    }
}
