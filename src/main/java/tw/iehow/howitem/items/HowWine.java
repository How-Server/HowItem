package tw.iehow.howitem.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PotionEffect;

public class HowWine extends BaseHowItem {

    public HowWine() {
        super(Items.SKULL_BANNER_PATTERN, 1337015);
    }

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        long cooldown = CooldownManager.get(player.getUuid(), CooldownType.SWORD);
        if (cooldown > 0) {
            PlayerActionBar.showCD(serverPlayer, cooldown);
        }
        LivingEntity livingEntity = (LivingEntity) entity;
        PotionEffect.add(livingEntity, StatusEffects.NAUSEA, 120, 1);
        PotionEffect.add(livingEntity, StatusEffects.SLOW_FALLING, 120, 1);
        PotionEffect.add(livingEntity, StatusEffects.LEVITATION, 60, 1);
        PlayerParticle.show(serverPlayer, ParticleTypes.SOUL, player.getX(), player.getY() + 0.2, player.getZ(), 0.4F, 0.5F, 0.4F, 0.2F, 30);
        CooldownManager.set(player.getUuid(), CooldownType.SWORD, 120);
    }

}
