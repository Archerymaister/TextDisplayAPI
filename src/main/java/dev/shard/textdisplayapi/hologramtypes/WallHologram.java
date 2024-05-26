package dev.shard.textdisplayapi.hologramtypes;

import dev.shard.textdisplayapi.models.HologramType;
import org.bukkit.util.Vector;

public class WallHologram extends Hologram{
    private static double antiZFightingOffset = 0.01;
    WallHologram(HologramBuilder hologramBuilder) {
        super(hologramBuilder);
    }

    @Override
    public Vector getPositionRelativeToAnchor() {
        switch (this.getDirection()){
            case NORTH: return new Vector(0.5, 0, 0 - antiZFightingOffset);
            case EAST: return new Vector(1 + antiZFightingOffset, 0, 0.5);
            case SOUTH: return new Vector(0.5, 0, 1 + antiZFightingOffset);
            case WEST: return new Vector(0 - antiZFightingOffset, 0, 0.5);
            default: return new Vector(0, 0, 0);
        }
    }

    @Override
    public HologramType getHologramType() {
        return HologramType.WALL;
    }
}
