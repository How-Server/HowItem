package tw.iehow.howitem;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.ClaimCheck;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static tw.iehow.howitem.items.vip2407.how_popcicle;
import static tw.iehow.howitem.items.vip2407.how_popcicle_stick;
import static tw.iehow.howitem.util.check.SlotCheck.isValid;

public class UseItem {
    private static final Map<UUID, Long> cooldown = new HashMap<>();

    public static void safeHand(PlayerEntity player, Hand hand) {
        ItemCooldownManager cooldownManager = player.getItemCooldownManager();
        if (hand == Hand.MAIN_HAND) {
            ItemStack stack = player.getStackInHand(hand);
            //HowItem:popcicle_stick
            if (isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337013)) {
                PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), 1.0f, 0.5f);
                player.setStackInHand(hand, how_popcicle());
                player.getAttributeInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1.0d);
                player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1d);
            }
            //HowItem:popcicle
            if (isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337014)
                    || isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337015)
                    || isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337016)
                    || isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337017)) {
                PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), 1.0f, 0.94f);
                PlayerSound.play(player, SoundEvents.ENTITY_GENERIC_EAT, 1.0f, 1.0f);
                player.setStackInHand(hand, how_popcicle_stick());
                player.getAttributeInstance(EntityAttributes.GENERIC_SCALE).setBaseValue(1.5d);
                player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.05d);
            }
            //HowItem:goat
            if(isValid(stack, Items.GOAT_HORN, 1337001) && !cooldownManager.isCoolingDown(stack.getItem())) {
                PlayerSound.play(player, SoundEvent.of(Identifier.of("minecraft:item.goat_horn.sound.mediv")), 1.0f, 1.0f);
                for (PlayerEntity player1 : player.getWorld().getPlayers()) {
                    if (player.distanceTo(player1) < 16) {
                        PotionEffect.add(player1, StatusEffects.STRENGTH, 200, 1);
                        PotionEffect.add(player1, StatusEffects.ABSORPTION, 200, 1);
                    }
                }
            }
        }
        if (hand == Hand.OFF_HAND) {
            ItemStack stack = player.getStackInHand(hand);
            if(isValid(stack, Items.GOAT_HORN, 1337001) && !cooldownManager.isCoolingDown(stack.getItem())) {
                PlayerSound.play(player, SoundEvent.of(Identifier.of("minecraft:item.goat_horn.sound.fun")), 1.0f, 1.0f);
                for (PlayerEntity player1 : player.getWorld().getPlayers()) {
                    if (player.distanceTo(player1) < 16) {
                        PotionEffect.add(player1, StatusEffects.SPEED, 600, 1);
                        PotionEffect.add(player1, StatusEffects.JUMP_BOOST, 600, 1);
                    }
                }
            }
        }
    }

    public static void unsafeMainHand(PlayerEntity player, Hand hand) {
        UUID playerUuid = player.getUuid();
        long lastUsedTime = cooldown.getOrDefault(playerUuid, 0L);
        long currentTime = player.getWorld().getTime();
        long interval = currentTime - lastUsedTime;

        if (hand == Hand.MAIN_HAND) {
            ItemStack stack = player.getStackInHand(hand);
            //HowItem:pencil
            if (isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337018)) {
                if (interval < 40){
                    penShoot(player, 3.0F, 8, false, false, 2);
                    PlayerActionBar.showCD(player, 40 - interval);
                    PlayerSound.onlyPlay(player, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, 1.0f, 1.0f);
                } else {
                    penShoot(player, 3.0F, 8, false, false, 5);
                    cooldown.put(playerUuid, currentTime);
                    PlayerSound.onlyPlay(player, SoundEvents.BLOCK_CHERRY_SAPLING_STEP, 1.0f, 1.0f);
                    PotionEffect.add(player, StatusEffects.SLOWNESS, 40, 1);
                    PotionEffect.add(player, StatusEffects.WEAKNESS, 40, 1);
                }
            }
            //HowItem:fountain
            if (isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337019)) {
                if (interval < 40){
                    penShoot(player, 6.0F, 16, false, false, 2);
                    PlayerActionBar.showCD(player, 40 - interval);
                    PlayerSound.onlyPlay(player, SoundEvents.BLOCK_HANGING_SIGN_WAXED_INTERACT_FAIL, 1.0f, 1.0f);
                } else {
                    penShoot(player, 6.0F, 16, true, false, 20);
                    cooldown.put(playerUuid, currentTime);
                    PlayerSound.onlyPlay(player, SoundEvents.ENTITY_SQUID_SQUIRT, 1.0f, 1.0f);
                    PotionEffect.add(player, StatusEffects.SLOWNESS, 40, 1);
                    PotionEffect.add(player, StatusEffects.WEAKNESS, 40, 1);
                }
            }
            //HowItem:brush
            if (isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337021)) {
                if (interval < 40){
                    penShoot(player, 6.0F, 16, false, false, 2);
                    PlayerActionBar.showCD(player, 40 - interval);
                    PlayerSound.onlyPlay(player, SoundEvents.BLOCK_HANGING_SIGN_WAXED_INTERACT_FAIL, 1.0f, 1.0f);
                } else {
                    penShoot(player, 6.0F, 16, true, false, 20);
                    cooldown.put(playerUuid, currentTime);
                    PlayerSound.onlyPlay(player, SoundEvents.ENTITY_GLOW_SQUID_SQUIRT, 1.0f, 1.0f);
                    PotionEffect.add(player, StatusEffects.SLOWNESS, 40, 1);
                    PotionEffect.add(player, StatusEffects.WEAKNESS, 40, 1);
                }
            }
            //HowItem:gold_pen
            if (isValid(stack, Items.MOJANG_BANNER_PATTERN, 1337020)) {
                if (interval < 40){
                    penShoot(player, 6.0F, 16, false, true, 2);
                    PlayerActionBar.showCD(player, 40 - interval);
                    PlayerSound.onlyPlay(player, SoundEvents.BLOCK_HANGING_SIGN_WAXED_INTERACT_FAIL, 1.0f, 1.0f);
                } else {
                    penShoot(player, 6.0F, 16, true, true, 20);
                    cooldown.put(playerUuid, currentTime);
                    PlayerSound.onlyPlay(player, SoundEvents.ENTITY_SQUID_SQUIRT, 1.0f, 1.0f);
                    PotionEffect.add(player, StatusEffects.SLOWNESS, 40, 1);
                    PotionEffect.add(player, StatusEffects.WEAKNESS, 40, 1);
                }
            }
        }
    }

    private static void penShoot(PlayerEntity player, float damage, int range, boolean advanced, boolean gold, int particleCount) {
        Random random = new Random();
        Vec3d direction = player.getRotationVector();
        for (int i = 0; i < range; i++) {
            double x = player.getX() + direction.x * i;
            double y = player.getY() + player.getEyeHeight(player.getPose()) + direction.y * i;
            double z = player.getZ() + direction.z * i;
            BlockPos blockPos = new BlockPos((int) x, (int) y, (int) z);
            if (!player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.AIR) && !player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.LIGHT)) return;
            if (gold && i > 2 && i % 2 == 0)
                PlayerParticle.show(player, ParticleTypes.GLOW_SQUID_INK, x, y, z, 0.6f, 0.1f, 0.6f, 0.1f, particleCount);
            else if (!gold && i > 2 && i % 2 == 0)
                PlayerParticle.show(player, ParticleTypes.SQUID_INK, x, y, z, 0.6f, 0.1f, 0.6f, 0.1f, particleCount);
            player.getWorld().getEntitiesByType(
                    TypeFilter.instanceOf(LivingEntity.class),
                    new Box(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1),
                    entity -> entity != player
            ).forEach(entity -> {
                if (entity.isPlayer()) { PlayerEntity player1 = (PlayerEntity) entity;
                    if (player1.isCreative() || player1.isSpectator() || ClaimCheck.canPvP(player, player.getWorld())) return;
                }
                entity.damage(player.getDamageSources().magic(), damage);
                PlayerSound.onlyPlay(player, SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), 0.5f, 0.94f);
                if (advanced) {
                    PotionEffect.add(entity, StatusEffects.GLOWING, 40, 0);
                    if (random.nextInt(100) < 10) {
                        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, player.getWorld());
                        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
                        player.getWorld().spawnEntity(lightning);
                    }
                }
            });
        }
    }
}
