package org.kowboy.bukkit.light;

import org.bukkit.plugin.java.JavaPlugin;

public class LightLevelPlugin extends JavaPlugin {

    private final int DEFAULT_SNEAK_RADIUS = 8;
    private final int DEFAULT_STANDING_RADIUS = 6;
    private final int MAX_SNEAK_RADIUS = 16;
    private final int MAX_STANDING_RADIUS = 8;
    private LightLevelListener listener;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        int sneakRadius = getConfig().getInt("sneak-radius", DEFAULT_SNEAK_RADIUS);
        if (sneakRadius > MAX_SNEAK_RADIUS) {
            getLogger().warning("configured sneak-radius is above max of " +
                    MAX_SNEAK_RADIUS + " - using max instead");
            sneakRadius = MAX_SNEAK_RADIUS;
        }

        int standingRadius = getConfig().getInt("standing-radius", DEFAULT_STANDING_RADIUS);
        if (standingRadius > MAX_STANDING_RADIUS) {
            getLogger().warning("configured standing-radius is above max of " +
                    MAX_STANDING_RADIUS + " - using max instead");
            standingRadius = MAX_STANDING_RADIUS;
        }

        this.listener = new LightLevelListener(this, sneakRadius, standingRadius);
        getServer().getPluginManager().registerEvents(this.listener, this);
    }

    public void onDisable() {
        saveConfig();
    }
}
