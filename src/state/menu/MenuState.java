package state.menu;

import UI.UIContainer;
import core.Size;
import game.settings.GameSettings;
import input.Input;
import map.GameMap;
import state.State;
import state.menu.ui.UIMainMenu;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        gameMap = new GameMap(new Size(20, 20), spriteLibrary);
        gameSettings.getRenderSettings().getShouldRenderGrib().setValue(false);

        uiContainers.add(new UIMainMenu(windowSize));

        audioPlayer.playMusic("backgroundmusic.wav");
    }

    public void enterMenu(UIContainer container) {

        uiContainers.clear();
        uiContainers.add(container);

    }
}
