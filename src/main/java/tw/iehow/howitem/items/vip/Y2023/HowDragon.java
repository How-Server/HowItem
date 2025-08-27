package tw.iehow.howitem.items.vip.Y2023;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowDragon {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.FLOWER_BANNER_PATTERN, 1337037)){
            for (ServerPlayerEntity player1 : player.getServer().getPlayerManager().getPlayerList()){
                if (player1.distanceTo(player) < 24.0){
                    PotionEffect.add(player1, StatusEffects.LUCK, 10, 0);
                    PotionEffect.add(player1, StatusEffects.REGENERATION, 10, 0);
                }
            }
            if (player.getSteppingBlockState().getBlock().equals(Blocks.LAVA)){
                PotionEffect.add(player, StatusEffects.FIRE_RESISTANCE, 10, 1);
                PotionEffect.add(player, StatusEffects.STRENGTH, 10, 2);
                PlayerParticle.show(player, ParticleTypes.FLAME, player.getX(), player.getY() + 2.0, player.getZ(), 1.6F, 1.0F, 1.6F, 0.01f, 4);
            }
            if (player.getSteppingBlockState().getBlock().equals(Blocks.WATER)){
                PotionEffect.add(player, StatusEffects.POISON,10, 4);
            }
        }
    }
}
