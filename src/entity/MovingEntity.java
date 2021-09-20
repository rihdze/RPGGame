package entity;

import controllers.EntityController;
import core.*;
import entity.Effect.Effect;
import entity.action.Action;
import state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public abstract class MovingEntity extends GameObject{
    protected EntityController entityController;
    protected Movement movement;
    protected AnimationManager animationManager;
    protected Direction direction;
    protected List<Effect> effects;
    protected Optional<Action> action;
    protected boolean isAlive;
    protected Vector2D directionVector;
    protected boolean isAttacking;
    protected Size collisionBoxSize;

    public boolean isAttacking() {
        return isAttacking;
    }

    public MovingEntity(EntityController entityController, SpriteLibrary spriteLibrary) {
        super();
        this.entityController = entityController;
        this.movement = new Movement(2);
        this.animationManager = new AnimationManager(spriteLibrary.getUnit("enemy"));
        this.direction = Direction.S;
        this.directionVector = new Vector2D(0,0);

        this.isAttacking = false;

        effects = new ArrayList<>();
        action = Optional.empty();
        this.collisionBoxSize = new Size(16, 30);
        this.renderOffset = new Position(size.getWidth()/2, size.getHeight()-12);
        this.collisionBoxOffset = new Position(collisionBoxSize.getWidth()/2, collisionBoxSize.getHeight());
    }


    @Override
    public void update(State state) throws SQLException {
        handleAction(state);
        handleMotion();

        animationManager.update(direction);
        effects.forEach(effect -> effect.update(state, this));
        handleCollisions(state);
        manageDirection();
        decideAnimation();

        position.apply(movement);

        cleanup();

    }

    protected  void handleCollisions(State state){
        state.getCollidingGameObjects(this).forEach(this::handleCollision);
    }

    protected abstract void handleCollision(GameObject other);

    private void handleMotion() {

        if(!action.isPresent()){
            movement.update(entityController);
        } else {
            movement.stop();
        }

    }

    private void handleAction(State state) {
        if(action.isPresent()){
            action.get().update(state, this);
        }
    }

    public void cleanup(){
        List.copyOf(effects).stream()
                .filter(Effect::shouldDelete)
                .forEach(effects::remove);

        if(action.isPresent()&& action.get().isDone()){
            action = Optional.empty();
        }
    }

    protected void decideAnimation(){
        if(!isAlive){
            animationManager.playDeathAnimation();
        }
        else if(action.isPresent() ){
            animationManager.playAnimation(action.get().getAnimationName());
        }
         else if(movement.isMoving()){
            animationManager.playAnimation("walk");
        } else {
            animationManager.playAnimation("stand");
        }
    }

    private void manageDirection() {
        if(movement.isMoving()){
            this.direction = Direction.fromMotion(movement);
            this.directionVector = movement.getDirection();
        }
    }

    @Override
    public Image getSprite() {
      return animationManager.getSprite();
    }


    @Override
    public CollisionBox getCollisionBox() {

        Position positionWithMotion = Position.copyOf(getPosition());
        positionWithMotion.apply(movement);
        positionWithMotion.subtract(collisionBoxOffset);
        return new CollisionBox(
                new Rectangle(
                        positionWithMotion.intX(),
                        positionWithMotion.intY(),
                        collisionBoxSize.getWidth(),
                        collisionBoxSize.getHeight()
                )
        );
    }

    public EntityController getController() {
        return entityController;
    }

    public void multiplySpeed(double multiplier) {
        movement.multiply(multiplier);
    }

    public void perform(Action action){
        this.action = Optional.of(action);
    }

//    public void attack(NPC npc){
//        npc.subtractHealth(50);
//    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    protected void clearEffect() {
        effects.clear();
    }
// For sickness follower
    public boolean isAffected(Class<?> clazz) {
        return effects.stream()
                .anyMatch(effect -> clazz.isInstance(effect));
    }
    public EntityController getEntityController() {
        return entityController;
    }

    public boolean isFacing(Position other){
        Vector2D direction = Vector2D.directionBetweenPositions(other, getPosition());
        double dotProduct = Vector2D.dotProduct(direction, directionVector);

        return dotProduct >0;
        //if dotProduct > 0 our target is infront if  0 < then target is behind us.
    }



}
