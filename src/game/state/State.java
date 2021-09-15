package game.state;


import UI.UIContainer;
import display.Camera;
import entity.GameObject;
import entity.NPC;
import entity.Player;
import game.Time;
import gfx.SpriteLibrary;
import input.Input;
import map.GameMap;
import core.Position;
import core.Size;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class State {
    protected List<GameObject> gameObjects;
    protected List<UIContainer> uiContainers;
    protected SpriteLibrary spriteLibrary;
    protected GameMap gameMap;
    protected Input input;
    protected Camera camera;
    protected Time time;
    public Camera getCamera() {
        return camera;
    }

    public State(Size windowSize, Input input) {

        this.input = input;
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
        camera = new Camera(windowSize);
        time = new Time();

    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void update(){
        time.update();

        sortObjectsByPosition();
        updateGameObjects();

        uiContainers.forEach(uiContainer -> uiContainer.update(this));
        camera.update(this);
    }

    protected  void updateGameObjects(){
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).update(this);
        }
    }

    public Time getTime() {
        return time;
    }

    protected void sortObjectsByPosition(){
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(gameObject -> gameObject.getPosition().getY()));
    }


    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

//GET PLAYER
    public Player getPlayer(){

       return getGameObjectsOfClass(Player.class).get(0);
    }

    public Position getRandomPosition(){
        return gameMap.getRandomPosition();
    }

    public List<GameObject> getCollidingGameObjects(GameObject gameObject){
        return gameObjects.stream()
                .filter(other -> other.collidesWith(gameObject))
                .collect(Collectors.toList());
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }


    public <T extends GameObject> List<T> getGameObjectsOfClass(Class<T> clazz){

        return gameObjects.stream()
                .filter(clazz::isInstance)
                .map(gameObject -> (T)gameObject)
                .collect(Collectors.toList());

    }

    public void removeNPC(NPC npc){
            if(!npc.isAlive()){
                gameObjects.remove(npc);
            }

    }


}
