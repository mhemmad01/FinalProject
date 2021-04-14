package com.example.myapplication.improvelevel_model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.TrainMotor;

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
        View TransactionrawView = inflater.inflate(R.layout.levelsmotorresult_row, parent, false);
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
        original.setImageResource(TrainMotor.textureArrayWin[current.level-1][current.level-1]);

        if(motorResults.get(position).stars>=0)
            holder.motorStars.setRating(motorResults.get(position).stars);


        holder.stageText.setText("Stage: " + current.stage);
          holder.levelText.setText("Level: " + current.level);
          holder.improveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here Code For Improve Button
                // you Can use "current" to get details
                Log.i("ImproveLevel", "onClick: stage: " + current.stage + ", level: " + current.level);
                

            }
        });

    }

    @Override
    public int getItemCount() {
        return motorResults.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{

        public ImageView img;
        public ImageView original;
        public View itemView;
        public RatingBar motorStars;
        public ImageView improveButton;
        public TextView stageText;
        public TextView levelText;


        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.MotorTrainingResultImage);
            original=(ImageView) itemView.findViewById(R.id.originalTraningMotorImage);
            motorStars=(RatingBar) itemView.findViewById(R.id.motorTrainingStars);
            improveButton=(ImageView) itemView.findViewById(R.id.improveLevelButton);
            stageText=(TextView) itemView.findViewById(R.id.stageTextView);
            levelText=(TextView) itemView.findViewById(R.id.levelTextView4);
            //button - improveLevelButton , stage - stageTextView, level - levelTextView4
            motorStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                     ratingBar.setRating(motorResults.get(getAdapterPosition()).stars);
                }
            });
            this.itemView=itemView;
            itemView.setOnLongClickListener(this);
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
}