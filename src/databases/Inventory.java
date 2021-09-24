package databases;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Inventory extends Component {


    // add new Item to user specific database of items ID's
    public static void addItem(int id, String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd+"\\src\\databases\\gameDB.db";
            Connection conn6 = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
            String sql = "INSERT INTO "+userName+" (itemsID) VALUES ("+id+");";
            Statement statement6 = conn6.createStatement();
            statement6.execute(sql);
            System.out.println("A new item ID: "+id+" has been looted from NPC's body and added to Inventory.");
            statement6.close();
            conn6.close();
        } catch (SQLException e) {
            System.out.println("failed to add item ID");
            System.out.println(" id is: "+id+" user "+ userName);
        }
    }

}
