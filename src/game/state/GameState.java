package game.state;

import UI.*;
import controllers.NPCController;
import controllers.PlayerController;
import entity.Effect.Sick;
import entity.NPC;
import entity.Player;
import game.ui.UIGameTime;
import input.Input;
import map.GameMap;
import core.Size;

import java.awt.*;


public class GameState extends State{

    public GameState(Size windowSize, Input input) {
        super(windowSize, input);

        gameMap = new GameMap(new Size(20, 20), spriteLibrary);
        initializeUI(windowSize);
        initializeCharacters();
    }

    private void initializeUI(Size windowSize) {
        //You can change this to new HorizontalContainer to have horizontal ui container.
//        UIContainer container = new VerticalContainer(windowSize);
//        container.setPadding(new Spacing(20));
//        container.setBackgroundColor(new Color(0,0,0,0)); // Makes the container Transparent
//
//        UIContainer containerEnd = new VerticalContainer(windowSize);
//        containerEnd.setPadding(new Spacing(20));
//        containerEnd.setBackgroundColor(new Color(0,0,0,0)); // Makes the container Transparent
//        containerEnd.setAlignment(new Alignment(Alignment.Position.END, Alignment.Position.START));
//
//        container.addUIComponent(new UIText("HELLO UI WORLD"));
//        containerEnd.addUIComponent(new UIText("BEST GAME EVEEEEEER"));
//        uiContainers.add(container);
        uiContainers.add(new UIGameTime(windowSize));
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
