package databases;

import java.sql.*;

public class Weapons {
    String weaponName_DB;
    int weaponRange_DB;
    int weaponSpeed_DB;
   private int weaponDamage_DB;


    public Weapons(String weaponName_DB, int weaponRange_DB, int weaponSpeed_DB, int weaponDamage) {
        this.weaponName_DB = weaponName_DB;
        this.weaponRange_DB = weaponRange_DB;
        this.weaponSpeed_DB = weaponSpeed_DB;
        this.weaponDamage_DB = weaponDamage;
    }

    public static Weapons loadWeapons(int id) {
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
            }
                catch (SQLException e) {
                System.out.println("Weapon ID out of bounds");
                return null;
                }
        return null;
    }


    public String getWeaponName_DB() {
        return weaponName_DB;
    }

    public void setWeaponName_DB(String weaponName_DB) {
        this.weaponName_DB = weaponName_DB;
    }

    public int getWeaponRange_DB() {
        return weaponRange_DB;
    }

    public void setWeaponRange_DB(int weaponRange_DB) {
        this.weaponRange_DB = weaponRange_DB;
    }

    public int getWeaponSpeed_DB() {
        return weaponSpeed_DB;
    }

    public void setWeaponSpeed_DB(int weaponSpeed_DB) {
        this.weaponSpeed_DB = weaponSpeed_DB;
    }

    public int getWeaponDamage_DB() {
        return weaponDamage_DB;
    }

}






