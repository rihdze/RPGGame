package entity.Effect;

import entity.MovingEntity;
import game.GameLoop;
import state.State;
//Just for testing purposes
public class Caffeinated extends Effect{

    private double speedMultiplier;

// * INT for duration in seconds
    public Caffeinated() {
        super(GameLoop.UPDATES_PER_SECOND *5000);
        speedMultiplier = 0.75;
    }

    @Override
    public void update(State state, MovingEntity entity){
        super.update(state,entity);

        entity.multiplySpeed(speedMultiplier);
    }
}
