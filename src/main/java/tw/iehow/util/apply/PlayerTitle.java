package tw.iehow.util.apply;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerTitle {
    public static void showTitle(ServerPlayerEntity player, long CD) {
        player.sendMessage(Text.literal("還有CD " + CD / 20 + " 秒").formatted(Formatting.YELLOW),true);
    }
}
