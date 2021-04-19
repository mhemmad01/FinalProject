package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class TrainSync extends AppCompatActivity {
    public static TrainSync Instance;
    static String Type=null;
    public static Dialog dialog3=null;
    PaintView SyncDiagnosisView1;
    PaintView SyncDiagnosisView2;
    private String lastLevel2;
    private String lastStage2;
    AlertDialog dialog;
    private TextView level;
    private ViewGroup SyncviewGroup1;
    private ViewGroup SyncviewGroup2;
    private LinearLayout parent;
    Bitmap b1;
    private int currentlevel;
    private int currentStage;
    private int stars;
    public static int[][] textureArrayWin = {
            {R.drawable.qw,
                    R.drawable.ss2,
                    R.drawable.qw,
            },
            {
                    R.drawable.qw,
                    R.drawable.ss2,
                    R.drawable.qw,
            },
            {
                    R.drawable.qw,
                    R.drawable.ss2,
                    R.drawable.qw,
            }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traningsync);
        Instance=this;
        ImageView myImageView;
        final ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_toolbar));//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.abs_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Training Sync");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
        level=(TextView)findViewById(R.id.textView8);
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
        myImageView = (ImageView) findViewById(R.id.imageView17);
        myImageView.setImageResource(textureArrayWin[Integer.parseInt(lastLevel2) - 1][Integer.parseInt(lastStage2) - 1]);
        SyncDiagnosisView1=new PaintView(this,"Sync");
        SyncDiagnosisView1.setFlag2(2);
        SyncDiagnosisView2=new PaintView(this,"Sync");
        SyncDiagnosisView2.setFlag2(2);
        SyncviewGroup1 = (ViewGroup) findViewById(R.id.SyncTrain1);
        SyncviewGroup1.addView(SyncDiagnosisView1);
        SyncviewGroup2 = (ViewGroup) findViewById(R.id.SyncTrain2);
        SyncviewGroup2.addView(SyncDiagnosisView2);
        if (dialog3 != null)
            dialog3.dismiss();
    }
    public void drawfinish(){
        int percent=0;
        if(Type.equals("improve")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Good job");
            //builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "FINISH",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            LoadingShow();
                            Bitmap mydraw1=SyncDiagnosisView1.get();
                            Bitmap mydraw2=SyncDiagnosisView2.get();
                            ByteArrayOutputStream baos1=new ByteArrayOutputStream();
                            mydraw1.compress(Bitmap.CompressFormat.PNG,100, baos1);
                            byte [] b1=baos1.toByteArray();
                            String temp1= Base64.encodeToString(b1, Base64.DEFAULT);
                            ByteArrayOutputStream baos2=new ByteArrayOutputStream();
                            mydraw2.compress(Bitmap.CompressFormat.PNG,100, baos2);
                            byte [] b2=baos2.toByteArray();
                            String temp2= Base64.encodeToString(b2, Base64.DEFAULT);
                            TrainSync.EditMotorLevel s1=new TrainSync.EditMotorLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp1,temp2,percent);
                            s1.execute("");
                            Intent intent = new Intent();
                            intent.putExtra("action", "FINISH");
                            intent.putExtra("img1", temp1);
                            intent.putExtra("img2", temp2);
                            setResult(Activity.RESULT_OK, intent);
                            TrainSync.this.finish();
                        }
                    });
            builder1.setNegativeButton(
                    "RESTART",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            LoadingShow();
                            Intent intent = new Intent();
                            intent.putExtra("action", "RESTART");
                            intent.putExtra("username", User.currentUser.getUsername());
                            intent.putExtra("levelnum", lastLevel2);
                            intent.putExtra("stagenum", lastStage2);
                            setResult(Activity.RESULT_OK, intent);
                            TrainSync.this.finish();
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
                            LoadingShow();
                            Bitmap mydraw1=SyncDiagnosisView1.get();
                            Bitmap mydraw2=SyncDiagnosisView2.get();
                            ByteArrayOutputStream baos1=new ByteArrayOutputStream();
                            mydraw1.compress(Bitmap.CompressFormat.PNG,100, baos1);
                            byte [] b1=baos1.toByteArray();
                            String temp1= Base64.encodeToString(b1, Base64.DEFAULT);
                            ByteArrayOutputStream baos2=new ByteArrayOutputStream();
                            mydraw2.compress(Bitmap.CompressFormat.PNG,100, baos2);
                            byte [] b2=baos2.toByteArray();
                            String temp2= Base64.encodeToString(b2, Base64.DEFAULT);
                            TrainSync.AddMotorLevel s=new TrainSync.AddMotorLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp1,temp2,percent);
                            s.execute("");
                            Intent intent = new Intent();
                            intent.putExtra("action", "FINISH");
                            setResult(Activity.RESULT_OK, intent);
                            TrainSync.this.finish();
                        }
                    });
            builder1.setNegativeButton(
                    "RESTART",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            LoadingShow();
                            Intent intent = new Intent();
                            intent.putExtra("action", "RESTART");
                            setResult(Activity.RESULT_OK, intent);
                            TrainSync.this.finish();
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
                            LoadingShow();
                            Restart();
                        }
                    });
            builder1.setNegativeButton(
                    "NEXT",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            LoadingShow();
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
        TrainSync.this.finish();
    }
    public void Next(){
        int percent1=0;
        Bitmap mydraw1=SyncDiagnosisView1.get();
        Bitmap mydraw2=SyncDiagnosisView2.get();
        ByteArrayOutputStream baos1=new ByteArrayOutputStream();
        mydraw1.compress(Bitmap.CompressFormat.PNG,100, baos1);
        byte [] b1=baos1.toByteArray();
        String temp1= Base64.encodeToString(b1, Base64.DEFAULT);
        ByteArrayOutputStream baos2=new ByteArrayOutputStream();
        mydraw2.compress(Bitmap.CompressFormat.PNG,100, baos2);
        byte [] b2=baos2.toByteArray();
        String temp2= Base64.encodeToString(b2, Base64.DEFAULT);
        SyncviewGroup1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        SyncviewGroup1.layout(0, 0, SyncviewGroup1.getMeasuredWidth(), SyncviewGroup1.getMeasuredHeight());
        SyncviewGroup2.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        SyncviewGroup2.layout(0, 0, SyncviewGroup2.getMeasuredWidth(), SyncviewGroup2.getMeasuredHeight());
        TrainSync.AddMotorLevel s=new TrainSync.AddMotorLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp1,temp2,percent1);
        s.execute("");
        Intent intent = new Intent();
        intent.putExtra("action", "NEXT");
        setResult(Activity.RESULT_OK, intent);
        TrainSync.this.finish();

    }
    private class AddMotorLevel extends AsyncTask<String, Void, Boolean> {
        String usr;
        int stars;
        int stage;
        int level;
        String img1;
        String img2;
        int percent;
        public AddMotorLevel(String usr, int stars, int stage, int level, String img1,String img2,int percent) {
            this.usr = usr;
            this.stars = stars;
            this.stage = stage;
            this.img1 = img1;
            this.img2 = img2;
            this.level = level;
            this.percent=percent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.addmotorlevelSync(usr, stars, stage, level, img1,img2,percent);
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
    private class EditMotorLevel extends AsyncTask<String, Void, Boolean> {
        String usr;
        int stars;
        int stage;
        int level;
        String img1;
        String img2;
        int percent;
        public EditMotorLevel(String usr, int stars, int stage, int level, String img1,String img2,int percent) {
            this.usr = usr;
            this.stars = stars;
            this.stage = stage;
            this.img1 = img1;
            this.img2 = img2;
            this.level = level;
            this.percent=percent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.editmotorlevelSync(usr, stars, stage, level, img1,img2,percent);
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
}
