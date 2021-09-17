package state.game.ui;

import UI.Alignment;
import UI.HorizontalContainer;
import UI.UIText;
import core.Size;
import state.State;

public class UIGameTime extends HorizontalContainer {

    private UIText gameTime;


    public UIGameTime(Size windowSize) {
        super(windowSize);
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new UIText("");
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state){
        super.update(state);
        gameTime.setText(state.getTime().getFormattedTime());
    }
}
