package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
    static int lastLevel=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=this;
        setContentView(R.layout.trainingselect);
        getSupportActionBar().hide();
    }
    public void StartSyncTrain(View view) {
        SetTrainMode("Sync");
        Intent intent = new Intent(getApplicationContext(), TrainSync.class);
        startActivity(intent);
    }
    public void StartMotorTrain(View view) {
        SetTrainMode("Motor");
        // Start the SecondActivity
        Intent intent = new Intent(SelectTrainMode.this, TrainMotor.class);
        intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
        SelectTrainMode.this.startActivityForResult(intent, 1);
       // Intent intent = new Intent(getApplicationContext(), TrainMotor.class);
        //intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
        //startActivity(intent);
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
                startActivityForResult(intent, 1);
            }else if(action.equals("NEXT")){
                a.lastLevel=a.lastLevel+1;
                Intent intent = new Intent(this, TrainMotor.class);
                intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
                startActivityForResult(intent, 1);
            }
        }
    }
    public void SetTrainMode(String TrainMode){
        this.TrainMode=TrainMode;
    }

}