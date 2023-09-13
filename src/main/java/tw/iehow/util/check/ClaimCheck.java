package tw.iehow.util.check;

import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.ClaimList;
import me.drex.itsours.claim.permission.PermissionManager;
import me.drex.itsours.claim.permission.node.Node;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;

public class ClaimCheck {
    public static boolean permission(PlayerEntity player, Entity entity){
        Optional<AbstractClaim> claim = ClaimList.INSTANCE.getClaimAt((ServerWorld) player.getWorld(), player.getSteppingPos());

        if (!(entity instanceof PlayerEntity) && claim.isPresent() && !claim.get().hasPermission(player.getUuid(), PermissionManager.DAMAGE_ENTITY, Node.dummy(Registries.ENTITY_TYPE, entity.getType()))) {
            return true;
        }
        return (entity instanceof PlayerEntity) && claim.isPresent() && !claim.get().hasPermission(null, PermissionManager.PVP);
    }
}
