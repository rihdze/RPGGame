package ai.state;

import ai.AITransition;
import controllers.NPCController;
import core.Position;
import entity.NPC;
import entity.Player;
import state.State;

import java.util.ArrayList;
import java.util.List;

public class LockOnTarget extends AIState{


    private List<Position> targets;
    private Player player;
    private Position test;

    public static String stateName = "lockontarget";
    public LockOnTarget() {
        super();
        targets = new ArrayList<>();
    }

    @Override
    protected AITransition initializeTransition() {
        System.out.println("INITIALIZE TRANSITION FROM LOCK ON TARGET");

        if(stateName.equals("wander")){
            return toWander();
        } else if(stateName.equals("lockontarget")){
            return toLockOnTarget();
        } else {
            return toStand();
        }
    }


    public AITransition toLockOnTarget(){
        return new AITransition("lockontarget",(state, currentCharacter) -> (currentCharacter.getPosition().isInRangeOf(getCurrentPlayerPosition(state))));
    }

    public AITransition toStand(){
        return new AITransition("stand",(state, currentCharacter) -> (currentCharacter.getPosition().isInRangeOf(getCurrentPlayerPosition(state))));
    }

    public AITransition toWander(){
        return new AITransition("wander",(state, currentCharacter) -> (arrived(currentCharacter)));
    }

    @Override
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


    @Override
    public void update(State state, NPC currentCharacter) {

        if(targets.isEmpty()){


            targets.add(getCurrentPlayerPosition(state));
        }


        NPCController controller = (NPCController) currentCharacter.getController();
        controller.moveToTarget(getCurrentPlayerPosition(state), currentCharacter.getPosition());

        if(currentCharacter.getPosition().isInRangeOf(getCurrentPlayerPosition(state))){
            setStateName("lockontarget");
            initializeTransition();
        }


    }

    private Position getCurrentPlayerPosition(State state){
        double b = state.getPlayer().getPosition().getY();
        double a = state.getPlayer().getPosition().getX();
        Position currentPosition;
        return currentPosition = new Position(a,b);
    }


    private boolean arrived(NPC currentCharacter){
//        System.out.println(currentCharacter.getPosition().isInRangeOf(targets.get(0)));
        return currentCharacter.getPosition().isInRangeOf(targets.get(0));
    }

}
