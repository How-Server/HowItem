package tw.iehow.howitem.items;

import com.gmail.sneakdevs.diamondeconomy.DiamondUtils;
import com.gmail.sneakdevs.diamondeconomy.sql.DatabaseManager;
import com.gmail.sneakdevs.diamondeconomy.sql.TransactionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import tw.iehow.howitem.enums.TriggerType;
import tw.iehow.howitem.util.apply.PlayerActionBar;
import tw.iehow.howitem.util.apply.PlayerSound;
import tw.iehow.howitem.util.apply.PotionEffect;

public class HowCard extends BaseHowItem {

    public HowCard() {
        super(Items.SKULL_BANNER_PATTERN, 1337022);
        this.triggerTypes.remove(TriggerType.ATTACK_HAND);
        this.triggerTypes.add(TriggerType.OFF_HAND);
    }

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
        if(!(entity instanceof ServerPlayerEntity)){
            return;
        }
        DatabaseManager dm = DiamondUtils.getDatabaseManager();

        int money = dm.getBalanceFromUUID(player.getUuid().toString());
        if (money > 0) {
            dm.changeBalance(player.getUuid().toString(), TransactionType.EXPENSE, -1, "How黑卡 - 支付給 " + entity.getDisplayName());
            PlayerActionBar.showText((ServerPlayerEntity) player, "您賞了 " + entity.getDisplayName().getString() + " 1 塊", Formatting.YELLOW);
            PotionEffect.add(player, StatusEffects.GLOWING, 200, 1);
            dm.changeBalance(entity.getUuid().toString(), TransactionType.INCOME, 1, "How黑卡 - 收到來自 " + player.getDisplayName() + " 的賞金");
            PlayerActionBar.showText((ServerPlayerEntity) entity, player.getDisplayName().getString() + " 有錢人賞了你 1 塊", Formatting.GREEN);
            PlayerSound.play((ServerPlayerEntity) player, SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        } else {
            PlayerActionBar.showText(player, "菜就多練，沒錢就別搞", Formatting.RED);
            PlayerSound.play((ServerPlayerEntity) player, SoundEvents.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
        }
    }
}
