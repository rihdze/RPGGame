package input.mouse.action;

import UI.UIImage;
import core.Position;
import game.Game;
import map.Tile;
import state.State;

import java.sql.SQLException;

public class TilePlacer extends MouseAction{

    private Tile tile;
    private UIImage preview;
    private int gridX;
    private int gridY;

    public TilePlacer(Tile tile) {
        this.tile = tile;
        preview = new UIImage(tile.getSprite());
    }

    @Override
    public void update(State state) {
        Position position = Position.copyOf(state.getInput().getMousePosition());
        position.add(state.getCamera().getPosition());

        gridX = position.intX() / Game.SPRITE_SIZE;
        gridY = position.intY() / Game.SPRITE_SIZE;

        position.subtract(new Position(position.intX() % Game.SPRITE_SIZE, position.intY() % Game.SPRITE_SIZE));
        position.subtract(state.getCamera().getPosition());

        preview.setAbsolutePosition(position);
    }

    @Override
    public void onClick(State state) throws SQLException {}

    @Override
    public void onDrag(State state) {
        if(state.getGameMap().griWithinBounds(gridX, gridY)){
            state.getGameMap().setTile(gridX,gridY, Tile.copyOf(tile));
        }
    }

    @Override
    public UIImage getSprite() {
        return preview;
    }
}
