package tw.iehow.howitem.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tw.iehow.howitem.util.apply.PlayerSound;

@Mixin(GoatHornItem.class)
public abstract class GoatHorn {
    @Inject(method = "use", at = @At("HEAD"))
    public void useHorn(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (user.getStackInHand(hand).get(DataComponentTypes.CUSTOM_MODEL_DATA) == null
        && user.getStackInHand(hand).get(DataComponentTypes.INSTRUMENT) == null) {
            PlayerSound.play(user, SoundEvent.of(Identifier.of("minecraft:item.goat_horn.sound.call")), 1.0f, 1.0f);
        }
    }
}