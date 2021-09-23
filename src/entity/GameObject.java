package entity;

import core.CollisionBox;
import display.Camera;
import state.State;
import core.Position;
import core.Size;



import java.awt.*;
import java.sql.SQLException;

public abstract class GameObject {

    protected Position position;
    protected Size size;
    protected Position renderOffset;

    protected int renderOrder;
    protected Position collisionBoxOffset;
    protected GameObject parent;

    public GameObject(){
        position = new Position(0,0);
        size = new Size(64, 64);
        renderOffset = new Position(0,0);
        collisionBoxOffset = new Position(0,0);
        renderOrder = 5;

    }

    public GameObject getParent() {
        return parent;
    }

    public abstract void update(State state) throws SQLException;


    public abstract CollisionBox getCollisionBox();

    public boolean collidesWith(GameObject other){
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }

    public abstract Image getSprite();

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        Position finalPosition = Position.copyOf(position);

        if(parent != null){
            finalPosition.add(parent.getPosition());
        }

        return finalPosition;
    }

    public Size getSize() {
        return size;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public Position getRenderPosition(Camera camera) {
        return new Position(
                getPosition().getX() - camera.getPosition().getX() -renderOffset.getX(),
                getPosition().getY() - camera.getPosition().getY() -renderOffset.getY()

        );
    }

    public int getRenderOrder() {
        return renderOrder;
    }

    protected void clearParent() {
        parent = null;
    }
}
