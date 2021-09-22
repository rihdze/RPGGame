package input.mouse.action;

import UI.UIImage;
import input.mouse.MouseConsumer;
import state.State;

public abstract class MouseAction implements MouseConsumer {

    public abstract void update(State state);
    public abstract UIImage getSprite();

}
