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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

public class ViewDiagnosisResults extends AppCompatActivity implements TrainingResultsFragment.ClickHandler {



    RecyclerView rvMotorResult;
    MotorResultViewModel model;
    public static ArrayList<MotorResult> motorResults;
    public static MotorResultAdapter adapter;
    public static Context context;
    public static ViewDiagnosisResults instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosticresults);
        ((TextView)findViewById(R.id.selectedDiagnosedUsername)).setText("Diagnosed: "+ Diagnosed.selected.getFullName());
        instance=this;
        if(MotorResult.selected==null) {
            MotorResult.selected = new ArrayList<>();
        }
        motorResults = MotorResult.selected;
        adapter = new MotorResultAdapter(motorResults);
        rvMotorResult = (RecyclerView) findViewById(R.id.MotorResults);
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