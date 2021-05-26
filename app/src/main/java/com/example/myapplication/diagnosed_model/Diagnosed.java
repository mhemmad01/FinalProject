package com.example.myapplication.diagnosed_model;

import com.example.myapplication.User;

import java.util.ArrayList;
import java.util.Random;

/*
This Class is Responsible for Diagnoseds View And Adapter And Functions
 */

public class Diagnosed extends User {
    public static Diagnosed selected;

    public Diagnosed(User user){
        super(user.getId(),user.getUsername(),user.getFullName(),user.isDiagnostic(),user.getAge());
    }

    public Diagnosed(String username){
        super(0,username,username+ " Last",false,0);

    }

    public static ArrayList<Diagnosed> generateRandom(){
        ArrayList<Diagnosed> diagnoseds = new ArrayList<Diagnosed>();
        diagnoseds.add(new Diagnosed("Mhemmad"));
        diagnoseds.add(new Diagnosed("ayman"));
        diagnoseds.add(new Diagnosed("admin"));

        return diagnoseds;
    }
}
