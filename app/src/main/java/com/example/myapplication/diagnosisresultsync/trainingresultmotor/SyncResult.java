package com.example.myapplication.diagnosisresultsync.trainingresultmotor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SyncResult extends com.example.myapplication.trainingresultmotor.MotorResult {
    public static ArrayList<SyncResult> selected;
    public static ArrayList<String> diagnosisIds;
    public static Map<String, ArrayList<SyncResult>> diagnosis= new HashMap<>();
    /*
          This Classes is Responsible for Sync Results View And Adapter And Functions
      */
    public String username;
    public String original;
    public String img;
    public String img2;
    public String percent;
    public int stage;
    public int level;
    public float stars;
    public int score=0;
    public static int totalScore=0;
    public static int total=0;

    public SyncResult(){

    }

}
