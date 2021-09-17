package ai.state;

import ai.AITransition;
import entity.NPC;
import state.State;

public abstract class AIState {

    private AITransition transition;
    public static String stateName;

    public AIState() {
        this.transition = initializeTransition();
    }


    protected abstract AITransition initializeTransition();

    protected abstract String getStateName();

    public void setStateName(String stateName){
        this.stateName = stateName;
    }

    public abstract void update(State state, NPC currentCharacter);

    public boolean shouldTransition(State state, NPC currentCharacter){

        return transition.shouldTransition(state, currentCharacter);

    }

    public String getNextState(){
        return transition.getNextState();
    }
}
