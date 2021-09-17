package state.menu;

import UI.UIContainer;
import core.Size;
import input.Input;
import map.GameMap;
import state.State;
import state.menu.ui.UIMainMenu;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input) {
        super(windowSize, input);
        gameMap = new GameMap(new Size(20, 20), spriteLibrary);

        uiContainers.add(new UIMainMenu(windowSize));

        audioPlayer.playMusic("backgroundmusic.wav");
    }

    public void enterMenu(UIContainer container) {

        uiContainers.clear();
        uiContainers.add(container);

    }
}
