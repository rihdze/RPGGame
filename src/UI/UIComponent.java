package UI;

import core.Position;
import core.Size;
import game.state.State;

import java.awt.*;

public abstract class UIComponent {

    protected Position position;
    protected Size size;
    protected Spacing margin;
    protected Spacing padding;

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setMargin(Spacing margin) {
        this.margin = margin;
    }

    public void setPadding(Spacing padding) {
        this.padding = padding;
    }

    public UIComponent() {
        position = new Position(0,0);
        size = new Size(1,1);
        margin = new Spacing(0);
        padding = new Spacing(0);
    }

    public abstract Image getSprite();

    public abstract void update(State state);

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

    public Spacing getMargin() {
        return margin;
    }

    public Spacing getPadding() {
        return padding;
    }
}
