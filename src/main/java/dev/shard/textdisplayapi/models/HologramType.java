package dev.shard.textdisplayapi.models;

import dev.shard.textdisplayapi.hologramtypes.HangingHologram;
import dev.shard.textdisplayapi.hologramtypes.Hologram;
import dev.shard.textdisplayapi.hologramtypes.StandingHologram;
import dev.shard.textdisplayapi.hologramtypes.WallHologram;
import lombok.Getter;

public enum HologramType {
    STANDING(StandingHologram.class, true, 'S'),
    HANGING(HangingHologram.class, true, 'H'),
    WALL(WallHologram.class, false, 'W');

    @Getter
    private Class<? extends Hologram> clazz;

    @Getter
    private boolean supportsFineDirections;

    @Getter
    private char shortIdentifier;

    HologramType(Class<? extends Hologram> clazz, boolean supportsFineDirections, char shortIdentifier) {
        this.clazz = clazz;
        this.supportsFineDirections = supportsFineDirections;
        this.shortIdentifier = shortIdentifier;
    }
}
