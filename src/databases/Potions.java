package databases;

import java.sql.*;

public class Potions {
    private int potionID_DB;
    private String potionName_DB;
    private int potionBoost_DB;
    private String potionType_DB;
    private int potionTime_DB;


    public Potions(int potionID_DB, String potionName_DB, int potionBoost_DB, String potionType_DB, int potionTime_DB) {
        this.potionID_DB = potionID_DB;
        this.potionName_DB = potionName_DB;
        this.potionBoost_DB = potionBoost_DB;
        this.potionType_DB = potionType_DB;
        this.potionTime_DB = potionTime_DB;
    }


    public String getPotionName_DB() { return potionName_DB; }

    public int getPotionBoost_DB() { return potionBoost_DB; }

    public String getPotionType_DB() { return potionType_DB; }

    public int getPotionTime_DB() { return potionTime_DB; }

    public int getPotionID_DB() { return potionID_DB; }

    public static Potions loadPotions(int id) {
        try {

                String cwd = System.getProperty("user.dir");
                String pathToDb = cwd+"\\src\\databases\\gameDB.db";
                Connection conn3 = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
                String sql = "SELECT * FROM potionsDB WHERE PotionID_DB = " + id + ";";
                Statement statement3 = conn3.createStatement();
                statement3.execute(sql);
                ResultSet result3 = statement3.getResultSet();

                Potions potion = new Potions(result3.getInt("potionID_DB"), result3.getString("potionName_DB"), result3.getInt("potionBoost_DB"), result3.getString("potionType_DB"), result3.getInt("potionTime_DB"));

                result3.close();
                statement3.close();
                conn3.close();
                return potion;

        }
        catch (SQLException e) {
            System.out.println("Potion ID out of bounds");
            return null;
        }
    }



    public static void removePotions(String userName, int id) throws SQLException {
        String cwd = System.getProperty("user.dir");
        String pathToDb = cwd+"\\src\\databases\\gameDB.db";
        Connection conn5 = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
        String sql = "DELETE FROM "+userName+" WHERE rowid = (SELECT rowid FROM "+userName+" WHERE ItemsID = "+id+")";
        Statement statement5 = conn5.createStatement();
        statement5.execute(sql);
        statement5.close();
        conn5.close();
      }

}






