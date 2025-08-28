package tw.iehow.howitem.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneBlocked {
    @Shadow @Final
    Inventory input;

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void blockResult(CallbackInfo ci) {
        for (int i = 0; i < 2; i++) {
            NbtComponent nbt = this.input.getStack(i).get(DataComponentTypes.CUSTOM_DATA);
            if (nbt != null && nbt.contains("official")) {
                ci.cancel();
                return;
            }
        }
    }
}