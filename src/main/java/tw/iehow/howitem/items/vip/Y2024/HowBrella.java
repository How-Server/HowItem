package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowBrella {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getOffHandStack(), Items.CROSSBOW, 1337002)){
            if (!player.getOffHandStack().get(DataComponentTypes.CHARGED_PROJECTILES).isEmpty()){
                PotionEffect.add(player, StatusEffects.SLOW_FALLING, 10, 1);
                PotionEffect.add(player, StatusEffects.RESISTANCE, 10, 0);
            }
        }
    }
    public static void close(PlayerEntity player, ItemStack stack) {
        if(SlotCheck.isValid(stack, Items.CROSSBOW, 1337002)) {
            stack.set(DataComponentTypes.CHARGED_PROJECTILES, Items.AIR.getDefaultStack().get(DataComponentTypes.CHARGED_PROJECTILES));
            ItemEntity itemEntity = new ItemEntity(player.getWorld(), player.getX(), player.getY(), player.getZ(), new ItemStack(Items.ARROW));
            itemEntity.setPickupDelay(0);
            player.getWorld().spawnEntity(itemEntity);
        }
    }
}
