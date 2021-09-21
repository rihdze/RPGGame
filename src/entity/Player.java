package entity;

import controllers.EntityController;
import core.Movement;
import core.Position;
import core.Vector2D;
import databases.Inventory;
import databases.Potions;
import databases.Weapons;
import entity.action.Attack;
import entity.action.WalkInDirection;
import game.Game;
import state.State;
import gfx.SpriteLibrary;
import core.Movement;
import java.sql.*;
import java.util.*;


public class Player extends MovingEntity{
    private String userName;
    private int hp;
    private int damage;
    private int damageBoost = 0;
    private boolean attacking;
    int nexti = 0;
    int nextj = 0;
    Potions potion;
    Weapons weapon;
;
    private NPC target;


    //MBY I COULD USE THIS FOR NPC'S TO LOCK ON ME WHEN I GO TOO CLOSE TO THEM
    private double targetRange;
    private SelectionCircle selectionCircle;


    public Player(String userName, EntityController entityController, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle) throws SQLException {

        super(entityController, spriteLibrary);
        this.attacking = false;
        this.isAlive = true;
        this.userName = userName;
        this.selectionCircle = selectionCircle;
        this.targetRange = Game.SPRITE_SIZE;
        this.hp = 100;
        //THIS IS FOR WALKING INTO THE MAP AT THE START OF THE GAME
        setPosition(new Position(Game.SPRITE_SIZE * 5, 0));
        perform(new WalkInDirection(new Vector2D(0, 1)));

//        Adds caffeinated effect = 2.5 speed for x amount of seconds (visu norada ieksa klase)
//        effects.add(new Caffeinated());

    }


    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        handleTarget(state);
        handleInput(state);
        handleWeapons1(state);
        handleWeapons2(state);
        handlePotions1(state);
        handlePotions2(state);
        handleUsePotion(state);

    }


    // switch next weapon from "userName" DB with button "2"
    private int handleWeapons2(State state) throws SQLException {
        if (nexti < playerWeapons("userName1").size()) {
            if (entityController.isRequesting2()) {
                weapon = Weapons.loadWeapons(playerWeapons("userName1").get(nexti));
                this.nexti++;
                damage = weapon.getWeaponDamage_DB();
                System.out.println("Weapon switched to " + weapon.getWeaponName_DB());
                return this.nexti;
            } else {
                return this.nexti;
            }
        } else {return this.nexti;}
    }

    // switch previous weapon from "userName" DB with button "1"
    private int handleWeapons1(State state) throws SQLException {
        if (nexti > 1) {
            if (entityController.isRequesting1()) {
                --nexti;
                weapon = Weapons.loadWeapons(playerWeapons("userName1").get(nexti-1));
                damage = weapon.getWeaponDamage_DB();
                System.out.println("Weapon switched to " + weapon.getWeaponName_DB());
                return this.nexti;
            } else {
                return this.nexti;
            }
        } else {return this.nexti;}
    }

    // load all weapon item ID numbers into array(playerWeapons) from an existing user in database;
    public static ArrayList<Integer> playerWeapons(String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd + "\\src\\databases\\gameDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
            String sql = "SELECT DISTINCT ItemsID FROM " + userName + ";";
            Statement statement = conn.createStatement();
            statement.execute(sql);
            ResultSet IDs = statement.getResultSet();
            ArrayList<Integer> playerWeapons = new ArrayList<Integer>();
            while (IDs.next()) {
                if (IDs.getInt("ItemsID") > 1000 && IDs.getInt("ItemsID") < 2000) {
                    playerWeapons.add(IDs.getInt("ItemsID"));
                }
            }
            IDs.close();
            statement.close();
            conn.close();
            return playerWeapons;
        } catch (SQLException e) {
            System.out.println("failed to load item ID - weapons");
            return null;
        }
    }

    // select next potion from "userName" DB
    private int handlePotions2(State state) throws SQLException {
        if (nextj < playerPotions("userName1").size()) {
            if (entityController.isRequestingW()) {
                potion = Potions.loadPotions(playerPotions("userName1").get(nextj));
                this.nextj = nextj + 1;
                if (nextj > playerPotions("userName1").size()) {
                    this.nextj = nextj - 1;
                }
                System.out.println("Potion switched to " + potion.getPotionName_DB());
                return this.nextj;
            } else {
                return this.nextj;
            }
        } else {return this.nextj;}
    }

    // equip previous potion from "userName" DB
    private int handlePotions1(State state) throws SQLException {
        if (nextj > 1) {
            if (entityController.isRequestingQ()) {
                nextj -= 1;
                potion = Potions.loadPotions(playerPotions("userName1").get(nextj-1));
                    if (nextj > playerPotions("userName1").size()) { nextj -= 1; }
                System.out.println("Potion switched to " + potion.getPotionName_DB());
                return this.nextj;
            } else {
                return this.nextj;
            }
        } else {return this.nextj;}
    }

    private void handleUsePotion(State state) throws SQLException {
        try{
            if (entityController.isRequestingE()){
                if (playerPotions("userName1").size() > 0) {
                    switch (potion.getPotionType_DB()) {
                        case ("\"Health\""):
                            hp += potion.getPotionBoost_DB();
                            System.out.println("you have restored health");
                            Potions.removePotions("userName1", potion.getPotionID_DB());
                            if (playerPotions("userName1").size() > 0) {
                                potion = Potions.loadPotions(playerPotions("userName1").get(playerPotions("userName1").size()-1));
                                System.out.println("Potion switched to " + potion.getPotionName_DB());

                            } nextj = 0; break;
                        case ("\"Damage\""):
                            damageBoost = potion.getPotionBoost_DB();
                            System.out.println("you have increased your damage output");
                            Potions.removePotions("userName1", potion.getPotionID_DB());
                            Timer damageBoostTime = new Timer();
                            damageBoostTime.schedule(new TimerTask() {
                                @Override
                                public void run() {damageBoost = 0; System.out.println("damage boost ended");}}, 5000);
                            if (playerPotions("userName1").size() > 0) {
                                potion = Potions.loadPotions(playerPotions("userName1").get(playerPotions("userName1").size()-1));
                                System.out.println("Potion switched to " + potion.getPotionName_DB());
                            } nextj = 0; break;
                        case ("\"Speed\""):
                           Movement speedBoost = new Movement(20);
                            System.out.println("you have increased your speed");
                            Potions.removePotions("userName1", potion.getPotionID_DB());
                            if (playerPotions("userName1").size() > 0) {
                                potion = Potions.loadPotions(playerPotions("userName1").get(playerPotions("userName1").size()-1));
                                System.out.println("Potion switched to " + potion.getPotionName_DB());
                            } nextj = 0; break;
                    }
                } else {System.out.println("no potions left");}
            }
        } catch(NullPointerException e) {System.out.println("no potion selected: Null pointer exception");}
    }

    public static ArrayList<Integer> playerPotions(String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd + "\\src\\databases\\gameDB.db";
            Connection conn2 = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
            String sql = "SELECT ItemsID FROM " + userName + ";";
            Statement statement2 = conn2.createStatement();
            statement2.execute(sql);
            ResultSet IDs2 = statement2.getResultSet();
            ArrayList<Integer> playerPotions = new ArrayList<Integer>();
            while (IDs2.next()) {
                 if (IDs2.getInt("ItemsID") > 2000 && IDs2.getInt("ItemsID") < 3000) {
                    playerPotions.add(IDs2.getInt("ItemsID"));
                }
            }
            IDs2.close();
            statement2.close();
            conn2.close();
            return playerPotions;
            } catch (SQLException e) {
            System.out.println("failed to load item ID - potions");
            return null;
        }
    }


    private void handleInput(State state) throws SQLException {

        if(entityController.isRequestingAction()){

//            System.out.println(this.position.getX() + " " + this.position.getY()); test
            if(target != null && target.isAlive()){
                this.attacking = true;
                this.perform(new Attack());
                target.subtractHealth(damage + damageBoost);
                System.out.println("Enemy hp: " + target.getHp());
                this.cleanup();
//                state.removeNPC(target);
            }

            if(target != null && !target.isAlive()) {
                Random rWeapon = new Random();
                int minW = 1001;
                int maxW = 1008;
                int w = (int)(Math.random()*(maxW-minW+1)+minW);
                Random rPotion = new Random();
                int minP = 2001;
                int maxP = 2005;
                int p = (int)(Math.random()*(maxP-minP+1)+minP);
                Random rPotionWeaponOrNothing = new Random();
                int min = 1;
                int max = 3;
                int rLoot = (int)(Math.random()*(max-min+1)+min);
                if (rLoot == 1) {
                Inventory.addItem(w, "userName1");
                    state.removeNPC(target);
                }
                if (rLoot == 2) {
                Inventory.addItem(p, "userName1");
                    state.removeNPC(target);
                }
                if (rLoot == 3) {
                System.out.println(" You searched the NPC's body, but found nothing ... ");
                    state.removeNPC(target);
                }

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


}

