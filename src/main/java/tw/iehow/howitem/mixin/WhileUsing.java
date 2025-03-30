package tw.iehow.howitem.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.util.apply.PlayerParticle;

import java.util.Collections;
import java.util.List;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

@Mixin(Item.class)
public abstract class WhileUsing {
    private static final List<String> dyeableBlocks = List.of(
        "wool", "carpet" , "terracotta", "concrete", "stained_glass", "shulker_box", "_bed"
    );

    @Inject(method = "usageTick", at = @At("HEAD"))
    public void whileUse(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci) {
        if (isValid(stack, Items.SKULL_BANNER_PATTERN, 1337045, 1337060)) {
            if (remainingUseTicks < 199999960) { // 20 ticks
                ItemStack offHandStack = user.getStackInHand(Hand.OFF_HAND);

                if (offHandStack.getItem() instanceof DyeItem dyeItem) {
                    int color = dyeItem.getColor().getFireworkColor();
                    float cmd = dyeItem.getColor().getId() + 1337045.0f;
                    stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(Collections.singletonList(cmd), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
                    Vec3d direction = user.getRotationVector();
                    for (int i = 0; i < 8; i++) {
                        double x = user.getX() + direction.x * i;
                        double y = user.getY() + user.getEyeHeight(user.getPose()) + direction.y * i;
                        double z = user.getZ() + direction.z * i;
                        BlockPos blockPos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
                        System.out.println(world.getBlockState(blockPos).getBlock());
                        if (i > 0){
                            DustParticleEffect particleEffect = new DustParticleEffect(color, 0.6f);
                            PlayerParticle.show((ServerPlayerEntity) user, particleEffect, x, y - 0.2, z, 0, 0, 0, 1, 5);
                        }
                        if (dyeableBlocks.stream().anyMatch(name -> world.getBlockState(blockPos).getBlock().toString().contains(name))){
//                            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                            break;
                        }
                        if (!world.getBlockState(blockPos).isAir()) break;
                    }
                }
            }
        }
    }
}


