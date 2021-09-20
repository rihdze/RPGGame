package core;

import controllers.EntityController;
import entity.MovingEntity;

public class Movement {

    private Vector2D vector;
    private double speed;

    public Movement(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0,0);
    }

    public void update(EntityController entityController){

        int deltaX = 0;
        int deltaY = 0;

        if(entityController.isRequestingUp()){
            deltaY--;
        }
        if(entityController.isRequestingDown()){
            deltaY++;
        }
        if(entityController.isRequestingLeft()){
            deltaX--;
        }
        if(entityController.isRequestingRight()){
            deltaX++;
        }

        vector = new Vector2D(deltaX, deltaY);
        vector.normalize();
        vector.multiply(speed);

    }

    public Vector2D getVector() {
        return vector;
    }

    public boolean isMoving() {
        return vector.length() > 0;
    }

    public void multiply(double multiplier){
        vector.multiply(multiplier);
    }

    public void stop() {
        vector = new Vector2D(0,0);
    }

    public Vector2D getDirection() {
        Vector2D direction = Vector2D.copyOf(vector);
        direction.normalize();

        return direction;
    }


    public void increaseSpeed(double speed){
        this.speed += speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
