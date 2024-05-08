package tw.iehow.util.placeholder;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.util.Identifier;

public class register {
    public void levelPlaceHolder() {
        Placeholders.register(new Identifier("player", "level"), (ctx, arg) -> {
            if (!ctx.hasPlayer())
                return PlaceholderResult.invalid("No player!");
            return PlaceholderResult.value((ctx.player() != null ? ctx.player().experienceLevel : 0) + "");
        });
    }
}
