package game.settings;

public class GameSettings {

    private boolean debugMode;
    private double gameSpeedMultiplier;
    private AudioSettings audioSettings;




    public GameSettings(boolean debugMode) {
        this.debugMode = debugMode;
        gameSpeedMultiplier = 1;
        audioSettings = new AudioSettings();

    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void increaseGameSpeed(){
        gameSpeedMultiplier += 0.25;
    }

    public void decreaseGameSpeed(){
        gameSpeedMultiplier -= 0.25;
    }

    public double getGameSpeedMultiplier() {
        return gameSpeedMultiplier;
    }

    public void toggleDebugMode() {
        debugMode = !debugMode;
    }

    public AudioSettings getAudioSettings() {
        return audioSettings;
    }


}
