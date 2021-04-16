package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class SelectTrainMode extends AppCompatActivity {
    static String TrainMode;
    SelectTrainMode a;
    private int lastLevel=1;
    private int lastStage=1;
    private int lastSyncLevel=1;
    private int lastSyncStage=1;
    private float stars=0;
    private float syncstars=0;
    static Dialog dialog3=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=this;
        setContentView(R.layout.trainingselect);
        final androidx.appcompat.app.ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_toolbar));//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.abs_layout, null);
        androidx.appcompat.app.ActionBar.LayoutParams params = new androidx.appcompat.app.ActionBar.LayoutParams(//Center the textview in the ActionBar !
                androidx.appcompat.app.ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Selecting Page");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);


    }
    public void setLastLevel(int lastLevel){
        this.lastLevel=lastLevel;
    }
    public int getLastLevel(){
        return lastLevel;
    }
    public void setLastStage(int lastStage){
        this.lastStage=lastStage;
    }
    public int getLastStage(){
        return lastStage;
    }
    public void setLastSyncLevel(int lastLevel){
        this.lastSyncLevel=lastSyncLevel;
    }
    public int getLastSyncLevel(){
        return lastLevel;
    }
    public void setLastSyncStage(int lastStage){
        this.lastSyncStage=lastSyncStage;
    }
    public int getLastSyncStage(){
        return lastStage;
    }
    public void SetTrainMode(String TrainMode){
        this.TrainMode=TrainMode;
    }
    public void StartSyncTrain(View view) {
        SetTrainMode("Sync");
        Intent intent = new Intent(SelectTrainMode.this, TrainSync.class);
        intent.putExtra("Type", "Sync");
        intent.putExtra("NextLevel", Integer.toString(a.lastSyncLevel));
        intent.putExtra("NextStage", Integer.toString(a.lastSyncStage));
        SelectTrainMode.this.startActivityForResult(intent, 1);
        LoadingShow();
        getStarsSync s=new getStarsSync(User.currentUser);
        s.execute();
    }
    public void StartMotorTrain(View view) {
        LoadingShow();
        getStarsMotor s=new getStarsMotor(User.currentUser);
        s.execute();

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(data == null)
            return;
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 1
        if(requestCode==1)
        {
            String action =data.getStringExtra("action");
            if(action.equals("RESTART")){

                Intent intent = new Intent(this, TrainMotor.class);
                intent.putExtra("Type", "Train");
                intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
                intent.putExtra("NextStage", Integer.toString(a.lastStage));
                startActivityForResult(intent, 1);
            }else if(action.equals("NEXT")){
                if(TrainMotor.dialog3!=null)
                    TrainMotor.dialog3.dismiss();
                a.lastLevel=a.lastLevel+1;
                SaveLastStageLevel s=new SaveLastStageLevel(User.currentUser,lastLevel,lastStage);
                s.execute();
                Intent intent = new Intent(this, TrainMotor.class);
                intent.putExtra("Type", "Train");
                intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
                intent.putExtra("NextStage", Integer.toString(a.lastStage));
                startActivityForResult(intent, 1);
            }
            else if (action.equals("FINISH")){
                if(TrainMotor.dialog3!=null)
                    TrainMotor.dialog3.dismiss();
                lastLevel=1;
                lastStage=lastStage+1;
                SaveLastStageLevel s=new SaveLastStageLevel(User.currentUser,lastLevel,lastStage);
                s.execute();
                //previous_stage_stars=5;
            }
        }
    }
    private class SaveLastStageLevel extends AsyncTask<String, Void, Boolean> {
        User usr;
        int lastmotorlevel;
        int lastmotorstage;

        public SaveLastStageLevel(User usr,int lastmotorlevel,int lastmotorstage) {
            this.usr = usr;
            this.lastmotorlevel = lastmotorlevel;
            this.lastmotorstage = lastmotorstage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.savelastmotorlevel(usr, lastmotorlevel, lastmotorstage);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {

                Log.i("hhhh", "ccc");
                //getmotorImg a = new getmotorImg(usr, stage, level);
                //a.execute("");
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
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
                lastLevel=result[0];
                lastStage=result[1];
                if(lastLevel==1 &&lastStage>1&&stars<(lastStage-1)*6+(lastStage-1)){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(a);
                    builder1.setMessage("You don't have enougth stars to start stage "+lastStage+" " +
                            "please improve some of previous levels of stage "+(lastStage-1)+" " +
                            "or wait until you get your stars from the diagnosis.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog2, int id) {
                                    dialog2.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else {
                    SetTrainMode("Motor");
                    // Start the SecondActivity
                    Intent intent = new Intent(SelectTrainMode.this, TrainMotor.class);
                    intent.putExtra("Type", "Train");
                    intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
                    intent.putExtra("NextStage", Integer.toString(a.lastStage));
                    SelectTrainMode.this.startActivityForResult(intent, 1);
                }
                dialog3.dismiss();
                Log.i("hhhh", "ccc");
                //getmotorImg a = new getmotorImg(usr, stage, level);
                //a.execute("");
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
    }
    private class getStarsMotor extends AsyncTask<String, Void, Float> {
        User usr;

        public getStarsMotor(User usr) {
            this.usr = usr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Float doInBackground(String... params) {
            return dbConnection.getstars(usr);
        }

        @Override
        protected void onPostExecute(Float result) {
            if (result!=-1) {
                stars=result;
                GetLastStageLevel s=new GetLastStageLevel(User.currentUser);
                s.execute();
                Log.i("hhhh", "ccc"+stars);
                //getmotorImg a = new getmotorImg(usr, stage, level);
                //a.execute("");
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
    }
    private class getStarsSync extends AsyncTask<String, Void, Float> {
        User usr;

        public getStarsSync(User usr) {
            this.usr = usr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Float doInBackground(String... params) {
            return dbConnection.getstarssync(usr);
        }

        @Override
        protected void onPostExecute(Float result) {
            if (result!=-1) {
                syncstars=result;
                GetLastStageLevelSync s=new GetLastStageLevelSync(User.currentUser);
                s.execute();
                Log.i("hhhh", "ccc"+stars);
                //getmotorImg a = new getmotorImg(usr, stage, level);
                //a.execute("");
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
    }
    private class GetLastStageLevelSync extends AsyncTask<String, Void, int[]> {
        User usr;

        public GetLastStageLevelSync(User usr) {
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
            return dbConnection.getlastsyncStagelevel(usr);
        }

        @Override
        protected void onPostExecute(int[] result) {
            if (result!=null) {
                lastSyncLevel=result[0];
                lastSyncStage=result[1];
                if(lastSyncLevel==1 &&lastSyncStage>1&&syncstars<(lastSyncStage-1)*6+(lastSyncStage-1)){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(a);
                    builder1.setMessage("You don't have enougth stars to start stage "+lastStage+" " +
                            "please improve some of previous levels of stage "+(lastStage-1)+" " +
                            "or wait until you get your stars from the diagnosis.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog2, int id) {
                                    dialog2.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else {
                    SetTrainMode("Motor");
                    // Start the SecondActivity
                    Intent intent = new Intent(SelectTrainMode.this, TrainSync.class);
                    intent.putExtra("Type", "Train");
                    intent.putExtra("NextLevel", Integer.toString(a.lastSyncLevel));
                    intent.putExtra("NextStage", Integer.toString(a.lastSyncStage));
                    SelectTrainMode.this.startActivityForResult(intent, 1);
                }
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