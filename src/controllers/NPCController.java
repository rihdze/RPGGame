package controllers;

import core.Position;
import entity.Player;


public class NPCController implements EntityController {
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;


    @Override
    public boolean isRequestingUp() {
        return up;
    }

    @Override
    public boolean isRequestingDown() {
        return down;
    }

    @Override
    public boolean isRequestingLeft() {
        return left;
    }

    @Override
    public boolean isRequestingAction(){
        return false;
    }

    @Override
    public boolean isRequestingAttack() {
        return false;
    }

    @Override
    public boolean isRequestingRight() {
        return right;
    }

    public void moveToTarget(Position target, Position current) {
        double deltaX = target.getX() - current.getX();
        double deltaY = target.getY() - current.getY();

        up = deltaY < 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        down = deltaY > 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        right = deltaX > 0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;
        left = deltaX < 0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;
    }


    public void stop() {
        up = false;
        down = false;
        right = false;
        left = false;

    }

    @Override
    public boolean isRequesting1() {
        return false;
    }

    @Override
    public boolean isRequesting2() {
        return false;
    }





}
