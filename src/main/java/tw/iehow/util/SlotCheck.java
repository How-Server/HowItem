package tw.iehow.util;

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

            if (!stack.hasNbt() || !stack.getNbt().contains("CustomModelData")) {
                return false;
            }

            int actualCustomModelData = stack.getNbt().getInt("CustomModelData");
            return actualCustomModelData == customModelData;
        }
        return false;
    }
}
