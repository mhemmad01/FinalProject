package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SelectTrainMode extends AppCompatActivity {
    static String TrainMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = new Intent(getApplicationContext(), TrainMotor.class);
        startActivity(intent);
    }
    public void SetTrainMode(String TrainMode){
        this.TrainMode=TrainMode;
    }
}