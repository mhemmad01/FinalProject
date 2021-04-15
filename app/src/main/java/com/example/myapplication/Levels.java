package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.improvelevel_model.MotorResult;
import com.example.myapplication.improvelevel_model.MotorResultAdapter;
import com.example.myapplication.improvelevel_model.MotorResultViewModel;

import java.util.ArrayList;

public class Levels extends AppCompatActivity implements ImproveLevelsFragment.ClickHandler {



    RecyclerView rvMotorResult;
    MotorResultViewModel model;
    public static ArrayList<MotorResult> motorResults;
    public static MotorResultAdapter adapter;
    public static Context context;
    public static Levels instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
        final ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_toolbar));//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.abs_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Improve Levels");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
        instance=this;
        context=this;
        if(MotorResult.selected==null) {
            MotorResult.selected = new ArrayList<>();
        }
        motorResults = MotorResult.selected;
        adapter = new MotorResultAdapter(motorResults);
        rvMotorResult = (RecyclerView) findViewById(R.id.MotorLevels1);
        model =new ViewModelProvider(this).get(MotorResultViewModel.class);
        model.init(motorResults);
        model.getResults().observe(this, diagnosedListUpdateObserver);
    }


    Observer<ArrayList<MotorResult>> diagnosedListUpdateObserver = new Observer<ArrayList<MotorResult>>() {
        @Override
        public void onChanged(ArrayList<MotorResult> motorResults) {
            adapter = new MotorResultAdapter(motorResults);
            rvMotorResult.setLayoutManager(new LinearLayoutManager(context));
            rvMotorResult.setAdapter(adapter);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
    public void gotoMyDiagnosed(View view) {
        Intent myIntent = new Intent(this, MyDiagnosed.class);
        this.startActivity(myIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(data == null)
            return;
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 1
        if(requestCode==1)
        {
            String action =data.getStringExtra("action");
            String username =data.getStringExtra("username");
            String levelnum =data.getStringExtra("levelnum");
            String stagenum =data.getStringExtra("stagenum");

            if(action.equals("RESTART")){
                Intent intent = new Intent(Levels.instance, TrainMotor.class);
                intent.putExtra("Type","improve");
                intent.putExtra("username", username);
                intent.putExtra("levelnum", levelnum);
                intent.putExtra("stagenum", stagenum);
                Levels.instance.startActivityForResult(intent, 1);
            }
            else if (action.equals("FINISH")){
                String img=data.getStringExtra("img");
                byte[] b= Base64.decode(img,Base64.DEFAULT);
                Bitmap bmp= BitmapFactory.decodeByteArray(b , 0, b.length);
                MotorResultAdapter.toImprove.img.setImageBitmap(bmp);
                if(TrainMotor.dialog3!=null)
                    TrainMotor.dialog3.dismiss();

                //previous_stage_stars=5;
            }
        }
    }



    @Override
    public void OnClickEvent() {

    }
}