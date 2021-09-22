package state.game.ui;

import UI.*;
import core.Size;
import state.State;

//This class is for healthbar

public class UIHealth extends HorizontalContainer {

    private UIText numberOfSick;
    private int currentHP;
    private UIText health;
    private UIHealthBar test;
    public UIHealth(Size windowSize) {
        super(windowSize);
        this.health = new UIText("");
        this.test = new UIHealthBar(0,100);

        UIContainer healthContainer = new VerticalContainer(windowSize);
        healthContainer.setPadding(new Spacing(0));
        healthContainer.addUIComponent(this.health);
        healthContainer.addUIComponent(test);



        addUIComponent(healthContainer);

    }

    @Override
    public void update(State state){
        super.update(state);
        this.currentHP = state.getPlayer().getHp();
        this.test.setValue(currentHP);
        this.health.setText("Health: " + String.valueOf(currentHP));


    }


}
