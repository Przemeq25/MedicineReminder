package com.example.medicinereminder;

import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseController {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://195.150.230.210:5434/2019_cichon_przemyslaw";
    static final String USER = "2019_cichon_przemyslaw";
    static final String PASS = "przemcio687";


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
    public static Boolean checkLoginData(String login, String password){
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

    public static void createMedicine(String login, String nameMedicine, int nrOfTablets, int dateTakeMed, int nrOfTabletsOneTime, Date firstTakeMedicine){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO medicine.medicines (id_user,medicine_name,nb_of_tablets,date_take,nb_of_tablets_one_time,date_of_first_use) VALUES((select id_user from medicine.users where login='" +login+ "'),'" + nameMedicine + "','" + nrOfTablets + "','"+dateTakeMed+"','"+nrOfTabletsOneTime+"','"+firstTakeMedicine+"');");

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




