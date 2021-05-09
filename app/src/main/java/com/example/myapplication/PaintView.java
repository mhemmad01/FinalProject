package com.example.myapplication;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.diagnosis_model.Diagnosis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PaintView extends View {
    public ViewGroup.LayoutParams params;
    private Path path=new Path();
    private Paint brush=new Paint();
    private Context a;
    private  boolean flag=true;
    public Canvas c;
    public Bitmap scaledSprite=null;
    private int flag2;
    private String Type;
    static int counter=0;
    private Context context;
    static int holdflag=-1;
    static Toast mToast = null;
    private int viewnumber=0;
    static ArrayList<Point> f1;
    static ArrayList<Point> f2;
    private int start1=0,start2=0;
    private Point startpoint1=new Point(),startpoint2=new Point();
    static long starttime=-1;
    private long previoustime=-1;
    private float different_time=100;
    static float percent=0;
    public void setFlag2(int flag2){
        this.flag2=flag2;
    }
    public PaintView(Context context,String Type) {
        super(context);

        if(f1==null){
            f1=new ArrayList<Point>();
        }
        if(f2==null){
            f2=new ArrayList<Point>();
        }
        this.context=context;
        flag=true;
        this.Type=Type;
        a=context;
        this.setDrawingCacheEnabled(true);
        brush.setAntiAlias(true);
        brush.setColor(Color.RED);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);
        params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setDrawingCacheEnabled(true);
        if(counter==1) {
            mToast = Toast.makeText(context, "Don't move up your hand please wait for the second user and for the time to finish", Toast.LENGTH_SHORT);
            mToast.show();
        }

    }
    public PaintView(Context context,String Type,int viewnumber) {
        super(context);
        this.viewnumber=viewnumber;
        if(f1==null){
            f1=new ArrayList<Point>();
        }
        if(f2==null){
            f2=new ArrayList<Point>();
        }
        this.context=context;
        flag=true;
        this.Type=Type;
        a=context;
        this.setDrawingCacheEnabled(true);
        brush.setAntiAlias(true);
        brush.setColor(Color.RED);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);
        params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setDrawingCacheEnabled(true);
        if(counter==1) {
            mToast = Toast.makeText(context, "Don't move up your hand please wait for the second user and for the time to finish", Toast.LENGTH_SHORT);
            mToast.show();
        }

    }
    public void setviewnumber(int i){
        this.viewnumber=i;
    }
    public Bitmap get(){
        return this.getDrawingCache();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(Type.equals("Sync")){
            float pointX = event.getX();
            float pointY = event.getY();

            if (flag == true) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        counter++;
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Time left");
                        builder.setMessage("start time");

                        final AlertDialog alert = builder.create();
                        if(counter==2 && holdflag!=0) {
                            mToast.cancel();
                            new CountDownTimer(4000,1000) {
                                @Override
                                public void onTick(long l) {
                                            if(counter==2) {
                                                alert.setMessage("left: " + l / 1000);
                                                alert.show();
                                            }else{
                                                alert.dismiss();
                                                cancel();
                                            }
                                }

                                @Override
                                public void onFinish() {
                                    alert.dismiss();
                                    holdflag=0;

                                }
                            }.start();

                        }

                        if(counter==1){
                            mToast=Toast.makeText(context, "Don't move up your hand please wait for the second user and for the time to finish", Toast.LENGTH_SHORT);
                            mToast.show();
                        }
                        path.moveTo(pointX, pointY);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if(holdflag==0) {
                            if(starttime==-1) {
                                starttime = System.currentTimeMillis();
                                previoustime = starttime;
                            }
                            if(start1==0&&viewnumber==1){
                                startpoint1.setPointx(pointX);
                                startpoint1.setPointy(pointY);
                                startpoint1.settime(0);
                                start1++;
                            }else if(start2==0 && viewnumber==2){
                                startpoint2.setPointx(pointX);
                                startpoint2.setPointy(pointY);
                                startpoint2.settime(0);
                                start2++;
                            }
                            if(System.currentTimeMillis()-previoustime>=different_time){

                                if(viewnumber==1){
                                    Point current=new Point();
                                    current.setPointx(Math.abs(pointX-startpoint1.getPointx()));
                                    current.setPointy(Math.abs(pointY-startpoint1.getPointy()));
                                    current.settime(System.currentTimeMillis()-starttime);
                                    Log.i("nn2",System.currentTimeMillis()+"xxxxxxxxxxxxx");

                                    f1.add(current);
                                }else if(viewnumber==2){
                                    Point current=new Point();
                                    current.setPointx(Math.abs(pointX-startpoint2.getPointx()));
                                    current.setPointy(Math.abs(pointY-startpoint2.getPointy()));
                                    current.settime(System.currentTimeMillis()-starttime);
                                    f2.add(current);
                                    Log.i("nn0000",System.currentTimeMillis()+"xxxxxxxxxxxxx" + f2.size());

                                }
                                previoustime=System.currentTimeMillis();
                            }
                            Log.i("bb",System.currentTimeMillis()+"");
                            path.lineTo(pointX, pointY);
                        }else{
                            path.moveTo(pointX, pointY);
                        }
                            break;
                    case MotionEvent.ACTION_UP:
                        flag = false;
                         if(flag2==2){
                            counter--;
                            if(counter==0 && holdflag==0){
                                holdflag=-1;
                                DiagnosisSync.Instance.drawfinish();

                            } else if(holdflag!=0){
                                DiagnosisSync.Instance.addviewfun(viewnumber);
                            }
                        }else {
                            counter--;
                            if(counter==0&&holdflag==0){
                                holdflag=-1;//0.9141 0.687 0.7
                                /*
                                Log.i("arraysf1", "onTouchEvent: " + f1.toString());
                                Log.i("arraysf2", "onTouchEvent: " + f2.toString());
                                Log.i("arraysf2", "onTouchEvent:000 ");*/
                                Avg_g();
                                /*
                                5 - 0.647
                                5 - 0.656
                                4 - 0.261
                                3 - 0.204
                                2 -

                                 */
//2021-05-07 02:20:45.727 13402-13402/com.example.myapplication I/arraysf1: onTouchEvent: [0.99920654, 0.0, 16.0., 2.4980469, -1.4981384, 35.0., 5.3855133, -1.9974976, 66.0., 9.228714, -3.6134338, 83.0., 12.713379, -4.901642, 100.0., 18.359558, -7.1406555, 116.0., 24.933426, -8.380127, 133.0., 32.340973, -10.442352, 150.0., 41.552765, -10.986267, 168.0., 52.22061, -10.986267, 183.0., 70.941345, -6.492676, 201.0., 92.257965, 0.74783325, 220.0., 108.871185, 10.560547, 233.0., 122.65631, 20.702942, 250.0., 131.01392, 33.54413, 266.0., 134.13065, 45.003235, 283.0., 135.2851, 55.05777, 300.0., 132.60657, 62.790955, 317.0., 129.89792, 71.91214, 333.0., 124.73505, 83.52252, 350.0., 119.90631, 94.38199, 367.0., 112.53931, 105.864136, 383.0., 103.822876, 116.10745, 400.0., 91.77255, 129.45572, 417.0., 71.59375, 144.1434, 433.0., 51.260162, 157.24081, 451.0., 30.613022, 168.8057, 468.0., 13.783096, 176.56674, 485.0., 0.6930847, 182.46365, 501.0., -9.690582, 185.19308, 519.0., -15.152893, 186.20016, 535.0., -24.503723, 179.04233, 551.0., -36.350296, 166.37552, 569.0., -48.249588, 145.92917, 585.0., -56.455902, 118.35208, 603.0.]

                                TrainSync.Instance.drawfinish();
                            }
                            else if(holdflag!=0){
                                TrainSync.Instance.addviewfun(viewnumber);
                            }
                        }
                        break;
                    default:
                        return false;
                }
            }
            postInvalidate();
            return false;
        }
        else {
            float pointX = event.getX();
            float pointY = event.getY();
            if (flag == true) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        path.moveTo(pointX, pointY);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        path.lineTo(pointX, pointY);
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = false;
                        if (flag2 == 1) {
                            DiagnosisMotor.Instance.drawfinish();
                        } else{
                            TrainMotor.Instance.drawfinish();

                        }
                        break;
                    default:
                        return false;
                }
            }
            postInvalidate();
            return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path,brush);
    }
    public void Avg_g(){
        if(f1.size()==0 && f2.size()==0) {
            percent = 100;
        }
        else if(f1.size()==0 || f2.size()==0) {
            percent = 0;
            Log.i("cccf1", "Avg_g: " + f1.toString());
            Log.i("cccf2", "Avg_g: " + f2.toString());
        }
        else {
            int max = Math.max(f1.size(), f2.size());
            Log.i("cccf1", "Avg_g: " + f1.toString());
            Log.i("cccf2", "Avg_g: " + f2.toString());
            double sum = 0;
            int count = 0;
            double s = 0;
            double max_distance = 0;
            int i=(int)(200 / different_time);
            ArrayList<Boolean> distances = new ArrayList<>();
            ArrayList<Double> percents = new ArrayList<>();
            do{
                s = g(i * different_time);
                if (s > 55)
                    distances.add(false);
                else
                    distances.add(true);
                i++;
                Log.i("fffccc", s + "mm");
            }while(i<max);
            /*
            for (int i = (int) (200 / different_time); i < max; i++) {
                s = g(i * different_time);
                if (s > 60)
                    distances.add(false);
                else
                    distances.add(true);
            /*
            if(s>max_distance){
                max_distance=s;
            }*/

                //distances.add(s);
                //sum=sum+s;

                //if(s!= 0)
                // count++;
            //}

            //Collections.sort(distances);
            for (int j = 0; j < distances.size(); j++) {
                if (distances.get(j) == true) {
                    count++;
                }
            }
            Log.i("mnnnn", distances + "mm");
//            sum=sum+percent_calc(j, j + 5, distances);
//            count++;
//        }
            //sum=0;
            //sum=sum+percent_calc(distances.size()/5, 4*distances.size()/5, distances);
            // sum=sum+percent_calc(4*distances.size()/5, distances.size(), distances);


            /*

             */
            percent = (float) (((float) count / distances.size()) * 100);
        }
    }
    public double percent_calc(int index1,int index2,ArrayList<Double> arr){
        if(index2>arr.size())
            index2=arr.size();
        double max_distance=0;
        double sum=0;
        int count=0;
        for(int i=index1;i<index2;i++) {
            if (arr.get(i) > max_distance) {
                max_distance = arr.get(i);
            }
            sum =sum+arr.get(i);
            count++;
        }
        return 1-(float)((float)sum/((count)*max_distance));
    }
    public double g(float t){
        Point view2=f(f2,t);
        Point view1=f(f1,t);


/*
        double norm_a=Math.sqrt((view1.getPointx()*view1.getPointx())+(view1.getPointy()*view1.getPointy()));
        double norm_b=Math.sqrt((view2.getPointx()*view2.getPointx())+(view2.getPointy()*view2.getPointy()));
        if((norm_a*norm_b)==0){
            return 0;
        }

        return (Math.abs(view1.getPointx()*view2.getPointx())+Math.abs(view1.getPointy()*view2.getPointy()))/(norm_a*norm_b);*/
        if(view2==null || view1==null)
            return 1000;
        Log.i("fffccc",view2.toString()+"--"+view1.toString()+"ttttt"+t);
        return Math.sqrt((view1.getPointx()-view2.getPointx())*(view1.getPointx()-view2.getPointx())+((view1.getPointy()-view2.getPointy())*(view1.getPointy()-view2.getPointy())));
    }

    public Point f(ArrayList<Point> points, float t){
        int roundedt = Math.round(t);
        double min=Math.abs(points.get(0).gettime()-t);
        int index=0;
        if(t>points.get(points.size()-1).gettime()+100){
            return null;
        }
        for(int i=1; i< points.size(); i++){
            if(Math.abs(points.get(i).gettime()-t )<min){
                min=Math.abs(points.get(i).gettime()-t );
                index=i;
            }//14.126
        }
        return points.get(index);//2352.91
        //if(points.size()>0)
       // return points.get(points.size()-1);
       // return null;
    }
    public static float getsyncvalue(){
        return percent;
    }
}
class Point
{
    private float pointx;
    private float pointy;
    private float    time;

    public float getPointx(){
        return pointx;
    }
    public float getPointy(){
        return pointy;
    }
    public float gettime(){
        return time;
    }
    public void setPointx(float pointx){
        this.pointx=pointx;
    }
    public void setPointy(float pointy){
        this.pointy=pointy;
    }
    public void settime(float time){
        this.time=time;
    }


    public String toString(){
        return "  (x="+pointx+", y="+ pointy +", t="+ time +")  ";
    }
};
