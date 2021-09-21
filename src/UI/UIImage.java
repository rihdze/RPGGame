package UI;

import core.Size;
import state.State;

import java.awt.*;
import java.sql.SQLException;

public class UIImage extends UIComponent{

    private Image image;

    public UIImage(Image image) {
        this.image = image;
        size = new Size(image.getWidth(null), image.getHeight(null));
    }

    @Override
    public Image getSprite() {
        return image;
    }

    @Override
    public void update(State state) throws SQLException {}
}
