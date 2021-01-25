package org.kowboy.bukkit.light;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Displays visual indicators of light-level in a radius (square radius) around the player.
 * Uses colored particle effects to indicate light level according to the following table.
 *
 * <table>
 * <thead>
 * <th>Light Level</th><th>Color</th>
 * </thead>
 * <tbody>
 * <tr><td>12+</td><td>Bright Green</td></tr>
 * <tr><td>10,11</td><td>Green</td></tr>
 * <tr><td>8,9</td><td>Yellow</td></tr>
 * <tr><td>0-7</td><td>Dark Red*</td></tr>
 * </tbody>
 * </table>
 *
 * The dark red color indicates that mobs can spawn on that block.
 *
 * This task is repeated as long as the player is online and sneaking. If either of these conditions is false, the
 * task will cancel itself.
 *
 * @author Craig McDaniel
 * @since 1.0
 */
public class LightLevelTask extends BukkitRunnable {
    private final int sneakRadius;
    private final int standingRadius;
    private final Player player;

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

    public LightLevelTask(Player player, int sneakRadius, int standingRadius) {
        this.sneakRadius = sneakRadius;
        this.standingRadius = standingRadius;
        this.player = player;
    }

    @Override
    public void run() {
        if (!player.isOnline()) {
            cancel();
        }

        // Note, it would be more efficient to limit the blocks based on the yaw
        // (the direction the player is looking). Thus, we would not need to process
        // blocks that are behind the player or otherwise outside their field of view.
        if (isHoldingLight(player)) {
            int radius = player.isSneaking() ? this.sneakRadius : this.standingRadius;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    // Upper Y limit is 3, since you can't really see over that.
                    for (int y = -radius; y <= 3; y++) {
                        process(x, y, z);
                    }
                }
            }
        }
    }

    private void process(int x, int y, int z) {
        Location blockLocation = player.getLocation().add(x, y, z);
        Block block = blockLocation.getBlock();

        // We only care about solid blocks that have non-solid blocks above them.
        if (!block.getType().isSolid()) return;
        if (block.getRelative(BlockFace.UP).getType().isSolid()) return;

        // Get the location of the center of the top face of the block
        Location particalLoc = block.getLocation().add(0.5, 1.0, 0.5);

        // getLightFromBlocks ignores light from the sun, which is what we want
        byte lightLevel = particalLoc.getBlock().getLightFromBlocks();
        Particle.DustOptions options = new Particle.DustOptions(getColor(lightLevel), 1.0f);
        player.spawnParticle(Particle.REDSTONE, particalLoc, 3, options);
    }

    private Color getColor(byte lightLevel) {
        if (lightLevel >= 12) return Color.LIME;
        if (lightLevel >= 10) return Color.GREEN;
        if (lightLevel >= 8)  return Color.YELLOW;
        return Color.RED;
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
}
