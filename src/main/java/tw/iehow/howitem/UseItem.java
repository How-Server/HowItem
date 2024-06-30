package tw.iehow.howitem;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import tw.iehow.howitem.util.apply.PlayerSound;

import static tw.iehow.howitem.items.vip2407.how_popcicle;
import static tw.iehow.howitem.items.vip2407.how_popcicle_stick;
import static tw.iehow.howitem.util.check.SlotCheck.isValid;

public class UseItem {
    public static void mainHand(PlayerEntity player, Hand hand){
        if (hand == Hand.MAIN_HAND){
            ItemStack stack = player.getStackInHand(hand);

            if (isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337013)){
                PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), 1.0f, 0.5f);
                player.setStackInHand(hand, how_popcicle());
                player.getAttributeInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1.0d);
                player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1d);
            }
            if (isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337014)
            || isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337015)
            || isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337016)
            || isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337017)){
                PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), 1.0f, 0.94f);
                PlayerSound.play(player, SoundEvents.ENTITY_GENERIC_EAT, 1.0f, 1.0f);
                player.setStackInHand(hand, how_popcicle_stick());
                player.getAttributeInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1.5d);
                player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.05d);
            }
        }
    }
}
