package tw.iehow.howitem.LegacyItem;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlasticBottle {
    public static ItemStack usedBottle() throws CommandSyntaxException {
        ItemStack stack = Items.FLINT.getDefaultStack();
        stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(Collections.singletonList(1337002.0f), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
        stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("用過的空杯").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(false)));
        List<Text> lore = new ArrayList<>(List.of(
                Text.literal("烹飪食材").setStyle(Style.EMPTY.withItalic(false).withColor(Formatting.DARK_PURPLE)),
                Text.literal("PET瓶胚").setStyle(Style.EMPTY.withColor(Formatting.DARK_GREEN).withItalic(false)),
                Text.literal(""),
                Text.literal("「可重複使用。」").setStyle(Style.EMPTY.withColor(Formatting.BLUE).withItalic(false))));
        NbtCompound nbt = StringNbtReader.readCompound("{Tags:[\"BBTCup:1\"]}");
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        stack.set(DataComponentTypes.LORE, new LoreComponent(lore));
        return stack;
    }
}