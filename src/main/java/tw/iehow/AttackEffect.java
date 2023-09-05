package tw.iehow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import tw.iehow.util.PotionEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static tw.iehow.util.PlayerParticle.showParticle;
import static tw.iehow.util.PlayerTitle.showTitle;
import static tw.iehow.util.SlotCheck.isValid;

public class AttackEffect{
	//Log for CD
	private static final Map<UUID, Long> cooldown = new HashMap<>();
	public static void mainHand(PlayerEntity player, World world, Entity entity){
		ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
		ItemStack mainHand = player.getStackInHand(Hand.MAIN_HAND);
		//Timestamp for CD
		UUID playerUuid = player.getUuid();
		long lastUsedTime = cooldown.getOrDefault(player.getUuid(), 0L);
		long currentTime = world.getTime();
		long interval = currentTime - lastUsedTime;

		//HowItem:Sakura_Katana
		if (isValid(mainHand, "minecraft:netherite_sword", 1337003)) {
			if (interval >= 120) {
				PotionEffect.add(player, StatusEffects.REGENERATION, 60, 2);
				showParticle(serverPlayer, ParticleTypes.HEART, player.getX(), player.getY() + 1.0, player.getZ(), 0.5F, 0.5F, 0.5F, 1, 5);
				cooldown.put(playerUuid, currentTime);
			} else {
				showTitle(serverPlayer, 120 - interval);
			}
		}

		//HowItem:Black_Katana
		if (isValid(mainHand, "minecraft:netherite_sword", 1337004) && !(entity instanceof EnderDragonPart)) {
			LivingEntity livingEntity = (LivingEntity) entity;
			PotionEffect.add(livingEntity,StatusEffects.WITHER, 100, 1);
			PotionEffect.add(livingEntity,StatusEffects.DARKNESS, 300, 1);
		}

		//HowItem:KeyBoard
		if (isValid(mainHand, "minecraft:netherite_sword", 1337014) || isValid(mainHand, "minecraft:netherite_sword", 1337016) || isValid(mainHand, "minecraft:netherite_sword", 1337017)) {
			if (interval >= 300 && !(entity instanceof EnderDragonPart)) {
				LivingEntity livingEntity = (LivingEntity) entity;
				PotionEffect.add(livingEntity, StatusEffects.NAUSEA, 150, 1);
				PotionEffect.add(livingEntity, StatusEffects.SLOWNESS, 100, 1);
				cooldown.put(playerUuid, currentTime);
			} else {
				showTitle(serverPlayer, 300 - interval);
			}
		}

		//HowItem:how_wine
		if (isValid(mainHand, "minecraft:skull_banner_pattern", 1337015)) {
			if (interval >= 120 && !(entity instanceof EnderDragonPart)) {
				LivingEntity livingEntity = (LivingEntity) entity;
				PotionEffect.add(livingEntity, StatusEffects.NAUSEA, 120, 1);
				PotionEffect.add(livingEntity, StatusEffects.SLOW_FALLING, 120, 1);
				PotionEffect.add(livingEntity, StatusEffects.LEVITATION, 60, 1);
				showParticle(serverPlayer, ParticleTypes.SOUL, player.getX(), player.getY() + 0.2, player.getZ(), 0.4F, 0.5F, 0.4F, 0.2F, 30);
				cooldown.put(playerUuid, currentTime);
			} else {
				showTitle(serverPlayer, 120 - interval);
			}
		}
	}

}