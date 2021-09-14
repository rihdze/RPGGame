package databases;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Inventory extends Component {
    private static String gameWeaponID;
    private static String gamePotionID;

    public static void main(String[] args) throws SQLException {

        // method call examples
        //Inventory user1 = new Inventory();

        // 1008 is weapon ID (1... = weapon, 2... = potion)


        // Inventory userName1stuff = new Inventory();



        // load all Weapon ID numbers into array(playerInventory) from an existing user in database table(userName);
        ArrayList<Integer> loadPlayer = playerInventory("userName1");
        for (int i : loadPlayer) {
            if (i > 1000 && i < 2000) {
                loadWeapons(i);
            }
            if (i > 2000 && i < 3000) {
                loadPotions(i);
            }
        }

    }

    // call new Weapon from DB by inserting ID number (specified in DB);
    public static Weapons loadWeapons(int id) throws SQLException {
        try {
            if (id > 1000 && id < 2000) {
                String pathToDb = "C:\\Users\\igati\\IdeaProjects\\RPGGame\\src\\databases\\gameDB.db";
                Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
                String sql = "SELECT * FROM weaponsDB WHERE WeaponID_DB = " + id + ";";
                Statement statement = conn.createStatement();
                statement.execute(sql);
                ResultSet result = statement.getResultSet();
                gameWeaponID = String.valueOf(id);
                Weapons gameWeaponID = new Weapons(result.getString("weaponName_DB"), result.getInt("weaponRange_DB"), result.getInt("weaponSpeed_DB"), result.getInt("weaponDamage"));
                System.out.println("A new weapon named " + result.getString("weaponName_DB") + " has been found.");
                result.close();
                statement.close();
                conn.close();
                return gameWeaponID;
            }
        } catch (SQLException e) {
            System.out.println("Weapon ID out of bounds");
            return null;
        }
        return null;
    }

    // call new Potions from DB by inserting ID number (specified in DB);
    public static Potions loadPotions(int id) throws SQLException {
        try {
            if (id > 2000 && id < 3000) {
                String pathToDb = "C:\\Users\\igati\\IdeaProjects\\RPGGame\\src\\databases\\gameDB.db";
                Connection conn3 = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
                String sql = "SELECT * FROM potionsDB WHERE potionID_DB = " + id + ";";
                Statement statement3 = conn3.createStatement();
                statement3.execute(sql);
                ResultSet result = statement3.getResultSet();
                gamePotionID = String.valueOf(id);
                Potions gamePotionID = new Potions(result.getString("potionName_DB"), result.getInt("potionBoost_DB"), result.getString("potionType_DB"), result.getInt("potionTime_DB"));
                System.out.println("A new potion named " + result.getString("potionName_DB") + " has been found.");
                result.close();
                statement3.close();
                conn3.close();
                return gamePotionID;
            }
        } catch (SQLException e) {
            System.out.println("Potion ID out of bounds");
            return null;
        }
        return null;
    }

    // load all item ID numbers into array(playerInventory) from an existing user in database table(userName);
    public static ArrayList<Integer> playerInventory(String userName) throws SQLException {
        String pathToDb = "C:\\Users\\igati\\IdeaProjects\\RPGGame\\src\\databases\\gameDB.db";
        Connection conn2 = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
        String sql = "SELECT * FROM " + userName + ";";
        Statement statement2 = conn2.createStatement();
        statement2.execute(sql);
        ResultSet IDs = statement2.getResultSet();
        ArrayList<Integer> playerInventory = new ArrayList<Integer>();
        while (IDs.next()) {
            playerInventory.add(IDs.getInt("ItemsID"));
        }
        return playerInventory;
    }

}
