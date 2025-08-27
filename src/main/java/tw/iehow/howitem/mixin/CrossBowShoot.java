package tw.iehow.howitem.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.items.vip.Y2024.HowBrella;

@Mixin(CrossbowItem.class)
public abstract class CrossBowShoot {
    @Inject(method = "shootAll", at = @At("HEAD"))
    public void afterShoot(World world, LivingEntity shooter, Hand hand, ItemStack stack, float speed, float divergence, LivingEntity target, CallbackInfo ci) {
        if (shooter instanceof PlayerEntity player) {
            HowBrella.close(player, stack);
        }
    }
}