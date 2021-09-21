package state.menu.ui;

import UI.Alignment;
import UI.UIText;
import UI.VerticalContainer;
import UI.clickable.UIButton;
import core.Size;
import state.editor.EditorState;
import state.game.GameState;
import state.menu.MenuState;

public class UIMainMenu extends VerticalContainer {
    public UIMainMenu(Size windowSize) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        addUIComponent(new UIText("RGA game"));
        addUIComponent(new UIButton("Play", (state) -> state.setNextState(new GameState(windowSize, state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("Options", (state) -> ((MenuState)state).enterMenu(new UIOptionsMenu(windowSize, state.getGameSettings()))));
        addUIComponent(new UIButton("Editor", (state) -> state.setNextState(new EditorState(windowSize, state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("Exit", (state) -> System.exit(0)));
    }
}
