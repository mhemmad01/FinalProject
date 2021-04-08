package com.example.myapplication.diagnosis_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.diagnosed_model.Diagnosed;

import java.util.ArrayList;


public class DiagnosisViewModel extends ViewModel {
    private static MutableLiveData<ArrayList<Diagnosed>> diagnoseds;
    private final MutableLiveData<Diagnosed> selected = new MutableLiveData<Diagnosed>();
    public ArrayList<Diagnosed> transactionsArrayList;
    private static DiagnosisViewModel instance;

    public static DiagnosisViewModel getInstance(){
        if(instance==null)
            instance=new DiagnosisViewModel();
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
