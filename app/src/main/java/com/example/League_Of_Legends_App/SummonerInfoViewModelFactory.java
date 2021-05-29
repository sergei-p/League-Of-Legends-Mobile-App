package com.example.League_Of_Legends_App;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SummonerInfoViewModelFactory implements ViewModelProvider.Factory {
    private String BASE_API_URL;

    public SummonerInfoViewModelFactory(String _BASE_API_URL) {
        this.BASE_API_URL = _BASE_API_URL;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new SummonerInfoViewModel(BASE_API_URL);
    }
}
