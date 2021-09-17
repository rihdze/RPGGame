package state.menu.ui;

import UI.Alignment;
import UI.UIText;
import UI.VerticalContainer;
import UI.clickable.UIButton;
import UI.clickable.UISlider;
import core.Size;
import state.State;
import state.menu.MenuState;

public class UIOptionsMenu extends VerticalContainer {

    private UISlider musicVolume;
    private UIText musicVolLabel;

    public UIOptionsMenu(Size windowSize) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        musicVolume = new UISlider(0, 1);
        musicVolLabel = new UIText("");
        addUIComponent(new UIText("OPTIONS"));
        addUIComponent(musicVolLabel);
        addUIComponent(musicVolume);
        addUIComponent(new UIButton("BACK", (state) -> ((MenuState)state).enterMenu(new UIMainMenu(windowSize))));

    }

    @Override
    public void update(State state){
        super.update(state);
        handleVolume(state);
    }

    private void handleVolume(State state) {
        state.getGameSettings().getAudioSettings().setMusicVolume((float)musicVolume.getValue());
        musicVolLabel.setText(String.format("Music volume: %d", Math.round(musicVolume.getValue()*100)));
    }
}
