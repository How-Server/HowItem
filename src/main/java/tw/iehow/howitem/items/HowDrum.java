package tw.iehow.howitem.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;

public class HowDrum extends BaseHowItem {

    public HowDrum() {
        super(Items.SKULL_BANNER_PATTERN, 1337017);
    }

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        long cooldown = CooldownManager.get(player.getUuid(), CooldownType.TOY);
        if (cooldown > 0) {
            PlayerActionBar.showCD(serverPlayer, cooldown);
            return;
        }
        world.createExplosion(null, null, null, entity.getX(), entity.getY(), entity.getZ(), 1.0F, false, World.ExplosionSourceType.NONE);
        PlayerSound.play(serverPlayer, SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 1.0F, 1.0F);
        PlayerParticle.show(serverPlayer, ParticleTypes.SONIC_BOOM, player.getX(), player.getY() + 0.2, player.getZ(), 1.6F, 0.8F, 1.6F, 0.1F, 10);
        PlayerParticle.show(serverPlayer, ParticleTypes.SOUL, player.getX(), player.getY() + 0.2, player.getZ(), 0.4F, 0.5F, 0.4F, 0.2F, 30);
        CooldownManager.set(player.getUuid(), CooldownType.TOY, 120);
    }

}
