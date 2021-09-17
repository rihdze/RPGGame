package UI.clickable;

import UI.UIContainer;
import UI.UIText;
import UI.VerticalContainer;
import core.Size;
import state.State;

import java.awt.*;

public class UIButton extends UIClickable{

    private UIContainer container;
    private UIText label;

    private ClickAction clickAction;

    public UIButton(String label, ClickAction clickEvent) {
        this.label = new UIText(label);
        this.clickAction = clickEvent;

        container = new VerticalContainer(new Size(0,0));
        container.addUIComponent(this.label);
        this.container.setFixedSize(new Size(120,30));
    }

    @Override
    public void update(State state){
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
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    protected void onClick(State state) {
        clickAction.execute(state);
    }
}
