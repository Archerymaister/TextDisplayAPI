package dev.shard.textdisplayapi.hologramtypes;

import dev.shard.textdisplayapi.models.HologramType;
import org.bukkit.util.Vector;

public class HangingHologram extends Hologram{
    HangingHologram(HologramBuilder hologramBuilder) {
        super(hologramBuilder);
    }

    @Override
    public Vector getPositionRelativeToAnchor() {
        return new Vector(0.5, 0, 0.5);
    }

    @Override
    public HologramType getHologramType() {
        return HologramType.HANGING;
    }
}
