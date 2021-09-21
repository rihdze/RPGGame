package audio;

import game.settings.AudioSettings;
import game.settings.GameSettings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AudioPlayer {
    private AudioSettings audioSettings;
    private List<AudioClip> audioClips;

    public AudioPlayer(AudioSettings audioSettings) {
        this.audioSettings = audioSettings;
        this.audioClips = new ArrayList<>();
    }

    public void update(){
        audioClips.forEach(audioClip -> audioClip.update(audioSettings));

        List.copyOf(audioClips).forEach(audioClip -> {
            if(audioClip.hasFinishedPlaying()){
                audioClip.cleanUp();
                audioClips.remove(audioClip);
            }
        });

}



    public void playMusic(String fileName){
        final Clip clip = getClip(fileName);
        final MusicClip musicClip = new MusicClip(clip);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        musicClip.setVolume(audioSettings);
        audioClips.add(new MusicClip(clip));
    }
    public void playSound(String fileName){
        final Clip clip = getClip(fileName);
        SoundClip soundClip = new SoundClip(clip);
        soundClip.setVolume(audioSettings);
        audioClips.add(soundClip);

    }




    private Clip getClip(String fileName){
        final URL soundFile = AudioPlayer.class.getResource("/sounds/" + fileName);

        try(AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile)) {
            final Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(0);
            return clip;
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);;
        } catch (LineUnavailableException e) {
            System.out.println(e);
        }

        return null;
    }

    public void clear(){
        audioClips.forEach(AudioClip::cleanUp);
        audioClips.clear();
    }

}
