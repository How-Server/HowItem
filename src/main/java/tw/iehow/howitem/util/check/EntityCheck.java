package tw.iehow.howitem.util.check;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.ArmorStandEntity;

public class EntityCheck {
    public static Boolean invalidType(Entity entity){
        return !entity.isLiving() || (entity instanceof ArmorStandEntity) || (entity instanceof EnderDragonPart);
    }
}
