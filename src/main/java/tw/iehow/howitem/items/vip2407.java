package tw.iehow.howitem.LegacyItem;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class vip2407 {
    public static ItemStack how_popcicle_stick(){
        ItemStack stack = Items.MOJANG_BANNER_PATTERN.getDefaultStack();
        stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(1337013));
        stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("How").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withItalic(false))
                .append(Text.literal("棒棒冰棒棍").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withBold(true).withItalic(false))));
        List<Text> lore = new ArrayList<>(List.of(
                Text.literal("How盤子2024七月特典").setStyle(Style.EMPTY.withItalic(false).withColor(Formatting.DARK_PURPLE)),
                Text.literal(""),
                Text.literal("沁涼盛夏").setStyle(Style.EMPTY.withColor(Formatting.AQUA).withItalic(false)),
                Text.literal("來根冰棒解解暑氣！").setStyle(Style.EMPTY.withColor(Formatting.AQUA).withItalic(false)),
                Text.literal("讓甜蜜蜜雪糕融化你熾熱的心").setStyle(Style.EMPTY.withColor(Formatting.AQUA).withItalic(false)),
                Text.literal(""),
                Text.literal("不同口味、不同體驗").setStyle(Style.EMPTY.withColor(Formatting.GREEN).withItalic(false)),
                Text.literal("準備好放大你的歡樂時光了嗎？").setStyle(Style.EMPTY.withColor(Formatting.GREEN).withItalic(false)),
                Text.literal(""),
                Text.literal("「主手使用」即可再來一根。").setStyle(Style.EMPTY.withColor(Formatting.YELLOW).withItalic(false))));
        stack.set(DataComponentTypes.LORE, new LoreComponent(lore));
        stack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        return stack;
    }
    public static ItemStack how_popcicle(){
        ItemStack stack = Items.MOJANG_BANNER_PATTERN.getDefaultStack();
        Text name = null;
        Random random = new Random();
        int type = random.nextInt(4);
        switch (type) {
            case 0:
                name = Text.literal("How").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withItalic(false))
                        .append(Text.literal("棒棒冰棒").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withBold(true).withItalic(false)))
                        .append(Text.literal(" 蘇打").setStyle(Style.EMPTY.withItalic(false).withColor(Formatting.AQUA)));
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(1337014));
                break;
            case 1:
                name =Text.literal("How").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withItalic(false))
                        .append(Text.literal("棒棒冰棒").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withBold(true).withItalic(false)))
                        .append(Text.literal(" 布丁").setStyle(Style.EMPTY.withItalic(false).withColor(Formatting.YELLOW)));
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(1337015));
                break;
            case 2:
                name = Text.literal("How").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withItalic(false))
                        .append(Text.literal("棒棒冰棒").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withBold(true).withItalic(false)))
                        .append(Text.literal(" 巧克力").setStyle(Style.EMPTY.withItalic(false).withColor(TextColor.fromRgb(0x7B3F00))));
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(1337016));
                break;
            case 3:
                name = Text.literal("How").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withItalic(false))
                        .append(Text.literal("棒棒冰棒").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withBold(true).withItalic(false)))
                        .append(Text.literal(" 草莓").setStyle(Style.EMPTY.withItalic(false).withColor(TextColor.fromRgb(0xF07585))));
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(1337017));
                break;
        }
        List<Text> lore = new ArrayList<>(List.of(
                Text.literal("How盤子2024七月特典").setStyle(Style.EMPTY.withItalic(false).withColor(Formatting.DARK_PURPLE)),
                Text.literal(""),
                Text.literal("沁涼盛夏").setStyle(Style.EMPTY.withColor(Formatting.AQUA).withItalic(false)),
                Text.literal("來根冰棒解解暑氣！").setStyle(Style.EMPTY.withColor(Formatting.AQUA).withItalic(false)),
                Text.literal("讓甜蜜蜜雪糕融化你熾熱的心").setStyle(Style.EMPTY.withColor(Formatting.AQUA).withItalic(false)),
                Text.literal(""),
                Text.literal("不同口味、不同體驗").setStyle(Style.EMPTY.withColor(Formatting.GREEN).withItalic(false)),
                Text.literal("準備好放大你的歡樂時光了嗎？").setStyle(Style.EMPTY.withColor(Formatting.GREEN).withItalic(false)),
                Text.literal(""),
                Text.literal("「主手食用」即可放大自己！").setStyle(Style.EMPTY.withColor(Formatting.YELLOW).withItalic(false))));
        stack.set(DataComponentTypes.CUSTOM_NAME, name);
        stack.set(DataComponentTypes.LORE, new LoreComponent(lore));
        stack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        return stack;
    }
}