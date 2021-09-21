package state.editor.ui;

import UI.HorizontalContainer;
import UI.clickable.UIButton;
import core.Size;
import state.menu.MenuState;

public class UIButtonMenu extends HorizontalContainer {
    public UIButtonMenu(Size windowSize) {
        super(windowSize);

        addUIComponent(new UIButton("Main menu", state -> state.setNextState(new MenuState(state.getCamera().getSize(), state.getInput(), state.getGameSettings()))));


    }
}
