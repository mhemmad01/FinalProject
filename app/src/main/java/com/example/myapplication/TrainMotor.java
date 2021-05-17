package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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
    public static Dialog dialog3=null;
    static String Type=null;
    public static int[][] textureArrayWin = {
            {       R.drawable.level1_stage1,
                    R.drawable.level2_stage1,
                    R.drawable.level3_stage1,
            },
            {
                    R.drawable.level1_stage2,
                    R.drawable.level2_stage2,
                    R.drawable.level3_stage2,
            },
            {
                    R.drawable.level1_stage3,
                    R.drawable.level2_stage3,
                    R.drawable.level3_stage3,
            }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView myImageView;
        Instance=this;
        setContentView(R.layout.trainingmotor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //getSupportActionBar().setTitle("Training Motor");
        final ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_toolbar));//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.abs_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Training Motor");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
        level=(TextView)findViewById(R.id.textView13);
        if(getIntent().getStringExtra("Type").equals("improve")){
            Type="improve";
            lastLevel2=getIntent().getStringExtra("levelnum");
            lastStage2=getIntent().getStringExtra("stagenum");
        }else {
            Type="Train";
            lastLevel2 = getIntent().getStringExtra("NextLevel");
            lastStage2 = getIntent().getStringExtra("NextStage");
        }
            currentlevel = Integer.parseInt(lastLevel2);
            currentStage = Integer.parseInt(lastStage2);
            level.setText("Level " + currentlevel + " Stage " + currentStage);
            myImageView = (ImageView) findViewById(R.id.imageView4);
            myImageView.setImageResource(textureArrayWin[Integer.parseInt(lastLevel2) - 1][Integer.parseInt(lastStage2) - 1]);
            MotorDiagnosisView1 = new PaintView(this,"motor");
            MotorviewGroup1 = (ViewGroup) findViewById(R.id.MotorTrain);
            MotorviewGroup1.addView(MotorDiagnosisView1);
            if (dialog3 != null)
                dialog3.dismiss();

    }
    public void drawfinish(){
        if(Type.equals("improve")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Good job");
            //builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "FINISH",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            //LoadingShow();
                            Bitmap mydraw=MotorDiagnosisView1.get();
                            ByteArrayOutputStream baos=new ByteArrayOutputStream();
                            mydraw.compress(Bitmap.CompressFormat.PNG,100, baos);
                            byte [] b=baos.toByteArray();
                            String temp= Base64.encodeToString(b, Base64.DEFAULT);
                            EditMotorLevel s=new EditMotorLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp);
                            s.execute("");
                            Intent intent = new Intent();
                            intent.putExtra("action", "FINISH");
                            intent.putExtra("img", temp);
                            setResult(Activity.RESULT_OK, intent);
                            TrainMotor.this.finish();
                        }
                    });
            builder1.setNegativeButton(
                    "RESTART",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                           // LoadingShow();
                            Intent intent = new Intent();
                            intent.putExtra("action", "RESTART");
                            intent.putExtra("username", User.currentUser.getUsername());
                            intent.putExtra("levelnum", lastLevel2);
                            intent.putExtra("stagenum", lastStage2);
                            setResult(Activity.RESULT_OK, intent);
                            TrainMotor.this.finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.setCanceledOnTouchOutside(false);
            alert11.show();
        }
        else if(currentlevel==3){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Good job you finished stage "+currentStage+" if you have enougth stars " +
                    "you will move to next stage if you don't you must improve levels to get more stars.");
            //builder1.setCancelable(true);


            builder1.setPositiveButton(
                    "FINISH",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            //LoadingShow();
                            Bitmap mydraw=MotorDiagnosisView1.get();
                            ByteArrayOutputStream baos=new ByteArrayOutputStream();
                            mydraw.compress(Bitmap.CompressFormat.PNG,100, baos);
                            byte [] b=baos.toByteArray();
                            String temp= Base64.encodeToString(b, Base64.DEFAULT);
                            AddMotorLevel s=new AddMotorLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp);
                            s.execute("");
                            Intent intent = new Intent();
                            intent.putExtra("action", "FINISH");
                            setResult(Activity.RESULT_OK, intent);
                            TrainMotor.this.finish();
                        }
                    });
            builder1.setNegativeButton(
                    "RESTART",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            //LoadingShow();
                            Intent intent = new Intent();
                            intent.putExtra("action", "RESTART");
                            setResult(Activity.RESULT_OK, intent);
                            TrainMotor.this.finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.setCanceledOnTouchOutside(false);
            alert11.show();
        }else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("NICE");
            //builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "RESTART",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            //LoadingShow();
                            Restart();
                        }
                    });
            builder1.setNegativeButton(
                    "NEXT",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            //LoadingShow();
                            Next();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.setCanceledOnTouchOutside(false);
            alert11.show();
        }
    }
    public void LoadingShow(){
        // custom dialog
        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.loadingicon);
        dialog3.setTitle("Loading");
        dialog3.show();
    }
    public void Restart() {
        Intent intent = new Intent();
        intent.putExtra("action", "RESTART");
        setResult(Activity.RESULT_OK, intent);
        TrainMotor.this.finish();
    }
    public void Next(){
        Bitmap mydraw=MotorDiagnosisView1.get();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        mydraw.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        MotorviewGroup1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
               View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
         MotorviewGroup1.layout(0, 0, MotorviewGroup1.getMeasuredWidth(), MotorviewGroup1.getMeasuredHeight());
        AddMotorLevel s=new AddMotorLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp);
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
                //getmotorImg a = new getmotorImg(usr, stage, level);
                //a.execute("");
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
    private class EditMotorLevel extends AsyncTask<String, Void, Boolean> {
        String usr;
        int stars;
        int stage;
        int level;
        String img;

        public EditMotorLevel(String usr, int stars, int stage, int level, String img) {
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
            return dbConnection.editmotorlevel(usr, stars, stage, level, img);
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
        ///////

    }

