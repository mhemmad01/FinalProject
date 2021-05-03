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
                                holdflag=-1;
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
}
