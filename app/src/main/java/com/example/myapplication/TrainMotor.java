package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TrainMotor extends AppCompatActivity {
    public static TrainMotor Instance;
    PaintView MotorDiagnosisView1;
    private String lastLevel2;
    AlertDialog dialog;
    private TextView level;
    private ViewGroup MotorviewGroup1;
    private LinearLayout parent;


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
        MotorviewGroup1 = (ViewGroup) findViewById(R.id.MotorTrain);
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
/*
        MotorviewGroup1.setDrawingCacheEnabled(true);
        Bitmap b = MotorviewGroup1.getDrawingCache();
        String userHomeFolder = System.getProperty("user.home");
        File textFile = new File(userHomeFolder, "mytext");
    //    File dir = new File("C:\\Users\\ayman\\Desktop\\New folder (3)");
        if (!textFile.exists()) {
            textFile.mkdirs();
        }
        File output = new File(textFile, "tempfile.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        b.compress(Bitmap.CompressFormat.PNG, 95, fos);*/
        MotorviewGroup1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        MotorviewGroup1.layout(0, 0, MotorviewGroup1.getMeasuredWidth(), MotorviewGroup1.getMeasuredHeight());

        MotorviewGroup1.setDrawingCacheEnabled(true);
        MotorviewGroup1.buildDrawingCache();
        Bitmap b= MotorviewGroup1.getDrawingCache();// creates bitmap and returns the same
        /*
        Log.i("xx", "Next: "+b.getRowBytes());
        try (FileOutputStream out = new FileOutputStream("test.png")) {
            Log.i("xx",b.compress(Bitmap.CompressFormat.PNG, 100, out)+""); // bmp is your Bitmap instance
            out.flush(); // Not really required
            out.close(); // do not forget to close the stream
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println("xxx...");
       // FileOutputStream out = new FileOutputStream("the-file-name");
        //out.write("hi");
        //out.close();
       // File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        //File file = new File(path, "/" + "newfolder");
        //b.compress(Bitmap.CompressFormat.PNG, 95, file);

        /*
        String userHomeFolder = System.getProperty("user.home");
        File textFile = new File(userHomeFolder, "mytext");
        //    File dir = new File("C:\\Users\\ayman\\Desktop\\New folder (3)");
        if (!textFile.exists()) {
            textFile.mkdirs();
        }
        File output = new File(textFile, "tempfile.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        b.compress(Bitmap.CompressFormat.PNG, 95, fos);*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
