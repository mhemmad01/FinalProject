package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

    private static final String url = "jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6398951";
    private static final String user = "sql6398951";
    private static final String pass = "FjFtGjHgy1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
       // getSupportActionBar().hide();
    }

    public void login(View view){
        String Username = ((EditText)findViewById(R.id.editTextUsername1)).getText().toString();
        String Password = ((EditText)findViewById(R.id.editTextPassword1)).getText().toString();

        ConnectMySql_Registration connectMySql = new ConnectMySql_Registration(Username, Password);
        connectMySql.execute("");

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

                System.out.println("xs");
                if (rs.next()) {
                    if(rs.getString(3).equals(Password))
                    {
                        res= "Connected!!";
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
            // Log.i("sqlresult", result);
        }
    }

}
