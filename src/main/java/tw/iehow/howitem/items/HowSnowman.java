package tw.iehow.howitem.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.check.SlotCheck;


public class HowSnowman extends BaseHowItem {
    public HowSnowman() {
        super(Items.SKULL_BANNER_PATTERN, 1337043);
    }

    public void unsafeUse(PlayerEntity player, Hand hand) {
        if (!SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337039)
        && !SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337040)
        && !SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337041)
        && !SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337042)){
            return;
        }

        SnowballEntity snowball = new SnowballEntity(EntityType.SNOWBALL, player.getWorld());
        snowball.setPosition(
                player.getX() - Math.cos(Math.toRadians(player.getYaw() - 90)) * 0.5,
                player.getEyeY() - 0.1,
                player.getZ() - Math.sin(Math.toRadians(player.getYaw() - 90)) * 0.5
        );
        snowball.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.0F, 1.0F);
        snowball.setOwner(player);
        player.getWorld().spawnEntity(snowball);

        PlayerSound.play(player, SoundEvents.ENTITY_SNOWBALL_THROW, 1.0F, 1.0F);
        PlayerSound.play(player, SoundEvents.BLOCK_BELL_USE, 1.0F, 1.0F);
    }
}