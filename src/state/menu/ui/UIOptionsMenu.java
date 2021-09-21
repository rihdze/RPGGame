package state.menu.ui;

import UI.*;
import UI.clickable.UIButton;
import UI.clickable.UISlider;
import core.Size;
import game.settings.GameSettings;
import state.State;
import state.menu.MenuState;

import java.awt.*;

public class UIOptionsMenu extends VerticalContainer {

    private UISlider musicVolume;
    private UIText musicVolLabel;


    private UISlider soundVolume;
    private UIText soundVolLabel;


    public UIOptionsMenu(Size windowSize, GameSettings gameSettings) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        musicVolume = new UISlider(0, 1);
        musicVolume.setValue(gameSettings.getAudioSettings().getMusicVolume());
        musicVolLabel = new UIText("");

        soundVolume = new UISlider(0, 1);
        soundVolume.setValue(gameSettings.getAudioSettings().getSoundVolume());
        soundVolLabel = new UIText("");

        UIContainer labelContainer = new VerticalContainer(windowSize);
        labelContainer.setMargin(new Spacing(0));
        labelContainer.setBackgroundColor(Color.DARK_GRAY);
        labelContainer.addUIComponent(new UIText("OPTIONS"));

        UIContainer contentContainer = new VerticalContainer(windowSize);
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setBackgroundColor(Color.DARK_GRAY);
        contentContainer.setPadding(new Spacing(10));
        contentContainer.addUIComponent(musicVolLabel);
        contentContainer.addUIComponent(musicVolume);
        contentContainer.addUIComponent(soundVolLabel);
        contentContainer.addUIComponent(soundVolume);
        contentContainer.addUIComponent(new UIButton("BACK", (state) -> ((MenuState)state).enterMenu(new UIMainMenu(windowSize))));


        addUIComponent(labelContainer);
        addUIComponent(contentContainer);

    }

    @Override
    public void update(State state){
        super.update(state);
        handleVolume(state);
    }

    private void handleVolume(State state) {
        state.getGameSettings().getAudioSettings().setMusicVolume((float)musicVolume.getValue());
        musicVolLabel.setText(String.format("Music volume: %d", Math.round(musicVolume.getValue()*100)));

        state.getGameSettings().getAudioSettings().setSoundVolume((float)soundVolume.getValue());
        soundVolLabel.setText(String.format("Sound volume: %d", Math.round(soundVolume.getValue()*100)));
    }
}
