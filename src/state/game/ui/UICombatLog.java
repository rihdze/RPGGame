package state.game.ui;

import UI.*;
import core.Size;
import state.State;

public class UICombatLog extends VerticalContainer {

    private UIText combatLog;
    private String test;

    public UICombatLog(Size windowSize) {
        super(windowSize);

        this.combatLog = new UIText("");
        test = "";

        UIContainer combatLogContainer = new VerticalContainer(windowSize);
        combatLogContainer.setPadding(new Spacing(0));
        combatLogContainer.addUIComponent(new UIText("Combat log"));
        combatLogContainer.addUIComponent(combatLog);



        addUIComponent(combatLogContainer);

    }


    @Override
    public void update(State state){
        super.update(state);







        combatLog.setText(test + state.getPlayer().testForUIText() + " ");
//        numberOfHealthy.setText(String.valueOf(health));
    }

}
