package tw.iehow.howitem.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import tw.iehow.howitem.util.apply.PotionEffect;

public class BlackKatana extends BaseHowItem {

    public BlackKatana() {
        super(Items.NETHERITE_SWORD, 1337004);
    }


    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {

        if (entity instanceof PlayerEntity) {
            if (entity.hasPermissionLevel(4)) return;
        }
        LivingEntity livingEntity = (LivingEntity) entity;
        PotionEffect.add(livingEntity, StatusEffects.WITHER, 100, 1);
        PotionEffect.add(livingEntity, StatusEffects.DARKNESS, 300, 1);
    }
}
