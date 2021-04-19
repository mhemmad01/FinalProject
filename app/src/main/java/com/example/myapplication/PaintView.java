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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
                        if(counter==2) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Time left");
                            builder.setMessage("start time");

                            final AlertDialog alert = builder.create();
                            alert.show();
                            new CountDownTimer(3000, 1000) {
                                @Override
                                public void onTick(long l) {
                                    alert.setMessage("left: " + l);
                                }

                                @Override
                                public void onFinish() {
                                    alert.dismiss();
                                }
                            }.start();
                        }
                        path.moveTo(pointX, pointY);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        path.lineTo(pointX, pointY);
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = false;
                        if (flag2 == 1) {
                            DiagnosisMotor.Instance.drawfinish();
                        } else {
                            counter--;
                            if(counter==0){
                                TrainSync.Instance.drawfinish();
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
