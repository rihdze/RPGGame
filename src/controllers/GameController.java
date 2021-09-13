package controllers;

import game.Game;
import game.state.State;
import input.Input;

import java.awt.event.KeyEvent;

public class GameController {

    private Input input;

    public GameController(Input input) {
        this.input = input;
    }
//Turn debugmode on and off with F2
    public void update(Game game){
        if(input.isPressed(KeyEvent.VK_F2)){
            game.getSettings().toggleDebugMode();
        }
        // increase game speed
        if(input.isPressed(KeyEvent.VK_Y)){
            game.getSettings().increaseGameSpeed();
        }
        // decrease game speed
        if(input.isPressed(KeyEvent.VK_U)){
            game.getSettings().decreaseGameSpeed();
        }

    }
}
