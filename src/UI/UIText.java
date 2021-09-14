package UI;

import core.Size;
import game.state.State;
import gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIText extends UIComponent{

    private String text;
    private int fontSize;
    private int fontStyle;
    private String fontFamily;
    private Color color;

    private boolean dropShadow;
    private int dropShadowOffset;
    private Color shadowColor;

    private Font font;

    public UIText(String text) {

        this.text = text;
        this.fontSize = 15;
        this.fontStyle = Font.PLAIN;
        this.fontFamily = Font.MONOSPACED;
        this.color = Color.WHITE;

        this.dropShadow = true;
        this.dropShadowOffset = 2;
        this.shadowColor = new Color(140, 140, 140);
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASK);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        if(dropShadow){
            graphics.setColor(shadowColor);
            graphics.drawString(text, padding.getLeft()+dropShadowOffset, fontSize + padding.getTop());
        }
        graphics.setColor(color);
        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop());
        graphics.dispose();

        return image;
    }

    @Override
    public void update(State state) {
        createFont();
        calculateSize();

    }

    private void calculateSize() {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(font);
         int width = fontMetrics.stringWidth(text) + padding.getHorizontal();
         int height = fontMetrics.getHeight() + padding.getVertical();

         if(dropShadow){
             width += dropShadowOffset;
         }
        size = new Size(width,height);
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    public void setText(String text) {
        this.text = text;
    }
}
