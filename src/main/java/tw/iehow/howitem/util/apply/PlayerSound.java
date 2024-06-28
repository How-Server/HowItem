package tw.iehow.howitem.util.apply;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class PlayerSound {
    public static void play(ServerPlayerEntity player, SoundEvent soundEvent, float volume, float pitch) {
        PlaySoundS2CPacket packet = new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(soundEvent), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), volume, pitch, player.getRandom().nextLong());
        player.getServerWorld().getPlayers().forEach((serverPlayer) -> sendPacket(serverPlayer, player, packet));
    }

    private static void sendPacket(ServerPlayerEntity player, Entity target, PlaySoundS2CPacket packet){
        if(player.distanceTo(target) <= 16.0F){
            player.networkHandler.sendPacket(packet);
        }
    }
}
