package com.example.myapplication.diagnosisresultmotor.trainingresultmotor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.TrainMotor;
import com.example.myapplication.ViewDiagnosisResults;
import com.example.myapplication.dbConnection;

import java.util.ArrayList;
import java.util.List;


public class MotorResultAdapter extends RecyclerView.Adapter<MotorResultAdapter.ViewHolder> {

    public List<MotorResult> motorResults;
    public static MotorResultAdapter instance;
    // Pass in the contact array into the constructor
    public MotorResultAdapter(List<MotorResult> results) {
        motorResults = results;
        instance=this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View TransactionrawView = inflater.inflate(R.layout.diagnosismotorresult_row, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(TransactionrawView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MotorResult current = motorResults.get(position);
        byte[] b= Base64.decode(current.img,Base64.DEFAULT);
        Bitmap bmp= BitmapFactory.decodeByteArray(b , 0, b.length);
        ImageView image =  holder.img;
        image.setImageBitmap(bmp);

        ImageView original =  holder.original;
        if(current.score>0){
            holder.score.setChecked(true);
        }
//        original.setImageResource(TrainMotor.textureArrayWin[current.level-1][current.level-1]);



    }

    @Override
    public int getItemCount() {
        return motorResults.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{

        public ImageView img;
        public ImageView original;
        public View itemView;
        public CheckBox score;

        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.MotorResultImage);
            original=(ImageView) itemView.findViewById(R.id.originalMotorImage);
            score=(CheckBox) itemView.findViewById(R.id.scoreCheckBox);
            this.itemView=itemView;
            itemView.setOnLongClickListener(this);
            score.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MotorResult m = motorResults.get(getAdapterPosition());
                    if(isChecked)
                        m.score=1;
                    else
                        m.score=0;
                    UpdateMotorStars t = new UpdateMotorStars(m);
                    t.execute();
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
        motorResults.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, motorResults.size());
        updateList();

    }

    public void add(MotorResult res) {
        motorResults.add(res);
        notifyDataSetChanged();
        updateList();
    }

    public void removeAll() {
        for (int i = motorResults.size()-1; i>=0 ; i--) {
            motorResults.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, motorResults.size());
        }
        updateList();
    }

    public void updateList(){

    }


    private class UpdateMotorStars extends AsyncTask<com.example.myapplication.trainingresultmotor.MotorResult, Void, Boolean> {
        MotorResult toUpdate;
        public UpdateMotorStars(MotorResult toUpdate){
            this.toUpdate=toUpdate;
        }

        @Override
        protected Boolean doInBackground(com.example.myapplication.trainingresultmotor.MotorResult... motorResults) {
            return dbConnection.updateDiagnosisMotorScore(toUpdate);
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