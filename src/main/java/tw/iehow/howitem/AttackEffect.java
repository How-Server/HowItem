package tw.iehow.howitem;

import com.gmail.sneakdevs.diamondeconomy.DiamondUtils;
import com.gmail.sneakdevs.diamondeconomy.sql.DatabaseManager;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

public class AttackEffect{
	//Log for CD
	private static final Map<UUID, Long> cooldown = new HashMap<>();
	public static void mainHand(PlayerEntity player, World world, Entity entity){
		ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
		ItemStack mainHand = player.getStackInHand(Hand.MAIN_HAND);
		ItemStack offHand = player.getStackInHand(Hand.OFF_HAND);
		ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
		//Timestamp for CD
		UUID playerUuid = player.getUuid();
		long lastUsedTime = cooldown.getOrDefault(playerUuid, 0L);
		long currentTime = world.getTime();
		long interval = currentTime - lastUsedTime;

		//HowBank
		DatabaseManager dm = DiamondUtils.getDatabaseManager();
		//EnderDragon Pass
		if (!(entity instanceof EnderDragonPart)){
			//HowItem:Sakura_Katana
			if (isValid(mainHand, "minecraft:netherite_sword", 1337003)) {
				if (interval >= 120) {
					PotionEffect.add(player, StatusEffects.REGENERATION, 60, 2);
					PotionEffect.add((LivingEntity) entity, StatusEffects.SLOWNESS, 20, 2);
					PlayerParticle.show(serverPlayer, ParticleTypes.HEART, player.getX(), player.getY() + 1.0, player.getZ(), 0.5F, 0.5F, 0.5F, 1, 5);
					cooldown.put(playerUuid, currentTime);
				} else {
					PlayerActionBar.showCD(serverPlayer, 120 - interval);
				}
			}
			//HowItem:Blue_Katana
			if (isValid(mainHand, "minecraft:netherite_sword", 1337008)) {
				if (!player.getSteppingBlockState().getBlock().equals(Blocks.WATER)) return;
				if (interval >= 120) {
					PotionEffect.add(player, StatusEffects.ABSORPTION, 120, 1);
					PotionEffect.add(player, StatusEffects.DOLPHINS_GRACE, 120, 2);
					PotionEffect.add((LivingEntity) entity, StatusEffects.SLOWNESS, 20, 2);
					player.setAir(player.getMaxAir());
					PlayerParticle.show(serverPlayer, ParticleTypes.BUBBLE, player.getX(), player.getY() + 1.0, player.getZ(), 1.5F, 1.5F, 1.5F, 0.1F, 120);
					PlayerSound.onlyPlay(serverPlayer, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 1.0F, 1.0F);
					cooldown.put(playerUuid, currentTime);
				} else {
					PlayerActionBar.showCD(serverPlayer, 120 - interval);
				}
			}
			//HowItem:Black_Katana
			if (isValid(mainHand, "minecraft:netherite_sword", 1337004)) {
				LivingEntity livingEntity = (LivingEntity) entity;
				PotionEffect.add(livingEntity,StatusEffects.WITHER, 100, 1);
				PotionEffect.add(livingEntity,StatusEffects.DARKNESS, 300, 1);
			}

			//HowItem:KeyBoard
			if (isValid(mainHand, "minecraft:netherite_sword", 1337014) || isValid(mainHand, "minecraft:netherite_sword", 1337016) || isValid(mainHand, "minecraft:netherite_sword", 1337017)) {
				if (interval >= 300) {
					LivingEntity livingEntity = (LivingEntity) entity;
					PotionEffect.add(livingEntity, StatusEffects.NAUSEA, 150, 1);
					PotionEffect.add(livingEntity, StatusEffects.SLOWNESS, 100, 1);
					cooldown.put(playerUuid, currentTime);
				} else {
					PlayerActionBar.showCD(serverPlayer, 300 - interval);
				}
			}

			//HowItem:how_wine
			if (isValid(mainHand, "minecraft:skull_banner_pattern", 1337015)) {
				if (interval >= 120) {
					LivingEntity livingEntity = (LivingEntity) entity;
					PotionEffect.add(livingEntity, StatusEffects.NAUSEA, 120, 1);
					PotionEffect.add(livingEntity, StatusEffects.SLOW_FALLING, 120, 1);
					PotionEffect.add(livingEntity, StatusEffects.LEVITATION, 60, 1);
					PlayerParticle.show(serverPlayer, ParticleTypes.SOUL, player.getX(), player.getY() + 0.2, player.getZ(), 0.4F, 0.5F, 0.4F, 0.2F, 30);
					cooldown.put(playerUuid, currentTime);
				} else {
					PlayerActionBar.showCD(serverPlayer, 120 - interval);
				}
			}
			//HowItem:how_drum
			if (isValid(mainHand, "minecraft:skull_banner_pattern", 1337017)) {
				PlayerSound.play(serverPlayer, SoundEvents.ENTITY_WARDEN_HEARTBEAT, 1.0F, 1.0F);
				if (interval >= 120) {
					if (entity.isPlayer()) PlayerActionBar.showText((ServerPlayerEntity) entity, "哈哈！單身狗是你", Formatting.AQUA);
					world.createExplosion(null, null, null, entity.getX(), entity.getY(), entity.getZ(), 1.0F, false, World.ExplosionSourceType.NONE);
					PlayerSound.play(serverPlayer, SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 1.0F, 1.0F);
					PlayerParticle.show(serverPlayer, ParticleTypes.SONIC_BOOM, player.getX(), player.getY() + 0.2, player.getZ(), 1.6F, 0.8F, 1.6F, 0.1F, 10);
					cooldown.put(playerUuid, currentTime);
				} else {
					PlayerActionBar.showCD(serverPlayer, 120 - interval);
				}
			}
			//HowItem:clown
			if (isValid(head, "minecraft:skull_banner_pattern", 1337021) && entity instanceof PlayerEntity) {
				PlayerSound.play((ServerPlayerEntity) entity, SoundEvents.ENTITY_WITCH_CELEBRATE, 1.0F ,1.0F);
				PlayerActionBar.showText((ServerPlayerEntity) entity, "小丑竟是我？", Formatting.RED);
				PlayerSound.play((ServerPlayerEntity) player, SoundEvents.ENTITY_WITCH_CELEBRATE, 1.0F ,1.0F);
			}
			//HowItem:how_card
			if (isValid(offHand, "minecraft:skull_banner_pattern", 1337022) && entity instanceof PlayerEntity) {
				int money = dm.getBalanceFromUUID(player.getUuid().toString());
				if (money > 0) {
					dm.changeBalance(player.getUuid().toString(), -1);
					PlayerActionBar.showText((ServerPlayerEntity) player, "您賞了 " + entity.getDisplayName().getString() + " 1 塊", Formatting.YELLOW);
					PotionEffect.add(player, StatusEffects.GLOWING, 200, 1);
					dm.changeBalance(entity.getUuid().toString(), 1);
					PlayerActionBar.showText((ServerPlayerEntity) entity, player.getDisplayName().getString() + " 有錢人賞了你 1 塊", Formatting.GREEN);
					PlayerSound.play((ServerPlayerEntity) player, SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
				}else {
					PlayerActionBar.showText(player, "菜就多練，沒錢就別搞", Formatting.RED);
					PlayerSound.play((ServerPlayerEntity) player, SoundEvents.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
				}
			}
		}

	}

}