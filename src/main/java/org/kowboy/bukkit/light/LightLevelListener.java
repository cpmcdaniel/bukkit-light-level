package org.kowboy.bukkit.light;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LightLevelListener implements Listener {

    private final LightLevelPlugin plugin;

    private final int sneakRadius;
    private final int standingRadius;

    private final Map<UUID, BukkitRunnable> tasks = new HashMap<>();

    public LightLevelListener(LightLevelPlugin plugin, int sneakRadius, int standingRadius) {
        this.plugin = plugin;
        this.sneakRadius = sneakRadius;
        this.standingRadius = standingRadius;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Do nothing if the player is not online
        Player player = event.getPlayer();
        if (!player.isOnline()) return;

        // If a task exists and the player is no longer
        // holding a light source, cancel the task.
        if (tasks.containsKey(player.getUniqueId())) {
            return;
        }

        scheduleTask(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        endTask(event.getPlayer());
    }

    public void onPlayerKick(PlayerKickEvent event) {
        endTask(event.getPlayer());
    }

    private void scheduleTask(Player player) {
        if (tasks.containsKey(player.getUniqueId())) return;
        BukkitRunnable task = new LightLevelTask(player, sneakRadius, standingRadius);
        tasks.put(player.getUniqueId(), task);
        task.runTaskTimer(plugin, 10, 10);
    }



    private void endTask(Player player) {
        BukkitRunnable task = tasks.get(player.getUniqueId());
        if (task != null) {
            task.cancel();
            tasks.remove(player.getUniqueId());
        }
    }
}
