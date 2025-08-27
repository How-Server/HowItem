package tw.iehow.howitem.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import tw.iehow.howitem.util.apply.PlayerSound;

public class DefaultHorn{
    // we remove the sound of default goat horn (mediv), and play a custom sound for WarHorn
    // this is a fallback sound that the default goat horn is not overridden by the custom model data
    public static void use(PlayerEntity user, Hand hand) {
        if (user.getStackInHand(hand).get(DataComponentTypes.CUSTOM_MODEL_DATA) == null
                && user.getStackInHand(hand).get(DataComponentTypes.INSTRUMENT).instrument().getKey().get().getValue().toString().equals("minecraft:ponder_goat_horn")) {
            PlayerSound.play(user, SoundEvent.of(Identifier.of("minecraft:item.goat_horn.sound.call")), 1.0f, 1.0f);
        }
    }
}
