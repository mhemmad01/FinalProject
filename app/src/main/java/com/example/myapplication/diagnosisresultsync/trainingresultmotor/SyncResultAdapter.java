package com.example.myapplication.diagnosisresultsync.trainingresultmotor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DiagnosisMotor;
import com.example.myapplication.R;
import com.example.myapplication.ViewDiagnosisResults;
import com.example.myapplication.dbConnection;

import java.util.ArrayList;
import java.util.List;


public class SyncResultAdapter extends RecyclerView.Adapter<SyncResultAdapter.ViewHolder> {
    /*
          This Classes is Responsible for Sync Results View And Adapter And Functions
      */
    public List<SyncResult> syncResults;
    public static SyncResultAdapter instance;
    // Pass in the contact array into the constructor
    public SyncResultAdapter(List<SyncResult> results) {
        syncResults = results;
        instance=this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View TransactionrawView = inflater.inflate(R.layout.diagnosissyncresult_row, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(TransactionrawView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SyncResult current = syncResults.get(position);
        byte[] b= Base64.decode(current.img,Base64.DEFAULT);
        Bitmap bmp= BitmapFactory.decodeByteArray(b , 0, b.length);
        ImageView image =  holder.img;
        image.setImageBitmap(bmp);

        byte[] b1= Base64.decode(current.img2,Base64.DEFAULT);
        Bitmap bmp2= BitmapFactory.decodeByteArray(b1 , 0, b1.length);
        ImageView image2 =  holder.img2;
        image2.setImageBitmap(bmp2);

        holder.percent.setText("Sync: "+current.percent+"%");
        ImageView original =  holder.original;
        if(current.score>0){
            holder.score.setChecked(true);
        }
        else
        {
            holder.score.setChecked(false);
        }
        original.setImageResource(DiagnosisMotor.textureArrayWin[current.level-1]);



    }

    @Override
    public int getItemCount() {
        return syncResults.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{

        public ImageView img;
        public ImageView img2;
        public ImageView original;
        public View itemView;
        public CheckBox score;
        public TextView percent;

        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.syncResultImage);
            img2=(ImageView) itemView.findViewById(R.id.syncResultImage2);
            original=(ImageView) itemView.findViewById(R.id.originalSyncImage);
            score=(CheckBox) itemView.findViewById(R.id.scoreCheckBoxSync);
            percent=(TextView) itemView.findViewById(R.id.SyncPercent);
            this.itemView=itemView;
            itemView.setOnLongClickListener(this);
            score.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SyncResult m = syncResults.get(getAdapterPosition());
                    if(isChecked)
                        m.score=1;
                    else
                        m.score=0;
                    UpdateMotorStars t = new UpdateMotorStars(m);
                    syncResults.set((getAdapterPosition()),m);
                    SyncResult.diagnosis.replace(SyncResult.diagnosisIds.get(ViewDiagnosisResults.spSelectedPos),(ArrayList<SyncResult>) syncResults);
                    t.execute();

                    int count=0;
                    int count1=0;
                    for(SyncResult x : SyncResult.selected){
                        if(x.score>0){
                            count++;
                        }
                        count1++;
                    }
                    ViewDiagnosisResults.scoreTSync.setText("Score: "+count+"/"+count1);

                    int score=0;
                    int total=0;
                    for( String id: SyncResult.diagnosisIds){
                        for(SyncResult i : SyncResult.diagnosis.get(id)){
                            if(i.score>0) {
                                score++;
                            }
                            total++;
                        }
                    }
                    SyncResult.totalScore=score;
                    SyncResult.total=total;

                    ViewDiagnosisResults.totalScoreTSync.setText("Total Score: "+score+"/"+total);

                }
            });
        }

        @Override
        public boolean onLongClick(View v) { ;
            //removeAt(getAdapterPosition());
            Log.i("longcolor", "onClick: ");
            return true;
        }

        @Override
        public void onClick(View v) {
            Log.i("color", "onClick: ");
        }
    }
    public void removeAt(int position) {
        syncResults.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, syncResults.size());
        updateList();

    }

    public void add(SyncResult res) {
        syncResults.add(res);
        notifyDataSetChanged();
        updateList();
    }

    public void removeAll() {
        for (int i = syncResults.size()-1; i>=0 ; i--) {
            syncResults.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, syncResults.size());
        }
        updateList();
    }

    public void updateList(){

    }


    private class UpdateMotorStars extends AsyncTask<com.example.myapplication.trainingresultmotor.MotorResult, Void, Boolean> {
        SyncResult toUpdate;
        public UpdateMotorStars(SyncResult toUpdate){
            this.toUpdate=toUpdate;
        }

        @Override
        protected Boolean doInBackground(com.example.myapplication.trainingresultmotor.MotorResult... motorResults) {
           return dbConnection.updateDiagnosisSyncScore(toUpdate);
        }





        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(ViewDiagnosisResults.context, "Error", Toast.LENGTH_SHORT)
                        .show();
            }

        }

    }
}