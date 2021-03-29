package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TrainMotor extends AppCompatActivity {
    public static TrainMotor Instance;
    PaintView MotorDiagnosisView1;
    private String lastLevel2;
    AlertDialog dialog;
    private TextView level;
    private int[] textureArrayWin = {
            R.drawable.qw,
            R.drawable.ss2,
            R.drawable.qw,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView myImageView;
        int currentlevel;
        Instance=this;
        setContentView(R.layout.trainingmotor);
        level=(TextView)findViewById(R.id.textView13);
        lastLevel2 = getIntent().getStringExtra("NextLevel");
        currentlevel=Integer.parseInt(lastLevel2)+1;
        level.setText("Level "+currentlevel);
        myImageView = (ImageView)findViewById(R.id.imageView4);
        //Drawable drawableId =getResources().getDrawable(textureArrayWin[lastLevel]);
        myImageView.setImageResource(textureArrayWin[Integer.parseInt(lastLevel2)]);
        MotorDiagnosisView1=new PaintView(this);
        final ViewGroup MotorviewGroup1 = (ViewGroup) findViewById(R.id.MotorTrain);
        MotorviewGroup1.addView(MotorDiagnosisView1);
    }
    public void drawfinish(){

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.starsdialog, null);
            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.show();
    }
    public void Restart(View view) {
        dialog.cancel();
        Intent intent = new Intent();
        intent.putExtra("action", "RESTART");
        setResult(Activity.RESULT_OK, intent);
        TrainMotor.this.finish();
    }
    public void Next(View view){
        dialog.cancel();
        Intent intent = new Intent();
        intent.putExtra("action", "NEXT");
        setResult(Activity.RESULT_OK, intent);
        TrainMotor.this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
