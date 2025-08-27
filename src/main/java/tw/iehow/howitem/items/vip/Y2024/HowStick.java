package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.apply.PlayerActionBar;

public class HowStick extends BaseHowItem {

    public HowStick() {
        super(Items.FLOWER_BANNER_PATTERN, 1337067);
    }

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        long cooldown = CooldownManager.get(player.getUuid(), CooldownType.SWORD);
        if (cooldown > 0) {
            PlayerActionBar.showCD(serverPlayer, cooldown);
            return;
        }

        WolfEntity wolf = new WolfEntity(EntityType.WOLF, world);
        wolf.setHealth(20.0F);
        wolf.setPos(entity.getX(), entity.getY(), entity.getZ());
        wolf.setAngryAt(entity.getUuid());
        wolf.setOwner(player);
        wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 720000, 2));
        wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 720, 1));
        wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 720, 1));
        wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 720, 1));
        wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 720, 2));
        world.spawnEntity(wolf);
        CooldownManager.set(player.getUuid(), CooldownType.SWORD, 400);
    }

}
