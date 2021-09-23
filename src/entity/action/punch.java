package entity.action;

import core.Size;
import entity.MovingEntity;
import game.GameLoop;
import state.State;

public class punch extends Action{

    private int lifeSpanInSeconds;
    private Size spreadAreaSize;
    private double risk;

    public punch() {
        lifeSpanInSeconds = GameLoop.UPDATES_PER_SECOND;

    }

    @Override
    public void update(State state, MovingEntity entity) {


        if(entity.isAttacking()){
            lifeSpanInSeconds--;
        }

    }

    @Override
    public boolean isDone() {
        return lifeSpanInSeconds <= 0;
    }

    @Override
    public String getAnimationName() {
        return "punch";
    }

    @Override
    public String getSoundName() {
        return "attack.wav";
    }


}
