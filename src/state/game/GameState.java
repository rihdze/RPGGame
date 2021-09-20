package state.game;


import controllers.NPCController;
import controllers.PlayerController;
import entity.Effect.Sick;
import entity.NPC;
import entity.Player;
import entity.SelectionCircle;
import input.Input;
import map.GameMap;
import core.Size;
import state.State;
import state.game.ui.UIGameTime;
import state.game.ui.UISicknessStatistics;

import java.sql.SQLException;
import java.util.ArrayList;


public class GameState extends State {

    public GameState(Size windowSize, Input input) throws SQLException {
        super(windowSize, input);

        gameMap = new GameMap(new Size(20, 20), spriteLibrary);
        initializeUI(windowSize);
        // have to pass the userName somehow after login:
        initializeCharacters("userName1");

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
        uiContainers.add(new UISicknessStatistics(windowSize));
    }

    private void initializeCharacters(String userName) throws SQLException {
        SelectionCircle circle = new SelectionCircle();
        Player player = new Player(userName, new PlayerController(input), spriteLibrary, circle);

        gameObjects.add(player);
        camera.focusOn(player);
        gameObjects.add(circle);
        initializeNPCs(1);
        makeNumberOfNPCsSick(1);
    }


    private void makeNumberOfNPCsSick(int number) {
        getGameObjectsOfClass(NPC.class)
                .stream().limit(number) // limits the amount of npc affected
                .forEach(npc -> npc.addEffect(new Sick())); // adds effect sick to 10 npcs
    }

    private void initializeNPCs(int numberOfNPCs) {

        for(int i = 0; i < numberOfNPCs; i++){
            NPC npc = new NPC(new NPCController(), spriteLibrary);
            npc.setPosition(gameMap.getRandomPosition());
            //     npc.perform(new Cough()); // Giving direct action
//            npc.addEffect(new Sick());  //adds effect of sick to all of the spawned npc's

            gameObjects.add(npc);
        }

    }

    private void initializeInventory() {}





}
