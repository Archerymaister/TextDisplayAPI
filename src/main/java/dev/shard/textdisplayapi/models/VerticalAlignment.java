package dev.shard.textdisplayapi.models;

import lombok.Getter;
import org.bukkit.util.Vector;

public enum VerticalAlignment {
    TOP(-1),
    BOTTOM(+1),
    CENTER(+1);

    @Getter
    private final double directionMultiplier;
    VerticalAlignment(double directionMultiplier){
        this.directionMultiplier = directionMultiplier;
    }

    public Vector[] getPlacementVectors(int lineCount){
        Vector[] placements = new Vector[lineCount];
        double lineHeight = 0.3;
        double verticalOffset = 0;

        // ((lineCount - 1) / -2.0) * lineHeight
        // + 0.5 - (lineHeight/2) -> Put center to
        if(this == CENTER){
            verticalOffset = ((lineCount - 1) / -2.0) * lineHeight + 0.5 - (lineHeight/2);
        } else if (this == TOP){
            verticalOffset = -1 * lineHeight;
        }

        for(int i = 0; i < lineCount; i++){
            placements[i] = new Vector(0, i * lineHeight * this.directionMultiplier + verticalOffset, 0);
        }
        return placements;
    }
}
