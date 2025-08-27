package tw.iehow.howitem.mixin;

import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.list.ClaimList;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Mixin(Item.class)
public abstract class FlyingLobster {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    public void applyFly(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (SlotCheck.isValid(stack, Items.ENCHANTED_GOLDEN_APPLE, 1337001)) {
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