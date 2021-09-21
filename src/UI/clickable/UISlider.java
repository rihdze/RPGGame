package UI.clickable;

import UI.Spacing;
import core.Size;
import gfx.ImageUtils;
import state.State;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class UISlider extends UIClickable{

    private double value;
    private double min;
    private double max;

    public UISlider(double min, double max) {

        this.min = min;
        this.max = max;
        this.value = max;
        this.size = new Size(360, 10);
        this.margin = new Spacing(0,0,10,0);

    }

    @Override
    public Image getSprite() {

        BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = sprite.createGraphics();
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0,0,size.getWidth(), size.getHeight());

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,getPixelsOfCurrentValue(), size.getHeight());

        graphics.dispose();
        return sprite;
    }

    public double getValue() {
        return value;
    }

    private int getPixelsOfCurrentValue() {

        double range = max - min;
        double percentage = value - min;

        return (int)((percentage / range) * size.getWidth());
    }

    @Override
    protected void onFocus(State state) {}

    @Override
    protected void onDrag(State state) {
        this.value = getValueAt(state.getInput().getMousePosition().getX());
    }

    @Override
    protected void onClick(State state){}

    public void setValue(double value) {
        this.value = value;
    }

    private double getValueAt(double xPos) {
        double positionOnSlider = xPos - absolutePosition.getX();
        double percentage = positionOnSlider / size.getWidth();
        double range = max-min;


        return min + range *percentage;
    }
}
