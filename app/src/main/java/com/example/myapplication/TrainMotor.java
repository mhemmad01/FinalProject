package com.example.myapplication;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class TrainMotor extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainingmotor);
        getSupportActionBar().hide();
        PaintView SyncDiagnosisView1=new PaintView(this);
        final ViewGroup SyncviewGroup1 = (ViewGroup) findViewById(R.id.MotorTrain);
        SyncviewGroup1.addView(SyncDiagnosisView1);
    }
}
