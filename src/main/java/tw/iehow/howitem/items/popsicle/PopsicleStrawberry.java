package tw.iehow.howitem.items.popsicle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.items.popsicle.util.PopsicleUse;

public class PopsicleStrawberry extends BaseHowItem {

    public PopsicleStrawberry() {
        super(Items.MOJANG_BANNER_PATTERN, 1337017);
    }

    public void safeUse(PlayerEntity player, Hand hand) {
        PopsicleUse.apply(player, hand);
    }
}
