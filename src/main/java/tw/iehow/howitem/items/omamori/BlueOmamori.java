package tw.iehow.howitem.items.omamori;

import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class BlueOmamori {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getOffHandStack(), Items.FLOWER_BANNER_PATTERN,1337001)) {
            if (player.getSteppingBlockState().getBlock().equals(Blocks.WATER)) PotionEffect.add(player, StatusEffects.CONDUIT_POWER, 10, 1);
            else if (!player.getSteppingBlockState().getBlock().equals(Blocks.WATER)
                    && player.getWorld().getBlockState(player.getBlockPos().add(0, 1, 0)).getBlock().equals(Blocks.WATER)){
                PotionEffect.add(player, StatusEffects.WATER_BREATHING, 10, 1);
                PotionEffect.add(player, StatusEffects.NIGHT_VISION, 10, 1);
            }
        }
    }
}
