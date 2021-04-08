package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

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
    private int previous_stage_stars=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=this;
        setContentView(R.layout.trainingselect);
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
    public void SetTrainMode(String TrainMode){
        this.TrainMode=TrainMode;
    }
    public void StartSyncTrain(View view) {
        SetTrainMode("Sync");
        Intent intent = new Intent(getApplicationContext(), TrainSync.class);
        startActivity(intent);
    }
    public void StartMotorTrain(View view) {
        if(lastLevel==1 &&lastStage>1&&previous_stage_stars<6){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
            intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
            intent.putExtra("NextStage", Integer.toString(a.lastStage));
            SelectTrainMode.this.startActivityForResult(intent, 1);
        }
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
                intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
                intent.putExtra("NextStage", Integer.toString(a.lastStage));
                startActivityForResult(intent, 1);
            }else if(action.equals("NEXT")){
                a.lastLevel=a.lastLevel+1;
                Intent intent = new Intent(this, TrainMotor.class);
                intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
                intent.putExtra("NextStage", Integer.toString(a.lastStage));
                startActivityForResult(intent, 1);
            }
            else if (action.equals("FINISH")){
                lastLevel=1;
                lastStage=lastStage+1;
                previous_stage_stars=5;
            }
        }
    }


}