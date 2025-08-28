package tw.iehow.howitem.items.vip.Y2025;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.apply.PotionEffect;

public class HowHammer extends BaseHowItem {
    public HowHammer() {
        super(Items.MACE, 1337003);
    }

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        long cd = CooldownManager.get(player.getUuid(), CooldownType.SWORD);
        if (player.isSneaking() && player.isOnGround() && cd == 0) {
            PotionEffect.add(player, StatusEffects.LEVITATION, 3, 59);
            CooldownManager.set(player.getUuid(), CooldownType.SWORD, 10, player, player.getMainHandStack());
        }
    }
}
