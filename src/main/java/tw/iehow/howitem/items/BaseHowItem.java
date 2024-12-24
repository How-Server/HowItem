package tw.iehow.howitem.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import tw.iehow.howitem.enums.TriggerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseHowItem {

    protected Item item;
    protected float customModelID;
    protected boolean bypassDragonPart = false;
    protected List<TriggerType> triggerTypes = new ArrayList<>();

    public BaseHowItem(Item item, int customModelID) {
        this.item = item;
        this.customModelID = customModelID;
        this.triggerTypes.add(TriggerType.ATTACK_HAND);
    }

    public void beforeSafeAttack(PlayerEntity player, World world, Entity entity) {
        if (!bypassDragonPart && entity instanceof EnderDragonPart) {
            return;
        }
        safeAttack(player, world, entity);
    }

    public void beforeUnsafeAttack(PlayerEntity player, World world, Entity entity) {
        if (!bypassDragonPart && entity instanceof EnderDragonPart) {
            return;
        }
        unsafeAttack(player, world, entity);
    }

    public int getHash() {
        return Objects.hash(this.item.toString(), this.customModelID);
    }

    public void setBypassDragonPart() {
        this.bypassDragonPart = true;
    }

    public boolean canTrigger(TriggerType type) {
        return triggerTypes.contains(type);
    }

    public void safeUse(PlayerEntity player, Hand hand) {
    }

    public void unsafeUse(PlayerEntity player, Hand hand) {
    }

    public void safeAttack(PlayerEntity player, World world, Entity entity) {
    }

    public void unsafeAttack(PlayerEntity player, World world, Entity entity) {
    }
}
