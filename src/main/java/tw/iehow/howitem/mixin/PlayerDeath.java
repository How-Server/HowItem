package tw.iehow.howitem.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.items.others.HowTotem;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerDeath {

    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    public void beforeDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        HowTotem.use(player, ci);
    }
}