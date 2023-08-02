package tw.iehow;

import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerParticle {
    public static void showParticle(ServerPlayerEntity player, ParticleEffect particleType, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        ParticleS2CPacket packet = new ParticleS2CPacket(particleType, false, x, y, z, offsetX, offsetY, offsetZ, speed, count);
        player.networkHandler.sendPacket(packet);
    }
}
