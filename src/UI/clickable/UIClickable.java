package UI.clickable;

import UI.UIComponent;
import core.Position;
import input.mouse.MouseConsumer;
import state.State;

import java.awt.*;
import java.sql.SQLException;

public abstract class UIClickable extends UIComponent implements MouseConsumer {

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state) throws SQLException {
        Position mousePosition = state.getInput().getMousePosition();
        boolean previousFocus = hasFocus;


        hasFocus = getBounds().contains(mousePosition.intX(), mousePosition.intY());
        isPressed = hasFocus && state.getInput().isMousePressed();

        if(!previousFocus && hasFocus){
            onFocus(state);
        }

        if(hasFocus){
            state.getMouseHandler().setActiveConsumer(this);
        }
    }

    protected abstract void onFocus(State state);


    private Rectangle getBounds(){
        return new Rectangle(
                absolutePosition.intX(),
                absolutePosition.intY(),
                size.getWidth(),
                size.getHeight()
        );
    }
}
