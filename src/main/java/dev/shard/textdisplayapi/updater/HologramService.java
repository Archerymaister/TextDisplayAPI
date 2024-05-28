package dev.shard.textdisplayapi.updater;

import dev.shard.textdisplayapi.TextDisplayAPI;
import dev.shard.textdisplayapi.hologramtypes.Hologram;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
     * Returns all holograms with the anchor block in the radius of the given location.
     *
     * @param location the expected location of a hologram
     * @param radiusX offset on the x-axis
     * @param radiusY offset on the y-axis
     * @param radiusZ offset on the z-axis
     * @return hologram in this approximate area
     */
    public static Set<Hologram> getHolograms(Location location, double radiusX, double radiusY, double radiusZ){
        Set<Hologram> holograms = new HashSet<>();
        for(Hologram hologram : getRegisteredHolograms()){
            if (Math.abs(hologram.getAnchorLocation().getBlockX() - location.getBlockX()) <= radiusX
                    && Math.abs(hologram.getAnchorLocation().getBlockY() - location.getBlockY()) <= radiusY
                    && Math.abs(hologram.getAnchorLocation().getBlockZ() - location.getBlockZ()) <= radiusZ) {
                holograms.add(hologram);
            }
        }
        return holograms;
    }

    /**
     * Returns all holograms with the anchor block at the exact given location.
     *
     * @param location the expected location of a hologram
     * @return holograms at this block
     */
    public static Set<Hologram> getHologramsAt(Location location) {
        return getHolograms(location, 0, 0, 0);
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
