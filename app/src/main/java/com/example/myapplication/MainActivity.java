package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{//} implements MyRecyclerViewAdapter.ItemClickListener {

    //MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaintView SyncDiagnosisView1=new PaintView(this);
        PaintView SyncDiagnosisView2=new PaintView(this);
        //setContentView(paintView);
        setContentView(R.layout.diagnosissync);
        final ViewGroup SyncviewGroup1 = (ViewGroup) findViewById(R.id.view);
        SyncviewGroup1.addView(SyncDiagnosisView1);
        final ViewGroup SyncviewGroup2 = (ViewGroup) findViewById(R.id.view3);
        SyncviewGroup2.addView(SyncDiagnosisView2);
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

/*    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }*/
}