package org.kowboy.bukkit.light;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LightLevelListener implements Listener {

    private static final int APOTHEM = 8;

    private final LightLevelPlugin plugin;

    private boolean enabled = false;

    private final Set<UUID> locks = new HashSet<>();

    private final Set<Material> lightSources = Stream.of(
            Material.TORCH,
            Material.LANTERN,
            Material.GLOWSTONE,
            Material.JACK_O_LANTERN,
            Material.SEA_LANTERN,
            Material.SEA_PICKLE,
            Material.CONDUIT,
            Material.CAMPFIRE,
            Material.SOUL_CAMPFIRE,
            Material.END_ROD,
            Material.ENDER_CHEST,
            Material.REDSTONE_TORCH,
            Material.MAGMA_BLOCK,
            Material.LAVA_BUCKET)
            .collect(Collectors.toUnmodifiableSet());


    public LightLevelListener(LightLevelPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        // Do nothing if the feature is not enabled
        if (!enabled) return;

        // Do nothing if the player is not online
        Player player = event.getPlayer();
        if (!player.isOnline()) return;

        // Do nothing if there is already a task for this player
        if (locks.contains(player.getUniqueId())) return;

        // Only create the task if the player is sneaking (initiating sneak)
        if (!event.isSneaking()) return;

        // ...and holding a light
        if (!isHoldingLight(player)) return;

        scheduleTask(player);
    }

    private void scheduleTask(Player player) {
        if (lock(player)) {
            BukkitRunnable task = new LightLevelTask(APOTHEM, player,
                    () -> release(player));
            task.runTaskTimer(plugin, 10, 10);
        } else {
            // Theoretically, this should never happen. I believe all event handlers
            // are processed in a single thread, so there should be no lock contention.
            plugin.getLogger().warning("Player " + player.getName() +
                    " already has a lock for light-level task");
        }
    }

    private synchronized boolean lock(Player player) {
        if (locks.contains(player.getUniqueId())) return false;
        locks.add(player.getUniqueId());
        return true;
    }

    private synchronized void release(Player player) {
        locks.remove(player.getUniqueId());
    }

    private boolean isHoldingLight(Player player) {
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem  = player.getInventory().getItemInOffHand();
        return isLight(mainHandItem.getType()) || isLight(offHandItem.getType());
    }

    private boolean isLight(Material type) {
        if (null == type) return false;
        return lightSources.contains(type);
    }

    public void on() {
        enabled = true;
    }

    public void off() {
        enabled = false;
    }
}
