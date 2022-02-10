package com.example.navigationdrawerpractice.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Change me in MapViewModel.java");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

