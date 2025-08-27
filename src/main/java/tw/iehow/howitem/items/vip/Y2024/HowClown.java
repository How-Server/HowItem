package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import tw.iehow.howitem.enums.TriggerType;
import tw.iehow.howitem.items.BaseHowItem;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.DimensionCheck;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowClown extends BaseHowItem {

    public HowClown() {
        super(Items.SKULL_BANNER_PATTERN, 1337021);
        this.triggerTypes.remove(TriggerType.ATTACK_HAND);
        this.triggerTypes.add(TriggerType.OFF_HAND);
        this.triggerTypes.add(TriggerType.HEAD);
    }

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        PlayerSound.play((ServerPlayerEntity) player, SoundEvents.ENTITY_WITCH_CELEBRATE, 1.0F, 1.0F);
        PlayerActionBar.showText((ServerPlayerEntity) player, "小丑竟是我？", Formatting.RED);
        if (entity instanceof ServerPlayerEntity) {
            PlayerSound.play((ServerPlayerEntity) entity, SoundEvents.ENTITY_WITCH_CELEBRATE, 1.0F, 1.0F);
        }
    }

    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getEquippedStack(EquipmentSlot.HEAD), Items.SKULL_BANNER_PATTERN, 1337021) && DimensionCheck.isVanillaWorld(player)){
            if (SlotCheck.isValid(player.getOffHandStack(),  Items.FLOWER_BANNER_PATTERN, 1337016)){
                PotionEffect.add(player, StatusEffects.LEVITATION,10,0);
            }
            if (SlotCheck.isValid(player.getMainHandStack(), Items.FLOWER_BANNER_PATTERN, 1337016)){
                PotionEffect.add(player, StatusEffects.SLOW_FALLING,10,0);
            }
        }
    }
}
