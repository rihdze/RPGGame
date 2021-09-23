package state.game;



import UI.Alignment;
import UI.UIText;
import UI.VerticalContainer;
import UI.clickable.UIButton;
import controllers.NPCController;
import controllers.PlayerController;
import core.Condition;
import entity.Effect.Sick;
import entity.NPC;
import entity.Player;
import entity.SelectionCircle;
import entity.action.PlayerDeath;
import game.Game;
import game.settings.GameSettings;
import input.Input;
import core.Size;
import io.MapIO;
import state.State;
import state.game.ui.UIEquipment;
import state.game.ui.UIGameTime;
import state.game.ui.UIHealth;
import state.menu.MenuState;

import java.sql.SQLException;
import java.util.List;


public class GameState extends State {

    private List<Condition> victoryConditions;
    private List<Condition> defeatConditions;
    private boolean currentlyPlaying;

    public GameState(Size windowSize, Input input, GameSettings gameSettings) throws SQLException {
        super(windowSize, input, gameSettings);
        currentlyPlaying = true;

        gameMap = MapIO.load(spriteLibrary);

        initializeUI(windowSize);
        // have to pass the userName somehow after login:
        gameSettings.getRenderSettings().getShouldRenderGrib().setValue(false);
        initializeCharacters("userName1");
        initializeConditions();
        audioPlayer.playMusic("backgroundmusic.wav");

    }

    private void initializeConditions() {
        victoryConditions = List.of(()-> aliveNPCs() == 0);
        defeatConditions = List.of(() -> getPlayer().getHp() == 0);
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

//        UIContainer combatLogContainer = new VerticalContainer(windowSize);
//        combatLogContainer.setPadding(new Spacing(10));
//        combatLogContainer.setBackgroundColor(new Color( 255, 255 ,255 ));
//        combatLogContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.END));
//
//        combatLogContainer.addUIComponent(new UICombatLog(windowSize));

        uiContainers.add(new UIGameTime(windowSize));
        uiContainers.add(new UIHealth(windowSize));
        uiContainers.add(new UIEquipment(windowSize));


    }

    private void initializeCharacters(String userName) throws SQLException {
        SelectionCircle circle = new SelectionCircle();
        Player player = new Player(userName, new PlayerController(input), spriteLibrary, circle);


        gameObjects.add(player);
        camera.focusOn(player);
        gameObjects.add(circle);
        initializeNPCs(50);
        makeNumberOfNPCsSick(0);

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

    @Override
    public void update(Game game) throws SQLException {
        super.update(game);

        if(currentlyPlaying){
            if(victoryConditions.stream().allMatch(Condition::isMet)){
                win();
                currentlyPlaying = false;
            }
            if(defeatConditions.stream().allMatch(Condition::isMet)){
                lose();
                currentlyPlaying = false;
            }
        }
    }

    private void lose() {
        VerticalContainer loseContainer = new VerticalContainer(camera.getSize());
        loseContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        loseContainer.setCenterChildren(true);
        loseContainer.addUIComponent(new UIText("DEFEAT"));
        loseContainer.addUIComponent(new UIButton("Main menu", state -> state.setNextState(new MenuState(state.getCamera().getSize(), state.getInput(), state.getGameSettings()))));
        uiContainers.add(loseContainer);

        getPlayer().perform(new PlayerDeath());

    }

    private void win() {
        VerticalContainer winContainer = new VerticalContainer(camera.getSize());
        winContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        winContainer.setCenterChildren(true);
        winContainer.addUIComponent(new UIText("VICTORY"));
        winContainer.addUIComponent(new UIButton("Main menu", state -> state.setNextState(new MenuState(state.getCamera().getSize(), state.getInput(), state.getGameSettings()))));
        uiContainers.add(winContainer);

    }

    private void initializeInventory() {}

    public long aliveNPCs(){
        return getGameObjectsOfClass(NPC.class).stream().filter(npc -> npc.isAlive()==true).count();
    }






}
