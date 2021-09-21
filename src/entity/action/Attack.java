package entity.action;

import core.CollisionBox;
import core.Position;
import core.Size;
import entity.Effect.Sick;
import entity.MovingEntity;
import game.Game;
import game.GameLoop;
import state.State;

public class Attack extends Action{

    private int lifeSpanInSeconds;
    private Size spreadAreaSize;
    private double risk;

    public Attack() {
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
        return "attack";
    }

    @Override
    public String getSoundName() {
        return "attack.wav";
    }


}
