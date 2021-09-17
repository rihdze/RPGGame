package state.game.ui;

import UI.*;
import core.Size;
import entity.Effect.Sick;
import entity.MovingEntity;
import state.State;

public class UIHealth extends HorizontalContainer {

    private UIText numberOfSick;
    private UIText numberOfHealthy;

    public UIHealth(Size windowSize) {
        super(windowSize);

        this.numberOfHealthy = new UIText("");


        UIContainer healthContainer = new VerticalContainer(windowSize);
        healthContainer.setPadding(new Spacing(0));
        healthContainer.addUIComponent(new UIText("Health"));
        healthContainer.addUIComponent(numberOfHealthy);



        addUIComponent(healthContainer);

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

        numberOfHealthy.setText(String.valueOf(health));
    }


}
