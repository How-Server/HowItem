package tw.iehow.howitem;

import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import tw.iehow.howitem.enums.CooldownType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private static final Map<UUID, Map<CooldownType, Long>> cooldown = new HashMap<>();
    private static MinecraftServer server;
    public static long get(UUID uuid, CooldownType type) {
        cooldown.computeIfAbsent(uuid, k -> new HashMap<>());
        long cooldownTime = cooldown.get(uuid).getOrDefault(type, 0L);
        return Math.max(cooldownTime - server.getTicks(), 0);
    }

    public static void set(UUID uuid, CooldownType type, long cd) {
        cooldown.computeIfAbsent(uuid, k -> new HashMap<>());
        cooldown.get(uuid).put(type, server.getTicks() + cd);
    }

    // add vanilla item cooldown
    public static void set(UUID uuid, CooldownType type, long cd, PlayerEntity player, ItemStack stack) {
        ItemCooldownManager itemCooldownManager = player.getItemCooldownManager();
        itemCooldownManager.set(stack, (int) cd);
        set(uuid, type, cd);
    }

    public static void setServer(MinecraftServer actualServer) {
        if (server != null) {
            return;
        }
        server = actualServer;
    }
}
