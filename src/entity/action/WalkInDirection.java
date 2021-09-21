package entity.action;

import core.Movement;
import core.Vector2D;
import entity.MovingEntity;
import game.GameLoop;
import state.State;
// THIS CLASS IS FOR PLAYER TO WALK INTO THE GAME MAP.
public class WalkInDirection extends Action{


    private int walkTime;
    private Movement movement;

    public WalkInDirection(Vector2D direction){
        walkTime = GameLoop.UPDATES_PER_SECOND * 3;
        movement = new Movement(1);
        movement.add(direction);
    }

    @Override
    public void update(State state, MovingEntity entity) {
        walkTime--;

        entity.apply(movement);
    }

    @Override
    public boolean isDone() {
        return walkTime == 0;
    }

    @Override
    public String getAnimationName() {
        return "walk";
    }

    @Override
    public String getSoundName() {
        return null;
    }
}
