package game.settings;

import display.Render;

public class GameSettings {

    private boolean debugMode;
    private double gameSpeedMultiplier;
    private AudioSettings audioSettings;
    private final RenderSettings renderSettings;



    public GameSettings(boolean debugMode) {
        this.debugMode = debugMode;
        gameSpeedMultiplier = 1;
        audioSettings = new AudioSettings();
        renderSettings = new RenderSettings();

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

    public RenderSettings getRenderSettings() {
        return renderSettings;
    }


}
