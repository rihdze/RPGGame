package entity;

import controllers.EntityController;
import controllers.PlayerController;
import core.Position;
import game.Game;
import game.state.State;
import gfx.SpriteLibrary;

import java.util.Comparator;
import java.util.Optional;


public class Player extends MovingEntity{

    private int hp;
    private int damage = 5;
    private NPC target;
    private double targetRange;
    private SelectionCircle selectionCircle;

    public Player(EntityController entityController, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle){

        super(entityController, spriteLibrary);
        this.selectionCircle = selectionCircle;
        this.targetRange = Game.SPRITE_SIZE;

        //Adds caffeinated effect = 2.5 speed for x amount of seconds (visu norada ieksa klase)
//        effects.add(new Caffeinated());

    }
    @Override
    public void update(State state){
        super.update(state);
        handleTarget(state);
        handleInput(state);
    }

    private void handleInput(State state) {
        if(entityController.isRequestingAction()){
            if(target != null){

                target.subtractHealth(50);
                System.out.println(target.getHp());
                System.out.println("TESTTESTESTTESTSETSET");
                state.removeNPC(target);
            }
        }
    }



    private void handleTarget(State state) {
        Optional<NPC> closestNPC = findClosestNPC(state);

        if(closestNPC.isPresent()){
            NPC npc = closestNPC.get();
            if(!npc.equals(target)){
                selectionCircle.setParent(npc);
                target = npc;
            }
        } else {
            selectionCircle.clearParent();
            target = null;
        }
    }

    private Optional<NPC> findClosestNPC(State state) {

        return state.getGameObjectsOfClass(NPC.class).stream()
                .filter(npc -> getPosition().distanceTo(npc.getPosition()) < targetRange)
                .filter(npc -> isFacing(npc.getPosition()))
                .min(Comparator.comparingDouble(npc -> position.distanceTo(npc.getPosition())));

    }


    // If npc collides with me, I clear their effects.
    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof NPC){
            NPC npc = (NPC) other;
            npc.clearEffect();
        }
    }


//
//    public void subtractHealth(int points){
//
//
//
//    }

    public void attack(NPC npc){
            npc.subtractHealth(damage);

        }

    }

