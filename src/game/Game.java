package game;
import controllers.GameController;
import core.Size;
import display.Display;
import game.settings.GameSettings;
import game.state.GameState;
import game.state.State;
import input.Input;

import java.sql.SQLException;

public class Game {

    public static int SPRITE_SIZE = 64;

    private Display display;
    private Input input;
    private State state;
    private GameSettings settings;
    private GameController gameController;

    public Game(int width, int height) throws SQLException {
        input = new Input();
        display = new Display(width, height, input);
        state = new GameState(new Size(width, height), input);
        settings = new GameSettings(true);
        gameController = new GameController(input);
    }

    public void update() {
        state.update();
        gameController.update(this);
    }
    //for debugmode toggle.
    public void render() {
        display.render(state, settings.isDebugMode());
    }

    public GameSettings getSettings() {
        return settings;
    }
}


