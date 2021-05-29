package com.example.League_Of_Legends_App.data;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.League_Of_Legends_App.MatchDetailViewModel;

public class MatchDetailViewModelFactory implements ViewModelProvider.Factory {
    private SharedPreferences prefs;

    public MatchDetailViewModelFactory(SharedPreferences app_prefs){
        prefs = app_prefs;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MatchDetailViewModel(prefs);
    }
}
