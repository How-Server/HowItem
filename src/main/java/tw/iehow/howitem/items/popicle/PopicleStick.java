package tw.iehow.howitem.items.popicle;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.apply.PlayerSound;

import static tw.iehow.howitem.LegacyItem.PopcicleDisplay.how_popcicle;

public class PopicleStick extends BaseHowItem {

    public PopicleStick() {
        super(Items.MOJANG_BANNER_PATTERN, 1337013);
    }

    public void safeUse(PlayerEntity player, Hand hand) {
        PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), 1.0f, 0.5f);
        player.setStackInHand(hand, how_popcicle());
        player.getAttributeInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1.0d);
        player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1d);
    }
}
