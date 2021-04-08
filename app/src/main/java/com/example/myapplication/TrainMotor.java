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
    AlertDialog dialog;
    private TextView level;
    private ViewGroup MotorviewGroup1;
    private LinearLayout parent;
    Bitmap b1;

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
        /*
        b1 = Bitmap.createBitmap(MotorviewGroup1.getWidth(),
                MotorviewGroup1.getHeight(),
                Bitmap.Config.RGB_565);
        MotorviewGroup1.draw(new Canvas(b1));*/
        //MotorviewGroup1.setDrawingCacheEnabled(true);
       // MotorviewGroup1.buildDrawingCache();
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
       // Intent intent = new Intent();
       // intent.putExtra("action", "NEXT");
       // setResult(Activity.RESULT_OK, intent);
      //  TrainMotor.this.finish();

        MotorviewGroup1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        MotorviewGroup1.layout(0, 0, MotorviewGroup1.getMeasuredWidth(), MotorviewGroup1.getMeasuredHeight());
        Bitmap bitmap3=viewToImage(MotorviewGroup1.getRootView());
       // Bitmap bitmap3=getViewBitmap(MotorviewGroup1);
       // Bitmap bitmap= MotorviewGroup1.getDrawingCache();// creates bitmap and returns the same
       // ByteArrayOutputStream baos=new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG,10, baos);
       // byte [] b=baos.toByteArray();
       // Bitmap bmp=BitmapFactory.decodeByteArray(b , 0, b.length);
        // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // bit.compress(Bitmap.CompressFormat.JPEG, 90, b);
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        image.setImageBitmap(MotorviewGroup1.getDrawingCache());

        /*
        ContentValues values = new ContentValues();
        values.put("img",  b);
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        AddMotorLevel s=new AddMotorLevel(User.currentUser.getUsername(),1,23,3,temp,values);
        s.execute("");
        System.out.println("xxx...");*/

    }
    Bitmap getViewBitmap(View view)
    {
        //Get the dimensions of the view so we can re-layout the view at its current size
        //and create a bitmap of the same size
        int width = view.getWidth();
        int height = view.getHeight();

        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        //Cause the view to re-layout
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        //Create a bitmap backed Canvas to draw the view into
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        //Now that the view is laid out and we have a canvas, ask the view to draw itself into the canvas
        view.draw(c);

        return b;
    }
        private Bitmap viewToImage(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(2000, 1000, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);

        return returnedBitmap;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    private class AddMotorLevel extends AsyncTask<String, Void, Boolean>{
        String usr;
        int stars;
        int stage;
        int level;
        String img;
        ContentValues values;
        public AddMotorLevel(String usr, int stars,int stage,int level,String img,ContentValues values){
            this.usr=usr;
            this.stars=stars;
            this.stage=stage;
            this.img=img;
            this.level=level;
            this.values=values;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
                //    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.addmotorlevel(usr,stars,stage,level,img,values);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Log.i("hhhh","ccc");
                getmotorImg a=new getmotorImg(usr,stage,level);
                a.execute("");
            }else {
                Log.i("hhhh","ffffff");
            }

        }

        private class getmotorImg extends AsyncTask<String, Void, byte[]> {
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
            protected byte[] doInBackground(String... params) {
                return dbConnection.getimg(usr, level, stage);
            }

            @Override
            protected void onPostExecute(byte[] result) {
                if (result!=null) {
                    Log.i("hhhh","eeee");

                    byte[] b=result;//Base64.decode(result,Base64.DEFAULT);
                    Bitmap bit=null;
                    //ByteBuffer byte_buffer = ByteBuffer.wrap(b);
                    //byte_buffer.rewind();
                   // Bitmap bmp = Bitmap.createBitmap(60, 60, Bitmap.Config.ARGB_8888);*/
                    //bmp.copyPixelsFromBuffer(byte_buffer);
                    Bitmap bmp=BitmapFactory.decodeByteArray(b , 0, b.length);
                   // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                   // bit.compress(Bitmap.CompressFormat.JPEG, 90, b);
                   // ImageView image = (ImageView) findViewById(R.id.imageView4);
                   // image.setImageBitmap(bmp);

                    //image.setImageBitmap(bmp);
                } else {
                    Log.i("hhhh", "ffffff");
                }

            }

        }

    }
}
