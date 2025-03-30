package tw.iehow.howitem.util.check;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SlotCheck {
    public static boolean isValid(ItemStack stack, Item item, int customModelData) {
        if (!stack.isEmpty()) {

            if (stack.getItem() != item) return false;
            if (!stack.getComponents().contains(DataComponentTypes.CUSTOM_MODEL_DATA)) return false;

            return stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA).floats().contains((float) customModelData);
        }
        return false;
    }
    public static boolean isValid(ItemStack stack, Item item, int minCMD, int maxCMD) {
        if (stack.isEmpty() || stack.getItem() != item) return false;
        if (!stack.getComponents().contains(DataComponentTypes.CUSTOM_MODEL_DATA)) return false;

        float customModelData = stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA).floats().getFirst();
        return customModelData >= minCMD && customModelData <= maxCMD;
    }

    public static boolean isValid(ItemStack stack, Item item, Identifier itemModel) {
        if (!stack.isEmpty()) {

            if (stack.getItem() != item) return false;
            if (!stack.getComponents().contains(DataComponentTypes.ITEM_MODEL)) return false;

            Identifier actualItemModel = stack.getComponents().get(DataComponentTypes.ITEM_MODEL);
            return actualItemModel.equals(itemModel);
        }
        return false;
    }
}
