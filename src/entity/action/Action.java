package entity.action;

import audio.AudioPlayer;
import entity.MovingEntity;
import state.State;

public abstract class  Action {

    protected boolean soundPlaying;


    public abstract void update(State state, MovingEntity entity);
    public abstract boolean isDone();
    public abstract String getAnimationName();
    public abstract String getSoundName();
    public void playSound(AudioPlayer audioPlayer){
        if(!soundPlaying && getSoundName() != null){
            audioPlayer.playSound(getSoundName());
            soundPlaying = true;
        }
    }
}
