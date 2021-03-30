package com.example.myapplication.diagnosed_model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

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



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

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
        }

        @Override
        public boolean onLongClick(View v) { ;
            removeAt(getAdapterPosition());
            return true;
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