package state.editor.ui;

import UI.Alignment;
import UI.UIText;
import UI.VerticalContainer;
import UI.clickable.UIChecbox;
import UI.clickable.UIMinimap;
import core.Size;
import game.settings.RenderSettings;
import map.GameMap;

public class UIRenderSettings extends VerticalContainer {


    public UIRenderSettings(Size windowSize, RenderSettings renderSettings, GameMap gameMap) {
        super(windowSize);
        setAlignment(new Alignment(Alignment.Position.END, Alignment.Position.START));

        addUIComponent(new UIMinimap(gameMap));
        addUIComponent(new UIChecbox("GRID", renderSettings.getShouldRenderGrib()));
    }


}
