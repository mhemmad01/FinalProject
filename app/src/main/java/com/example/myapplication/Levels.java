package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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


    public void gotoMyDiagnosed(View view) {
        Intent myIntent = new Intent(this, MyDiagnosed.class);
        this.startActivity(myIntent);
    }





    @Override
    public void OnClickEvent() {

    }
}