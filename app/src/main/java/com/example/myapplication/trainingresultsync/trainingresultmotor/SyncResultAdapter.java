package com.example.myapplication.trainingresultsync.trainingresultmotor;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.TrainMotor;

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
        View TransactionrawView = inflater.inflate(R.layout.synchronozationmotorresult_row, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(TransactionrawView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SyncResult current = syncResults.get(position);
        byte[] b= Base64.decode(current.img,Base64.DEFAULT);
        Bitmap bmp= BitmapFactory.decodeByteArray(b , 0, b.length);

        byte[] b1= Base64.decode(current.img2,Base64.DEFAULT);
        Bitmap bmp1= BitmapFactory.decodeByteArray(b1 , 0, b1.length);
        ImageView image =  holder.img;
        image.setImageBitmap(bmp);
        ImageView image2 =  holder.img2;
        image2.setImageBitmap(bmp1);
        holder.percent.setText("Sync: "+current.percent+"%");
        ImageView original =  holder.original;
        original.setImageResource(TrainMotor.textureArrayWin[current.level-1][current.level-1]);

        if(syncResults.get(position).stars>=0)
            holder.motorStars.setRating(syncResults.get(position).stars);


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
        public RatingBar motorStars;
        public TextView percent;

        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.SyncResultImage);
            img2=(ImageView) itemView.findViewById(R.id.syncResultImage2);
            original=(ImageView) itemView.findViewById(R.id.originalSyncImage);
            percent=(TextView) itemView.findViewById(R.id.SyncPercent);
            motorStars=(RatingBar) itemView.findViewById(R.id.SyncStars3);
            motorStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    syncResults.get(getAdapterPosition()).stars = rating;
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