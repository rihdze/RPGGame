package ai.state;

import ai.AITransition;
import entity.NPC;
import state.State;

public class Stand extends AIState{
    private int updatesAlive;
    public static String stateName;

    @Override
    protected AITransition initializeTransition() {
        setStateName("wander");


        if(stateName.equals("wander")){
            return toWander();
        } else if(stateName.equals("lockontarget")){
            return toLockOnTarget();
        } else {
            return toStand();
        }

    }


    public AITransition toLockOnTarget(){
        return new AITransition("lockontarget",(state, currentCharacter) -> updatesAlive >= state.getTime().getUpdatesFromSeconds(1));
    }

    public AITransition toStand(){
        return new AITransition("stand",(state, currentCharacter) -> updatesAlive >= state.getTime().getUpdatesFromSeconds(1));
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
        setStateName("wander");
        updatesAlive++;
    }

}
