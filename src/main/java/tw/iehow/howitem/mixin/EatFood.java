package tw.iehow.howitem.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tw.iehow.howitem.items.bottle;

import java.util.Objects;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

@Mixin(Item.class)
public abstract class EatFood {
    @Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
    public void tradeWithVillager(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) throws CommandSyntaxException {
        if(isValid(stack, Items.APPLE, 1337001)
                || isValid(stack, Items.GOLDEN_APPLE, 1337010)
                || isValid(stack, Items.GOLDEN_CARROT, 1337004)) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            ItemEntity item = playerEntity.dropItem(bottle.usedBottle(), true);
            Objects.requireNonNull(item).setPickupDelay(0);
        }
    }
}