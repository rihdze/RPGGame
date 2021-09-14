package entity;

import core.CollisionBox;
import core.Position;
import core.Size;
import game.state.State;
import gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SelectionCircle extends GameObject{

    private Color color;
    private BufferedImage sprite;

    public SelectionCircle() {
        this.color = Color.ORANGE;
        size = new Size(20,16);
        renderOffset = new Position(size.getWidth()/2, size.getHeight());
        collisionBoxOffset = renderOffset;
        renderOrder = 4;
        initializeSprites();

    }

    private void initializeSprites() {
        sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASK);
        Graphics2D graphics = sprite.createGraphics();
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(2));
        graphics.drawOval(0,0,  size.getWidth(), size.getHeight());
        graphics.dispose();
    }

    @Override
    public void update(State state) {}

    @Override
    public CollisionBox getCollisionBox() {
        Position position = getPosition();
        position.subtract(collisionBoxOffset);

        return CollisionBox.of(
                position,getSize());
    }



    @Override
    public Image getSprite() {


        return parent != null ? sprite : null;
    }
}
