package tw.iehow.util.apply;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerActionBar {
    public static void showCD(ServerPlayerEntity player, long CD) {

//        int phantomCD = player.getServerWorld().getScoreboard().getPlayerScore("phantom_Display", player.getScoreboard().getObjective("bossfight")).getScore();
        Text cd = Text.literal("道具CD ").formatted(Formatting.YELLOW)
                .append(Text.literal(String.valueOf(CD / 20)).formatted(Formatting.WHITE))
                .append(" 秒");
//        Text divider = Text.literal(" | ").formatted(Formatting.WHITE).formatted(Formatting.BOLD);
//        Text cdWithBoss = Text.empty().append(cd).append(divider)
//                .append("Boss技能CD ").formatted(Formatting.RED)
//                .append(Text.literal(String.valueOf(phantomCD)).formatted(Formatting.DARK_RED))
//                .append(" 秒");
//
//        if (player.getCommandTags().contains("bf.Ph")) {
//            player.sendMessage(cdWithBoss, true);
//        } else {
            player.sendMessage(cd, true);
        }
//    }
    public static void showText(ServerPlayerEntity player, String text, Formatting color) {
        player.sendMessage(Text.literal(text).formatted(color), true);
    }

    public static void showText(PlayerEntity player, String text, Formatting color) {
        player.sendMessage(Text.literal(text).formatted(color), true);
    }
}
