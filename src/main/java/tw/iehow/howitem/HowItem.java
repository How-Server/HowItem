package tw.iehow.howitem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import tw.iehow.howitem.util.check.ClaimCheck;
import tw.iehow.howitem.util.check.EntityCheck;
import tw.iehow.howitem.util.placeholder.register;


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
		ServerLifecycleEvents.SERVER_STARTED.register(server -> new register().levelPlaceHolder());

		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemStack stack = player.getStackInHand(hand);
			UseItem.safeMainHand(player, hand);
			if (ClaimCheck.useItem(player, hand)){return TypedActionResult.fail(stack);}
			UseItem.unsafeMainHand(player, hand); // after claim check
			return TypedActionResult.pass(stack);
		});

//		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
//			if (ClaimCheck.useBlock(player, world)){return ActionResult.FAIL;}
//			if (!DimensionCheck.isSurvival(player) && !player.hasPermissionLevel(4)){return ActionResult.FAIL;}
//
//			return ActionResult.PASS;
//		});
	}
}