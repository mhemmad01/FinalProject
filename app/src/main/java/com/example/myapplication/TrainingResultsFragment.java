package com.example.myapplication;

//import android.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class TrainingResultsFragment extends Fragment implements OnClickListener{
	ClickHandler listener;

	@Override
	public void onAttach(@NonNull Context context) {
		try{
			this.listener = (ClickHandler)context;
		}catch(ClassCastException e){
			throw new ClassCastException("the class " +
					getActivity().getClass().getName() +
					" must implements the interface 'ClickHandler'");
		}
		super.onAttach(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.trainingresultsfragment, container,false);
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()){
//			case R.id.button5:
				//calculatePlus();
			//	break;
		}
		listener.OnClickEvent();
	}

	public interface ClickHandler{
		public void OnClickEvent();
	}



}
