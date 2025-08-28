package tw.iehow.howitem.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(VillagerEntity.class)
public abstract class VillagerTrade {
    // after claim check
    @Inject(method = "beginTradeWith", at = @At("HEAD"), cancellable = true)
    public void limitVillager(PlayerEntity customer, CallbackInfo ci){
        VillagerEntity villager = (VillagerEntity) (Object) this;
        Box box = new Box(villager.getBlockPos()).expand(64);
        List<VillagerEntity> villagersAround = customer.getWorld().getEntitiesByType(EntityType.VILLAGER, box, e -> true);

        if (villagersAround.size() > 32 && customer.getWorld().getRegistryKey().equals(World.OVERWORLD)) {
            customer.sendMessage(Text.literal("無法與村民交易，周圍 64 格內超過 32 隻村民，請將村民分散，避免同時載入過多村民").formatted(Formatting.RED), true);
            ci.cancel();
        }
    }
    // after claim check
    @Inject(method = "beginTradeWith", at = @At("HEAD"))
    public void removeHeroEffect(PlayerEntity customer, CallbackInfo ci){
        VillagerEntity villager = (VillagerEntity) (Object) this;
        if(villager.getCommandTags().contains("official")){
            customer.removeStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
        }
    }
}