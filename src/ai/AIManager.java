package ai;

import ai.state.AIState;
import ai.state.LockOnTarget;
import ai.state.Stand;
import ai.state.Wander;
import entity.NPC;
import state.State;

public class AIManager {

    private AIState currentAIState;

    public AIManager() {
        transitionTo("wander");

    }


    public void update(State state, NPC currentCharacter){
        currentAIState.update(state, currentCharacter);

        if(currentAIState.shouldTransition(state, currentCharacter)){
            transitionTo(currentAIState.getNextState());
        }
    }

    private void transitionTo(String nextState) {


//        System.out.println("NEXT STATE FROM TRANSITION TO: " + nextState);
        switch(nextState){
            case"wander":
                currentAIState = new Wander();
                return;
            case "stand":
                currentAIState = new Stand();
                return;
            case "lockontarget":
                currentAIState = new LockOnTarget();
                return;
            default:
                currentAIState = new Wander();
        }
    }


}
