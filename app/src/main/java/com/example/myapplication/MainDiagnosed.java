package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
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
        getSupportActionBar().hide();
        ((TextView)findViewById(R.id.diagnosedTitle)).setText("Hi "+User.currentUser.getFullName());

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
