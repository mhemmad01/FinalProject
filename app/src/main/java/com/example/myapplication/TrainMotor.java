package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TrainMotor extends AppCompatActivity {
    public static TrainMotor Instance;
    PaintView MotorDiagnosisView1;
    private String lastLevel2;
    private String lastStage2;
    AlertDialog dialog;
    private TextView level;
    private ViewGroup MotorviewGroup1;
    private LinearLayout parent;
    Bitmap b1;
    private int currentlevel;
    private int currentStage;
    private int stars;
    private int[] textureArrayWin = {
            R.drawable.qw,
            R.drawable.ss2,
            R.drawable.qw,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView myImageView;
        Instance=this;
        setContentView(R.layout.trainingmotor);
        level=(TextView)findViewById(R.id.textView13);
        lastLevel2 = getIntent().getStringExtra("NextLevel");
        lastStage2 = getIntent().getStringExtra("NextStage");
        currentlevel=Integer.parseInt(lastLevel2)+1;
        currentStage=Integer.parseInt(lastStage2)+1;
        level.setText("Level "+currentlevel+" Stage "+currentStage);
        myImageView = (ImageView)findViewById(R.id.imageView4);
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
        Bitmap mydraw=MotorDiagnosisView1.get();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        mydraw.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        MotorviewGroup1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
               View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
         MotorviewGroup1.layout(0, 0, MotorviewGroup1.getMeasuredWidth(), MotorviewGroup1.getMeasuredHeight());
        AddMotorLevel s=new AddMotorLevel(User.currentUser.getUsername(),1,currentStage,currentlevel,temp);
        s.execute("");
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
    private class AddMotorLevel extends AsyncTask<String, Void, Boolean> {
        String usr;
        int stars;
        int stage;
        int level;
        String img;

        public AddMotorLevel(String usr, int stars, int stage, int level, String img) {
            this.usr = usr;
            this.stars = stars;
            this.stage = stage;
            this.img = img;
            this.level = level;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.addmotorlevel(usr, stars, stage, level, img);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Log.i("hhhh", "ccc");
                getmotorImg a = new getmotorImg(usr, stage, level);
                a.execute("");
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
    }
        private class getmotorImg extends AsyncTask<String, Void, String> {
            String usr;
            int stage;
            int level;

            public getmotorImg(String usr,int stage, int level) {
                this.usr = usr;
                this.stage = stage;
                this.level = level;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
                //    .show();
            }


            @Override
            protected String doInBackground(String... params) {
                return dbConnection.getimg(usr, level, stage);
            }

            @Override
            protected void onPostExecute(String result) {
                if (result!=null) {
                    Log.i("hhhh","eeee");
                    byte[] b=Base64.decode(result,Base64.DEFAULT);
                    Bitmap bmp=BitmapFactory.decodeByteArray(b , 0, b.length);
                    //ImageView image = (ImageView) findViewById(R.id.imageView4);
                    //image.setImageBitmap(bmp);
                } else {
                    Log.i("hhhh", "ffffff");
                }

            }

        }

    }

