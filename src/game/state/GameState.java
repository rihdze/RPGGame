package game.state;

import controllers.NPCController;
import controllers.PlayerController;
import entity.Effect.Sick;
import entity.NPC;
import entity.Player;
import input.Input;
import map.GameMap;
import core.Size;


public class GameState extends State{

    public GameState(Size windowSize, Input input) {
        super(windowSize, input);

        gameMap = new GameMap(new Size(20, 20), spriteLibrary);

        initializeCharacters();
    }

    private void initializeCharacters() {
        Player player = new Player(new PlayerController(input), spriteLibrary);

        gameObjects.add(player);
        camera.focusOn(player);

        initializeNPCs(200);

    }

    private void initializeNPCs(int numberOfNPCs) {

        for(int i = 0; i < numberOfNPCs; i++){
            NPC npc = new NPC(new NPCController(), spriteLibrary);
            npc.setPosition(gameMap.getRandomPosition());
            //     npc.perform(new Cough()); // Giving direct action
            npc.addEffect(new Sick());
            gameObjects.add(npc);

        }



    }
}
