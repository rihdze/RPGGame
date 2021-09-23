package ai.state;

import ai.AITransition;
import controllers.NPCController;
import entity.NPC;
import state.State;
import core.Position;

import java.util.ArrayList;
import java.util.List;

public class Wander extends AIState{
    private List<Position> targets;
    public static String stateName = "";

    public Wander() {
        super();

        targets = new ArrayList<>();
    }

    @Override
    protected AITransition initializeTransition() {


   //     System.out.println("INITIALIZE TRANSITION FROM WANDER");
//        if(stateName.equals("wander")){
//            return toWander();
         if(stateName.equals("lockontarget")){
            return toLockOnTarget();
        } else {
            return toStand();
        }

    }

    public AITransition toLockOnTarget(){
       return new AITransition("lockontarget",(state, currentCharacter) -> (currentCharacter.getPosition().isInRangeOf(getCurrentPlayerPosition(state))));
    }

    public AITransition toStand(){
        return new AITransition("stand", ((state, currentCharacter) -> arrived(currentCharacter)));
    }

//    public AITransition toWander(){
//        return new AITransition("wander",(state, currentCharacter) -> arrived(currentCharacter));
////        (currentCharacter.getPosition().isInRangeOf(getCurrentPlayerPosition(state)))
//    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String getStateName() {
        return stateName;
    }


    @Override
    public void update(State state, NPC currentCharacter) {
        if(targets.isEmpty()) {
            targets.add(state.getRandomPosition());
        }

        NPCController controller = (NPCController) currentCharacter.getEntityController();
        controller.moveToTarget(targets.get(0), currentCharacter.getPosition());
        if(inRangeOfPlayer(currentCharacter,state)){

            controller.moveToTarget(state.getPlayer().getPosition(), currentCharacter.getPosition());
        }

        if (isBeingAttacked(currentCharacter)) {
            controller.moveToTarget(state.getPlayer().getPosition(), currentCharacter.getPosition());
        }

        if(arrived(currentCharacter)) {
            controller.stop();
        }
    }

//    @Override
//    public void update(State state, NPC currentCharacter) {
//
//
//        if(targets.isEmpty()){
//            targets.add(state.getRandomPosition());
//        }
//
//
//        NPCController controller = (NPCController) currentCharacter.getController();
//        controller.moveToTarget(targets.get(0), currentCharacter.getPosition());
//        if(currentCharacter.collidesWith(state.getPlayer())){
////            if(currentCharacter.getPosition().distanceTo(state.getPlayer().getPosition()) < 100){
////                currentCharacter.collidesWith(state.getPlayer())
//
//
////            controller.stop();
//                setStateName("lockontarget");
//                initializeTransition();
//
//
//
//
//            }
//        if(arrived(currentCharacter)){
//            System.out.println("I ARRIVED FROM WANDER");
//
//            controller.moveToTarget(targets.get(0), currentCharacter.getPosition());
//            controller.stop();
////            initializeTransition();
//        }

  //      private boolean isBeingAttacked(NPC currentCharacter) {
    //        return moveToTarget(targets.get(0), currentCharacter.getPosition());
    //    }
    private boolean isBeingAttacked(NPC currentCharacter) {
        return currentCharacter.isBeingAttacked;
    }

    private boolean arrived(NPC currentCharacter){

       return currentCharacter.getPosition().isInRangeOf(targets.get(0));
    }

    private boolean inRangeOfPlayer(NPC currentCharacter, State state){

        return currentCharacter.getPosition().isInRangeOfPlayer(getCurrentPlayerPosition(state));
    }

    private Position getCurrentPlayerPosition(State state){
        double b = state.getPlayer().getPosition().getY();
        double a = state.getPlayer().getPosition().getX();
        Position currentPosition;
        return currentPosition = new Position(a,b);
    }
}
