package tw.iehow.howitem.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.items.vip.Y2024.HowClown;
import tw.iehow.howitem.items.omamori.BlackOmamori;
import tw.iehow.howitem.items.omamori.BlueOmamori;
import tw.iehow.howitem.items.omamori.PurpleOmamori;
import tw.iehow.howitem.items.omamori.RedOmamori;
import tw.iehow.howitem.items.vip.Y2023.*;
import tw.iehow.howitem.items.vip.Y2024.*;
import tw.iehow.howitem.items.vip.Y2025.HowPants;

@Mixin(PlayerEntity.class)
public abstract class TickItem {
    @Unique
    private int tickCounter = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        tickCounter++;
        if (tickCounter % 4 != 0) return;

        PlayerEntity player = ((PlayerEntity)(Object)this);
        ItemStack offHand = player.getStackInHand(Hand.OFF_HAND);
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leg = player.getEquippedStack(EquipmentSlot.LEGS);

        if (offHand.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null){
            BlueOmamori.apply(player);
            PurpleOmamori.apply(player);
            RedOmamori.apply(player);
            BlackOmamori.apply(player);

            RedRose.apply(player);
            ChocolateBox.apply(player);

            HowBucket.apply(player);
            HowPillow.apply(player);
            HowBrella.apply(player);
        }

        if (head.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null){
            HowHat.apply(player);
            HowDragon.apply(player);
            HowStudent.apply(player);
            HowClown.apply(player);
            HeartGlasses.apply(player);
            HowRabbit.apply(player);
        }

        if (chest.get(DataComponentTypes.ITEM_MODEL) != null){
            HowSweater.apply(player);
        }

        if (leg.get(DataComponentTypes.ITEM_MODEL) != null){
            HowPants.apply(player);
        }
    }
}
