package tw.iehow.util.check;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;

public class EntityCheck {
    public static Boolean entityType(Entity entity){
        return !entity.isLiving() || (entity instanceof ArmorStandEntity);
    }
}
