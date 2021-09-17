package state.menu.ui;

import UI.Alignment;
import UI.UIText;
import UI.VerticalContainer;
import UI.clickable.UIButton;
import core.Size;
import state.menu.MenuState;

public class UIOptionsMenu extends VerticalContainer {
    public UIOptionsMenu(Size windowSize) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        addUIComponent(new UIText("OPTIONS"));
        addUIComponent(new UIButton("BACK", (state) -> ((MenuState)state).enterMenu(new UIMainMenu(windowSize))));

    }
}
