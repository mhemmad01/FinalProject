package com.example.myapplication;

import android.content.ContentValues;
import android.util.Log;

import com.example.myapplication.diagnosed_model.Diagnosed;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dbConnection {

    private static final String url = "jdbc:mysql://freedb.tech:3306/freedbtech_FinalProject";
    private static final String user = "freedbtech_mhemmad";
    private static final String pass = "Mhmd12345.";
    private static Connection conn;


    public static Connection getConnection() {
        if(conn==null)
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        return conn;
    }

    public static boolean validatePassword(String username, String password){
        try {

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select distinct * from users WHERE Username='"+username+"' AND Password='"+password+"'");
            ResultSetMetaData rsmd = rs.getMetaData();

            if (rs.next()) {
                    User usr = new User(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(4), Integer.parseInt(rs.getString(5)) == 1, Integer.parseInt(rs.getString(6)));
                    User.currentUser = usr;
                    if(usr.isDiagnostic()){
                        User.diagnoseds=getDiagnosedForUser(usr.getUsername());
                    }
                    return true;
            }
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User getUser(String username) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select distinct * from users WHERE username='" + username + "'");
            ResultSetMetaData rsmd = rs.getMetaData();

        if (rs.next()) {
            User usr = new User(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(4), Integer.parseInt(rs.getString(5)) == 1, Integer.parseInt(rs.getString(6)));
            st.close();
            return usr;
        }
            return null;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    public static ArrayList<Diagnosed> getDiagnosedForUser(String username) {
        Statement st = null;
        ArrayList<Diagnosed> users = new ArrayList<>();
        try {
            st = getConnection().createStatement();

            ResultSet rs = st.executeQuery("select distinct * from diagnoseds WHERE diagnostic='" + username + "'");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                users.add(new Diagnosed(getUser(rs.getString(1))));
            }
            st.close();
            return users;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public static Boolean addmotorlevel(String usr, int stars, int stage, int level, String img) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("INSERT INTO motor (username, stage, level, img,stars)  VALUES('"+usr+"', '"+stage+"','"+level+"','"+img+"','"+stars+"')");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static String getimg(String username,int level,int stage) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from motor WHERE username='" + username  + "' AND stage='"+stage+"' AND level='"+level+"'");
            ResultSetMetaData rsmd = rs.getMetaData();

            if (rs.next()) {
                String temp=rs.getString(4);
                //Blob temp = rs.getBlob(4);
                st.close();
                return temp;//.getBytes(0,(int)temp.length());
            }
            return null;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


}
