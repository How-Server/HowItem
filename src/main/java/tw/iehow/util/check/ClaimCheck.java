package tw.iehow.util.check;

import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.ClaimList;
import me.drex.itsours.claim.permission.PermissionManager;
import me.drex.itsours.claim.permission.node.Node;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Optional;

public class ClaimCheck {
    public static boolean attackEntity(PlayerEntity player, Entity entity){
        Optional<AbstractClaim> claim = ClaimList.getClaimAt(player.getWorld(), player.getSteppingPos());

        if (!(entity instanceof PlayerEntity) && claim.isPresent() && !claim.get().hasPermission(player.getUuid(), PermissionManager.DAMAGE_ENTITY, Node.registry(Registries.ENTITY_TYPE, entity.getType()))) {
            return true;
        }
        return (entity instanceof PlayerEntity) && claim.isPresent() && !claim.get().hasPermission(null, PermissionManager.PVP);
    }
    public static boolean useEntity(PlayerEntity player, Entity entity){
        Optional<AbstractClaim> claim = ClaimList.getClaimAt(player.getWorld(), player.getSteppingPos());
        return claim.isPresent() && !claim.get().hasPermission(player.getUuid(), PermissionManager.INTERACT_ENTITY, Node.registry(Registries.ENTITY_TYPE, entity.getType()));
    }
    public static boolean useItem(PlayerEntity player, Hand hand){
        Optional<AbstractClaim> claim = ClaimList.getClaimAt(player.getWorld(), player.getSteppingPos());
        return claim.isPresent() && !claim.get().hasPermission(player.getUuid(), PermissionManager.USE_ITEM, Node.registry(Registries.ITEM, player.getStackInHand(hand).getItem()));
    }
    public static boolean useBlock(PlayerEntity player, World world){
        Optional<AbstractClaim> claim = ClaimList.getClaimAt(player.getWorld(), player.getSteppingPos());
        return claim.isPresent() && !claim.get().hasPermission(player.getUuid(), PermissionManager.PLACE, Node.registry(Registries.BLOCK, world.getBlockState(player.getBlockPos()).getBlock()));
    }
}
