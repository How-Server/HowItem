package tw.iehow;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import tw.iehow.util.check.ClaimCheck;
import tw.iehow.util.check.DimensionCheck;
import tw.iehow.util.check.EntityCheck;

import java.util.Objects;


public class HowItem implements ModInitializer {
	@Override
	public void onInitialize() {
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (ClaimCheck.attackEntity(player, entity)){return ActionResult.FAIL;}
			if (EntityCheck.entityType(entity)){return ActionResult.PASS;}

			AttackEffect.mainHand(player, world, entity);
			return ActionResult.PASS;
		});

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (ClaimCheck.useEntity(player, entity)){return ActionResult.FAIL;}

			UseEffect.mainHand(player, entity);
			return ActionResult.PASS;
		});

//		UseItemCallback.EVENT.register((player, world, hand) -> {
//			if (ClaimCheck.useItem(player, hand)){return TypedActionResult.fail(ItemStack.EMPTY);}
//			if (Objects.requireNonNull(player.getStackInHand(hand).getNbt()).contains("nouse")){
//				return TypedActionResult.fail(ItemStack.EMPTY);
//			}
//
//			return TypedActionResult.success(ItemStack.EMPTY);
//		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (ClaimCheck.useBlock(player, world)){return ActionResult.FAIL;}
			if (!DimensionCheck.isSurvival(player) && !player.hasPermissionLevel(4)){return ActionResult.FAIL;}

			return ActionResult.PASS;
		});
	}
}