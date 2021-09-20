package entity.action;

import entity.MovingEntity;
import game.GameLoop;
import state.State;

public class Death extends Action{
    private int lifeSpanInSeconds;

    public Death() {
        lifeSpanInSeconds = GameLoop.UPDATES_PER_SECOND;
    }

    @Override
    public void update(State state, MovingEntity entity) {

        lifeSpanInSeconds--;
    }

    @Override
    public boolean isDone() {
        return lifeSpanInSeconds <= 0;
    }

    @Override
    public String getAnimationName() {
        return "death";
    }
}
