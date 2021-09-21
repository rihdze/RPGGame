package display;

import game.Game;
import state.State;
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
                uiContainer.getRelativePosition().intX(),
                uiContainer.getRelativePosition().intY(),
                null
        ));
    }

    private void renderGameObjects(State state, Graphics graphics){
        Camera camera = state.getCamera();
        state.getGameObjects().stream().filter(gameObject -> camera.isInView(gameObject)).forEach(gameObject -> graphics.drawImage(gameObject.getSprite(),
                gameObject.getRenderPosition(camera).intX(),
                gameObject.getRenderPosition(camera).intY(), null));
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


                if(state.getGameSettings().getRenderSettings().getShouldRenderGrib().getValue()){
                    graphics.setColor(Color.GRAY);
                    graphics.drawRect(x * Game.SPRITE_SIZE - camera.getPosition().intX(),
                            y * Game.SPRITE_SIZE - camera.getPosition().intY(), Game.SPRITE_SIZE, Game.SPRITE_SIZE);
                }


            }

        }

    }
}
