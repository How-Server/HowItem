package tw.iehow.howitem.items.popicle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import tw.iehow.howitem.items.BaseHowItem;

public class PopicleStrawberry extends BaseHowItem {

    public PopicleStrawberry() {
        super(Items.MOJANG_BANNER_PATTERN, 1337017);
    }

    public void safeUse(PlayerEntity player, Hand hand) {
        PopicleUse.apply(player, hand);
    }
}
