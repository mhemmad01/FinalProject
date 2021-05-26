package com.example.myapplication.diagnosed_model;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainDiagnostic;
import com.example.myapplication.MyDiagnosed;
import com.example.myapplication.R;
import com.example.myapplication.ViewDiagnosisResults;
import com.example.myapplication.dbConnection;
import com.example.myapplication.trainingresultmotor.MotorResult;
import com.example.myapplication.trainingresultsync.trainingresultmotor.SyncResult;

import java.util.ArrayList;
import java.util.List;

/*
This Class is Responsible for Diagnoseds View And Adapter And Functions
 */
public class DiagnosedAdapter extends RecyclerView.Adapter<DiagnosedAdapter.ViewHolder> {

    public List<Diagnosed> mDiagnoseds;
    public static DiagnosedAdapter instance;
    public static Dialog dialog3=null;
    // Pass in the contact array into the constructor
    public DiagnosedAdapter(List<Diagnosed> diagnoseds) {
        mDiagnoseds = diagnoseds;
        instance=this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View TransactionrawView = inflater.inflate(R.layout.diagnosed_row, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(TransactionrawView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Diagnosed diagnosed = mDiagnoseds.get(position);
       TextView diagnosedName = holder.diagnosedName;
        TextView diagnosedAge = holder.diagnosedAge;
        TextView diagnosedUsername = holder.diagnosedUsername;
        diagnosedName.setText(diagnosed.getFullName());
        diagnosedAge.setText(diagnosed.getAge()+"");
        diagnosedUsername.setText(diagnosed.getUsername());
    }

    @Override
    public int getItemCount() {
        return mDiagnoseds.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

        public View itemView;
        public TextView diagnosedName;
        public TextView diagnosedAge;
        public TextView diagnosedUsername;

        public ViewHolder(View itemView) {
            super(itemView);
            diagnosedName=(TextView) itemView.findViewById(R.id.diagnosedName);
            diagnosedAge=(TextView) itemView.findViewById(R.id.diagnosedAge);
            diagnosedUsername=(TextView) itemView.findViewById(R.id.diagnosedUsername);
            this.itemView=itemView;
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) { ;
//            itemView.setBackgroundColor(Color.RED);
//            Diagnosed.selected= mDiagnoseds.get(getAdapterPosition());
//            Log.i("color", "onClick: ");
//            Intent myIntent = new Intent(MyDiagnosed.instance, ViewDiagnosisResults.class);
//            MyDiagnosed.instance.startActivity(myIntent);
            return true;
        }

        @Override
        public void onClick(View v) {
            LoadingShow();
            LoadResults lr = new LoadResults(mDiagnoseds.get(getAdapterPosition()).getUsername());
            lr.execute("");
            itemView.setBackgroundColor(Color.RED);
            Diagnosed.selected= mDiagnoseds.get(getAdapterPosition());
            Log.i("color", "onClick: ");
//            Intent myIntent = new Intent(MyDiagnosed.instance, ViewDiagnosisResults.class);
//            MyDiagnosed.instance.startActivity(myIntent);

        }
    }
    public void LoadingShow(){
        // custom dialog
        dialog3 = new Dialog(MyDiagnosed.instance);
        dialog3.setContentView(R.layout.loadingicon);
        dialog3.setTitle("Loading");
        dialog3.setCanceledOnTouchOutside(false);
        dialog3.show();
    }
    public void removeAt(int position) {
        mDiagnoseds.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDiagnoseds.size());
        updateList();

    }

    public void add(Diagnosed diagnosed) {
        mDiagnoseds.add(diagnosed);
        notifyDataSetChanged();
        updateList();
    }

    public void removeAll() {
        for (int i = mDiagnoseds.size()-1; i>=0 ; i--) {
            mDiagnoseds.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, mDiagnoseds.size());
        }
        updateList();
    }

    public void updateList(){

    }

    private class LoadResults extends AsyncTask<String, Void, ArrayList<MotorResult>> {
        String usr;


        public LoadResults(String usr) {
            this.usr = usr;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT)
            //    .show();
        }


        @Override
        protected ArrayList<MotorResult> doInBackground(String... params) {
//            if (MotorResult.selected == null) {
//                MotorResult.selected = new ArrayList<>();
//            }
//            if (SyncResult.selected == null) {
//                SyncResult.selected = new ArrayList<>();
//            }
//            if (com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected == null) {
//                com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.selected = new ArrayList<>();
//            }
//            if (com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.selected == null) {
//                com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.selected = new ArrayList<>();
//            }
            com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.selected=null;
            com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosisIds= dbConnection.getDiagnosisIds(usr);
            int score=0;
            int total=0;//com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosisIds.size();
            for( String id: com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosisIds){
                com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosis.put(id,  dbConnection.getDiagnosisMotorResults(usr,id));
                for(com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult i : com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosis.get(id)){
                    if(i.score>0) {
                        score++;
                    }
                    total++;
                }
            }
            com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.totalScore=score;
            com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.total=total;



            SyncResult.selected = dbConnection.getTrainSyncResults(usr);
            com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.diagnosisIds= dbConnection.getDiagnosisIdsSync(usr);
            int score1=0;
            int total1=0;//com.example.myapplication.diagnosisresultmotor.trainingresultmotor.MotorResult.diagnosisIds.size();
            for( String id: com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.diagnosisIds){
                com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.diagnosis.put(id,  dbConnection.getDiagnosisSyncResults(usr,id));
                for(com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult i : com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.diagnosis.get(id)){
                    if(i.score>0) {
                        score1++;
                    }
                    total1++;
                }
            }
            com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.totalScore=score1;
            com.example.myapplication.diagnosisresultsync.trainingresultmotor.SyncResult.total=total1;




            return dbConnection.getTrainMotorResults(usr);
        }

        @Override
        protected void onPostExecute(ArrayList<MotorResult>  result) {
            if (result.size()>0) {
                Log.i("hhhh", "ccc");
                MotorResult.selected=result;
                //dialog3.dismiss();
                Intent myIntent = new Intent(MyDiagnosed.instance, ViewDiagnosisResults.class);
                MyDiagnosed.instance.startActivity(myIntent);
            } else {
                Log.i("hhhh", "ffffff");
                Intent myIntent = new Intent(MyDiagnosed.instance, ViewDiagnosisResults.class);
                MyDiagnosed.instance.startActivity(myIntent);
            }

        }
    }
}
