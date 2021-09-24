package ai.state;

import ai.AITransition;
import core.Position;
import entity.NPC;
import state.State;

public class Stand extends AIState{
    private int updatesAlive;
    public static String stateName;

    @Override
    protected AITransition initializeTransition() {
        setStateName("wander");

        if(stateName.equals("lockontarget")){
            return toLockOnTarget();
        } else {
            return toWander();
        }



    }


    public AITransition toLockOnTarget(){
        return new AITransition("lockontarget",(state, currentCharacter) -> (currentCharacter.getPosition().isInRangeOf(getCurrentPlayerPosition(state))));
    }


    public AITransition toWander(){
        return new AITransition("wander", ((state, currentCharacter) -> updatesAlive >= state.getTime().getUpdatesFromSeconds(3)));
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
        if(currentCharacter.getPosition().distanceTo(state.getPlayer().getPosition()) < 100){

            setStateName("lockontarget");
            initializeTransition();



        }
        updatesAlive++;
    }


    private Position getCurrentPlayerPosition(State state){
        double b = state.getPlayer().getPosition().getY();
        double a = state.getPlayer().getPosition().getX();
        Position currentPosition;
        return currentPosition = new Position(a,b);
    }

}
