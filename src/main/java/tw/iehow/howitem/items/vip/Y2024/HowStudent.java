package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Formatting;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowStudent {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337020)){
            if (player.isSneaking() && !player.getAbilities().flying){
                PotionEffect.add(player, StatusEffects.JUMP_BOOST, 25, 2);
                PlayerActionBar.showText(player, "先蹲後跳，魚躍龍門。", Formatting.GOLD);
            }
            if ((player.getSteppingBlockState().getBlock().equals(Blocks.AIR) || player.getSteppingBlockState().getBlock().equals(Blocks.LIGHT))
                    && player.hasStatusEffect(StatusEffects.JUMP_BOOST) && !player.getAbilities().flying){
                PlayerParticle.show(player, ParticleTypes.GUST, player.getX(), player.getY() + 0.8 ,player.getZ(), 1.6F, 1.0F, 1.6F, 0.001f, 2);
            }
        }
    }
}
