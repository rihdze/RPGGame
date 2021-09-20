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
              //  if (id > 1000 && id < 2000) {
                    String cwd = System.getProperty("user.dir");
                    String pathToDb = cwd+"\\src\\databases\\gameDB.db";
                    Connection conn4 = DriverManager.getConnection("jdbc:sqlite:" + pathToDb);
                    String sql = "SELECT DISTINCT * FROM weaponsDB WHERE WeaponID_DB = " + id + ";";
                    Statement statement4 = conn4.createStatement();
                    statement4.execute(sql);
                    ResultSet result1 = statement4.getResultSet();

                    Weapons weapon = new Weapons(result1.getString("weaponName_DB"), result1.getInt("weaponRange_DB"), result1.getInt("weaponSpeed_DB"), result1.getInt("weaponDamage"));

                  //  System.out.println("A new weapon named " + result.getString("weaponName_DB") + " has been found.");
                    result1.close();
                    statement4.close();
                    conn4.close();
                    return weapon;
                //}
            }
                catch (SQLException e) {
                System.out.println("Weapon ID out of bounds");
                return null;
                }
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






