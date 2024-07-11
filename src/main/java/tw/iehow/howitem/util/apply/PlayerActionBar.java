package tw.iehow.howitem.util.apply;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerActionBar {
    public static void showCD(ServerPlayerEntity player, long CD) {
        Text cd = Text.literal("道具CD ").formatted(Formatting.YELLOW)
                .append(Text.literal(String.valueOf(CD / 20)).formatted(Formatting.WHITE))
                .append(" 秒");
            player.sendMessage(cd, true);
        }
    public static void showCD(PlayerEntity player, long CD) {
        Text cd = Text.literal("道具CD ").formatted(Formatting.YELLOW)
                .append(Text.literal(String.valueOf(CD / 20)).formatted(Formatting.WHITE))
                .append(" 秒");

        player.sendMessage(cd, true);
    }
    public static void showText(ServerPlayerEntity player, String text, Formatting color) {
        player.sendMessage(Text.literal(text).formatted(color), true);
    }

    public static void showText(PlayerEntity player, String text, Formatting color) {
        player.sendMessage(Text.literal(text).formatted(color), true);
    }
}
