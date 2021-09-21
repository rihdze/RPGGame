package entity;

import ai.AIManager;
import controllers.EntityController;
import core.Movement;
import entity.Effect.Caffeinated;
import entity.action.Attack;
import entity.action.Death;
import game.Game;
import game.Time;
import state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;

public class NPC extends MovingEntity{

    private int hp;
//    private boolean isAlive;
    private int damage;
    private Time timer;
    private Player target;
    private double targetRange;
    private static final double ATTACK_SPEED = 2 ;



    private AIManager aiManager;
    public NPC(EntityController entityController, SpriteLibrary spriteLibrary) {
        super(entityController, spriteLibrary);
        this.targetRange = Game.SPRITE_SIZE;
        timer = new Time();
        this.hp = 100;
        this.damage = 10;
        this.isAlive = true;
        //Change movement speed for monsters.
        movement = new Movement(Math.random() + 1);
        //just to reduce monster speed, have to change this later.
        effects.add(new Caffeinated());
//        animationManager = new AnimationManager(spriteLibrary.getUnit("enemy"));
        aiManager = new AIManager();
    }
    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        aiManager.update(state, this);
        handleTarget(state);
        timer.update();
        if(!isAlive()){
            this.movement.stop();
            this.perform(new Death());
            animationManager.playDeathAnimation();

        }

    }

    public void test(){
        if(!isAlive){
            this.perform(new Death());
        }
    }




    //If npc collides with me, they stop ( varetu apvienot ar kkadu npc.attack())
    @Override
    protected void handleCollision(GameObject other) {


        if(other instanceof Player){
            movement.stop();

            if(timer.getSeconds() > ATTACK_SPEED){

                attack();
                timer.reset();

            }
        }

    }


    public void attack() {


        if(target != null && isAlive()){
            this.isAttacking = true;

            target.subtractHealth(damage);
            this.perform(new Attack());
            System.out.println("NPC just did " + damage +" damage to you and left you with " +target.getHp() +" hp");
            this.cleanup();

//            System.out.println(target.getHp());

        }



    }


    public void subtractHealth(int points){

        this.hp -= points;


    }

    public boolean isAlive() {

        if(this.hp > 0){
            return this.isAlive;
        } else {

            return !this.isAlive;
        }

    }

    private Optional<Player> findClosestPlayer(State state) {

        return state.getGameObjectsOfClass(Player.class).stream()
                .filter(player -> getPosition().distanceTo(player.getPosition()) < targetRange)
                .filter(player -> isFacing(player.getPosition()))
                .min(Comparator.comparingDouble(player -> position.distanceTo(player.getPosition())));

    }

    public int getHp() {
        return hp;
    }


    private void handleTarget(State state) {
        Optional<Player> closestPlayer = findClosestPlayer(state);

        if(closestPlayer.isPresent()){
            Player player1 = closestPlayer.get();
            if(!player1.equals(target)){

                target = player1;
            }
        } else {

            target = null;
        }
    }
}
