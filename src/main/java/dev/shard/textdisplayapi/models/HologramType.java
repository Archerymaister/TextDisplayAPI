package dev.shard.textdisplayapi.models;

import dev.shard.textdisplayapi.hologramtypes.HangingHologram;
import dev.shard.textdisplayapi.hologramtypes.Hologram;
import dev.shard.textdisplayapi.hologramtypes.StandingHologram;
import dev.shard.textdisplayapi.hologramtypes.WallHologram;
import lombok.Getter;

@Getter
public enum HologramType {
    STANDING(StandingHologram.class, true, 'S'),
    HANGING(HangingHologram.class, true, 'H'),
    WALL(WallHologram.class, false, 'W');

    private final Class<? extends Hologram> clazz;

    private final boolean supportsFineDirections;

    private final char shortIdentifier;

    HologramType(Class<? extends Hologram> clazz, boolean supportsFineDirections, char shortIdentifier) {
        this.clazz = clazz;
        this.supportsFineDirections = supportsFineDirections;
        this.shortIdentifier = shortIdentifier;
    }
}
