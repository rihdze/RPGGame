package state.game.ui;

import UI.Spacing;
import UI.UIComponent;
import UI.clickable.UIClickable;
import core.Size;
import gfx.ImageUtils;
import state.State;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class UIHealthBar extends UIComponent {

    private double value;
    private double min;
    private double max;

    public UIHealthBar(double min, double max) {

        this.min = min;
        this.max = max;
        this.value = max;
        this.size = new Size(100, 10);
        this.margin = new Spacing(0,0,10,0);

    }

    @Override
    public Image getSprite() {

        BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = sprite.createGraphics();
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0,0,size.getWidth(), size.getHeight());

        graphics.setColor(Color.RED);
        graphics.fillRect(0,0,getPixelsOfCurrentValue(), size.getHeight());

        graphics.dispose();
        return sprite;
    }

    @Override
    public void update(State state) throws SQLException {

    }

    public double getValue() {
        return value;
    }

    private int getPixelsOfCurrentValue() {

        double range = max - min;
        double percentage = value - min;

        return (int)((percentage / range) * size.getWidth());
    }


    public void setValue(double value) {
        this.value = value;
    }


}
