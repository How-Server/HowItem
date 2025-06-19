package tw.iehow.howitem.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.ClaimCheck;
import tw.iehow.howitem.util.check.DimensionCheck;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

@Mixin(PlayerEntity.class)
public abstract class HandHeldEffect {
    @Unique
    private boolean absorptionEffect = false;

    //Log for CD
    @Unique
    private final Map<UUID, Long> cooldown = new HashMap<>();

    @Unique
    private int tickCounter = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        tickCounter++;
        if (tickCounter % 4 != 0) return;
        Random random = new Random();
        //Get player info
        PlayerEntity player = ((PlayerEntity)(Object)this);
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        ItemStack offHand = ((PlayerEntity)(Object)this).getStackInHand(Hand.OFF_HAND);
        ItemStack hand = ((PlayerEntity)(Object)this).getStackInHand(Hand.MAIN_HAND);
        ItemStack head = ((PlayerEntity)(Object)this).getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = ((PlayerEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leg = ((PlayerEntity)(Object)this).getEquippedStack(EquipmentSlot.LEGS);
        //Timestamp for CD
        UUID playerUuid = player.getUuid();
        long lastUsedTime = cooldown.getOrDefault(playerUuid, 0L);
        long currentTime = player.getWorld().getTime();
        long interval = currentTime - lastUsedTime;

        //Detect the usage of absorption
        float absorptionAmount = player.getAbsorptionAmount();

        //HowItem:blue_omamori
        if (isValid(offHand, Items.FLOWER_BANNER_PATTERN,1337001)) {
            if (player.getSteppingBlockState().getBlock().equals(Blocks.WATER))
                PotionEffect.add(player, StatusEffects.CONDUIT_POWER, 10, 1);
            else if (!player.getSteppingBlockState().getBlock().equals(Blocks.WATER)
            && player.getWorld().getBlockState(player.getBlockPos().add(0, 1, 0)).getBlock().equals(Blocks.WATER)){
                PotionEffect.add(player, StatusEffects.WATER_BREATHING, 10, 1);
                PotionEffect.add(player, StatusEffects.NIGHT_VISION, 10, 1);
            }
        }

        //HowItem:purple_omamori
        if (isValid(offHand, Items.FLOWER_BANNER_PATTERN,1337002)) {
            PotionEffect.remove(player, StatusEffects.HUNGER);
            PotionEffect.remove(player, StatusEffects.DARKNESS);
            PotionEffect.remove(player, StatusEffects.POISON);
            PotionEffect.remove(player, StatusEffects.WITHER);
        }

        //HowItem:red_omamori
        if (isValid(offHand, Items.TOTEM_OF_UNDYING,1337001)) {
            if (interval >= 300 && !absorptionEffect) {
                PotionEffect.add(player, StatusEffects.ABSORPTION, -1, 3);
                absorptionAmount = 1.0F;
                absorptionEffect = true;
            }else {
                if (300 - interval >= 0){
                    PlayerActionBar.showCD(serverPlayer, 300 - interval);
                }
            }
        }
        if (absorptionEffect && (!isValid(offHand, Items.TOTEM_OF_UNDYING,1337001) || absorptionAmount == 0.0F)){
            PotionEffect.remove(player, StatusEffects.ABSORPTION);
            absorptionEffect = false;
            cooldown.put(playerUuid, currentTime);
        }

        //HowItem:blue_omamori
        if (isValid(offHand, Items.SKULL_BANNER_PATTERN,1337001)) {
            PotionEffect.add(player, StatusEffects.SLOWNESS, 25, 1);
            PotionEffect.add(player, StatusEffects.WEAKNESS, 25, 1);
            PotionEffect.add(player, StatusEffects.BLINDNESS, 25, 1);
        }

        //HowItem:chinese_valentines_2023/red_rose
        if (isValid(offHand, Items.FLOWER_BANNER_PATTERN,1337028)
         || isValid(offHand, Items.SKULL_BANNER_PATTERN, 1337029)) {
            if (interval >= 200) {
                PotionEffect.add(player, StatusEffects.REGENERATION, 80, 1);
                PlayerParticle.show(serverPlayer, ParticleTypes.HEART, player.getX(), player.getY() + 1.0, player.getZ(), 0.5F, 0.5F, 0.5F, 1, 5);
                cooldown.put(playerUuid, currentTime);
            }else {
                PlayerActionBar.showCD(serverPlayer, 200 - interval);
            }
        }

        //HowItem:how_water_bucket
        if (isValid(offHand, Items.SKULL_BANNER_PATTERN, 1337016)){
            if (player.getSteppingBlockState().getBlock() == Blocks.MAGMA_BLOCK){
                PotionEffect.add(player, StatusEffects.FIRE_RESISTANCE,5,1);
                PlayerParticle.show(serverPlayer, ParticleTypes.SPLASH, player.getX(), player.getY(), player.getZ(), 1.6F, 0.8F, 1.6F, 0.4F, 240);
            }
            if (player.fallDistance >= 3.0F){
                BlockPos surfacePos = player.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, player.getBlockPos());
                double distanceToSurface = player.getY() - surfacePos.getY();

                if (distanceToSurface <= 3.0) {
                    PotionEffect.add(player, StatusEffects.SLOW_FALLING, 5, 1);
                    PlayerParticle.show(serverPlayer, ParticleTypes.SPLASH, player.getX(), player.getY(), player.getZ(), 1.6F, 0.8F, 1.6F, 0.4F, 240);
                    PlayerSound.play(serverPlayer, SoundEvents.ITEM_BUCKET_FILL, 1.0F, (float) (2.0F - (player.fallDistance / 16.0F) * 0.2F));
                }
            }
        }
        //HowItem:how_hat
        if (isValid(head, Items.FLOWER_BANNER_PATTERN, 1337030)
        || isValid(head, Items.SKULL_BANNER_PATTERN, 1337037)){
            PlayerParticle.show(serverPlayer, ParticleTypes.SNOWFLAKE, player.getX(), player.getY() + 3.0, player.getZ(), 1.6F, 1.0F, 1.6F, 0.01f, 4);
            PlayerParticle.show(serverPlayer, ParticleTypes.ITEM_SNOWBALL, player.getX(), player.getY() + 0.2, player.getZ(), 0.8F, 0.5F, 0.8F, 0.01f, 2);
        }

        //HowItem:dragon_head
        if (isValid(head, Items.FLOWER_BANNER_PATTERN, 1337037)){
            for (ServerPlayerEntity player1 : serverPlayer.getServer().getPlayerManager().getPlayerList()){
                if (player1.distanceTo(player) < 24.0){
                    PotionEffect.add(player1, StatusEffects.LUCK, 10, 0);
                    PotionEffect.add(player1, StatusEffects.REGENERATION, 10, 0);
                }
            }
            if (player.getSteppingBlockState().getBlock().equals(Blocks.LAVA)){
                PotionEffect.add(player, StatusEffects.FIRE_RESISTANCE, 10, 1);
                PotionEffect.add(player, StatusEffects.STRENGTH, 10, 2);
                PlayerParticle.show(serverPlayer, ParticleTypes.FLAME, player.getX(), player.getY() + 2.0, player.getZ(), 1.6F, 1.0F, 1.6F, 0.01f, 4);
            }
            if(player.getSteppingBlockState().getBlock().equals(Blocks.WATER)){
                PotionEffect.add(player, StatusEffects.POISON,10, 4);
            }
        }
        //HowItem:student_hat
        if (isValid(head, Items.SKULL_BANNER_PATTERN, 1337020)){
            if (player.isSneaking() && !player.getAbilities().flying){
                PotionEffect.add(player, StatusEffects.JUMP_BOOST, 25, 2);
                PlayerActionBar.showText(serverPlayer, "先蹲後跳，魚躍龍門。", Formatting.GOLD);
            }
            if ((player.getSteppingBlockState().getBlock().equals(Blocks.AIR) || player.getSteppingBlockState().getBlock().equals(Blocks.LIGHT))
            && player.hasStatusEffect(StatusEffects.JUMP_BOOST) && !player.getAbilities().flying){
                PlayerParticle.show(serverPlayer, ParticleTypes.GUST, player.getX(), player.getY() + 0.8 ,player.getZ(), 1.6F, 1.0F, 1.6F, 0.001f, 2);
            }
        }
        //HowItem:clown
        if (isValid(head, Items.SKULL_BANNER_PATTERN, 1337021) && DimensionCheck.isSurvival(player)){
            if (isValid(offHand,  Items.FLOWER_BANNER_PATTERN, 1337016)){
                PotionEffect.add(player, StatusEffects.LEVITATION,10,0);
            }
            if (isValid(hand, Items.FLOWER_BANNER_PATTERN, 1337016)){
                PotionEffect.add(player, StatusEffects.SLOW_FALLING,10,0);
            }
        }
        //HowItem:pillow
        if (isValid(offHand, Items.SKULL_BANNER_PATTERN, 1337023)){
            if (player.getHungerManager().isNotFull()){
                if (interval > 30){
                    PotionEffect.add(player, StatusEffects.SATURATION,1,0);
                    cooldown.put(playerUuid, currentTime);
                }
                PotionEffect.add(player, StatusEffects.DARKNESS,60,2);
                PotionEffect.add(player, StatusEffects.SLOWNESS,30,9);
                PlayerActionBar.showText(serverPlayer, "小睡一下．．．", Formatting.AQUA);
            }else {
                PlayerActionBar.showText(serverPlayer, "睡飽就別睡ㄌ！去做事", Formatting.RED);
            }
        }
        //HowItem:heart_glasses
        if (isValid(head, Items.SKULL_BANNER_PATTERN, 1337026)){
            heartShoot(player);
        }
        //HowItem:chocolate_box
        if (isValid(offHand, Items.SKULL_BANNER_PATTERN, 1337029)){
            PlayerParticle.show(serverPlayer, ParticleTypes.CHERRY_LEAVES, player.getX(), player.getY() + 2.0, player.getZ(), 1.5F, 0.5F, 1.5F, 1, 4);
        }
        //HowItem:howbrella(opened)
        if (isValid(offHand, Items.CROSSBOW, 1337002)){
            if (!offHand.get(DataComponentTypes.CHARGED_PROJECTILES).isEmpty()){
                PotionEffect.add(player, StatusEffects.SLOW_FALLING, 10, 1);
                PotionEffect.add(player, StatusEffects.RESISTANCE, 10, 0);
            }
        }
        //HowItem:rabbit
        if (isValid(head,  Items.FLOWER_BANNER_PATTERN, 1337062)){
            PotionEffect.add(player, StatusEffects.JUMP_BOOST, 2, random.nextInt(5));
            if (random.nextInt(100) > 97
                    && (player.getSteppingBlockState().getBlock().equals(Blocks.AIR) || player.getSteppingBlockState().getBlock().equals(Blocks.LIGHT))
                    && !player.getAbilities().flying
                    && !player.hasStatusEffect(StatusEffects.INVISIBILITY)){
                PotionEffect.add(player, StatusEffects.INVISIBILITY, 200, 1);
                PlayerParticle.show(player, ParticleTypes.POOF, player.getX(), player.getY() + 0.8, player.getZ(), 1.6F, 1.0F, 1.6F, 0.001f, 50);
                PlayerSound.play(player, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.5F, 1.0F);
            }
        }
        //HowItem:sweater
        if (isValid(chest, Items.NETHERITE_CHESTPLATE, Identifier.of("minecraft:sweater_how"))){
            PotionEffect.add(player, StatusEffects.HASTE, 10, 1);
        }

        //HowItem:red_pants
        if (isValid(leg, Items.NETHERITE_LEGGINGS, Identifier.of("minecraft:how_red_pants"))){
            if (ClaimCheck.interactEntity(player)) return;
            List<LivingEntity> passiveEntities = ((ServerPlayerEntity) player).getWorld().getEntitiesByClass(LivingEntity.class,
                    player.getBoundingBox().expand(16), entity -> entity instanceof PassiveEntity
            );
            for (LivingEntity entity : passiveEntities) {
                if (entity instanceof TameableEntity tameable && ((tameable.isTamed() && tameable.getOwner() != player) || tameable.isSitting())) continue;
                if (entity.isSilent() || entity.isSleeping()) continue;
                if (entity instanceof HoglinEntity) continue;

                if (entity instanceof MobEntity mob) {
                    if (mob instanceof MerchantEntity || mob instanceof AxolotlEntity) {
                        mob.getNavigation().startMovingTo(player, 0.5);
                    }else mob.getNavigation().startMovingTo(player, 1.2);
                }
                if (random.nextInt(100) > 70) PlayerParticle.show(entity, ParticleTypes.HEART, entity.getX(), entity.getY() + 0.8, entity.getZ(), 0.5F, 0.5F, 0.5F, 0.001f, 1);
            }
        }
    }

    @Unique
    private static void heartShoot(PlayerEntity player) {
        AtomicInteger distance = new AtomicInteger(0);
        Vec3d direction = player.getRotationVector();
        for (int i = 0; i < 16; i++) {
            double x = player.getX() + direction.x * i;
            double y = player.getY() + player.getEyeHeight(player.getPose()) + direction.y * i;
            double z = player.getZ() + direction.z * i;
            BlockPos blockPos = new BlockPos((int) x, (int) y, (int) z);
            if (!player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.AIR) && !player.getWorld().getBlockState(blockPos).getBlock().equals(Blocks.LIGHT)) return;

            int finalI = i;
            player.getWorld().getEntitiesByType(
                    TypeFilter.instanceOf(LivingEntity.class),
                    new Box(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1),
                    entity -> entity != player
            ).forEach(entity -> {
                if (entity.isPlayer()) { PlayerEntity targetPlayer = (PlayerEntity) entity;
                    if (targetPlayer.isCreative() || targetPlayer.isSpectator()) return;
                    if (!targetPlayer.hasStatusEffect(StatusEffects.REGENERATION)){
                        distance.set(finalI);
                    }
                }
            });
            if (distance.get() > 0) break;
        }
        if (distance.get() > 0){
            for (int i = 0; i < distance.get() + 1; i++) {
                double x = player.getX() + direction.x * i;
                double y = player.getY() + player.getEyeHeight(player.getPose()) + direction.y * i;
                double z = player.getZ() + direction.z * i;
                if (i % 2 == 0 && i > 0) PlayerParticle.show(player, ParticleTypes.HEART, x, y, z, 0.1f, 0.1f, 0.1f, 0.1f, 5);
                player.getWorld().getEntitiesByType(
                        TypeFilter.instanceOf(LivingEntity.class),
                        new Box(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1),
                        entity -> entity != player
                ).forEach(entity -> {
                    if (entity.isPlayer()) { PlayerEntity player1 = (PlayerEntity) entity;
                        if (player1.isCreative() || player1.isSpectator()) return;
                        PotionEffect.add(player1, StatusEffects.REGENERATION, 300, 0);
                        PlayerParticle.show(player1, ParticleTypes.HEART, player1.getX(), player1.getY() + 1.0, player1.getZ(), 1.0F, 0.5F, 1.0F, 0.1F, 8);
                        PlayerActionBar.showText(player1, "您收到來自 " + player.getName().getLiteralString() + "內心的愛意！", Formatting.GOLD);
                        PlayerActionBar.showText(player, "您對 " + player1.getName().getLiteralString() + " 投射了內心的愛意！", Formatting.GOLD);
                    }
                });
            }
        }
    }
}
