package dev.shard.textdisplayapi;

import dev.shard.textdisplayapi.updater.HologramService;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

public class TextDisplayAPI {
    @Getter
    private static Plugin plugin;

    @Getter
    private static final long updateInterval = 30;

    public static void init(Plugin basePlugin){
        plugin = basePlugin;

        HologramService.init();
    }

    public static void reload(){
        HologramService.reload();
    }

    public static void stop(){
        HologramService.stop();
    }
}
