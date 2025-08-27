package tw.iehow.howitem.items.vip.Y2023;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowHat {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.FLOWER_BANNER_PATTERN, 1337030)
            || SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337037)){
            PlayerParticle.show(player, ParticleTypes.SNOWFLAKE, player.getX(), player.getY() + 3.0, player.getZ(), 1.6F, 1.0F, 1.6F, 0.01f, 4);
            PlayerParticle.show(player, ParticleTypes.ITEM_SNOWBALL, player.getX(), player.getY() + 0.2, player.getZ(), 0.8F, 0.5F, 0.8F, 0.01f, 2);
        }
    }
}
