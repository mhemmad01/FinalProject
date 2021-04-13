package com.example.myapplication.diagnosed_model;

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

import java.util.ArrayList;
import java.util.List;


public class DiagnosedAdapter extends RecyclerView.Adapter<DiagnosedAdapter.ViewHolder> {

    public List<Diagnosed> mDiagnoseds;
    public static DiagnosedAdapter instance;
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
            LoadResults lr = new LoadResults(mDiagnoseds.get(getAdapterPosition()).getUsername());
            lr.execute("");
            itemView.setBackgroundColor(Color.RED);
            Diagnosed.selected= mDiagnoseds.get(getAdapterPosition());
            Log.i("color", "onClick: ");
//            Intent myIntent = new Intent(MyDiagnosed.instance, ViewDiagnosisResults.class);
//            MyDiagnosed.instance.startActivity(myIntent);

        }
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
            return dbConnection.getTrainMotorResults(usr);
        }

        @Override
        protected void onPostExecute(ArrayList<MotorResult>  result) {
            if (result.size()>0) {
                Log.i("hhhh", "ccc");
                MotorResult.selected=result;
                Intent myIntent = new Intent(MyDiagnosed.instance, ViewDiagnosisResults.class);
                MyDiagnosed.instance.startActivity(myIntent);
            } else {
                Log.i("hhhh", "ffffff");
            }

        }
    }

}