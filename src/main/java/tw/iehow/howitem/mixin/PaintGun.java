package tw.iehow.howitem.mixin;

import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.flags.Flags;
import me.drex.itsours.claim.flags.node.Node;
import me.drex.itsours.claim.list.ClaimList;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tw.iehow.howitem.util.apply.PlayerParticle;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static tw.iehow.howitem.util.check.SlotCheck.isValid;

@Mixin(Item.class)
public abstract class PaintGun {
    @Unique
    private static final List<String> dyeableBlocks = List.of(
        "wool", "carpet" , "terracotta", "concrete", "glass","stained_glass", "shulker_box"
    );

    @Unique
    private static final List<String> dyeColors = List.of(
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
            "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    );

    @Unique
    private static boolean isDyeColor(String color) {
        return dyeColors.contains(color);
    }

    @Inject(method = "usageTick", at = @At("HEAD"))
    public void whileUse(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci) {
        if (isValid(stack, Items.SKULL_BANNER_PATTERN, 1337045, 1337060)) {
            if (remainingUseTicks < 199999960) { // 20 ticks
                ItemStack offHandStack = user.getStackInHand(Hand.OFF_HAND);

                if (offHandStack.getItem() instanceof DyeItem dyeItem) {
                    PotionEffect.add(user, StatusEffects.SPEED, 1, 5);
                    int color = dyeItem.getColor().getFireworkColor();
                    float cmd = dyeItem.getColor().getIndex() + 1337045.0f;
                    stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(Collections.singletonList(cmd), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
                    Vec3d direction = user.getRotationVector();
                    for (int i = 0; i < 8; i++) {
                        double x = user.getX() + direction.x * i;
                        double y = user.getY() + user.getEyeHeight(user.getPose()) + direction.y * i;
                        double z = user.getZ() + direction.z * i;
                        BlockPos blockPos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
                        BlockState blockState = world.getBlockState(blockPos);

                        if (i > 0){
                            DustParticleEffect particleEffect = new DustParticleEffect(color, 0.6f);
                            PlayerParticle.show((ServerPlayerEntity) user, particleEffect, x, y - 0.2, z, 0, 0, 0, 1, 5);
                            PlayerSound.play((ServerPlayerEntity) user, SoundEvents.BLOCK_SCULK_STEP, 1.0f, 1.0f);
                        }

                        if (dyeableBlocks.stream().anyMatch(name -> blockState.getBlock().toString().contains(name))) {
                            Optional<AbstractClaim> claim = ClaimList.getClaimAt(world, blockPos);
                            if (claim.isPresent() && !claim.get().checkAction(user.getUuid(), Flags.PLACE, Node.registry(Registries.BLOCK, blockState.getBlock()))
                            && !claim.get().getFullName().equals("spawn.play")) break;
                            String blockName = blockState.getBlock().getTranslationKey();
                            String[] nameParts = blockName.split("\\.");

                            if (nameParts.length > 0) {
                                String lastPart = nameParts[nameParts.length - 1];

                                String[] splitName = lastPart.split("_");
                                String newBlockName;
                                if (blockState.getBlock() instanceof PaneBlock) break;
                                if (blockState.getBlock() instanceof ShulkerBoxBlock) {
                                    BlockEntity blockEntity = world.getBlockEntity(blockPos);
                                    if (blockEntity instanceof ShulkerBoxBlockEntity shulkerBox) {
                                        if (!shulkerBox.isEmpty()) break;
                                    }
                                }

                                if (lastPart.equals("glass")) {
                                    newBlockName = dyeItem.getColor().asString() + "_stained_glass";
                                } else {
                                    if (splitName.length > 1 && isDyeColor(splitName[0])) {
                                        newBlockName = dyeItem.getColor().asString() + "_" + String.join("_", Arrays.copyOfRange(splitName, 1, splitName.length));
                                    } else if (splitName.length > 2 && isDyeColor(splitName[0] + "_" +splitName[1])) {
                                        newBlockName = dyeItem.getColor().asString() + "_" + String.join("_", Arrays.copyOfRange(splitName, 2, splitName.length));
                                    } else {
                                        newBlockName = dyeItem.getColor().asString() + "_" + lastPart;
                                    }
                                }

                                Block newBlock = Registries.BLOCK.get(Identifier.of("minecraft", newBlockName));
                                if (newBlock != Blocks.AIR) world.setBlockState(blockPos, newBlock.getDefaultState());
                            }
                        }
                        if (!blockState.isAir()) break;
                    }
                }
            }
        }
    }
}
