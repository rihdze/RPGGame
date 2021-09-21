package game;
import controllers.GameController;
import core.Size;
import display.Display;
import game.settings.GameSettings;
import state.game.GameState;
import state.State;
import input.Input;
import state.menu.MenuState;

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
        settings = new GameSettings(true);
        state = new MenuState(new Size(width, height), input, settings);
        gameController = new GameController(input);

    }

    public void update() throws SQLException {
        state.update(this);
        gameController.update(this);
    }
    //for debugmode toggle.
    public void render() {
        display.render(state, settings.isDebugMode());
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void enterState(State nextState) {
        state.cleanUp();
        state = nextState;
    }
}


