import game.Game;
import game.GameLoop;

public class Launcher {



    public static void main(String[] args) {
//        Display display = new Display(800, 600  );
        new Thread(new GameLoop(new Game(1280,720))).start();
    }



}
