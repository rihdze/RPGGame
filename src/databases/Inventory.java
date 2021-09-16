package databases;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Inventory extends Component {

    //TESTS:
    public static void main(String[] args) throws SQLException {

        // load all Weapon ID numbers into array(playerInventory) from an existing user in database table(userName);
       ArrayList<Integer> loadPlayer = playerInventory("userName1");
        for (int i : loadPlayer) {
            if (i > 1000 && i < 2000) {
                //create objects of type weapons
                loadWeapons(i);
            }
            if (i > 2000 && i < 3000) {
                //create objects of type potions
                loadPotions(i);
            }
        }

       // adds item (ID 2003) to user: userName1
      /*
       addItem(1003, "userName1");
       addItem(1003, "userName1");
       addItem(1003, "userName1");
        addItem(1003, "userName1");

     */
        // Creates new user by username
       // createNewUser("userName2");
      //  removeItem(1003, "userName1");

        saveInventory(loadPlayer, "userName2");



    }



    // call new Weapon from DB by inserting ID number (specified in DB);
    public static Weapons loadWeapons(int id) throws SQLException {
        try {
            if (id > 1000 && id < 2000) {
                String cwd = System.getProperty("user.dir");
                String pathToDb = cwd+"\\src\\databases\\gameDB.db";
                Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
                String sql = "SELECT DISTINCT * FROM weaponsDB WHERE WeaponID_DB = " + id + ";";
                Statement statement = conn.createStatement();
                statement.execute(sql);
                ResultSet result = statement.getResultSet();

                Weapons weapon = new Weapons(result.getString("weaponName_DB"), result.getInt("weaponRange_DB"), result.getInt("weaponSpeed_DB"), result.getInt("weaponDamage"));

                System.out.println("A new weapon named " + result.getString("weaponName_DB") + " has been found.");
                result.close();
                statement.close();
                conn.close();
                return weapon;
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
                String cwd = System.getProperty("user.dir");
                String pathToDb = cwd+"\\src\\databases\\gameDB.db";
                Connection conn3 = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
                String sql = "SELECT * FROM potionsDB WHERE potionID_DB = " + id + ";";
                Statement statement3 = conn3.createStatement();
                statement3.execute(sql);
                ResultSet result = statement3.getResultSet();

                Potions potion = new Potions(result.getString("potionName_DB"), result.getInt("potionBoost_DB"), result.getString("potionType_DB"), result.getInt("potionTime_DB"));

                System.out.println("A new potion named " + result.getString("potionName_DB") + " has been found.");
                result.close();
                statement3.close();
                conn3.close();
                return potion;
            }
        } catch (SQLException e) {
            System.out.println("Potion ID out of bounds");
            return null;
        }
        return null;
    }

    // load all item ID numbers into array(playerInventory) from an existing user in database table(userName);
    public static ArrayList<Integer> playerInventory(String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd + "\\src\\databases\\gameDB.db";
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
        } catch (SQLException e) {
            System.out.println("failed load item ID");
            return null;
        }
    }

    // add new Item to user specific database of items ID's
    public static void addItem(int id, String userName) throws SQLException {
        try {
            String cwd = System.getProperty("user.dir");
            String pathToDb = cwd+"\\src\\databases\\gameDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
            String sql = "INSERT INTO "+userName+" (itemsID) VALUES("+id+");";
            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("A new item ID: "+id+" has been added to "+userName+" database.");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("failed to add item ID");
        }
    }

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




}
