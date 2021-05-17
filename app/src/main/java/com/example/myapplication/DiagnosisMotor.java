package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import com.example.myapplication.diagnosis_model.Diagnosis;
import com.example.myapplication.diagnosis_model.DiagnosisAdapter;

import java.io.ByteArrayOutputStream;

public class DiagnosisMotor extends AppCompatActivity {
    int imgnum;
    int diagnosisnum;
    PaintView MotorDiagnosisView1;
    TextView level;
    private ViewGroup MotorviewGroup1;
    static Dialog dialog3=null;
    static  DiagnosisMotor Instance;
    public static int[] textureArrayWin = {
                    R.drawable.level3_stage1,
                    R.drawable.level1_stage2,
                    R.drawable.level2_stage2,
                    R.drawable.level3_stage2,
                    R.drawable.level1_stage3,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView myImageView;
        Instance=this;
        setContentView(R.layout.diagnsismotor);
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
        textviewTitle.setText("Diagnosis Motor");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
        level=(TextView)findViewById(R.id.textView12);
        imgnum = Integer.parseInt(getIntent().getStringExtra("imgnum"));
        diagnosisnum=Integer.parseInt(getIntent().getStringExtra("diagnosisnum"));
        level.setText("Image "+imgnum +" Diagnosis "+diagnosisnum);
        myImageView = (ImageView)findViewById(R.id.imageView4);
        myImageView.setImageResource(textureArrayWin[imgnum-1]);
        MotorDiagnosisView1=new PaintView(this,"motor");
        MotorDiagnosisView1.setFlag2(1);
        MotorviewGroup1 = (ViewGroup) findViewById(R.id.MotorDiagnosis);
        MotorviewGroup1.addView(MotorDiagnosisView1);
        if(dialog3!=null)
            dialog3.dismiss();
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }*/
    public void drawfinish(){
        if(imgnum==5){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Good job you finished the diagnosis");
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
                            AddMotorDiagnosisImg s=new AddMotorDiagnosisImg(SelectDiagnosisMode.DiagnosedUsername,diagnosisnum,imgnum,temp);
                            s.execute("");
                            Intent intent = new Intent();
                            intent.putExtra("action", "FINISH");
                            setResult(Activity.RESULT_OK, intent);
                            DiagnosisMotor.this.finish();
                            if(DiagnosisAdapter.last!=null)
                            DiagnosisAdapter.last.setBackgroundColor(Color.BLACK);
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
                            DiagnosisMotor.this.finish();
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
                           // LoadingShow();
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
        DiagnosisMotor.this.finish();
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
        AddMotorDiagnosisImg s=new AddMotorDiagnosisImg(SelectDiagnosisMode.DiagnosedUsername,diagnosisnum,imgnum,temp);
        s.execute("");
        Intent intent = new Intent();
        intent.putExtra("action", "NEXT");
        setResult(Activity.RESULT_OK, intent);
        DiagnosisMotor.this.finish();

    }
    private class AddMotorDiagnosisImg extends AsyncTask<String, Void, Boolean> {
        String usr;
        int diagnosisnum;
        int imgnum;
        String img;

        public AddMotorDiagnosisImg(String usr, int diagnosisnum, int imgnum, String img) {
            this.usr = usr;
            this.diagnosisnum = diagnosisnum;
            this.img = img;
            this.imgnum = imgnum;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.addmotordiagnosisimg(usr, diagnosisnum, imgnum, img);
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
