package com.example.myapplication.diagnosis_model;

import com.example.myapplication.User;

import java.util.ArrayList;

public class Diagnosis extends User {

    /*
        This Class is Responsible for Diagnosis View And Adapter And Functions
    */

    public Diagnosis(User user){
        super(user.getId(),user.getUsername(),user.getFullName(),user.isDiagnostic(),user.getAge());
    }

    public Diagnosis(String username){
        super(0,username,username+ " Last",false,0);

    }

    public static ArrayList<Diagnosis> generateRandom(){
        ArrayList<Diagnosis> diagnoseds = new ArrayList<Diagnosis>();
        diagnoseds.add(new Diagnosis("Mhemmad"));
        diagnoseds.add(new Diagnosis("ayman"));
        diagnoseds.add(new Diagnosis("admin"));

        return diagnoseds;
    }
}
