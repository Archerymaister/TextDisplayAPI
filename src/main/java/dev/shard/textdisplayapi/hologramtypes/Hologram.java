package dev.shard.textdisplayapi.hologramtypes;

import dev.shard.textdisplayapi.models.Direction;
import dev.shard.textdisplayapi.models.HologramLine;
import dev.shard.textdisplayapi.models.HologramType;
import dev.shard.textdisplayapi.models.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Hologram {

    @Getter @Setter
    private boolean visible;

    @Getter @Setter
    private Location anchorLocation;

    @Getter @Setter
    private VerticalAlignment alignment;

    @Getter @Setter
    private Direction direction;

    @Getter
    private final boolean staticContent = true;

    private final double lineHeight = 0.3;

    private final List<HologramLine> lines;

    Hologram(HologramBuilder hologramBuilder) {
        this.visible = hologramBuilder.visible;
        this.anchorLocation = hologramBuilder.anchorLocation;
        this.lines = hologramBuilder.lines;
        this.alignment = hologramBuilder.alignment;
        this.direction = hologramBuilder.direction;
        this.anchorLocation.setYaw(this.direction.getDefaultYaw());

        create();

        // Visible per default, maybe change default behavior later based on config
        this.show();
    }

    public void show(){
        this.visible = true;
        this.lines.forEach(HologramLine::show);
    }

    public void hide(){
        this.visible = false;
        this.lines.forEach(HologramLine::hide);
    }

    // TODO: Rework
    private void create() {
        Vector[] lineSlots = alignment.getPlacementVectors(lines.size());
        for (int i = 0; i != lines.size(); i++) {
            int alignedI = (int) (i * alignment.getDirectionMultiplier());

            // To put the top line beneath the targeted block. If not, the first line would be stuck inside the block
            // on which the hologram appears to be hanging on.
            if(alignment.getDirectionMultiplier() == -1){
                alignedI -= 1;
            }

            Location lineLocation = this.anchorLocation.clone().add(lineSlots[i]).add(getPositionRelativeToAnchor());
            lineLocation.setYaw(direction.getDefaultYaw());
            getLine(i, alignment != VerticalAlignment.TOP).create(lineLocation);
        }
    }

    /**
     * Updates the hologram lines with whatever the current text is.
     */
    public void update() {
        for (int i = 0; i != lines.size(); i++) {
            getLine(i, false).updateText(lines.get(i).getText());
        }
    }

    public void remove() {
        this.lines.forEach(HologramLine::removeFromWorld);
    }

    private HologramLine getLine(int index, boolean reversed) {
        if (reversed) {
            return lines.get(lines.size() - index - 1);
        }

        return lines.get(index);
    }

    public abstract Vector getPositionRelativeToAnchor();

    public abstract HologramType getHologramType();

    public void addHologramLine(HologramLine... newLines) {
        lines.addAll(Arrays.asList(newLines));
    }

    public void removeHologramLine(HologramLine line) {
        lines.remove(line);
    }

    public void removeHologramLine(int index){
        lines.remove(index);
    }

    public void clearLines(){
        lines.clear();
    }

    public static class HologramBuilder {
        private Location anchorLocation;
        private List<HologramLine> lines = new ArrayList<>();
        private boolean visible = true;
        private VerticalAlignment alignment = VerticalAlignment.BOTTOM;
        private Direction direction = Direction.SOUTH;
        private boolean staticContent = true;

        public HologramBuilder setAnchorLocation(Location anchorLocation){
            this.anchorLocation = anchorLocation;
            return this;
        }

        public HologramBuilder addLines(HologramLine... lines) {
            this.lines.addAll(Arrays.asList(lines));
            return this;
        }

        public HologramBuilder setVisible(boolean visible){
            this.visible = visible;
            return this;
        }

        public HologramBuilder setVerticalAlignment(VerticalAlignment verticalAlignment){
            this.alignment = verticalAlignment;
            return this;
        }

        public HologramBuilder setDirection(Direction direction){
            this.direction = direction;
            return this;
        }

        public HologramBuilder setContentStatic(boolean contentStatic){
            this.staticContent = contentStatic;
            return this;
        }

        public StandingHologram buildStandingHologram(){
            return new StandingHologram(this);
        }

        public HangingHologram buildHangingHologram(){
            return new HangingHologram(this);
        }

        public WallHologram buildWallHologram(){
            return new WallHologram(this);
        }
    }
}
