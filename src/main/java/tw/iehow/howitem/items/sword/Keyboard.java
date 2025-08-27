package tw.iehow.howitem.items.sword;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PotionEffect;

public class Keyboard extends BaseHowItem {

    public Keyboard() {
        super(Items.NETHERITE_SWORD, 1337014);
    }

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        long cooldown = CooldownManager.get(player.getUuid(), CooldownType.SWORD);
        if (cooldown > 0) {
            PlayerActionBar.showCD(serverPlayer, cooldown);
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;
        PotionEffect.add(livingEntity, StatusEffects.NAUSEA, 150, 1);
        PotionEffect.add(livingEntity, StatusEffects.SLOWNESS, 100, 1);
        CooldownManager.set(player.getUuid(), CooldownType.SWORD, 300);
    }
}
