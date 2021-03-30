package com.example.myapplication.diagnosed_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


public class DiagnosedViewModel extends ViewModel {
    private static MutableLiveData<ArrayList<Diagnosed>> diagnoseds;
    private final MutableLiveData<Diagnosed> selected = new MutableLiveData<Diagnosed>();
    public ArrayList<Diagnosed> transactionsArrayList;
    private static DiagnosedViewModel instance;

    public static DiagnosedViewModel getInstance(){
        if(instance==null)
            instance=new DiagnosedViewModel();
        return instance;
    }

    public void init(ArrayList<Diagnosed> diagnosedArrayList) {
        diagnoseds= new MutableLiveData<>();
        diagnoseds.setValue(diagnosedArrayList);
        this.transactionsArrayList=diagnosedArrayList;
        instance=this;
    }
    public MutableLiveData<ArrayList<Diagnosed>> getDiagnoseds() {
        return diagnoseds;
    }



    public LiveData<Diagnosed> getSelected() {
        return selected;
    }



}
