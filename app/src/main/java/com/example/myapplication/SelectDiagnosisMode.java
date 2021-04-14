package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
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
    static String DiagnosisMode;
    SelectDiagnosisMode a;
    static int lastLevel=0;

    RecyclerView rvDiagnosed;
    DiagnosisViewModel model;
    public static ArrayList<Diagnosed> diagnoseds;
    public static DiagnosisAdapter adapter;
    public static Context context;
    static Dialog dialog3=null;
    static String DiagnosedUsername;
    private int lastimgnummotor=1;
    private int lastdiagnosisnum=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=this;
        setContentView(R.layout.diagnosisselect);
        final androidx.appcompat.app.ActionBar abar = getSupportActionBar();
        abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_toolbar));//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.abs_layout, null);
        androidx.appcompat.app.ActionBar.LayoutParams params = new androidx.appcompat.app.ActionBar.LayoutParams(//Center the textview in the ActionBar !
                androidx.appcompat.app.ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Selecting Page");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);
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
        SetDiagnosisMode("Sync");
        Intent intent = new Intent(getApplicationContext(), TrainSync.class);
        startActivity(intent);
    }
    public void LoadingShow(){
        // custom dialog
        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.loadingicon);
        dialog3.setTitle("Loading");
        dialog3.show();
    }

    public void StartMotorTrain(View view) {
        SetDiagnosisMode("Motor");
        if(DiagnosisAdapter.temp==null){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Please Select diagnosed from the list above.");
            //builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog2, int id) {
                            dialog2.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.setCanceledOnTouchOutside(false);
            alert11.show();
        }
        else {
            LoadingShow();
            DiagnosedUsername=DiagnosisAdapter.temp.getUsername();
            DiagnosisAdapter.temp=null;
            GetLastdiagnosisimg s = new GetLastdiagnosisimg(DiagnosedUsername);
            s.execute();
        }
        // Start the SecondActivity
    //    model.getSelected().getValue().
       // Intent intent = new Intent(SelectDiagnosisMode.this, TrainMotor.class);
        //intent.putExtra("NextLevel", Integer.toString(a.lastLevel));
        //SelectDiagnosisMode.this.startActivityForResult(intent, 1);
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
                Intent intent = new Intent(this, DiagnosisMotor.class);
                intent.putExtra("imgnum", Integer.toString(lastimgnummotor));
                intent.putExtra("diagnosisnum", Integer.toString(lastdiagnosisnum));
                startActivityForResult(intent, 1);
            }else if(action.equals("NEXT")){
                a.lastimgnummotor=a.lastimgnummotor+1;
                SaveLastdiagnosisimg s=new SaveLastdiagnosisimg(a.DiagnosedUsername,a.lastimgnummotor,lastdiagnosisnum);
                s.execute();
                Intent intent = new Intent(this, DiagnosisMotor.class);
                intent.putExtra("imgnum", Integer.toString(lastimgnummotor));
                intent.putExtra("diagnosisnum", Integer.toString(lastdiagnosisnum));
                startActivityForResult(intent, 1);
            }else if(action.equals("FINISH")){
                if(DiagnosisMotor.dialog3!=null)
                    DiagnosisMotor.dialog3.dismiss();
                a.lastimgnummotor=1;
                a.lastdiagnosisnum=a.lastdiagnosisnum+1;
                SaveLastdiagnosisimg s=new SaveLastdiagnosisimg(a.DiagnosedUsername,a.lastimgnummotor,lastdiagnosisnum);
                s.execute();
            }
        }
    }
    public void SetDiagnosisMode(String DiagnosisMode){
        this.DiagnosisMode=DiagnosisMode;
    }

    private class GetLastdiagnosisimg extends AsyncTask<String, Void, int[]> {
        String usr;

        public GetLastdiagnosisimg(String usr) {
            this.usr = usr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected int[] doInBackground(String... params) {
            return dbConnection.getlastdiagnosismotorimg(usr);
        }

        @Override
        protected void onPostExecute(int[] result) {
            if (result!=null) {
                lastimgnummotor=result[0];
                lastdiagnosisnum=result[1];
                    SetDiagnosisMode("Motor");
                    // Start the SecondActivity
                    Intent intent = new Intent(SelectDiagnosisMode.this, DiagnosisMotor.class);
                    intent.putExtra("imgnum", Integer.toString(lastimgnummotor));
                intent.putExtra("diagnosisnum", Integer.toString(lastdiagnosisnum));
                   // intent.putExtra("NextStage", Integer.toString(a.lastStage));
                    SelectDiagnosisMode.this.startActivityForResult(intent, 1);
                    dialog3.dismiss();
                Log.i("hhhh", "ccc");
                //getmotorImg a = new getmotorImg(usr, stage, level);
                //a.execute("");
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
    }
    private class SaveLastdiagnosisimg extends AsyncTask<String, Void, Boolean> {
        String usr;
        int lastimgnum;
        int lastdiagnosisnum;

        public SaveLastdiagnosisimg(String usr,int lastimgnum,int lastdiagnosisnum) {
            this.usr = usr;
            this.lastimgnum = lastimgnum;
            this.lastdiagnosisnum = lastdiagnosisnum;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            return dbConnection.savelastdiagnosisimg(usr, lastdiagnosisnum,lastimgnum);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {

                Log.i("hhhh", "ccc");
                //getmotorImg a = new getmotorImg(usr, stage, level);
                //a.execute("");
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
    }
}