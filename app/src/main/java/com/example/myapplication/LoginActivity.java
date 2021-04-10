package com.example.myapplication;


import android.app.AlertDialog;
import android.app.Dialog;
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
    static Dialog dialog3=null;
    static int[] MotorStageAndLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        instance = this;

        //dialog.getWindow().setLayout(800,400);       // getSupportActionBar().hide();
    }
    public void LoadingShow(){
        // custom dialog
        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.loadingicon);
        dialog3.setTitle("Loading");
        dialog3.show();
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
        LoadingShow();
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
                        dialog3.dismiss();
                    }else {
                        GetLastStageLevel s=new GetLastStageLevel(User.currentUser);
                        s.execute();
                       // Intent myIntent = new Intent(LoginActivity.this, MainDiagnosed.class);
                        //LoginActivity.this.startActivity(myIntent);
                       // finish();
                       // dialog3.dismiss();

                    }

            }else {
                Toast.makeText(LoginActivity.this.getBaseContext(), "Details are incorrect", Toast.LENGTH_SHORT)
                        .show();
                dialog3.dismiss();

            }

        }


    }



    public void gotoRegistration(View view){
        Intent myIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        LoginActivity.this.startActivity(myIntent);
        finish();
    }
    private class GetLastStageLevel extends AsyncTask<String, Void, int[]> {
        User usr;

        public GetLastStageLevel(User usr) {
            this.usr = usr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected int[] doInBackground(String... params) {
            return dbConnection.getlastmotorStagelevel(usr);
        }

        @Override
        protected void onPostExecute(int[] result) {
            if (result!=null) {
                MotorStageAndLevel=result;
                Intent myIntent = new Intent(LoginActivity.this, MainDiagnosed.class);
                LoginActivity.this.startActivity(myIntent);
                finish();
                dialog3.dismiss();
                Log.i("hhhh", "ccc");
                //getmotorImg a = new getmotorImg(usr, stage, level);
                //a.execute("");
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
    }

}
