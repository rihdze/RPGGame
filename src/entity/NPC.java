package entity;

import ai.AIManager;
import controllers.EntityController;

import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public class NPC extends MovingEntity{

    private int hp;
    private boolean isAlive;



    private AIManager aiManager;
    public NPC(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);
        this.hp = 100;
        this.isAlive = true;
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


    public void attack(Player player) {

    }

    public void subtractHealth(int points){

        this.hp -= points;


    }

    public boolean isAlive() {

        if(this.hp > 0){
            return isAlive;
        } else {
            return !isAlive;
        }

    }

    public int getHp() {
        return hp;
    }
}
