package tw.iehow.howitem.items.vip.Y2024;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class HowPillow {
    public static void apply(PlayerEntity player) {
        if (SlotCheck.isValid(player.getOffHandStack(), Items.SKULL_BANNER_PATTERN, 1337023)){
            if (player.getHungerManager().isNotFull()){
                long cd = CooldownManager.get(player.getUuid(), CooldownType.TOY);
                if (cd == 0){
                    PotionEffect.add(player, StatusEffects.SATURATION,1,0);
                    CooldownManager.set(player.getUuid(), CooldownType.TOY, 30);
                }
                PotionEffect.add(player, StatusEffects.DARKNESS,60,2);
                PotionEffect.add(player, StatusEffects.SLOWNESS,30,9);
                PlayerActionBar.showText(player, "小睡一下．．．", Formatting.AQUA);
            }else {
                PlayerActionBar.showText(player, "睡飽就別睡ㄌ！去做事", Formatting.RED);
            }
        }
    }
}
