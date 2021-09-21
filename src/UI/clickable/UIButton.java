package UI.clickable;

import UI.Spacing;
import UI.UIContainer;
import UI.UIText;
import UI.VerticalContainer;
import core.Size;
import state.State;

import java.awt.*;
import java.sql.SQLException;

public class UIButton extends UIClickable{

    private UIContainer container;
    private UIText label;

    private ClickAction clickAction;

    public UIButton(String label, ClickAction clickEvent) {
        this.label = new UIText(label);
        this.clickAction = clickEvent;
        setMargin(new Spacing(5,0,0,0));
        container = new VerticalContainer(new Size(0,0));
        container.setCenterChildren(true);
        container.addUIComponent(this.label);
        this.container.setFixedSize(new Size(120,30));
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        container.update(state);
        size = container.getSize();


        Color color = Color.GRAY;

        if(hasFocus){
            color = Color.LIGHT_GRAY;
        }
        if(isPressed){
            color = Color.DARK_GRAY;
        }

        container.setBackgroundColor(color);
    }

    @Override
    protected void onFocus(State state) {
        state.getAudioPlayer().playSound("button.wav");
    }

    @Override
    protected void onDrag(State state) {

    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    protected void onClick(State state) throws SQLException {
        clickAction.execute(state);
    }
}
