package tw.iehow.howitem.util.apply;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerParticle {
    public static void show(ServerPlayerEntity player, ParticleEffect particleType, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        if (!player.isSpectator()){
            ParticleS2CPacket packet = new ParticleS2CPacket(particleType, false, x, y, z, offsetX, offsetY, offsetZ, speed, count);
            player.getServerWorld().getPlayers().forEach((serverPlayer) -> sendPacket(serverPlayer, player, packet));
        }
    }

    private static void sendPacket(ServerPlayerEntity player, Entity target, ParticleS2CPacket packet){
        if (player.shouldRender(player.distanceTo(target))){
            player.networkHandler.sendPacket(packet);
        }
    }
}
