package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.diagnosed_model.Diagnosed;
import com.example.myapplication.diagnosed_model.DiagnosedAdapter;
import com.example.myapplication.diagnosed_model.DiagnosedViewModel;

import java.util.ArrayList;

public class MyDiagnosed extends AppCompatActivity{
        RecyclerView rvDiagnosed;
        DiagnosedViewModel model;
        public static ArrayList<Diagnosed> diagnoseds;
        public static DiagnosedAdapter adapter;
        public static Context context;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.mydiagnosed);
            getSupportActionBar().hide();

            diagnoseds = User.diagnoseds;
            adapter = new DiagnosedAdapter(diagnoseds);
            rvDiagnosed = (RecyclerView) findViewById(R.id.diagnosedView);
            model =new ViewModelProvider(this).get(DiagnosedViewModel.class);
            model.init(diagnoseds);
            model.getDiagnoseds().observe(this, diagnosedListUpdateObserver);
        }


    Observer<ArrayList<Diagnosed>> diagnosedListUpdateObserver = new Observer<ArrayList<Diagnosed>>() {
        @Override
        public void onChanged(ArrayList<Diagnosed> diagnosedArrayList) {
            adapter = new DiagnosedAdapter(diagnosedArrayList);
            rvDiagnosed.setLayoutManager(new LinearLayoutManager(context));
            rvDiagnosed.setAdapter(adapter);

        }
    };

    }