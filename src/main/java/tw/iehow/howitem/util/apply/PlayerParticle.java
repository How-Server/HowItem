package tw.iehow.howitem.util.apply;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class PlayerParticle {
    public static void show(ServerPlayerEntity player, ParticleEffect particleType, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        if (!player.isSpectator()){
            ParticleS2CPacket packet = new ParticleS2CPacket(particleType, false, false,x, y, z, offsetX, offsetY, offsetZ, speed, count);
            player.getServerWorld().getPlayers().forEach((serverPlayer) -> sendPacket(serverPlayer, player, packet));
        }
    }

    public static void show(PlayerEntity player, ParticleEffect particleType, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        if (!player.isSpectator()){
            ParticleS2CPacket packet = new ParticleS2CPacket(particleType, false, false, x, y, z, offsetX, offsetY, offsetZ, speed, count);
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            serverPlayer.getServerWorld().getPlayers().forEach((serverPlayer1) -> sendPacket(serverPlayer1, player, packet));
        }
    }

    public static void show(Entity entity, ParticleEffect particleType, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        ParticleS2CPacket packet = new ParticleS2CPacket(particleType, false, false, x, y, z, offsetX, offsetY, offsetZ, speed, count);
        Objects.requireNonNull(entity.getServer()).getPlayerManager().getPlayerList().forEach((player) -> sendPacket(player, entity, packet));
    }

    private static void sendPacket(ServerPlayerEntity player, Entity target, ParticleS2CPacket packet){
        if (player.shouldRender(player.distanceTo(target))){
            player.networkHandler.sendPacket(packet);
        }
    }
}
