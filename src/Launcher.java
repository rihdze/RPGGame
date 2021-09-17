import entity.Player;
import game.Game;
import game.GameLoop;

import java.sql.SQLException;

public class Launcher {



    public static void main(String[] args) throws SQLException {
//        Display display = new Display(800, 600  );
        new Thread(new GameLoop(new Game(1280,720))).start();

    }



}
