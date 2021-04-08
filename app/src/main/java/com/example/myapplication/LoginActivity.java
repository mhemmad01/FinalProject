package com.example.myapplication;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity  extends AppCompatActivity {

    public static LoginActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        instance = this;

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
        Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
        CheckUsernamePassword check = new CheckUsernamePassword(Username,Password);
        check.execute("");

    }
    private class CheckUsernamePassword extends AsyncTask<String, Void, Boolean>{
        String usr;
        String pass;
        public CheckUsernamePassword(String usr, String pass){
            this.usr=usr;
            this.pass=pass;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.validatePassword(usr,pass);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                if(User.currentUser!=null)
                    if (User.currentUser.isDiagnostic()){
                        Intent myIntent = new Intent(LoginActivity.this, MainDiagnostic.class);
                        LoginActivity.this.startActivity(myIntent);
                        finish();
                    }else {
                        Intent myIntent = new Intent(LoginActivity.this, MainDiagnosed.class);
                        LoginActivity.this.startActivity(myIntent);
                        finish();
                    }

            }else {
                Toast.makeText(LoginActivity.this.getBaseContext(), "Details are incorrect", Toast.LENGTH_SHORT)
                        .show();
            }

        }


    }



    public void gotoRegistration(View view){
        Intent myIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }


}
