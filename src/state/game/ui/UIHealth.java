package state.game.ui;

import UI.*;
import core.Size;
import state.State;

//This class is for healthbar

public class UIHealth extends HorizontalContainer {


    private int currentHP;
    private int enemyCurrentHP;
    private UIText playerLabel;
    private UIText enemyLabel;
    private UIHealthBar test;
    private UIHealthBar enemyHealthBar;
    public UIHealth(Size windowSize) {
        super(windowSize);

        this.playerLabel = new UIText("");
        this.enemyLabel = new UIText("");
        this.test = new UIHealthBar(0,100);
        this.enemyHealthBar = new UIHealthBar(0, 100);

        UIContainer healthContainer = new VerticalContainer(windowSize);
        healthContainer.setPadding(new Spacing(0));
        healthContainer.addUIComponent(this.playerLabel);
        healthContainer.addUIComponent(test);
        healthContainer.addUIComponent(this.enemyLabel);
        healthContainer.addUIComponent(this.enemyHealthBar);
        setCenterChildren(true);

        addUIComponent(healthContainer);

    }

    @Override
    public void update(State state){
        super.update(state);
        this.currentHP = state.getPlayer().getHp();

        this.test.setValue(currentHP);
        this.playerLabel.setText("Health: " + String.valueOf(currentHP));


             if (state.getPlayer().getTarget() != null && state.getPlayer().getTarget().getHp() < 0){
            this.enemyLabel.setText("Enemy is dead");
            this.enemyHealthBar.setValue(state.getPlayer().getTarget().getHp());
        }

         else if(state.getPlayer().getTarget() != null){
            this.enemyLabel.setText("Enemy hp: " + state.getPlayer().getTarget().getHp());
            this.enemyHealthBar.setValue(state.getPlayer().getTarget().getHp());
        }

        else {

            this.enemyLabel.setText("No target");
            this.enemyCurrentHP = 1;
        }

    }


}
