package entity.action;

import core.Movement;
import core.Vector2D;
import entity.MovingEntity;
import game.GameLoop;
import state.State;

public class PlayerDeath extends Action{

    private Movement movement;



    public PlayerDeath() {

    }




    @Override
    public void update(State state, MovingEntity entity) {
        movement = new Movement(0);
        movement.add(new Vector2D(0,0));
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public String getAnimationName() {
        return "UnarmedDeath";
    }

    @Override
    public String getSoundName() {
        return null;
    }
}
