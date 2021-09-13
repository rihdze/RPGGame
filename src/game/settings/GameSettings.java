package game.settings;

public class GameSettings {

    private boolean debugMode;
    private double gameSpeedMultiplier;



    public GameSettings(boolean debugMode) {
        this.debugMode = debugMode;
        gameSpeedMultiplier = 1;
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
}
