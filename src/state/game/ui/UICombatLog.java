package state.game.ui;

import UI.*;
import core.Size;
import state.State;

public class UICombatLog extends HorizontalContainer {

    private UIText combatLog;


    public UICombatLog(Size windowSize) {
        super(windowSize);

        this.combatLog = new UIText("");


        UIContainer combatLogContainer = new VerticalContainer(windowSize);
        combatLogContainer.setPadding(new Spacing(0));
        combatLogContainer.addUIComponent(new UIText("Combat log"));
        combatLogContainer.addUIComponent(combatLog);



        addUIComponent(combatLogContainer);

    }


    @Override
    public void update(State state){
        super.update(state);
//        long sickCount = state.getGameObjects().stream()
//                .filter(gameObject -> gameObject instanceof MovingEntity)
//                .map(gameObject -> (MovingEntity) gameObject)
//                .filter(movingEntity -> movingEntity.isAffected(Sick.class))
//                .count();

        long health = state.getPlayer().getHp();
        String combatLogText = state.getPlayer().testForUIText();

        combatLog.setText(combatLogText);
//        numberOfHealthy.setText(String.valueOf(health));
    }

}
