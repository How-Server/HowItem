package tw.iehow.howitem.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.CooldownManager;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

public class PinkKatana extends BaseHowItem {
    public PinkKatana() {
        super(Items.NETHERITE_SWORD, 1337003);
    }

    private final int cooldown = 120;

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        long cooldown = CooldownManager.get(player.getUuid(), CooldownType.SWORD);

        if (cooldown > 0) {
            PlayerActionBar.showCD(serverPlayer, cooldown);
            return;
        }
        PotionEffect.add(player, StatusEffects.REGENERATION, 60, 2);
        PotionEffect.add((LivingEntity) entity, StatusEffects.SLOWNESS, 20, 2);
        PlayerParticle.show(player, ParticleTypes.HEART, player.getX(), player.getY() + 1.0, player.getZ(), 0.5F, 0.5F, 0.5F, 1, 5);
        CooldownManager.set(player.getUuid(), CooldownType.SWORD, 120);

    }

}

