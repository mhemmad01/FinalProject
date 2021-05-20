package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.improvelevel_model.MotorResult;
import com.example.myapplication.improvelevel_modelSync.improvelevel_model.SyncResult;

import java.util.ArrayList;

public class MainDiagnosed extends AppCompatActivity {
    static String Username;
    static Dialog dialog3=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosted_mainpage);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        if(dialog3!=null)
            dialog3.dismiss();
        if(User.currentUser!=null)
        ((TextView)findViewById(R.id.diagnosedTitle)).setText("Hi "+User.currentUser.getFullName());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void Logout(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void StartTrain(View view) {
        Intent intent = new Intent(getApplicationContext(), SelectTrainMode.class);
        startActivity(intent);
    }
    public void LoadingShow(){
        // custom dialog
        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.loadingicon);
        dialog3.setTitle("Loading");
        dialog3.setCanceledOnTouchOutside(false);
        dialog3.show();
    }
    public void gotoAbout(View view) {
        Intent intent = new Intent(getApplicationContext(), AboutActivity_Diagnosed.class);
        startActivity(intent);
    }

    public void gotoImproveLevels(View view) {
        LoadingShow();
        LoadResults l = new LoadResults(User.currentUser.getUsername());
        l.execute();
    }


    private class LoadResults extends AsyncTask<String, Void, ArrayList<MotorResult>> {
        String usr;


        public LoadResults(String usr) {
            this.usr = usr;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected ArrayList<MotorResult> doInBackground(String... params) {
            SyncResult.selected=dbConnection.getTrainSyncResults_ImproveLever(usr);
            return dbConnection.getTrainMotorResultsImprove(usr);
        }

        @Override
        protected void onPostExecute(ArrayList<MotorResult>  result) {
            if (result.size()>0) {
                Log.i("hhhh", "ccc");
                if(dialog3!=null)
                dialog3.dismiss();
                MotorResult.selected=result;
                Intent intent = new Intent(getApplicationContext(), Levels.class);
                startActivity(intent);
            } else {
                if(dialog3!=null)
                dialog3.dismiss();
                Log.i("hhhh", "ffffff");
            }

        }
    }

/*
        public void backtomain(View view) {

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();

        }*/
}
