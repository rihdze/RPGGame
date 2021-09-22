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
/*
    // remove 1 instance of Item ID from user specific database
    public static void removeItem(int id, String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd+"\\src\\databases\\gameDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
            String sql = "DELETE FROM "+userName+" WHERE rowid IN (SELECT MIN(rowid) FROM "+userName+" WHERE itemsID = "+id+");";
            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("item ID: "+id+" has been removed from "+userName+" database.");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("failed to remove item");
        }
    }

/*

    //save game items for a user (username) - MUST DELETE EXISTING DB BEFORE SAVING NEW!
    public static void saveInventory(ArrayList itemsList, String userName) throws SQLException {
        try {
        String cwd = System.getProperty("user.dir");
        String pathToDb = cwd+"\\src\\databases\\gameDB.db";
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
        Statement statement = conn.createStatement();
        for (int i = 0; i < itemsList.size(); i++) {
            String sql = "INSERT INTO "+userName+" (ItemsID) VALUES ("+itemsList.get(i)+");";
            statement.execute(sql); }
        } catch (SQLException e) {
            System.out.println("failed to save items to user's db");
        }
    }


    //delete all items for a user (username) - MUST DELETE EXISTING DB BEFORE SAVING NEW!
    public static void deleteInventory(ArrayList itemsList, String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd + "\\src\\databases\\gameDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
            Statement statement = conn.createStatement();
            String sql = "DELETE FROM " + userName + ";";
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("failed to delete all items from user's db");
        }
    }


    public static void createNewUser(String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd+"\\src\\databases\\gameDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
            String sql = "CREATE TABLE IF NOT EXISTS "+userName+" (itemsID INTEGER DEFAULT 0);";
            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("A new user DB named: "+userName+" has been added to game DB");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("failed to create new user, try different userName");
        }
    }

*/


}
