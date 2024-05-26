package dev.shard.textdisplayapi;

import dev.shard.textdisplayapi.hologramtypes.Hologram;
import dev.shard.textdisplayapi.models.Direction;
import dev.shard.textdisplayapi.models.HologramLine;
import dev.shard.textdisplayapi.models.HologramType;
import dev.shard.textdisplayapi.models.VerticalAlignment;
import org.bukkit.Location;

import java.util.logging.Logger;

import static dev.shard.textdisplayapi.hologramtypes.Hologram.HologramBuilder;

public class HologramFactory {

    public static Hologram getHologram(HologramType type, Location location, Direction direction){
        return switch (type) {
            case HANGING -> getHangingHologram(location, direction);
            case WALL -> getWallHologram(location, direction);
            case STANDING -> getStandingHologram(location, direction);
            default -> throw new UnsupportedOperationException("Hologram type " + type.name() + " has not been implemented!");
        };
    }

    public static Hologram getHangingHologram(Location location, Direction direction, HologramLine... hologramLines){
        Logger.getAnonymousLogger().fine("Creating hanging hologram at " + location.toString());
        return new HologramBuilder()
                .addLines(hologramLines)
                .setAnchorLocation(location)
                .setDirection(direction)
                .setVerticalAlignment(VerticalAlignment.TOP)
                .setVisible(true)
                .buildHangingHologram();
    }

    public static Hologram getStandingHologram(Location location, Direction direction, HologramLine... hologramLines){
        Logger.getAnonymousLogger().fine("Creating standing hologram at " + location.toString());
        return new HologramBuilder()
                .addLines(hologramLines)
                .setAnchorLocation(location)
                .setDirection(direction)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setVisible(true)
                .buildStandingHologram();
    }

    public static Hologram getWallHologram(Location location, Direction direction, HologramLine... hologramLines){
        Logger.getAnonymousLogger().fine("Creating wall-mounted hologram at " + location.toString());
        return new HologramBuilder()
                .addLines(hologramLines)
                .setAnchorLocation(location)
                .setDirection(direction)
                .setVerticalAlignment(VerticalAlignment.CENTER)
                .setVisible(true)
                .buildWallHologram();
    }
}
