package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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


        public void addDiagnosed(View v){
            AddDiagnosedQuery ex=new AddDiagnosedQuery(User.currentUser.getUsername(),((EditText)findViewById(R.id.editTextTextDiagnosedUsername)).getText().toString());
            ex.execute();
        }


    private class AddDiagnosedQuery extends AsyncTask<String, Void, Boolean> {
        String diagnosticUsername;
        String diagnosedUsername;
        public AddDiagnosedQuery(String diagnosticUsername, String diagnosedUsername){
            this.diagnosticUsername=diagnosticUsername;
            this.diagnosedUsername=diagnosedUsername;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MyDiagnosed.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.AddDiagnosed(diagnosticUsername,diagnosedUsername);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                model.getDiagnoseds().setValue(User.diagnoseds);

                Toast.makeText(MyDiagnosed.this, "User Added Successfully", Toast.LENGTH_SHORT)
                        .show();
                Log.i("xx", "onPostExecute: true ");
            }else {
                Toast.makeText(MyDiagnosed.this, "Can't add user, its not in system or its already assigned to someone", Toast.LENGTH_SHORT)
                        .show();
                Log.i("xx", "onPostExecute: False ");

            }

        }


    }

}