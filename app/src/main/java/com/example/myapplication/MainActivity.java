package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{//} implements MyRecyclerViewAdapter.ItemClickListener {

    //MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosted_mainpage);
        getSupportActionBar().hide();
        Intent myIntent = new Intent(MainActivity.this, RegistrationActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

}



/*        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Mhemmad");
        animalNames.add("Ayman");
        animalNames.add("Abdalla");
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.diangosidsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
}

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }*/