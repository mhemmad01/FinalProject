package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import java.util.ArrayList;

//The user start train on sync mode
public class TrainSync extends AppCompatActivity {
    public static TrainSync Instance;
    static String Type=null;
    public static Dialog dialog3=null;
    static PaintView SyncTrainView1;
    static PaintView SyncTrainView2;
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
    static int counter222=0;
    //matrix of sync stages and levels
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
        setContentView(R.layout.traningsync);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
        myImageView.setImageResource(textureArrayWin[Integer.parseInt(lastStage2) - 1][Integer.parseInt(lastLevel2) - 1]);
        SyncTrainView1=new PaintView(this,"Sync",1,currentStage);
        SyncTrainView1.setFlag2(1);
        //SyncTrainView1.setviewnumber(1);
        SyncTrainView2=new PaintView(this,"Sync",2,currentStage);
        SyncTrainView2.setFlag2(1);
        //SyncTrainView2.setviewnumber(2);
        SyncviewGroup1 = (ViewGroup) findViewById(R.id.SyncTrain1);
        SyncviewGroup1.addView(SyncTrainView1);
        SyncviewGroup2 = (ViewGroup) findViewById(R.id.SyncTrain2);
        SyncviewGroup2.addView(SyncTrainView2);
        if (dialog3 != null)
            dialog3.dismiss();
    }
    //change the viewnumber fun of board for the paintview object
    public void addviewfun(int viewnumber){
        if(viewnumber==1){
            SyncviewGroup1.removeView(SyncTrainView1);
            SyncTrainView1=new PaintView(this,"Sync",1,currentStage);
            SyncTrainView1.setviewnumber(1);
            SyncviewGroup1.addView(SyncTrainView1);
        }else{
            SyncviewGroup2.removeView(SyncTrainView2);
            SyncTrainView2=new PaintView(this,"Sync",2,currentStage);
            SyncTrainView2.setviewnumber(2);
            SyncviewGroup2.addView(SyncTrainView2);
        }
    }
    //Both users move up there finger and finished the draw
    public void drawfinish(){
        int percent=PaintView.getsyncvalue();
        TextView percent_view=(findViewById(R.id.textView12));
        percent_view.setText(percent+"%");
        if(Type.equals("improve")){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            if(percent >90)
                builder1.setMessage("EXCELLENT");
            else if(percent >75)
                builder1.setMessage("very good");
            else if(percent >50)
                builder1.setMessage("good");
            else
                builder1.setMessage("You can do better");
            //builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "FINISH",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            //LoadingShow();
                            Bitmap mydraw1=SyncTrainView1.get();
                            Bitmap mydraw2=SyncTrainView2.get();
                            ByteArrayOutputStream baos1=new ByteArrayOutputStream();
                            mydraw1.compress(Bitmap.CompressFormat.PNG,100, baos1);
                            byte [] b1=baos1.toByteArray();
                            String temp1= Base64.encodeToString(b1, Base64.DEFAULT);
                            ByteArrayOutputStream baos2=new ByteArrayOutputStream();
                            mydraw2.compress(Bitmap.CompressFormat.PNG,100, baos2);
                            byte [] b2=baos2.toByteArray();
                            String temp2= Base64.encodeToString(b2, Base64.DEFAULT);
                            TrainSync.EditSyncLevel s1=new TrainSync.EditSyncLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp1,temp2,percent);
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
                            //LoadingShow();
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
                            //LoadingShow();
                            Bitmap mydraw1=SyncTrainView1.get();
                            Bitmap mydraw2=SyncTrainView2.get();
                            ByteArrayOutputStream baos1=new ByteArrayOutputStream();
                            mydraw1.compress(Bitmap.CompressFormat.PNG,100, baos1);
                            byte [] b1=baos1.toByteArray();
                            String temp1= Base64.encodeToString(b1, Base64.DEFAULT);
                            ByteArrayOutputStream baos2=new ByteArrayOutputStream();
                            mydraw2.compress(Bitmap.CompressFormat.PNG,100, baos2);
                            byte [] b2=baos2.toByteArray();
                            String temp2= Base64.encodeToString(b2, Base64.DEFAULT);
                            TrainSync.AddSyncLevel s=new TrainSync.AddSyncLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp1,temp2,percent);
                            s.execute("");
                            //TrainSync.SaveSyncTrainData s2=new TrainSync.SaveSyncTrainData(User.currentUser.getUsername(),currentStage,currentlevel,PaintView.f1,PaintView.f2);
                            //s2.execute("");
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
                            //LoadingShow();
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
            if(percent >90)
                builder1.setMessage("EXCELLENT");
            else if(percent >75)
                builder1.setMessage("very good");
            else if(percent >50)
                builder1.setMessage("good");
            else
                builder1.setMessage("You can do better");
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
    //display loading icon
    public void LoadingShow(){
        // custom dialog
        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.loadingicon);
        dialog3.setTitle("Loading");
        dialog3.show();
    }
    //The user could restart the current level
    public void Restart() {
        Intent intent = new Intent();
        intent.putExtra("action", "RESTART");
        setResult(Activity.RESULT_OK, intent);
        TrainSync.this.finish();
    }
    //The user move to the next level
    public void Next(){
        int percent1=PaintView.getsyncvalue();
        Bitmap mydraw1=SyncTrainView1.get();
        Bitmap mydraw2=SyncTrainView2.get();
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
        TrainSync.AddSyncLevel s=new TrainSync.AddSyncLevel(User.currentUser.getUsername(),0,currentStage,currentlevel,temp1,temp2,percent1);
        s.execute("");
        //TrainSync.SaveSyncTrainData s2=new TrainSync.SaveSyncTrainData(User.currentUser.getUsername(),currentStage,currentlevel,PaintView.f1,PaintView.f2);
        //s2.execute("");

        Intent intent = new Intent();
        intent.putExtra("action", "NEXT");
        setResult(Activity.RESULT_OK, intent);
        TrainSync.this.finish();

    }
    //Thread to save the sync train in DB
    private class AddSyncLevel extends AsyncTask<String, Void, Boolean> {
        String usr;
        int stars;
        int stage;
        int level;
        String img1;
        String img2;
        int percent;
        public AddSyncLevel(String usr, int stars, int stage, int level, String img1,String img2,int percent) {
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
            return dbConnection.addtrainlevelSync(usr, stars, stage, level, img1,img2,percent);
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
    //Thread to change the result of the specific sync train level and stage at DB
    private class EditSyncLevel extends AsyncTask<String, Void, Boolean> {
        String usr;
        int stars;
        int stage;
        int level;
        String img1;
        String img2;
        int percent;
        public EditSyncLevel(String usr, int stars, int stage, int level, String img1,String img2,int percent) {
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
            return dbConnection.edittrainlevelSync(usr, stars, stage, level, img1,img2,percent);
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
    /*
    private class SaveSyncTrainData extends AsyncTask<String, Void, Boolean> {
        String usr;
        int stage;
        int level;
        ArrayList<Point> f1;
        ArrayList<Point> f2;
        public SaveSyncTrainData(String usr, int stage, int level, ArrayList<Point> f1,ArrayList<Point> f2) {
            this.usr = usr;
            this.stage = stage;
            this.level = level;
            this.f1=f1;
            this.f2=f2;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            Log.i("gg",usr+","+stage+","+level+","+f1.size()+","+f2.size());
            return dbConnection.savesynctraindata(usr,stage,level,f1,f2);
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
    }*/
}
