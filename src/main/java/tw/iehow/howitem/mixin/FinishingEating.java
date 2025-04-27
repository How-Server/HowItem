package tw.iehow.howitem.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.list.ClaimList;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tw.iehow.howitem.LegacyItem.PlasticBottle;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

@Mixin(Item.class)
public abstract class FinishingEating {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    public void afterUse(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) throws CommandSyntaxException {
        // used_bottle
        if(isValid(stack, Items.APPLE, 1337001)
                || isValid(stack, Items.GOLDEN_APPLE, 1337010)
                || isValid(stack, Items.GOLDEN_CARROT, 1337004)) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            ItemEntity item = playerEntity.dropItem(PlasticBottle.usedBottle(), true);
            Objects.requireNonNull(item).setPickupDelay(0);
        }
        // lobster
        else if (isValid(stack, Items.ENCHANTED_GOLDEN_APPLE, 1337001)) {
            User user1 = LuckPermsProvider.get().getUserManager().getUser(user.getUuid());
            long expiryDuration = 0;
            for (Node node : user1.getNodes()) {
                if (node.getKey().equals("group.lobster") && node.hasExpiry()) {
                    expiryDuration = node.getExpiryDuration().toSeconds();
                    user1.data().remove(node);
                }
            }
            user1.data().add(InheritanceNode.builder("lobster").expiry(43200 + expiryDuration, TimeUnit.SECONDS).build());
            LuckPermsProvider.get().getUserManager().saveUser(user1);
            Optional<AbstractClaim> optional = ClaimList.getClaimAt(user);
            optional.ifPresent(claim -> claim.onEnter(null, (ServerPlayerEntity) user));
        }
    }
}