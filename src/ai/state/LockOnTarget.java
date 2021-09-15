package ai.state;

import ai.AITransition;
import controllers.NPCController;
import core.Position;
import entity.NPC;
import entity.Player;
import game.state.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LockOnTarget extends AIState{


    private List<Position> targets;
    private Player player;
    private Position test;
    public LockOnTarget() {
        targets = new ArrayList<>();
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("LockOnTarget", (((state, currentCharacter) -> arrived(currentCharacter))));
    }

    @Override
    public void update(State state, NPC currentCharacter) {
//        System.out.println("PLAYER POS : "+getCurrentPlayerPosition(state).getX() +" "+getCurrentPlayerPosition(state).getY() );
//        System.out.println("NPC POS : " +currentCharacter.getPosition().getX() +" " +currentCharacter.getPosition().getY());
        if(targets.isEmpty()){


            targets.add(getCurrentPlayerPosition(state));
        }


        NPCController controller = (NPCController) currentCharacter.getController();
        controller.moveToTarget(getCurrentPlayerPosition(state), currentCharacter.getPosition());

        if(arrived(currentCharacter)){
            currentCharacter.attack();
        }


    }

    private Position getCurrentPlayerPosition(State state){
        double b = state.getPlayer().getPosition().getY();
        double a = state.getPlayer().getPosition().getX();
        Position currentPosition;
        return currentPosition = new Position(a,b);
    }


    private boolean arrived(NPC currentCharacter){
        return currentCharacter.getPosition().isInRangeOf(targets.get(0));
    }

}
