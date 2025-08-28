package tw.iehow.howitem.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tw.iehow.howitem.items.horn.DefaultHorn;

@Mixin(GoatHornItem.class)
public abstract class HornUse {
    // after claim check
    @Inject(method = "use", at = @At("HEAD"))
    public void useHorn(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        DefaultHorn.use(user, hand);
    }
}