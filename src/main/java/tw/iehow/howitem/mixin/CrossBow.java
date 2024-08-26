package tw.iehow.howitem.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

@Mixin(CrossbowItem.class)
public abstract class CrossBow {
    @Inject(method = "shootAll", at = @At("HEAD"), cancellable = true)
    public void shootCrossbow(World world, LivingEntity shooter, Hand hand, ItemStack stack, float speed, float divergence, LivingEntity target, CallbackInfo ci) {
        if(isValid(stack, Items.CROSSBOW, 1337002)) {
            stack.set(DataComponentTypes.CHARGED_PROJECTILES, Items.AIR.getDefaultStack().get(DataComponentTypes.CHARGED_PROJECTILES));
            ItemEntity itemEntity = new ItemEntity(shooter.getWorld(), shooter.getX(), shooter.getY(), shooter.getZ(), new ItemStack(Items.ARROW));
            itemEntity.setPickupDelay(0);
            shooter.getWorld().spawnEntity(itemEntity);
        }
    }
}