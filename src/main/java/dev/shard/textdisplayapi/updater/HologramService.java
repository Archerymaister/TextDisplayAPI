package dev.shard.textdisplayapi.updater;

import dev.shard.textdisplayapi.TextDisplayAPI;
import dev.shard.textdisplayapi.exceptions.InvalidLocationException;
import dev.shard.textdisplayapi.hologramtypes.Hologram;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

public class HologramService {
    private static final HashMap<Location, Hologram> holograms = new HashMap<>();

    /**
     * Registers a topX hologram to be updated regularly in the configured interval.
     *
     * @param hologram Registered hologram.
     */
    public static void registerHologram(Hologram hologram){
        holograms.put(hologram.getAnchorLocation(), hologram);
    }

    public static Hologram unregisterHologram(Hologram hologram) {
        return holograms.remove(hologram.getAnchorLocation());
    }

    /**
     * Returns the amount of registered holograms.
     * @return Amount of registered holograms.
     */
    public static int getRegisteredCount(){
        return holograms.size();
    }

    /**
     * Returns a Collection of the registered holograms.
     * @return Collection of registered holograms.
     */
    public static Collection<Hologram> getRegisteredHolograms(){
        return holograms.values();
    }

    /**
     * Returns a hologram at the approximate given location. The X and Z axis have to be correct,
     * but the Y axis (up/down) may be off by up to five blocks.
     *
     * @param location the expected location of a hologram
     * @return hologram in this approximate area
     * @throws InvalidLocationException When no hologram was found
     */
    public static Hologram getHologram(Location location) throws InvalidLocationException {
        for (Hologram hologram : getRegisteredHolograms()) {
            if (hologram.getAnchorLocation().getBlockX() == location.getBlockX()
                    && hologram.getAnchorLocation().getBlockZ() == location.getBlockZ()
                    && Math.abs(hologram.getAnchorLocation().getBlockY() - location.getBlockY()) <= 5) {
                return hologram;
            }
        }
        throw new InvalidLocationException("No hologram at this location!", location);
    }

    private static void removeRegisteredHolograms(){
        for(Hologram hologram : getRegisteredHolograms()){
            hologram.remove();
        }

        holograms.clear();
    }

    public static void init() {
        HologramUpdater.start();
    }

    public static void reload() {
        TextDisplayAPI.getPlugin().getLogger().log(Level.INFO, "Reloading holograms...");
        stop();

        HologramUpdater.start();
    }

    public static void stop() {
        HologramUpdater.stop();
        removeRegisteredHolograms();
    }

    private static class HologramUpdater extends BukkitRunnable {
        private static HologramUpdater instance;

        private HologramUpdater(){}

        public static HologramUpdater getInstance(boolean reload) {
            if(instance == null)
                instance = new HologramUpdater();
            return instance;
        }

        public static HologramUpdater getInstance() {
            return getInstance(false);
        }

        @Override
        public void run() {
            getRegisteredHolograms().stream().filter(hologram -> !hologram.isStaticContent()).forEach(Hologram::update);
        }

        public static void start(){
            getInstance().runTaskTimer(TextDisplayAPI.getPlugin(), 0L, TextDisplayAPI.getUpdateInterval());
        }

        public static void stop(){
            getInstance().cancel();
            instance = null;
        }
    }
}
