package controllers;

import input.Input;

import java.awt.event.KeyEvent;

public class PlayerController implements EntityController {
    private Input input;

    public PlayerController(Input input) {
        this.input = input;
    }

    @Override
    public boolean isRequestingUp() {
        return input.isCurrentlyPressed(KeyEvent.VK_UP);
    }

    @Override
    public boolean isRequestingDown() {
        return input.isCurrentlyPressed(KeyEvent.VK_DOWN);
    }

    @Override
    public boolean isRequestingLeft() {
        return input.isCurrentlyPressed(KeyEvent.VK_LEFT);
    }

    @Override
    public boolean isRequestingRight() {
        return input.isCurrentlyPressed(KeyEvent.VK_RIGHT);
    }

    @Override
    public boolean isRequestingAction(){
        return input.isPressed(KeyEvent.VK_SPACE);
    }

    @Override
    public boolean isRequesting1() {return input.isPressed(KeyEvent.VK_1); }

    @Override
    public boolean isRequesting2() { return input.isPressed(KeyEvent.VK_2); }


}
