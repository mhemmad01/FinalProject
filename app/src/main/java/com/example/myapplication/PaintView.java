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

    /*
This Class is Responsible for Paint View and managing painting and drawing
 */

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
    static int percent=0;
    private int stagenum=0;
    public void setFlag2(int flag2){
        this.flag2=flag2;
    }

    public PaintView(Context context,String Type) {
        super(context);
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
    public PaintView(Context context,String Type,int viewnumber,int stagenum) {
        super(context);
        this.viewnumber=viewnumber;
        this.stagenum=stagenum;
        if(viewnumber==1){
            Log.i("eeeee",System.currentTimeMillis()+"eeee");
            f1=new ArrayList<Point>();
        }
        if(viewnumber==2){
            f2=new ArrayList<Point>();
        }
        starttime=-1;
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
    //Set board number of the object
    public void setviewnumber(int i){
        this.viewnumber=i;
    }
    public Bitmap get(){
        return this.getDrawingCache();
    }
    //Draw on the board function and save draw position point each different_time
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
                                    current.setPointx(pointX-startpoint1.getPointx());
                                    current.setPointy(pointY-startpoint1.getPointy());
                                    current.settime(System.currentTimeMillis()-starttime);
                                    Log.i("nn2",System.currentTimeMillis()+"xxxxxxxxxxxxx");

                                    f1.add(current);
                                }else if(viewnumber==2){
                                    Point current=new Point();
                                    current.setPointx(pointX-startpoint2.getPointx());
                                    current.setPointy(pointY-startpoint2.getPointy());
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
                                Avg_g();
                                DiagnosisSync.Instance.drawfinish();

                            } else if(holdflag!=0){
                                DiagnosisSync.Instance.addviewfun(viewnumber);
                            }
                        }else {
                            counter--;
                            if(counter==0&&holdflag==0){
                                holdflag=-1;//0.9141 0.687 0.7
                                Avg_g();
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
    //Calculate the sync percent of two users
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
                if (s > 55-(5*(stagenum-1)))
                    distances.add(false);
                else
                    distances.add(true);
                i++;
                Log.i("fffccc", s + "mm");
            }while(i<max);

            for (int j = 0; j < distances.size(); j++) {
                if (distances.get(j) == true) {
                    count++;
                }
            }
            Log.i("mnnnn", distances + "mm");
            percent = (int)(((float) count / distances.size()) * 100);
        }
    }

    //calculate the distance between two point at time t
    public double g(float t){
        Point view2=f(f2,t);
        Point view1=f(f1,t);

        if(view2==null || view1==null)
            return 1000;
        Log.i("fffccc",view2.toString()+"--"+view1.toString()+"ttttt"+t);
        return Math.sqrt((view1.getPointx()-view2.getPointx())*(view1.getPointx()-view2.getPointx())+((view1.getPointy()-view2.getPointy())*(view1.getPointy()-view2.getPointy())));
    }
    //return the finger position at time t
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
            }
        }
        return points.get(index);
    }
    //return sync percent value of two users
    public static int getsyncvalue(){
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
