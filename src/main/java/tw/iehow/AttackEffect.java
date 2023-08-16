package tw.iehow;

import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.ClaimList;
import me.drex.itsours.claim.permission.PermissionManager;
import me.drex.itsours.claim.permission.node.Node;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static tw.iehow.PlayerParticle.showParticle;
import static tw.iehow.SlotCheck.isValid;

public class AttackEffect implements ModInitializer {
	//Log for CD
	private final Map<UUID, Long> cooldown = new HashMap<>();

	@Override
	public void onInitialize() {
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ServerPlayerEntity ServerPlayer = (ServerPlayerEntity) player;
			//Claim Permission
			Optional<AbstractClaim> claim = ClaimList.INSTANCE.getClaimAt((ServerWorld) player.getWorld(), player.getSteppingPos());
			if (!(entity instanceof PlayerEntity) && claim.isPresent() && !claim.get().hasPermission(player.getUuid(), PermissionManager.DAMAGE_ENTITY, Node.dummy(Registries.ENTITY_TYPE, entity.getType()))) {
				return ActionResult.FAIL;
			}
			if ((entity instanceof PlayerEntity) && claim.isPresent() && !claim.get().hasPermission(player.getUuid(), PermissionManager.PVP)){
				return ActionResult.FAIL;
			}

			//Timestamp for CD
			UUID playerUuid = player.getUuid();
			long lastUsedTime = cooldown.getOrDefault(playerUuid, 0L);
			long currentTime = world.getTime();
			int SKcooldownTime = 120;
			int KBcooldownTime = 300;

			//Get MainHand_Item
			ItemStack mainHand = player.getStackInHand(Hand.MAIN_HAND);

			//HowItem:Sakura_Katana
			if (currentTime - lastUsedTime >= SKcooldownTime) {
				if (isValid(mainHand, "minecraft:netherite_sword", 1337003)) {
					player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 2, false, false));
					showParticle(ServerPlayer, ParticleTypes.HEART, player.getX(), player.getY() + 1.0, player.getZ(), 0.5F, 0.5F, 0.5F, 1, 5);
					cooldown.put(playerUuid, currentTime);
				}
			}

			//HowItem:Black_Katana
			if (isValid(mainHand, "minecraft:netherite_sword", 1337004) && !(entity instanceof EnderDragonPart)) {
				LivingEntity livingEntity = (LivingEntity) entity;
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1, false, false));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 300, 1, false, false));
			}

			//HowItem:KeyBoard
			if (currentTime - lastUsedTime >= KBcooldownTime && !(entity instanceof EnderDragonPart)) {
				if (isValid(mainHand, "minecraft:netherite_sword", 1337014) || isValid(mainHand, "minecraft:netherite_sword", 1337016) || isValid(mainHand, "minecraft:netherite_sword", 1337017) ) {
					LivingEntity livingEntity = (LivingEntity) entity;
					livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 150, 1, false, false));
					livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1, false, false));
					cooldown.put(playerUuid, currentTime);
				}
			}
			return ActionResult.PASS;
		});

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if(entity.getType() == EntityType.VILLAGER && entity.getCommandTags().contains("official")){
					player.removeStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
			}
			return ActionResult.PASS;
		});
	}

}