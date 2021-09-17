package entity.action;

import core.CollisionBox;
import core.Position;
import core.Size;
import entity.Effect.Sick;
import entity.MovingEntity;
import game.Game;
import game.GameLoop;
import state.State;
//Varetu lietot kaa low health rādītāju ??
public class Cough extends Action {

    private int lifeSpanInSeconds;
    private Size spreadAreaSize;
    private double risk;

    public Cough() {
        lifeSpanInSeconds = GameLoop.UPDATES_PER_SECOND;
        spreadAreaSize = new Size(2* Game.SPRITE_SIZE, 2* Game.SPRITE_SIZE); //affects 2x64 px area
        risk = 0.1;
    }

    @Override
    public void update(State state, MovingEntity entity) {
//        lifeSpanInSeconds--; // every update

        if(--lifeSpanInSeconds <= 0){
            Position spreadAreaPosition = new Position(
                    entity.getPosition().getX() - spreadAreaSize.getWidth()/2,
                    entity.getPosition().getY() - spreadAreaSize.getHeight()/2
            );

            CollisionBox spreadArea = CollisionBox.of(spreadAreaPosition, spreadAreaSize);
            //If healthy npc collides with npc that is coughing, there is a 10% chance he will get sick aswell.
            state.getGameObjectsOfClass(MovingEntity.class).stream()
                    .filter(movingEntity -> movingEntity.getCollisionBox().collidesWith(spreadArea))
                    .filter(movingEntity -> !movingEntity.isAffected(Sick.class)) //Filter the ones who are not sick, so you can't get multiple stacks of sick
                    .forEach(movingEntity -> {
                        if(Math.random() < risk){
                            movingEntity.addEffect(new Sick());
                        }
                    });
        }

    }

    @Override
    public boolean isDone() {
        return lifeSpanInSeconds <= 0;
    }

    @Override
    public String getAnimationName() {
        return "cough";
    }
}
