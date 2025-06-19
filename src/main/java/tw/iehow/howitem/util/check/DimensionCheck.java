package tw.iehow.howitem.util.check;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class DimensionCheck {
    public static boolean isSurvival(PlayerEntity player){
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        RegistryKey<World> world = serverPlayer.getWorld().getRegistryKey();
        return world.equals(World.OVERWORLD) || world.equals(World.NETHER) || world.equals(World.END);
    }
}
