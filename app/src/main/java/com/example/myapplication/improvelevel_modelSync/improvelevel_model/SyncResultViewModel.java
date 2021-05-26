package com.example.myapplication.improvelevel_modelSync.improvelevel_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


public class SyncResultViewModel extends ViewModel {
    private static MutableLiveData<ArrayList<SyncResult>> motorResults;
    private final MutableLiveData<SyncResult> selected = new MutableLiveData<SyncResult>();
    public ArrayList<SyncResult> transactionsArrayList;
    private static SyncResultViewModel instance;
    /*
          This Classes is Responsible for Sync Results View And Adapter And Functions
      */
    public static SyncResultViewModel getInstance(){
        if(instance==null)
            instance=new SyncResultViewModel();
        return instance;
    }

    public void init(ArrayList<SyncResult> diagnosedArrayList) {
        motorResults= new MutableLiveData<>();
        motorResults.setValue(diagnosedArrayList);
        this.transactionsArrayList=diagnosedArrayList;
        instance=this;
    }
    public MutableLiveData<ArrayList<SyncResult>> getResults() {
        return motorResults;
    }



    public LiveData<SyncResult> getSelected() {
        return selected;
    }



}
