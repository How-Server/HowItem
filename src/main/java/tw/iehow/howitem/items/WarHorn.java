package tw.iehow.howitem.items;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;

public class WarHorn extends BaseHowItem {

    public WarHorn() {
        super(Items.GOAT_HORN, 1337001);
    }

    public void safeUse(PlayerEntity player, Hand hand) {
        ItemCooldownManager cooldownManager = player.getItemCooldownManager();
        if (cooldownManager.isCoolingDown(player.getStackInHand(hand))) {
            return;
        }

        if (hand.equals(Hand.MAIN_HAND)) {
            attackHorn(player);
        } else {
            movementHorn(player);
        }
    }

    private void attackHorn(PlayerEntity player) {
        ItemCooldownManager cooldownManager = player.getItemCooldownManager();
        cooldownManager.set(player.getStackInHand(Hand.MAIN_HAND), 200);
        PlayerSound.play(player, SoundEvent.of(Identifier.of("minecraft:item.goat_horn.sound.mediv")), 1.0f, 1.0f);
        for (PlayerEntity player1 : player.getWorld().getPlayers()) {
            if (player.distanceTo(player1) < 16) {
                PotionEffect.add(player1, StatusEffects.STRENGTH, 200, 1);
                PotionEffect.add(player1, StatusEffects.ABSORPTION, 200, 1);
            }
        }
    }

    private void movementHorn(PlayerEntity player) {
        ItemCooldownManager cooldownManager = player.getItemCooldownManager();
        cooldownManager.set(player.getStackInHand(Hand.OFF_HAND), 600);
        PlayerSound.play(player, SoundEvent.of(Identifier.of("minecraft:item.goat_horn.sound.fun")), 1.0f, 1.0f);
        for (PlayerEntity player1 : player.getWorld().getPlayers()) {
            if (player.distanceTo(player1) < 16) {
                PotionEffect.add(player1, StatusEffects.SPEED, 600, 1);
                PotionEffect.add(player1, StatusEffects.JUMP_BOOST, 600, 1);
            }
        }
    }
}
