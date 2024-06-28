package tw.iehow.howitem.util.check;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class SlotCheck {
    public static boolean isValid(ItemStack stack, String itemId, int customModelData) {
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            Identifier itemIdentifier = Registries.ITEM.getId(item);

            if (!itemIdentifier.toString().equals(itemId)) {
                return false;
            }

            if (!stack.getComponents().contains(DataComponentTypes.CUSTOM_MODEL_DATA)) {
                return false;
            }

            int actualCustomModelData = stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA).value();
            return actualCustomModelData == customModelData;
        }
        return false;
    }
}
