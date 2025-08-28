package tw.iehow.howitem.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.items.vip.Y2025.HowChicken;
import tw.iehow.howitem.items.vip.Y2025.PaintGun;

@Mixin(Item.class)
public abstract class WhileUsing {
    // different from UseItem, it's called every tick, so player can have animation or sound
    // skip claim check
    @Inject(method = "usageTick", at = @At("HEAD"))
    public void whileUse(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci) {
        HowChicken.use(user, stack, remainingUseTicks);
        PaintGun.use(world, user, stack, remainingUseTicks);
    }
}
