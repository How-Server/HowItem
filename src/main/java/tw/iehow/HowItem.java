package tw.iehow;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import tw.iehow.util.check.ClaimCheck;
import tw.iehow.util.check.EffectCheck;
import tw.iehow.util.check.EntityCheck;

import static tw.iehow.util.check.SlotCheck.isValid;


public class HowItem implements ModInitializer {
	@Override
	public void onInitialize() {
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (ClaimCheck.permission(player, entity)){return ActionResult.FAIL;}
			if (EntityCheck.entityType(entity)){return ActionResult.PASS;}

			AttackEffect.mainHand(player, world, entity);
			return ActionResult.PASS;
		});

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			EffectCheck.HERO_OF_THE_VILLAGE(player, entity);
			if (isValid(player.getStackInHand(Hand.MAIN_HAND), "minecraft:water_bucket", 1337001)){return ActionResult.FAIL;}
			return ActionResult.PASS;
		});
	}
}