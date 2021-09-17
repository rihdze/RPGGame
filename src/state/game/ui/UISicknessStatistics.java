package state.game.ui;

import UI.*;
import core.Size;
import entity.Effect.Sick;
import entity.MovingEntity;
import state.State;

public class UISicknessStatistics extends HorizontalContainer {

    private UIText numberOfSick;
    private UIText numberOfHealthy;

    public UISicknessStatistics(Size windowSize) {
        super(windowSize);
        this.numberOfSick = new UIText("");
        this.numberOfHealthy = new UIText("");
//Create containers to follow sick/healthy persons
        UIContainer sickContainer = new VerticalContainer(windowSize);
        sickContainer.setPadding(new Spacing(0));
        sickContainer.addUIComponent(new UIText("SICK"));
        sickContainer.addUIComponent(numberOfSick);

        UIContainer healthyContainer = new VerticalContainer(windowSize);
        healthyContainer.setPadding(new Spacing(0));
        healthyContainer.addUIComponent(new UIText("HEALTHY"));
        healthyContainer.addUIComponent(numberOfHealthy);

        addUIComponent(healthyContainer);
        addUIComponent(sickContainer);
    }

    @Override
    public void update(State state){
        super.update(state);
        long sickCount = state.getGameObjects().stream()
                .filter(gameObject -> gameObject instanceof MovingEntity)
                .map(gameObject -> (MovingEntity) gameObject)
                .filter(movingEntity -> movingEntity.isAffected(Sick.class))
                .count();

        long healthyCount = state.getGameObjects().stream()
                .filter(gameObject -> gameObject instanceof MovingEntity)
                .map(gameObject -> (MovingEntity) gameObject)
                .filter(movingEntity -> !movingEntity.isAffected(Sick.class))
                .count();

        numberOfSick.setText(String.valueOf(sickCount));
        numberOfHealthy.setText(String.valueOf(healthyCount));
    }


}
