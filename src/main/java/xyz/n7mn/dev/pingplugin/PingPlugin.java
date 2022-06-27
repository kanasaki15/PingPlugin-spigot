package xyz.n7mn.dev.pingplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class PingPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("ping").setExecutor(new PingCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
