package tw.iehow.howitem.items;

import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.list.ClaimList;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Lobster {
    public static void applyFly(ItemStack stack, LivingEntity user) {
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
