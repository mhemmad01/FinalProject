package com.example.myapplication;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class TrainSync extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traningsync);
        getSupportActionBar().hide();
        PaintView SyncDiagnosisView1=new PaintView(this);
        PaintView SyncDiagnosisView2=new PaintView(this);
        final ViewGroup SyncviewGroup1 = (ViewGroup) findViewById(R.id.SyncTrain1);
        SyncviewGroup1.addView(SyncDiagnosisView1);
        final ViewGroup SyncviewGroup2 = (ViewGroup) findViewById(R.id.SyncTrain2);
        SyncviewGroup2.addView(SyncDiagnosisView2);
    }
}
