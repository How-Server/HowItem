package tw.iehow.howitem.items.popsicle;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import tw.iehow.howitem.util.apply.PlayerSound;

import static tw.iehow.howitem.LegacyItem.PopcicleDisplay.how_popcicle;
import static tw.iehow.howitem.LegacyItem.PopcicleDisplay.how_popcicle_stick;

public class PopsicleUse {
    public static void apply(PlayerEntity player, Hand hand) {
        PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), 1.0f, 0.94f);
        PlayerSound.play(player, SoundEvents.ENTITY_GENERIC_EAT.value(), 1.0f, 1.0f);
        player.setStackInHand(hand, how_popcicle_stick());
        player.getAttributeInstance(EntityAttributes.SCALE).setBaseValue(1.5d);
        player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.05d);
    }
    public static void restore(PlayerEntity player, Hand hand) {
        PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), 1.0f, 0.5f);
        player.setStackInHand(hand, how_popcicle());
        player.getAttributeInstance(EntityAttributes.SCALE).setBaseValue(1.0d);
        player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.1d);
    }
}
