package com.example.myapplication.diagnosis_model;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.diagnosed_model.Diagnosed;

import java.util.List;


public class DiagnosisAdapter extends RecyclerView.Adapter<DiagnosisAdapter.ViewHolder> {

    public List<Diagnosed> mDiagnoseds;
    public static DiagnosisAdapter instance;
    public static Diagnosed temp=null;
    // Pass in the contact array into the constructor
    public DiagnosisAdapter(List<Diagnosed> diagnoseds) {
        mDiagnoseds = diagnoseds;
        instance=this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View TransactionrawView = inflater.inflate(R.layout.diagnosis_row, parent, false);
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



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{

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
            //removeAt(getAdapterPosition());
            v.setBackgroundColor(Color.RED);
            Log.i("longcolor", "onClick: ");
            return true;
        }

        @Override
        public void onClick(View v) {
           v.setBackgroundColor(Color.RED);
            temp = mDiagnoseds.get(getAdapterPosition());

            Log.i("color", "onClick: "+temp.getUsername());
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
}