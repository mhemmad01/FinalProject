package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//Main page for the diagnostic
public class MainDiagnostic extends AppCompatActivity{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.diagnostic_mainpage);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getSupportActionBar().hide();
            ((TextView)findViewById(R.id.diagnosticTitle)).setText("Hi "+User.currentUser.getFullName());
        }
    //Go to diagnoseds page where the diagnostic could see his diagnoseds and add new diagnosed
    public void gotoMyDiagnosed(View view) {
        Intent myIntent = new Intent(this, MyDiagnosed.class);
        this.startActivity(myIntent);
    }
    //Logout from the user and back to login page
    public void Logout(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
    //Start diagnosis and select diagnosed and diagnosis mode
    public void gotoSelectDiagnosisMode(View view) {
        Intent myIntent = new Intent(this, SelectDiagnosisMode.class);
        this.startActivity(myIntent);
    }
    //Display about page where user cansee explain about the aplications and about the inventors.
    public void gotoAbout(View view) {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }
    }