package tw.iehow.howitem.items.popsicle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import tw.iehow.howitem.items.BaseHowItem;

public class PopsicleStick extends BaseHowItem {

    public PopsicleStick() {
        super(Items.MOJANG_BANNER_PATTERN, 1337013);
    }

    public void safeUse(PlayerEntity player, Hand hand) {
        PopsicleUse.restore(player, hand);
    }
}
