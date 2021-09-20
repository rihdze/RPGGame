package entity;

import controllers.EntityController;
import core.Position;
import databases.Weapons;
import entity.action.Attack;
import game.Game;
import state.State;
import gfx.SpriteLibrary;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;


public class Player extends MovingEntity{
    private String userName;
    private int hp;
    private int damage;
    private boolean attacking;
    private NPC target;


    //MBY I COULD USE THIS FOR NPC'S TO LOCK ON ME WHEN I GO TOO CLOSE TO THEM
    private double targetRange;
    private SelectionCircle selectionCircle;


    public Player(String userName, EntityController entityController, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle){

        super(entityController, spriteLibrary);
        this.attacking = false;
        this.isAlive = true;
        this.userName = userName;
        this.selectionCircle = selectionCircle;
        this.targetRange = Game.SPRITE_SIZE;
        this.hp = 100;

//        Adds caffeinated effect = 2.5 speed for x amount of seconds (visu norada ieksa klase)
//        effects.add(new Caffeinated());

    }


    @Override
    public void update(State state){
        super.update(state);
        handleTarget(state);
        handleInput(state);
        handeWeapons1(state);
        handeWeapons2(state);
    }
    Weapons weapon1 = Weapons.loadWeapons(1002);
    Weapons weapon2 = Weapons.loadWeapons(1001);

    private void handeWeapons1(State state) {

        if(entityController.isRequesting1()){
                damage = weapon1.getWeaponDamage_DB();
             //   target.subtractHealth(damage);
                System.out.println("Weapon switched to " + weapon1.getWeaponName_DB());
            //    state.removeNPC(target);
        }
    }

    private void handeWeapons2(State state) {

        if(entityController.isRequesting2()){
            damage = weapon2.getWeaponDamage_DB();
           // target.subtractHealth(damage);
            System.out.println("Weapon switched to "  + weapon2.getWeaponName_DB());
           // state.removeNPC(target);
        }
    }


    private void handleInput(State state) {



        if(entityController.isRequestingAction()){

//            System.out.println(this.position.getX() + " " + this.position.getY()); test
            if(target != null && target.isAlive()){
                this.attacking = true;
                this.perform(new Attack());
                target.subtractHealth(damage);
                System.out.println("Enemy hp: " + target.getHp());
                this.cleanup();
//                state.removeNPC(target);
            }
        }
    }

    public boolean isAttacking() {
        return attacking;
    }

    public int getHp() {
        return hp;
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
//finds closest npc.
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



    public void attack(NPC npc){
            npc.subtractHealth(damage);

        }
    public Position test(){
        Position asd = new Position(position.getX(), position.getY());
        return asd;
    }
    public void subtractHealth(int damage) {
        this.hp -= damage;
    }


    // GATIS:
    // load all item ID numbers into array(playerInventory) from an existing user in database table(userName);
    public static ArrayList<Integer> playerInventory(String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd + "\\src\\databases\\gameDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
            String sql = "SELECT * FROM " + userName + ";";
            Statement statement = conn.createStatement();
            statement.execute(sql);
            ResultSet IDs = statement.getResultSet();
            ArrayList<Integer> playerInventory = new ArrayList<Integer>();
            while (IDs.next()) {
                playerInventory.add(IDs.getInt("ItemsID"));
            }
            return playerInventory;
        } catch (SQLException e) {
            System.out.println("failed load item ID");
            return null;
        }
    }


    //public void loadPlayerWeapons("userName1") throws SQLException{
    //    Weapons w1001 = Weapons.loadWeapons(1001);
   // }


    //set damage to weapon damage
   // public void equipWeapon(int weaponID) {
   // }



}

