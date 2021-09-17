package audio;

import game.settings.AudioSettings;
import game.settings.GameSettings;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public abstract class AudioClip {

    private final Clip clip;

    public AudioClip(Clip clip) {
        this.clip = clip;
        clip.start();
    }

    public void update(AudioSettings audiosettings){
        setVolume(audiosettings);
    }

    public void setVolume(AudioSettings audiosettings) {
        final FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = (range*getVolume(audiosettings) + control.getMinimum());

        control.setValue(gain);
    }

    protected abstract float getVolume(AudioSettings audiosettings);

    public boolean hasFinishedPlaying(){
        return !clip.isRunning();
    }

    public void cleanUp(){
        clip.close();
    }
}
