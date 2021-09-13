package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    private boolean[] currentlyPressed;
    private boolean[] pressed;

    public Input(){
        pressed = new boolean[255];
        currentlyPressed = new boolean[255];
    }

    public boolean isPressed(int keyCode){
        if(!pressed[keyCode] && currentlyPressed[keyCode]){
            pressed[keyCode] = true;
            return true;
        }

        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isCurrentlyPressed(int keyCode){
        return currentlyPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = false;
        pressed[e.getKeyCode()] = false;
    }
}
