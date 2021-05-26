package com.example.myapplication;

import android.content.ContentValues;
import com.example.myapplication.trainingresultsync.trainingresultmotor.SyncResult;
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

    public static final String url = "jdbc:mysql://bnntwd7hikulfy4quorz-mysql.services.clever-cloud.com:3306/bnntwd7hikulfy4quorz";
    public static final String user = "uypnrj7rpuan2nnl";
    public static final String pass = "ry4OYPlLA06HCHRERlrF";
    private static Connection conn;

    /*
    This Class is Responsible for DB Functions
     */
    public static Connection getConnection() {
        if(conn==null)
            try {
                Class.forName("com.mysql.jdbc.Driver");
//                conn = DriverManager.getConnection(url, user, pass);
                conn = DriverManager.getConnection(url +  "?user="+user+"&password="+pass+"&characterEncoding=utf8&useSSL=false&useUnicode=true");
            } catch ( Exception throwables) {
                throwables.printStackTrace();
            }
        return conn;
    }
    //The user improved motor Level and we update it on DB
    public static boolean editmotorlevel(String usr, int stars, int stage, int level, String img){
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE motor SET img ='"+ img+ "', stars='"+ stars+ "' WHERE username='"+usr+"' AND stage='"+stage+"' AND level='"+level+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    //The user improved sync Level and we update it on DB
    public static boolean edittrainlevelSync(String usr, int stars, int stage, int level, String img1,String img2,int percent){
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE sync SET img1 ='"+ img1+ "', stars='"+ stars+ "', img2='"+img2+"', percent='"+percent+"' WHERE username='"+usr+"' AND stage='"+stage+"' AND level='"+level+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean validatePassword(String username, String password){
        try {

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select distinct * from users WHERE Username='"+username+"' AND Password='"+password+"'");
            ResultSetMetaData rsmd = rs.getMetaData();

            if (rs.next()) {
                    System.out.println("sxxx");
                    User usr = new User(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(4), Integer.parseInt(rs.getString(5)) == 1, Integer.parseInt(rs.getString(6)));
                    User.currentUser = usr;
                    if(usr.isDiagnostic()){
                        User.diagnoseds=getDiagnosedForUser(usr.getUsername());
                    }
                    return true;
            }
            System.out.println("uuuu");
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //save the img of diagnosis motor for specific user
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
    //save the images of diagnosis sync for specific user
    public static boolean savelastdiagnosisimgsync(String usr, int diagnosisnum, int imgnum){
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE diagnoseds SET lastdiagnosissyncimg ='"+ imgnum+ "', lastdiagnosissyncnum='"+ diagnosisnum+ "' WHERE diagnosed='"+usr+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    //Insert a new motor diagnosis img to DB for specific user
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
    //Insert a new sync diagnosis images to DB for specific user
    public static boolean adddiagnosisSync(String usr, int diagnosisnum, int imgnum, String img1,String img2,int percent){
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("INSERT INTO diagnosissync (username, diagnosisnum, imgnum, img,img2,percent,accept)  VALUES('"+usr+"', '"+diagnosisnum+"', '"+imgnum+"', '"+img1+"', '"+img2+"', '"+percent+"','"+(-1)+"')");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    //Get last diagnosis motor img num and diagnosis num for specific user
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
    //Get last diagnosis sync img num and diagnosis num for specific user
    public static int[] getlastdiagnosissyncimg(String user){
        try {

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from diagnoseds WHERE diagnosed='"+user+"'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int[] result=new int[2];
            if (rs.next()) {
                result[0]=rs.getInt(9);
                result[1]=rs.getInt(10);
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
    //Get sum of stars for train motor for specific user
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
    //Get sum of stars for train sync for specific user
    public static float getstarssync(User user){
        Statement st = null;
        float stars=0;
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select stars from sync WHERE username='" + user.getUsername() + "'");
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
    //update last motor level num and last motor stage num in DB for specific user
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
    //update last sync level num and last sync stage num in DB for specific user
    public static Boolean savelastmotorlevelSync(User usr, int lastsynclevel, int lastsyncstage) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE diagnoseds SET lasttrainsynclevel ='"+ lastsynclevel+ "', lasttrainsyncstage='"+ lastsyncstage+ "' WHERE diagnosed='"+usr.getUsername()+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    //Get last train sync img level and last train sync level num for specific user
    public static int[] getlastsyncStagelevel(User usr) {
        Statement st = null;
        try {
            int[] result=new int[2];
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from diagnoseds WHERE diagnosed='" + usr.getUsername()  + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            if (rs.next()) {
                result[0]=rs.getInt(7);
                result[1]=rs.getInt(8);
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
    //Get last train motor img level and last train motor level num for specific user
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
    //Insert new motor train level for specific user in DB
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
    //Insert new sync train level for specific user in DB
    public static Boolean addtrainlevelSync(String usr, int stars, int stage, int level, String img1,String img2,int percent) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("INSERT INTO sync (username, stage, level, img1,img2,stars,percent)  VALUES('"+usr+"', '"+stage+"','"+level+"','"+img1+"','"+img2+"','"+stars+"','"+percent+"')");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static ArrayList<com.example.myapplication.improvelevel_model.MotorResult> getTrainMotorResultsImprove(String username){
        Statement st = null;
        ArrayList<com.example.myapplication.improvelevel_model.MotorResult> temp = new ArrayList<>();
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from motor WHERE username='" + username  + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            Log.i("hhh", "getTrainMotorResults: 0 " + username);
            while (rs.next()) {
                Log.i("hhh", "getTrainMotorResults: 1");

                com.example.myapplication.improvelevel_model.MotorResult t = new com.example.myapplication.improvelevel_model.MotorResult();
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


    public static ArrayList<SyncResult> getTrainSyncResults(String username){
        Statement st = null;
        ArrayList<SyncResult> temp = new ArrayList<>();
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from sync WHERE username='" + username  + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            Log.i("hhh", "getTrainMotorResults: 0 " + username);
            while (rs.next()) {
                Log.i("hhh", "getTrainMotorResults: 1");

                SyncResult t = new SyncResult();
                t.username = rs.getString(1);
                t.stage = Integer.parseInt(rs.getString(2));
                t.level = Integer.parseInt(rs.getString(3));
                t.stars = Float.parseFloat(rs.getString(6));
                t.img = rs.getString(4);
                t.img2 = rs.getString(5);
                t.percent =  rs.getString(7);
                temp.add(t);
            }
            st.close();

            return temp;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static ArrayList<com.example.myapplication.improvelevel_modelSync.improvelevel_model.SyncResult> getTrainSyncResults_ImproveLever(String username){
        Statement st = null;
        ArrayList<com.example.myapplication.improvelevel_modelSync.improvelevel_model.SyncResult> temp = new ArrayList<>();
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from sync WHERE username='" + username  + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            Log.i("hhh", "getTrainMotorResults: 0 " + username);
            while (rs.next()) {
                Log.i("hhh", "getTrainMotorResults: 1");

                com.example.myapplication.improvelevel_modelSync.improvelevel_model.SyncResult t = new com.example.myapplication.improvelevel_modelSync.improvelevel_model.SyncResult();
                t.username = rs.getString(1);
                t.stage = Integer.parseInt(rs.getString(2));
                t.level = Integer.parseInt(rs.getString(3));
                t.stars = Float.parseFloat(rs.getString(6));
                t.img = rs.getString(4);
                t.img2 = rs.getString(5);
                t.percent =  rs.getString(7);
                temp.add(t);
            }
            st.close();

            return temp;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    public static ArrayList<String> getDiagnosisIds(String username ){
        Statement st = null;
        ArrayList<String> temp = new ArrayList<>();
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select distinct diagnosisnum from diagnosismotor WHERE username='" + username  + "'");//*************
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                temp.add(rs.getString(1));
            }
            st.close();

            return temp;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getDiagnosisIdsSync(String username ){
        Statement st = null;
        ArrayList<String> temp = new ArrayList<>();
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select distinct diagnosisnum from diagnosissync WHERE username='" + username  + "'");//*************
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                temp.add(rs.getString(1));
            }
            st.close();

            return temp;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static ArrayList<com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult> getDiagnosisMotorResults(String username, String diagnosisID ){
        Statement st = null;
        ArrayList<com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult> temp = new ArrayList<>();
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from diagnosismotor WHERE username='" + username  + "' AND diagnosisnum='"+diagnosisID+"'");//*************
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult t = new com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult();
                t.username = rs.getString(1);
                t.stage = Integer.parseInt(rs.getString(2));
                t.level = Integer.parseInt(rs.getString(3));
                t.score = Integer.parseInt(rs.getString(5));
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



    public static ArrayList<com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult> getDiagnosisSyncResults(String username, String diagnosisID ){
        Statement st = null;
        ArrayList<com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult> temp = new ArrayList<>();
        try {
            st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from diagnosissync WHERE username='" + username  + "' AND diagnosisnum='"+diagnosisID+"'");//*************
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult t = new com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult();
                t.username = rs.getString(1);
                t.stage = Integer.parseInt(rs.getString(2));
                t.level = Integer.parseInt(rs.getString(3));
                t.score = Integer.parseInt(rs.getString(7));
                t.img = rs.getString(4);
                t.img2 = rs.getString(5);
                t.percent = rs.getString(6);
                temp.add(t);
            }
            st.close();

            return temp;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    //Get img of specific user at level and stage of motor train
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
    public static Boolean updateDiagnosisMotorScore(com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult result) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE diagnosismotor SET accept ='"+ result.score+ "' WHERE username='"+result.username+"' AND diagnosisnum='"+result.stage+"' AND imgnum='"+result.level+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static Boolean updateSyncResultStars(SyncResult result) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE sync SET stars ='"+ result.stars+ "' WHERE username='"+result.username+"' AND stage='"+result.stage+"' AND level='"+result.level+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }



    public static Boolean updateDiagnosisSyncScore(com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult result) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
            st.executeUpdate("UPDATE diagnosissync SET accept ='"+ result.score+ "' WHERE username='"+result.username+"' AND diagnosisnum='"+result.stage+"' AND imgnum='"+result.level+"'");
            st.close();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

}
