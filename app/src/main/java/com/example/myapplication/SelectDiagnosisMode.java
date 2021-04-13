package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.diagnosed_model.Diagnosed;
import com.example.myapplication.diagnosed_model.DiagnosedAdapter;
import com.example.myapplication.diagnosed_model.DiagnosedViewModel;
import com.example.myapplication.diagnosis_model.DiagnosisAdapter;
import com.example.myapplication.diagnosis_model.DiagnosisViewModel;

import java.util.ArrayList;

public class SelectDiagnosisMode extends AppCompatActivity {
    static String TrainMode;
    SelectDiagnosisMode a;
    static int lastLevel=0;

    RecyclerView rvDiagnosed;
    DiagnosisViewModel model;
    public static ArrayList<Diagnosed> diagnoseds;
    public static DiagnosisAdapter adapter;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=this;
        setContentView(R.layout.diagnosisselect);

        diagnoseds = User.diagnoseds;
        adapter = new DiagnosisAdapter(diagnoseds);
        rvDiagnosed = (RecyclerView) findViewById(R.id.diagnosedsView);
        model =new ViewModelProvider(this).get(DiagnosisViewModel.class);
        model.init(diagnoseds);
        model.getDiagnoseds().observe(this, diagnosedListUpdateObserver);
    }

    Observer<ArrayList<Diagnosed>> diagnosedListUpdateObserver = new Observer<ArrayList<Diagnosed>>() {
        @Override
        public void onChanged(ArrayList<Diagnosed> diagnosedArrayList) {
            adapter = new DiagnosisAdapter(diagnosedArrayList);
            rvDiagnosed.setLayoutManager(new LinearLayoutManager(context));
            rvDiagnosed.setAdapter(adapter);

        }
    };

    public void StartSyncTrain(View view) {
        SetTrainMode("Sync");
        Intent intent = new Intent(getApplicationContext(), TrainSync.class);
        startActivity(intent);
    }
    public void StartMotorTrain(View view) {
        SetTrainMode("Motor");
        // Start the SecondActivity
    //    model.getSelected().getValue().
        Intent intent = new Intent(SelectDiagnosisMode.this, TrainMotor.class);
        intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
        SelectDiagnosisMode.this.startActivityForResult(intent, 1);
       // Intent intent = new Intent(getApplicationContext(), TrainMotor.class);
        //intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
        //startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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
            if(action.equals("RESTART")){
                Intent intent = new Intent(this, TrainMotor.class);
                intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
                startActivityForResult(intent, 1);
            }else if(action.equals("NEXT")){
                a.lastLevel=a.lastLevel+1;
                Intent intent = new Intent(this, TrainMotor.class);
                intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
                startActivityForResult(intent, 1);
            }
        }
    }
    public void SetTrainMode(String TrainMode){
        this.TrainMode=TrainMode;
    }

}