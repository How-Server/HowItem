package tw.iehow.howitem.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.CooldownManager;

public class BlueKatana extends BaseHowItem {

    public BlueKatana() {
        super(Items.NETHERITE_SWORD, 1337008);
    }


    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        if (!player.getSteppingBlockState().getBlock().equals(Blocks.WATER)) return;

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        long cooldown = CooldownManager.get(player.getUuid(), CooldownType.SWORD);

        if (cooldown > 0) {
            PlayerActionBar.showCD(serverPlayer, cooldown);
            return;
        }

        PotionEffect.add(player, StatusEffects.ABSORPTION, 120, 1);
        PotionEffect.add(player, StatusEffects.DOLPHINS_GRACE, 120, 2);
        PotionEffect.add((LivingEntity) entity, StatusEffects.SLOWNESS, 20, 2);
        player.setAir(player.getMaxAir());
        PlayerParticle.show(player, ParticleTypes.BUBBLE, player.getX(), player.getY() + 1.0, player.getZ(), 1.5F, 1.5F, 1.5F, 0.1F, 120);
        PlayerSound.onlyPlay(player, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 1.0F, 1.0F);
        CooldownManager.set(player.getUuid(), CooldownType.SWORD, 120);

    }

}
