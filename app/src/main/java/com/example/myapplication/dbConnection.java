package com.example.myapplication;

import android.content.ContentValues;
import android.util.Log;

import com.example.myapplication.diagnosed_model.Diagnosed;
import com.example.myapplication.trainingresultmotor.MotorResult;

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
    public static boolean savelastdiagnosisimg(String usr, int diagnosisnum, int imgnum){
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE diagnoseds SET lastdiagnosismotorimg ='"+ imgnum+ "', lastdiagnosisnum='"+ diagnosisnum+ "' WHERE diagnosed='"+usr+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static boolean addmotordiagnosisimg(String usr, int diagnosisnum, int imgnum, String img){
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("INSERT INTO diagnosismotor (username, diagnosisnum, imgnum, img,accept)  VALUES('"+usr+"', '"+diagnosisnum+"','"+imgnum+"','"+img+"','"+(-1)+"')");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static int[] getlastdiagnosismotorimg(String user){
        try {

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from diagnoseds WHERE diagnosed='"+user+"'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int[] result=new int[2];
            if (rs.next()) {
                result[0]=rs.getInt(5);
                result[1]=rs.getInt(6);
                st.close();
                return result;
            }
            else{
                st.close();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean AddDiagnosed(String Diagnostic, String Diagnosed){
        try {

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select distinct * from diagnoseds WHERE diagnosed='"+Diagnosed+"' AND diagnostic='admin'");
            ResultSetMetaData rsmd = rs.getMetaData();

            if (rs.next()) {
                Log.i("1", "AddDiagnosed: ");
                st.executeUpdate("UPDATE diagnoseds SET diagnostic ='"+ Diagnostic+ "' WHERE diagnosed='"+Diagnosed+"' AND diagnostic='admin'");
                st.close();
                User.diagnoseds=getDiagnosedForUser(Diagnostic);
                return true;
            }
            else{
                st.close();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static float getstars(User user){
        Statement st = null;
        float stars=0;
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select stars from motor WHERE username='" + user.getUsername() + "'");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                stars+=Float.parseFloat(rs.getString(1));
            }
            st.close();
            return stars;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return -1;
        }
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
    public static Boolean savelastmotorlevel(User usr, int lastmotorlevel, int lastmotorstage) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE diagnoseds SET lasttrainmotorlevel ='"+ lastmotorlevel+ "', lasttrainmotorstage='"+ lastmotorstage+ "' WHERE diagnosed='"+usr.getUsername()+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static int[] getlastmotorStagelevel(User usr) {
        Statement st = null;
        try {
            int[] result=new int[2];
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from diagnoseds WHERE diagnosed='" + usr.getUsername()  + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            if (rs.next()) {
                result[0]=rs.getInt(3);
                result[1]=rs.getInt(4);
                //Blob temp = rs.getBlob(4);
                st.close();
                return result;//.getBytes(0,(int)temp.length());
            }
            return null;
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

    public static ArrayList<MotorResult> getTrainMotorResults(String username){
        Statement st = null;
        ArrayList<MotorResult> temp = new ArrayList<>();
        try {
            st = getConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from motor WHERE username='" + username  + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            Log.i("hhh", "getTrainMotorResults: 0 " + username);
            while (rs.next()) {
                Log.i("hhh", "getTrainMotorResults: 1");

                MotorResult t = new MotorResult();
                t.username = rs.getString(1);
                t.stage = Integer.parseInt(rs.getString(2));
                t.level = Integer.parseInt(rs.getString(3));
                t.stars = Float.parseFloat(rs.getString(5));
                t.img = rs.getString(4);
                temp.add(t);
            }
            st.close();

            return temp;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
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


    public static Boolean updateMotorResultStars(MotorResult result) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE motor SET stars ='"+ result.stars+ "' WHERE username='"+result.username+"' AND stage='"+result.stage+"' AND level='"+result.level+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

}
