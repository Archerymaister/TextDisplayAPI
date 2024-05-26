package dev.shard.textdisplayapi.exceptions;

import org.bukkit.Location;

public class InvalidLocationException extends RuntimeException{
    private Location location;

    public InvalidLocationException(String message, Location location){
        super(message);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
