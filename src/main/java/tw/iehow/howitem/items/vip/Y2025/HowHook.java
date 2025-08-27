package tw.iehow.howitem.items.vip.Y2025;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tw.iehow.howitem.util.check.SlotCheck;

import java.util.HashSet;
import java.util.Set;

public class HowHook {
    private static final Set<Block> CROPS = new HashSet<>(Set.of(
            Blocks.WHEAT,
            Blocks.CARROTS,
            Blocks.POTATOES,
            Blocks.BEETROOTS,
            Blocks.NETHER_WART
    ));

    public static void use(PlayerEntity player, BlockPos pos) {
        if (!SlotCheck.isValid(player.getMainHandStack(), Items.SKULL_BANNER_PATTERN, 1337062)) return;

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

    }

    private static boolean shouldClear(BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.SHORT_GRASS || block == Blocks.TALL_GRASS || block == Blocks.BUSH) return true;
        if (CROPS.contains(block)) {
            return block instanceof CropBlock cropBlock && cropBlock.isMature(state);
        }
        return  false;
    }
}
