package entity;

import ai.AIManager;
import controllers.EntityController;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public class NPC extends MovingEntity{
    private AIManager aiManager;
    public NPC(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);
        animationManager = new AnimationManager(spriteLibrary.getUnit("dave"));
        aiManager = new AIManager();
    }
    @Override
    public void update(State state){
        super.update(state);
        aiManager.update(state, this);
    }
    //If npc collides with me, they stop ( varetu apvienot ar kkadu npc.attack())
    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof Player){
            movement.stop();
        }
    }
}
