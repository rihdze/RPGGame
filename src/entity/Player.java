package entity;

import controllers.Controller;
import entity.Effect.Caffeinated;
import gfx.SpriteLibrary;


public class Player extends MovingEntity{



    public Player(Controller controller, SpriteLibrary spriteLibrary){
        super(controller, spriteLibrary);
        //Adds caffeinated effect = 2.5 speed for x amount of seconds (visu norada ieksa klase)
//        effects.add(new Caffeinated());

    }
// If npc collides with me, I clear their effects.
    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof NPC){
            NPC npc = (NPC) other;
            npc.clearEffect();
        }
    }


}
