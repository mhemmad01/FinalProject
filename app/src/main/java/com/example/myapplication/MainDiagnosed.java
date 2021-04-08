package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainDiagnosed extends AppCompatActivity {
    static String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosted_mainpage);
        if(User.currentUser!=null)
        ((TextView)findViewById(R.id.diagnosedTitle)).setText("Hi "+User.currentUser.getFullName());

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void StartTrain(View view) {
        Intent intent = new Intent(getApplicationContext(), SelectTrainMode.class);
        startActivity(intent);
    }
/*
        public void backtomain(View view) {

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();

        }*/
}
