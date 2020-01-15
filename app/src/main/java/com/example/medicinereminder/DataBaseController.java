package com.example.medicinereminder;

import android.os.Handler;
import android.os.StrictMode;
import android.widget.Adapter;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DataBaseController {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://195.150.230.210:5434/2019_cichon_przemyslaw";
    static final String USER = "2019_cichon_przemyslaw";
    static final String PASS = "przemcio687";


    public static String login= null;

    public void setName(String string){
        login = string;
    }
    public static Boolean checkUserExists(String login) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Boolean userExists = false;

        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select login from medicine.users where login='" +login+ "' ;");
            if (rs.next()) {
                userExists = true;
            } else {
                userExists = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userExists;
    }

    public static void createUser(String login, String password) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO medicine.users (id_user,login,user_password) VALUES(DEFAULT,'" + login + "','" + password + "');");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public Boolean checkLoginData(String login, String password){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Boolean userExists = false;

        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from medicine.users where login='" +login+ "' AND user_password ='"+password+"' ;");
            if (rs.next()) {
                userExists = true;
            } else {
                userExists = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userExists;
    }

    public static void createMedicine(ArrayList<Medicine>listMedicines){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO medicine.medicines (id_user,medicine_name,nb_of_tablets,date_take,nb_of_tablets_one_time,date_of_first_use,date_of_last_use)" +
                    "VALUES((select id_user from medicine.users where login='" +login+ "'),?,?,?,?,?,?);";
            PreparedStatement pr = connection.prepareStatement(sql);
            for(int i = 0; i<listMedicines.size(); i++){
                pr.setString(1,listMedicines.get(i).getNameMedicine());
                pr.setInt(2,listMedicines.get(i).getNrOfTablets());
                pr.setInt(3,listMedicines.get(i).getDateTakeMed());
                pr.setInt(4,listMedicines.get(i).getNrOfTabletsOneTime());
                pr.setTimestamp(5, new  Timestamp(listMedicines.get(i).getFirstTakeMedicine().getTime()));
                pr.setTimestamp(6, new Timestamp(listMedicines.get(i).getDateOfLastUse().getTime()));

            }
            pr.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static ArrayList<Medicine> getMedicines(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        ArrayList<Medicine> medicinesList = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = connection.createStatement();
            String sql = "SELECT  medicine_name,nb_of_tablets,date_take,nb_of_tablets_one_time,date_of_first_use,date_of_last_use FROM medicine.medicines WHERE (select id_user from medicine.users where login='" +login+ "')=id_user;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String name = rs.getString("medicine_name");
                int nbTab = rs.getInt("nb_of_tablets");
                int dTake = rs.getInt("date_take");
                int nbTabOneTime = rs.getInt("nb_of_tablets_one_time");
                Date dateFirst = new Date(rs.getTimestamp("date_of_first_use").getTime());
                Date dateLast = new Date(rs.getTimestamp("date_of_last_use").getTime());

                Medicine med = new Medicine(name,nbTab,dTake,nbTabOneTime,dateFirst,dateLast);
                medicinesList.add(med);
            }
            return medicinesList;


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return medicinesList;
    }
    public static void updateMedicine(ArrayList<Medicine>listMedicines){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "UPDATE medicine.medicines SET medicine_name =?,nb_of_tablets=?,date_take=?,nb_of_tablets_one_time=?,date_of_first_use=?,date_of_last_use=?" +
                    "WHERE id_user = (select id_user from medicine.users where login='" +login+ "');";

            PreparedStatement pr = connection.prepareStatement(sql);
            for(int i = 0; i<listMedicines.size(); i++){
                pr.setString(1,listMedicines.get(i).getNameMedicine());
                pr.setInt(2,listMedicines.get(i).getNrOfTablets());
                pr.setInt(3,listMedicines.get(i).getDateTakeMed());
                pr.setInt(4,listMedicines.get(i).getNrOfTabletsOneTime());
                pr.setTimestamp(5, new  Timestamp(listMedicines.get(i).getFirstTakeMedicine().getTime()));
                pr.setTimestamp(6, new Timestamp(listMedicines.get(i).getDateOfLastUse().getTime()));
                pr.executeUpdate();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}



//
