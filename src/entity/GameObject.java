package entity;

import core.CollisionBox;
import game.state.State;
import core.Position;
import core.Size;

import java.awt.*;

public abstract class GameObject {

    protected Position position;
    protected Size size;

    public GameObject(){
        position = new Position(50,50);
        size = new Size(50, 50);

    }

    public abstract void update(State state);

    public abstract CollisionBox getCollisionBox();

    public abstract boolean collidesWith(GameObject other);

    public abstract Image getSprite();

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

}
