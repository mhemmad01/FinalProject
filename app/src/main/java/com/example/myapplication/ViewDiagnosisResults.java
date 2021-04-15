package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.diagnosed_model.Diagnosed;
import com.example.myapplication.diagnosed_model.DiagnosedAdapter;
import com.example.myapplication.diagnosed_model.DiagnosedViewModel;
import com.example.myapplication.trainingresultmotor.MotorResult;
import com.example.myapplication.trainingresultmotor.MotorResultAdapter;
import com.example.myapplication.trainingresultmotor.MotorResultViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ViewDiagnosisResults extends AppCompatActivity implements TrainingResultsFragment.ClickHandler,DiagnosisResultsFragment.ClickHandler {



    RecyclerView rvMotorResult;
    MotorResultViewModel model;
    Spinner sp1;

    TextView scoreT;
    TextView totalScoreT;

    public static ArrayList<MotorResult> motorResults;
    public static MotorResultAdapter adapter;
    public static Context context;
    public static ViewDiagnosisResults instance;

    RecyclerView rvMotorResultD;
    com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResultViewModel modelD;
    public static ArrayList<com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult> motorResultsD;
    public static com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResultAdapter adapterD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosticresults);
        ((TextView) findViewById(R.id.selectedDiagnosedUsername)).setText("Diagnosed: " + Diagnosed.selected.getFullName());
        instance = this;
        if (MotorResult.selected == null) {
            MotorResult.selected = new ArrayList<>();
        }


        motorResults = MotorResult.selected;
        adapter = new MotorResultAdapter(motorResults);
        rvMotorResult = (RecyclerView) findViewById(R.id.MotorResults);
        model = new ViewModelProvider(this).get(MotorResultViewModel.class);
        model.init(motorResults);
        model.getResults().observe(this, diagnosedListUpdateObserver);

        com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected = new ArrayList<>();

        ((Spinner) findViewById(R.id.diagnosisSpinner)).setAdapter(
                 new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosisIds));


        sp1= (Spinner) findViewById(R.id.diagnosisSpinner);
        scoreT= (TextView) findViewById(R.id.scoreTextview);
        totalScoreT= (TextView) findViewById(R.id.totalScoreTextview);

        sp1.setVisibility(View.INVISIBLE);
        scoreT.setVisibility(View.INVISIBLE);
        totalScoreT.setVisibility(View.INVISIBLE);

        totalScoreT.setText("Total Score: "+com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.totalScore+"/"+com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.total);


        ((Spinner) findViewById(R.id.diagnosisSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected = com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosis.get(com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosisIds.get(position));
                if(adapterD!=null&&com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected!=null) {
                    adapterD.motorResults = com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected;
                    adapterD.notifyDataSetChanged();
                }

                if(com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected!=null){
                    int count=0;
                    int count1=0;
                    for(com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult t : com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected){
                        if(t.score>0){
                            count++;
                        }
                        count1++;
                    }
                    scoreT.setText("Score: "+count+"/"+count1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

                ((TabLayout)findViewById(R.id.tabLayout)).addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               if(tab.getText().toString().equals("Trainings")){
                   motorResults = MotorResult.selected;
                   adapter = new MotorResultAdapter(motorResults);
                   rvMotorResult = (RecyclerView) findViewById(R.id.MotorResults);
                   model =new ViewModelProvider(ViewDiagnosisResults.this).get(MotorResultViewModel.class);
                   model.init(motorResults);
                   model.getResults().observe(ViewDiagnosisResults.this, diagnosedListUpdateObserver);
                   sp1.setVisibility(View.INVISIBLE);
                   scoreT.setVisibility(View.INVISIBLE);
                   totalScoreT.setVisibility(View.INVISIBLE);
               }else {
                   //viewDiagnosisResults(null);
                   motorResultsD = com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected;
                   adapterD = new com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResultAdapter(motorResultsD);
                   rvMotorResultD = (RecyclerView) findViewById(R.id.MotorResults);
                   modelD =new ViewModelProvider(ViewDiagnosisResults.this).get(com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResultViewModel.class);
                   modelD.init(motorResultsD);
                   modelD.getResults().observe(ViewDiagnosisResults.this, diagnosedListUpdateObserverD);
                   sp1.setVisibility(View.VISIBLE);
                   scoreT.setVisibility(View.VISIBLE);
                   totalScoreT.setVisibility(View.VISIBLE);

               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    Observer<ArrayList<MotorResult>> diagnosedListUpdateObserver = new Observer<ArrayList<MotorResult>>() {
        @Override
        public void onChanged(ArrayList<MotorResult> motorResults) {
            adapter = new MotorResultAdapter(motorResults);
            rvMotorResult.setLayoutManager(new LinearLayoutManager(context));
            rvMotorResult.setAdapter(adapter);

        }
    };
    Observer<ArrayList<com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult>> diagnosedListUpdateObserverD = new Observer<ArrayList<com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult>>() {
        @Override
        public void onChanged(ArrayList<com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult> motorResults) {
            adapterD = new com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResultAdapter(motorResults);
            rvMotorResultD.setLayoutManager(new LinearLayoutManager(context));
            rvMotorResultD.setAdapter(adapterD);

        }
    };


    public void gotoMyDiagnosed(View view) {
        Intent myIntent = new Intent(this, MyDiagnosed.class);
        this.startActivity(myIntent);
    }

    public void viewTrainingResults(View view) {

        Log.i("yyy", "viewDiagnosisResults: ");

    }

    public void viewDiagnosisResults(View view) {
//        DiagnosisResultsFragment fragB = new DiagnosisResultsFragment();
//        getSupportFragmentManager().beginTransaction().
//                add(R.id.fragContainer, fragB).//add on top of the static fragment
//                addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
//                commit();
//        getSupportFragmentManager().executePendingTransactions();
        Log.i("xxx", "viewDiagnosisResults: ");
    }


    private class UpdateMotorStars extends AsyncTask<MotorResult, Void, Boolean> {
        ArrayList<MotorResult> toUpdate;
        public UpdateMotorStars(ArrayList<MotorResult> toUpdate){
            this.toUpdate=toUpdate;
        }

        @Override
        protected Boolean doInBackground(MotorResult... motorResults) {
            boolean toreturn=true;
            for (int i=0; i< toUpdate.size(); i++) {
                toreturn&=dbConnection.updateMotorResultStars(toUpdate.get(i));
            }
            return toreturn;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             Toast.makeText(ViewDiagnosisResults.this, "Please wait...", Toast.LENGTH_SHORT)
                .show();
        }



        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(ViewDiagnosisResults.this, "Error", Toast.LENGTH_SHORT)
                        .show();
            }

        }

    }

    public void saveAll(View view) {
        UpdateMotorStars updater= new UpdateMotorStars((ArrayList)adapter.motorResults);
        updater.execute();
    }

    @Override
    public void OnClickEvent() {

    }
}