package tw.iehow.howitem.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilBlocked {

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void blockResult(CallbackInfo ci) {
        AnvilScreenHandler handler = (AnvilScreenHandler) (Object) this;
        for (int i = 0; i < 2; i++) {
            NbtComponent nbt = handler.getSlot(i).getStack().get(DataComponentTypes.CUSTOM_DATA);
            if (nbt != null && nbt.contains("official")) {
                ci.cancel();
                return;
            }
        }
    }
}