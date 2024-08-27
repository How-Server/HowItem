package tw.iehow.howitem.util;

import net.minecraft.item.ItemStack;
import tw.iehow.howitem.enums.TriggerType;

public class ItemSlotPair {
    private final TriggerType type;
    private final ItemStack stack;

    public ItemSlotPair(TriggerType type, ItemStack stack) {
        this.type = type;
        this.stack = stack;
    }

    public TriggerType getType() {
        return type;
    }

    public ItemStack getStack() {
        return stack;
    }
}
