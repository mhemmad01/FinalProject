package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.diagnosis_model.DiagnosisAdapter;

import java.io.ByteArrayOutputStream;

public class DiagnosisSync extends AppCompatActivity {
    int imgnum;
    int diagnosisnum;
    PaintView SyncDiagnosisView1;
    PaintView SyncDiagnosisView2;
    TextView level;
    private ViewGroup SyncviewGroup1;
    private ViewGroup SyncviewGroup2;

    static Dialog dialog3=null;
    static  DiagnosisSync Instance;
    public static int[] textureArrayWin = {
            R.drawable.qw,
            R.drawable.ss2,
            R.drawable.qw,
            R.drawable.ss2,
            R.drawable.qw,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView myImageView;
        Instance=this;
        setContentView(R.layout.diagnosissync);
        //getSupportActionBar().setTitle("Training Motor");
        final ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_toolbar));//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.abs_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Diagnosis Sync");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
        level=(TextView)findViewById(R.id.textView8);
        imgnum = Integer.parseInt(getIntent().getStringExtra("imgnum"));
        diagnosisnum=Integer.parseInt(getIntent().getStringExtra("diagnosisnum"));
        level.setText("Image "+imgnum +" Diagnosis "+diagnosisnum);
        System.out.println(imgnum);
        myImageView = (ImageView)findViewById(R.id.imageView8);
        myImageView.setImageResource(textureArrayWin[imgnum-1]);
        SyncDiagnosisView1=new PaintView(this,"Sync");
        SyncDiagnosisView1.setFlag2(2);
        SyncDiagnosisView2=new PaintView(this,"Sync");
        SyncDiagnosisView2.setFlag2(2);
        SyncviewGroup1 = (ViewGroup) findViewById(R.id.view);
        SyncviewGroup1.addView(SyncDiagnosisView1);
        SyncviewGroup2 = (ViewGroup) findViewById(R.id.view3);
        SyncviewGroup2.addView(SyncDiagnosisView2);
        if(dialog3!=null)
            dialog3.dismiss();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
    public void drawfinish(){
        int percent=0;
        if(imgnum==5){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Good job you finished the diagnosis");
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
                            DiagnosisSync.AddSyncLevel s=new DiagnosisSync.AddSyncLevel(SelectDiagnosisMode.DiagnosedUsername,diagnosisnum,imgnum,temp1,temp2,percent);
                            s.execute("");
                            Intent intent = new Intent();
                            intent.putExtra("action", "FINISHSYNC");
                            setResult(Activity.RESULT_OK, intent);
                            DiagnosisSync.this.finish();
                        }
                    });
            builder1.setNegativeButton(
                    "RESTART",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                            LoadingShow();
                            Intent intent = new Intent();
                            intent.putExtra("action", "RESTARTSYNC");
                            setResult(Activity.RESULT_OK, intent);
                            DiagnosisSync.this.finish();
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
                            Next(percent);
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
        intent.putExtra("action", "RESTARTSYNC");
        setResult(Activity.RESULT_OK, intent);
        DiagnosisSync.this.finish();
    }
    public void Next(int percent){
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
        DiagnosisSync.AddSyncLevel s=new DiagnosisSync.AddSyncLevel(SelectDiagnosisMode.DiagnosedUsername,diagnosisnum,imgnum,temp1,temp2,percent);
        s.execute("");
        Intent intent = new Intent();
        intent.putExtra("action", "NEXTSYNC");
        setResult(Activity.RESULT_OK, intent);
        DiagnosisSync.this.finish();

    }
    private class AddSyncLevel extends AsyncTask<String, Void, Boolean> {
        String usr;
        int diagnosisnum;
        int imgnum;
        String img1;
        String img2;
        int percent;
        public AddSyncLevel(String usr, int diagnosisnum, int imgnum, String img1,String img2,int percent) {
            this.usr = usr;
            this.diagnosisnum = diagnosisnum;
            this.img1 = img1;
            this.img2 = img2;
            this.imgnum = imgnum;
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
            System.out.println(usr+""+diagnosisnum+""+imgnum+""+percent);
            return dbConnection.adddiagnosisSync(usr, diagnosisnum, imgnum, img1,img2,percent);
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

