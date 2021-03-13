package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{//} implements MyRecyclerViewAdapter.ItemClickListener {

    //MyRecyclerViewAdapter adapter;
    String Job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // PaintView SyncDiagnosisView1=new PaintView(this);
        //PaintView SyncDiagnosisView2=new PaintView(this);
        //setContentView(paintView);
        SetJob("Diagnosed");
        setContentView(R.layout.login);
//        final ViewGroup SyncviewGroup1 = (ViewGroup) findViewById(R.id.view);
//        SyncviewGroup1.addView(SyncDiagnosisView1);
//        final ViewGroup SyncviewGroup2 = (ViewGroup) findViewById(R.id.view3);
//        SyncviewGroup2.addView(SyncDiagnosisView2);
        getSupportActionBar().hide();

/*        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Mhemmad");
        animalNames.add("Ayman");

        animalNames.add("Abdalla");
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.diangosidsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);*/
    }
    public void LogIn(View view) {
    if(Job.equals("Diagnostic")) {
            Intent intent = new Intent(getApplicationContext(), MainDiagnostic.class);
            //startActivity(intent);
            startActivityForResult(intent,1);
        }
        else if(Job.equals("Diagnosed")){
            Intent intent = new Intent(getApplicationContext(), MainDiagnosed.class);
            startActivityForResult(intent, 2);
        }


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
            /*
            String fname =data.getStringExtra("fname");
            String lname = data.getStringExtra("lname");
            String gender = data.getStringExtra("gender");
            if(gender.equals("female")){
                text.setText("Welcome Back Ms. " + fname + ", " + lname);
            }else{
                text.setText("Welcome Back Mr. " + fname + ", " + lname);
            }
            register.setText("again...");*/
        }

    }
    public void SetJob(String Job){
        this.Job=Job;
    }
/*    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }*/
}