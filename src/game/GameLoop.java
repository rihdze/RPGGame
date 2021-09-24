package game;

import java.sql.SQLException;

public class GameLoop implements Runnable{
    private Game game;
    public static final int UPDATES_PER_SECOND = 60;
    private boolean running;
    private final double updateRate = 1.0d/UPDATES_PER_SECOND;
    private long nextStatTime;

    public GameLoop(Game game){
        this.game = game;
    }

    @Override
    public void run(){

        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(running){
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) /1000d;
            accumulator += lastRenderTimeInSeconds * game.getSettings().getGameSpeedMultiplier();
            lastUpdate = currentTime;

            if(accumulator >= updateRate) {
                while(accumulator>updateRate){
                    try {
                        update();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    accumulator -= updateRate;
                }

            }
            render();
            printStats();
        }


    }

    private void printStats() {
        if(System.currentTimeMillis() > nextStatTime){

            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void update() throws SQLException {
        game.update();

    }

    private void render() {
        game.render();

    }


}
