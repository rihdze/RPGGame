package UI;

import core.Position;
import core.Size;
import state.State;
import gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class UIContainer extends UIComponent{

    protected boolean centerChildren;
    protected Color backgroundColor;
    protected List<UIComponent> children;

    protected Alignment alignment;
    protected Size windowSize;
    protected Size fixedSize;

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public UIContainer(Size windowSize) {
        super();
        this.windowSize = windowSize;
        centerChildren = false;
        alignment = new Alignment(Alignment.Position.START, Alignment.Position.START);
        children = new ArrayList<>();
        backgroundColor = new Color(0,0,0,0);
        margin = new Spacing(5);
        padding = new Spacing(5);
        calculateSize();
        calculatePosition();
    }

    protected abstract Size calculateContentSize();
    protected abstract void calculateContentPosition();

    public void setFixedSize(Size fixedSize) {
        this.fixedSize = fixedSize;
    }

    private void calculateSize(){
        Size calculateContentSize = calculateContentSize();
        //If fixed size isn't null, the we prioritize fixed size, if it's null then we use calculated size;
        size = fixedSize != null
        ? fixedSize
        :new Size(padding.getHorizontal() + calculateContentSize.getWidth(),
                        padding.getVertical() + calculateContentSize.getHeight());

    }

    private void calculatePosition(){

        int x = padding.getLeft();
        if(alignment.getHorizontal().equals(Alignment.Position.CENTER)){
            x = windowSize.getWidth() / 2 - size.getWidth() /2;
        }
        if(alignment.getHorizontal().equals(Alignment.Position.END)){
            x = windowSize.getWidth() - size.getWidth() - margin.getRight();
        }

        int y = padding.getTop();
        if(alignment.getVertical().equals(Alignment.Position.CENTER)){
            y = windowSize.getHeight() / 2 - size.getHeight() /2;
        }
        if(alignment.getVertical().equals(Alignment.Position.END)){
            y = windowSize.getHeight() - size.getHeight() - margin.getBottom();
        }
//        position = new Position(margin.getLeft(), margin.getTop());
        this.relativePosition = new Position(x, y);
        if (parent == null) {

            this.absolutePosition = new Position(x,y);

        }
        calculateContentPosition();
    }

    public void setCenterChildren(boolean centerChildren) {
        this.centerChildren = centerChildren;
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASK);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0,0,size.getWidth(), size.getHeight());

        for(UIComponent uiComponent : children){
            graphics.drawImage(
                    uiComponent.getSprite(),
                    uiComponent.getRelativePosition().intX(),
                    uiComponent.getRelativePosition().intY(),
                    null
            );
        }

        graphics.dispose();
        return image;
    }

    @Override
    public void update(State state) {
        children.forEach(component -> {
            try {
                component.update(state);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        calculateSize();
        calculatePosition();
    }

    public void addUIComponent(UIComponent uiComponent){
        children.add(uiComponent);
        uiComponent.setParent(this);

    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }
}
