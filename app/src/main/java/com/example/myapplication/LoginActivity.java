package com.example.myapplication;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity  extends AppCompatActivity {

    private static final String url = "jdbc:mysql://freedb.tech:3306/freedbtech_FinalProject";
    private static final String user = "freedbtech_mhemmad";
    private static final String pass = "Mhmd12345.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //dialog.getWindow().setLayout(800,400);       // getSupportActionBar().hide();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void login(View view){
        String Username = ((EditText)findViewById(R.id.editTextUsername1)).getText().toString();
        String Password = ((EditText)findViewById(R.id.editTextPassword1)).getText().toString();

        ConnectMySql_Registration connectMySql = new ConnectMySql_Registration(Username, Password);
        connectMySql.execute("");

    }

    public void gotoRegistration(View view){
        Intent myIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }

    private class ConnectMySql_Registration extends AsyncTask<String, Void, String> {
        String res = "";
        String Username;
        String Password;

        public ConnectMySql_Registration(String Username, String Password){
            this.Username=Username;
            this.Password=Password;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
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
                    if(rs.getString(3).equals(Password))
                    {
                        res= "Connected!!";
                        User usr = new User(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(4),Integer.parseInt(rs.getString(5))==1,Integer.parseInt(rs.getString(3)));
                        User.currentUser=usr;
                    }else{
                        res= "Password Are Incorrect";
                    }
                }
                else{
                    res = "Username Are incorrect";
                }
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT)
                    .show();
            if(result=="Connected!!") {
                //R.string.fullname
                if (User.currentUser.isDiagnostic()) {
                    Intent myIntent = new Intent(LoginActivity.this, MainDiagnostic.class);
                    LoginActivity.this.startActivity(myIntent);
                    finish();
                } else {
                    Intent myIntent = new Intent(LoginActivity.this, MainDiagnosed.class);
                    LoginActivity.this.startActivity(myIntent);
                    finish();
                }
            }
        }
    }

}
