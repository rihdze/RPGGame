import entity.Player;
import game.Game;
import game.GameLoop;

import java.sql.SQLException;

public class Launcher {



    public static void main(String[] args) throws SQLException {


        new Thread(new GameLoop(new Game(1900,1000))).start();

    }



}
