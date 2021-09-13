package display;

import game.Game;
import game.state.State;
import map.GameMap;
import map.Tile;
import core.Position;

import java.awt.*;

public class Render {

    public void render(State state, Graphics graphics){

        renderMap(state, graphics);
        renderGameObjects(state, graphics);
        renderUI(state, graphics);

    }

    private void renderUI(State state, Graphics graphics) {
        state.getUiContainers().forEach(uiContainer -> graphics.drawImage(
                uiContainer.getSprite(),
                uiContainer.getPosition().intX(),
                uiContainer.getPosition().intY(),
                null
        ));
    }

    private void renderGameObjects(State state, Graphics graphics){
        Camera camera = state.getCamera();
        state.getGameObjects().stream().filter(gameObject -> camera.isInView(gameObject)).forEach(gameObject -> graphics.drawImage(gameObject.getSprite(),
                gameObject.getPosition().intX() - camera.getPosition().intX() - gameObject.getSize().getWidth()/2,
                gameObject.getPosition().intY() - camera.getPosition().intY() - gameObject.getSize().getHeight() / 2, null));
    }

    private void renderMap(State state, Graphics graphics) {
        GameMap map = state.getGameMap();
        Tile[][] tiles = state.getGameMap().getTiles();
        Camera camera = state.getCamera();
        Position start = map.getViewableStartingGridPosition(camera);
        Position end = map.getViewableEndingGridPosition(camera);
        for(int x = start.intX(); x < end.intX(); x++){
            for(int y = start.intY() ; y < end.intY(); y++){
                graphics.drawImage(
                        map.getTiles()[x][y].getSprite(),
                        x * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        y * Game.SPRITE_SIZE - camera.getPosition().intY(), null);
            }
        }
    }
}
