package gfx;

import game.Game;
import core.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationManager {
    private SpriteSet spriteSet;
    private BufferedImage currentAnimationSheet;
    private int updatePerFrame;
    private String currentAnimationName;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;


    public AnimationManager(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
        this.updatePerFrame = 20;
        this.currentFrameTime = 0;
        this.frameIndex = 0;
        this.directionIndex = 0;
        currentAnimationName = "";
        playAnimation("stand");
    }

    public Image getSprite(){
        return currentAnimationSheet.getSubimage(
                frameIndex * Game.SPRITE_SIZE,
                directionIndex * Game.SPRITE_SIZE,
                Game.SPRITE_SIZE,
                Game.SPRITE_SIZE
        );
    }

    public void update(Direction direction){
        currentFrameTime++;
        directionIndex = direction.getAnimationRow();
        if(currentFrameTime >= updatePerFrame){
            currentFrameTime = 0;
            frameIndex++;

            if(frameIndex >= currentAnimationSheet.getWidth() / Game.SPRITE_SIZE ){
                frameIndex = 0;
            }
        }
    }

    public void playAnimation(String name){
        if(!name.equals(currentAnimationName)){
            this.currentAnimationSheet = (BufferedImage)spriteSet.get(name);
            currentAnimationName = name;
            frameIndex = 0;
        }


    }
}
