package tw.iehow.howitem.items.omamori;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import tw.iehow.howitem.CooldownManager;
import tw.iehow.howitem.enums.CooldownType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PotionEffect;
import tw.iehow.howitem.util.check.SlotCheck;

public class RedOmamori {
    private static boolean absorptionEffect = false;

    public static void apply(PlayerEntity player) {
        float absorptionAmount = player.getAbsorptionAmount();
        if (SlotCheck.isValid(player.getOffHandStack(), Items.TOTEM_OF_UNDYING,1337001)) {
            long cd = CooldownManager.get(player.getUuid(), CooldownType.OMAMORI);
            if (cd == 0 && !absorptionEffect) {
                PotionEffect.add(player, StatusEffects.ABSORPTION, -1, 3);
                absorptionAmount = 1.0F;
                absorptionEffect = true;
            }else {
                PlayerActionBar.showCD(player, cd);
            }
        }
        if (absorptionEffect && (!SlotCheck.isValid(player.getOffHandStack(), Items.TOTEM_OF_UNDYING,1337001) || absorptionAmount == 0.0F)){
            PotionEffect.remove(player, StatusEffects.ABSORPTION);
            absorptionEffect = false;
            CooldownManager.set(player.getUuid(), CooldownType.OMAMORI, 300);
        }
    }
}
