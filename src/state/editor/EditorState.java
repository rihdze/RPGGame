package state.editor;

import UI.clickable.UIButton;
import core.Size;
import game.settings.GameSettings;
import input.Input;
import map.GameMap;
import state.State;
import state.editor.ui.UIButtonMenu;

public class EditorState extends State {
    public EditorState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        gameMap = new GameMap(new Size(20, 20), spriteLibrary);
        uiContainers.add(new UIButtonMenu(windowSize));
    }


}
