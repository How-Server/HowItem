package tw.iehow.howitem.items.popicle;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.apply.PlayerSound;

import static tw.iehow.howitem.LegacyItem.PopcicleDisplay.how_popcicle_stick;

public class PopicleSoda extends BaseHowItem {

    public PopicleSoda() {
        super(Items.MOJANG_BANNER_PATTERN, 1337014);
    }

    public void safeUse(PlayerEntity player, Hand hand) {
        PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), 1.0f, 0.94f);
        PlayerSound.play(player, SoundEvents.ENTITY_GENERIC_EAT.value(), 1.0f, 1.0f);
        player.setStackInHand(hand, how_popcicle_stick());
        player.getAttributeInstance(EntityAttributes.SCALE).setBaseValue(1.5d);
        player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.05d);
    }
}
