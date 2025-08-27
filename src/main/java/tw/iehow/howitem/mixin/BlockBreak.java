package tw.iehow.howitem.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tw.iehow.howitem.items.vip.Y2025.HowHook;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class BlockBreak {
    @Shadow @Final
    protected ServerPlayerEntity player;

    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    private void breakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        HowHook.use(player, pos);
        cir.setReturnValue(true);
    }
}