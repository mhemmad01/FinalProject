package com.example.myapplication.improvelevel_modelSync.improvelevel_model;

import java.util.ArrayList;

public class SyncResult extends com.example.myapplication.trainingresultsync.trainingresultmotor.SyncResult {
    public static ArrayList<SyncResult> selected;
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

    public SyncResult(){

    }

}
