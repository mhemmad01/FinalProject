package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
//Registration Activity where the user could create account on the app
public class RegistrationActivity extends AppCompatActivity {

    private static final String url = dbConnection.url;
    private static final String user = dbConnection.user;
    private static final String pass = dbConnection.pass;
    static Dialog dialog3=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();

    }
    public void LoadingShow(){
        // custom dialog
        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.loadingicon);
        dialog3.setTitle("Loading");
        dialog3.setCanceledOnTouchOutside(false);
        dialog3.show();
    }
    //Back to login page
    public void gotoLogin(View view){
        Intent myIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        RegistrationActivity.this.startActivity(myIntent);
        finish();
    }
    //Save user registration details at DB
     public void registration(View view){
        String Username = ((EditText)findViewById(R.id.editTextUsername)).getText().toString();
        String FullName = ((EditText)findViewById(R.id.editTextFullName)).getText().toString();
        String Password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        String Age = ((EditText)findViewById(R.id.editTextAge)).getText().toString();
        int isDiagnostic = (((CheckBox)findViewById(R.id.checkBoxDiagnostic)).isChecked())? 1 : 0;
         LoadingShow();
         ConnectMySql_Registration connectMySql = new ConnectMySql_Registration(Username,FullName,Password,Age,isDiagnostic);
         connectMySql.execute("");

    }
    //Thread to Save user details at DB
    private class ConnectMySql_Registration extends AsyncTask<String, Void, String> {
        String res = "";
        String Username;
        String FullName;
        String Password;
        String Age;
        int isDiagnostic;

        public ConnectMySql_Registration(String Username, String FullName, String Password, String Age, int isDiagnostic){
            this.FullName=FullName;
            this.Username=Username;
            this.Password=Password;
            this.Age=Age;
            this.isDiagnostic=isDiagnostic;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RegistrationActivity.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Databaseection success");

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select distinct * from users WHERE username='"+Username+"'");
                ResultSetMetaData rsmd = rs.getMetaData();

                if (rs.next()) {
                    res = "Account is Already in our system";
                }
                else{
                    st.executeUpdate("INSERT INTO users (Username, Password, FullName, isDiagnostic, Age)  VALUES('"+Username+"', '"+Password+"','"+FullName+"','"+isDiagnostic+"','"+Age+"')");
                    st.executeUpdate("INSERT INTO diagnoseds (diagnosed, diagnostic)  VALUES('"+Username+"', 'admin')");

                    res = "Account Registred Successfully, You can login now...";
                }

            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog3.dismiss();
            Toast.makeText(RegistrationActivity.this, result, Toast.LENGTH_SHORT)
                    .show();
           // Log.i("sqlresult", result);
        }
    }

}
