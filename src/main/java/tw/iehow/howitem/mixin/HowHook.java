package tw.iehow.howitem.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class HowHook {

    @Final @Shadow
    protected ServerPlayerEntity player;

    @Unique
    private static final Set<Block> CROPS = new HashSet<>(Set.of(
            Blocks.WHEAT,
            Blocks.CARROTS,
            Blocks.POTATOES,
            Blocks.BEETROOTS,
            Blocks.NETHER_WART
    ));

    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    private void onBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!isValid(player.getMainHandStack(), Items.SKULL_BANNER_PATTERN, 1337062)) return;

        World world = player.getWorld();
        BlockState state = world.getBlockState(pos);

        if (!shouldClear(state)) return;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos target = pos.add(dx, 0, dz);
                BlockState targetState = world.getBlockState(target);
                if (shouldClear(targetState)) {
                    world.breakBlock(target, true, player);
                }
            }
        }
        cir.setReturnValue(true);
    }

    @Unique
    private boolean shouldClear(BlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.SHORT_GRASS || block == Blocks.TALL_GRASS || block == Blocks.BUSH) return true;
        if (CROPS.contains(block)) {
            return block instanceof CropBlock cropBlock && cropBlock.isMature(state);
        }

        return  false;
    }
}