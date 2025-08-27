package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.check.SlotCheck;

public class ChocolateBox {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getOffHandStack(), Items.SKULL_BANNER_PATTERN, 1337029)){
            PlayerParticle.show(player, ParticleTypes.CHERRY_LEAVES, player.getX(), player.getY() + 2.0, player.getZ(), 1.5F, 0.5F, 1.5F, 1, 4);
        }
    }
}
