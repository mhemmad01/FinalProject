package com.example.myapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
    public void setFlag2(int flag2){
        this.flag2=flag2;
    }
    public PaintView(Context context) {
        super(context);
        flag=true;
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
        float pointX=event.getX();
        float pointY=event.getY();
        if(flag==true) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(pointX, pointY);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(pointX, pointY);
                    break;
                case MotionEvent.ACTION_UP:
                    flag=false;
                    if(flag2==1){
                        DiagnosisMotor.Instance.drawfinish();
                    }else {
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

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path,brush);
    }
}
