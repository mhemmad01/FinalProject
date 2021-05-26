package com.example.myapplication.diagnosisresultmotor.trainingresultmotor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MotorResult extends com.example.myapplication.trainingresultmotor.MotorResult {
    public static ArrayList<MotorResult> selected;
    public static ArrayList<String> diagnosisIds;
    public static Map<String, ArrayList<MotorResult>> diagnosis= new HashMap<>();
    /*
        This Class is Responsible for Motor Results View And Adapter And Functions
    */
    public String username;
    public String original;
    public String img;
    public int stage;
    public int level;
    public float stars;
    public int score=0;
    public static int totalScore=0;
    public static int total=0;
    public MotorResult(com.example.myapplication.trainingresultmotor.MotorResult m){
        username=m.username;
        original=m.original;
        img=m.img;
        stage=m.stage;
        level=m.level;
        stars=m.stars;


    }
    public MotorResult(){

    }

}
