package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TrainMotor extends AppCompatActivity {
    public static TrainMotor Instance;
    PaintView MotorDiagnosisView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instance=this;
        setContentView(R.layout.trainingmotor);
        MotorDiagnosisView1=new PaintView(this);
        final ViewGroup MotorviewGroup1 = (ViewGroup) findViewById(R.id.MotorTrain);
        MotorviewGroup1.addView(MotorDiagnosisView1);

    }
    public void drawfinish(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.starsdialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    public void Restart(View view){
        Intent intent = new Intent(getApplicationContext(), TrainMotor.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
