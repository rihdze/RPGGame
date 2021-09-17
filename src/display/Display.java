package display;


import state.State;
import input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends JFrame {

    private Canvas canvas;
    private Render render;
    private DebugRender debugRenderer;

    public Display(int width, int height, Input input){
        setTitle("My Awesome RPG Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        this.render = new Render();
        this.debugRenderer = new DebugRender();

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        add(canvas);
        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(2);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void render(State state, boolean debugMode){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0,canvas.getWidth(), canvas.getHeight());


        render.render(state, graphics);
        if(debugMode){
            debugRenderer.render(state, graphics);
        }


        graphics.dispose();
        bufferStrategy.show();

    }

}
