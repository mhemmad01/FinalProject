package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {//} implements MyRecyclerViewAdapter.ItemClickListener {

    //MyRecyclerViewAdapter adapter;
    String Job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PaintView SyncDiagnosisView1=new PaintView(this);
        //PaintView SyncDiagnosisView2=new PaintView(this);
        //setContentView(paintView);
        //SetJob("diagnostic");
        //setContentView(R.layout.login);
        //final ViewGroup SyncviewGroup1 = (ViewGroup) findViewById(R.id.view);
        //SyncviewGroup1.addView(SyncDiagnosisView1);
        //final ViewGroup SyncviewGroup2 = (ViewGroup) findViewById(R.id.view3);
        //SyncviewGroup2.addView(SyncDiagnosisView2);
       // getSupportActionBar().hide();

            Toast.makeText(getApplicationContext(), "Please xxxx...", Toast.LENGTH_SHORT)
                    .show();

        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
       startActivity(myIntent);

    }


    public void LogIn(View view) {
        if (Job.equals("Diagnostic")) {
            Intent intent = new Intent(getApplicationContext(), MainDiagnostic.class);
            startActivity(intent);
        } else if (Job.equals("Diagnosed")) {
            Intent intent = new Intent(getApplicationContext(), MainDiagnosed.class);
            startActivity(intent);
        }


    }

    public void SetJob(String Job) {
        this.Job = Job;
    }
}
/*    @Override
>>>>>>> 3e2f51cea31edb5735622a675e7224b8a28d6406
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }*/