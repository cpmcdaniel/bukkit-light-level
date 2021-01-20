package org.kowboy.bukkit.light;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LightLevelPlugin extends JavaPlugin {

    private LightLevelListener listener;
    private List<String> completions = Arrays.asList("on", "off");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!"light-level".equals(command.getName())) {
            sender.sendMessage(ChatColor.RED + "Unrecognized command: " + label + ChatColor.RESET);
            return false;
        }

        if (args.length != 1) {
            return false;
        }

        if ("on".equalsIgnoreCase(args[0])) {
            listener.on();
        } else if ("off".equalsIgnoreCase(args[0])) {
            listener.off();
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid argument: " + args[0]);
            return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) return Collections.emptyList();
        return completions;
    }

    @Override
    public void onEnable() {
        this.listener = new LightLevelListener(this);
        getServer().getPluginManager().registerEvents(this.listener, this);
    }
}
