package dev.shard.textdisplayapi.models;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HologramLine {

    @Getter
    private String text;
    private final boolean defaultBackground;
    private final Color backgroundColor;
    private final TextDisplay.TextAlignment alignment;
    private final byte textOpacity;
    private final boolean seeThrough;
    private final boolean shadowed;
    private final int lineWidth;

    private TextDisplay textDisplay;

    public HologramLine(String text, boolean defaultBackground, Color backgroundColor, TextDisplay.TextAlignment alignment, byte textOpacity, boolean seeThrough, boolean shadowed, int lineWidth) {
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.alignment = alignment;
        this.textOpacity = textOpacity;
        this.seeThrough = seeThrough;
        this.shadowed = shadowed;
        this.lineWidth = lineWidth;
        this.defaultBackground = defaultBackground;
    }

    public HologramLine(String text){
        this(text, true, Color.GRAY, TextDisplay.TextAlignment.CENTER, (byte) 0, false, false, Integer.MAX_VALUE);

    }

    public void show(){
        textDisplay.setVisibleByDefault(true);
    }

    public void hide(){
        textDisplay.setVisibleByDefault(false);
    }

    public void updateText(String newText){
        this.text = newText;
        this.textDisplay.setText(newText);
    }

    public void create(@NotNull Location location){
        Objects.requireNonNull(location.getWorld());

        this.textDisplay = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        this.textDisplay.setText(text);
        this.textDisplay.setAlignment(alignment);
        this.textDisplay.setLineWidth(lineWidth);
        this.textDisplay.setBackgroundColor(backgroundColor);
        this.textDisplay.setTextOpacity(textOpacity);
        this.textDisplay.setShadowed(shadowed);
        this.textDisplay.setSeeThrough(seeThrough);
        this.textDisplay.setDefaultBackground(defaultBackground);

        this.show();
    }

    /**
     * Kills the TextDisplay entity.
     */
    public void removeFromWorld(){
        this.textDisplay.remove();
    }
}
