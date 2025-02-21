package dev.shard.textdisplayapi.models;

import lombok.Getter;

@Getter
public enum Direction {
    SOUTH(0, -22.5f, 22.49f),
    SOUTH_WEST(45, 22.5f, 67.49f),
    WEST(90, 67.5f, 112.49f),
    NORTH_WEST(135, 112.5f, 157.49f),
    NORTH(-180, 157.5f, 179.9f),
    NORTH2(-180, -180f, -157.5f),
    NORTH_EAST(-135, -157.49f, -112.5f),
    EAST(-90, -112.49f, -67.5f),
    SOUTH_EAST(-45, -67.49f, -22.5f);

    private final float minYaw;
    private final float maxYaw;
    private final float defaultYaw;

    Direction(float defaultYaw, float minYaw, float maxYaw) {
        this.minYaw = minYaw;
        this.maxYaw = maxYaw;
        this.defaultYaw = defaultYaw;
    }

    public static Direction getDirection(float yaw) {
        yaw = normalize(yaw);

        if(yaw > -22.5f && yaw <= 22.5f){
            return SOUTH;
        }else if (yaw > 22.5f && yaw <= 67.5f){
            return SOUTH_WEST;
        } else if (yaw > 67.5f && yaw <= 112.5f) {
            return WEST;
        } else if (yaw > 112.5f && yaw <= 157.5f) {
            return NORTH_WEST;
        } else if (yaw > -67.5f && yaw <= -22.5f) {
            return SOUTH_EAST;
        } else if (yaw > -112.5f && yaw <= -67.5f) {
            return EAST;
        } else if (yaw > -157.5f && yaw <= -112.5f) {
            return NORTH_EAST;
        } else {
            return NORTH;
        }
    }

    public static Direction getSimplifiedDirection(float yaw){
        yaw = normalize(yaw);

        if(yaw >= -45f && yaw < 45f){
            return SOUTH;
        } else if (yaw >= 45 && yaw < 135f) {
            return WEST;
        } else if (yaw >= -135f && yaw < -45f) {
            return EAST;
        } else {
            return NORTH;
        }
    }

    private static float normalize(float yaw) {
        while (yaw >= 180f) {
            yaw -= 360f;
        }

        while (yaw < -180f) {
            yaw += 360f;
        }
        return yaw;
    }

    public Direction getOpposite() {
        return getDirection(this.defaultYaw + 180f);
    }

}
