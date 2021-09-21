package state.editor.ui;

import UI.HorizontalContainer;
import UI.clickable.UIButton;
import core.Size;
import io.MapIO;
import state.menu.MenuState;

public class UIButtonMenu extends HorizontalContainer {
    public UIButtonMenu(Size windowSize) {
        super(windowSize);

        addUIComponent(new UIButton("Main menu", state -> state.setNextState(new MenuState(state.getCamera().getSize(), state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("Save", state -> MapIO.save(state.getGameMap())));
        addUIComponent(new UIButton("Load", state -> state.loadGameMap()));


    }
}
