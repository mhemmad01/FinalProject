package com.example.myapplication.improvelevel_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


public class MotorResultViewModel extends ViewModel {
    private static MutableLiveData<ArrayList<MotorResult>> motorResults;
    private final MutableLiveData<MotorResult> selected = new MutableLiveData<MotorResult>();
    public ArrayList<MotorResult> transactionsArrayList;
    private static MotorResultViewModel instance;
    /*
            This Classes is Responsible for Motor Results View And Adapter And Functions
        */
    public static MotorResultViewModel getInstance(){
        if(instance==null)
            instance=new MotorResultViewModel();
        return instance;
    }

    public void init(ArrayList<MotorResult> diagnosedArrayList) {
        motorResults= new MutableLiveData<>();
        motorResults.setValue(diagnosedArrayList);
        this.transactionsArrayList=diagnosedArrayList;
        instance=this;
    }
    public MutableLiveData<ArrayList<MotorResult>> getResults() {
        return motorResults;
    }



    public LiveData<MotorResult> getSelected() {
        return selected;
    }



}
