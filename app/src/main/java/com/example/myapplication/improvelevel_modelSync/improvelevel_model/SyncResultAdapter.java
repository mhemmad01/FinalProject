package com.example.myapplication.improvelevel_modelSync.improvelevel_model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Levels;
import com.example.myapplication.R;
import com.example.myapplication.TrainMotor;
import com.example.myapplication.TrainSync;

import java.util.List;


public class SyncResultAdapter extends RecyclerView.Adapter<SyncResultAdapter.ViewHolder>  {

    public List<SyncResult> syncResults;

    public static SyncResultAdapter instance;
    public static ViewHolder toImprove;
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
        View TransactionrawView = inflater.inflate(R.layout.levelssyncresult_row, parent, false);
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
        Bitmap bmp1= BitmapFactory.decodeByteArray(b1 , 0, b1.length);
        ImageView image2 =  holder.img2;
        image2.setImageBitmap(bmp1);

        ImageView original =  holder.original;
        original.setImageResource(TrainMotor.textureArrayWin[current.level-1][current.stage-1]);

        holder.percent.setText("Sync: "+current.percent+"%");
        if(syncResults.get(position).stars>=0)
            holder.motorStars.setRating(syncResults.get(position).stars);


        holder.stageText.setText("Stage: " + current.stage);
          holder.levelText.setText("Level: " + current.level);
          holder.improveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here Code For Improve Button
                // you Can use "current" to get details
                toImprove=holder;
                Log.i("ImproveLevel", "onClick: stage: " + current.stage + ", level: " + current.level);
                current.stars=0;
                syncResults.set(position,current);
                holder.motorStars.setRating(0);
                transfer s=new transfer(current);

            }
        });

    }

    @Override
    public int getItemCount() {
        return syncResults.size();
    }


    public class  transfer extends AppCompatActivity{
        SyncResult current;
        public transfer(SyncResult current) {
            this.current=current;
            Intent intent = new Intent(Levels.instance, TrainSync.class);
            intent.putExtra("Type","improve");
            intent.putExtra("username", current.username);
            intent.putExtra("levelnum", Integer.toString(current.level));
            intent.putExtra("stagenum", Integer.toString(current.stage));
            // intent.putExtra("NextStage", Integer.toString(a.lastStage));
            Levels.instance.startActivityForResult(intent, 2);
        }

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{

        public ImageView img;
        public ImageView img2;
        public ImageView original;
        public View itemView;
        public RatingBar motorStars;
        public ImageView improveButton;
        public TextView stageText;
        public TextView levelText;
        public TextView percent;


        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.SyncTrainingResultImage);
            img2=(ImageView) itemView.findViewById(R.id.SyncTrainingResultImage2);
            original=(ImageView) itemView.findViewById(R.id.originalTraningSyncImage);
            motorStars=(RatingBar) itemView.findViewById(R.id.SyncTrainingStars);
            improveButton=(ImageView) itemView.findViewById(R.id.improveLevelButtonSync);
            stageText=(TextView) itemView.findViewById(R.id.stageTextViewSync);
            levelText=(TextView) itemView.findViewById(R.id.levelTextViewSync);
            percent=(TextView) itemView.findViewById(R.id.SyncPercentImprove);
            //button - improveLevelButton , stage - stageTextView, level - levelTextView4
            motorStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                     ratingBar.setRating(syncResults.get(getAdapterPosition()).stars);
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
}