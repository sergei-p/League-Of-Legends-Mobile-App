package com.example.League_Of_Legends_App;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.League_Of_Legends_App.data.Match;
import com.example.League_Of_Legends_App.data.MatchDetailRepository;

public class MatchDetailViewModel extends ViewModel {
    private static final String TAG = MatchDetailViewModel.class.getSimpleName();

    private LiveData<Match> match;
    private MatchDetailRepository repo;

    public MatchDetailViewModel(SharedPreferences prefs){
        this.repo = new MatchDetailRepository(prefs);
        match = repo.getMatch();
    }

    public LiveData<Match> getMatch(){ return this.match; };

    public void loadMatchDetails(String matchId, String apiKey) {
        this.repo.loadMatchDetails(matchId, apiKey);
    }
}
