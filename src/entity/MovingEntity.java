package entity;

import controllers.Controller;
import core.*;
import entity.Effect.Effect;
import entity.action.Action;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class MovingEntity extends GameObject{
    protected Controller controller;
    protected Movement movement;
    protected AnimationManager animationManager;
    protected Direction direction;
    protected List<Effect> effects;
    protected Optional<Action> action;

    protected Size collisionBoxSize;

    public MovingEntity(Controller controller, SpriteLibrary spriteLibrary) {
        super();
        this.controller = controller;
        this.movement = new Movement(2);
        this.animationManager = new AnimationManager(spriteLibrary.getUnit("matt"));
        this.direction = Direction.S;
        effects = new ArrayList<>();
        action = Optional.empty();
        this.collisionBoxSize = new Size(16, 30);
    }


    @Override
    public void update(State state){
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
            movement.update(controller);
        } else {
            movement.stop();
        }

    }

    private void handleAction(State state) {
        if(action.isPresent()){
            action.get().update(state, this);
        }
    }

    private void cleanup(){
        List.copyOf(effects).stream()
                .filter(Effect::shouldDelete)
                .forEach(effects::remove);

        if(action.isPresent()&& action.get().isDone()){
            action = Optional.empty();
        }
    }

    protected void decideAnimation(){

        if(action.isPresent() ){
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
        }
    }

    @Override
    public Image getSprite() {
      return animationManager.getSprite();
    }

    @Override
    public boolean collidesWith(GameObject other) {
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }

    @Override
    public CollisionBox getCollisionBox() {

        Position positionWithMotion = Position.copyOf(position);
        positionWithMotion.apply(movement);
        return new CollisionBox(
                new Rectangle(
                        positionWithMotion.intX(),
                        positionWithMotion.intY(),
                        collisionBoxSize.getWidth(),
                        collisionBoxSize.getHeight()
                )
        );
    }

    public Controller getController() {
        return controller;
    }

    public void multiplySpeed(double multiplier) {
        movement.multiply(multiplier);
    }

    public void perform(Action action){
        this.action = Optional.of(action);
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    protected void clearEffect() {
        effects.clear();
    }
}
