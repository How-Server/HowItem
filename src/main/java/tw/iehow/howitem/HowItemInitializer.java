package tw.iehow.howitem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import tw.iehow.howitem.enums.TriggerType;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.ItemSlotPair;
import tw.iehow.howitem.util.check.ClaimCheck;
import tw.iehow.howitem.util.check.EntityCheck;
import tw.iehow.howitem.util.placeholder.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class HowItemInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            new register().levelPlaceHolder();
            CooldownManager.setServer(server);
        });

        registerItem();

    }

    public void registerItem() {
        Map<Integer, BaseHowItem> items = ItemManager.getItems();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            List<ItemSlotPair> stacks = new ArrayList<>();
            List<BaseHowItem> trigger = new ArrayList<>();
            stacks.add(new ItemSlotPair(TriggerType.ATTACK_HAND, player.getStackInHand(hand)));
            stacks.add(new ItemSlotPair(TriggerType.MAIN_HAND, player.getStackInHand(Hand.MAIN_HAND)));
            stacks.add(new ItemSlotPair(TriggerType.OFF_HAND, player.getStackInHand(Hand.OFF_HAND)));
            stacks.add(new ItemSlotPair(TriggerType.HEAD, player.getEquippedStack(EquipmentSlot.HEAD)));
            stacks.add(new ItemSlotPair(TriggerType.BODY, player.getEquippedStack(EquipmentSlot.BODY)));
            stacks.add(new ItemSlotPair(TriggerType.LEGS, player.getEquippedStack(EquipmentSlot.LEGS)));
            stacks.add(new ItemSlotPair(TriggerType.FEET, player.getEquippedStack(EquipmentSlot.FEET)));
            BaseHowItem targetItem = null;

            for (ItemSlotPair pair : stacks) {
                CustomModelDataComponent cmd = pair.getStack().getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA);
                if (cmd == null) {
                    continue;
                }
                targetItem = items.get(Objects.hash(pair.getStack().getItem().toString(), cmd.value()));
                if (targetItem != null && targetItem.canTrigger((pair.getType()))) {
                    trigger.add(targetItem);
                }
            }

            for (BaseHowItem item : trigger) {
                item.beforeSafeAttack(player, world, entity);
            }

            if (ClaimCheck.attackEntity(player, entity)) {
                return ActionResult.FAIL;
            }

            if (EntityCheck.entityType(entity)) {
                return ActionResult.PASS;
            }

            for (BaseHowItem item : trigger) {
                item.beforeUnsafeAttack(player, world, entity);
            }

            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            CustomModelDataComponent cmd = stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA);
            if (cmd == null) {
                return TypedActionResult.pass(stack);
            }
            BaseHowItem targetItem = items.get(Objects.hash(stack.getItem().toString(), cmd.value()));

            if (targetItem == null) {
                return TypedActionResult.pass(stack);
            }

            targetItem.safeUse(player, hand);

            if (ClaimCheck.useItem(player, hand)) {
                return TypedActionResult.fail(stack);
            }

            targetItem.unsafeUse(player, hand);

            return TypedActionResult.pass(stack);
        });

    }
}