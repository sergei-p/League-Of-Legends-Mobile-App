package com.example.League_Of_Legends_App;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.League_Of_Legends_App.data.GameHistoryList;
import com.example.League_Of_Legends_App.data.LoadingStatus;
import com.example.League_Of_Legends_App.data.Summoner;
import com.example.League_Of_Legends_App.data.SummonerInfoRepository;

public class SummonerInfoViewModel extends ViewModel {
    private static final String TAG = SummonerInfoViewModel.class.getSimpleName();
    private LiveData<Summoner> summonerResult;
    private LiveData<GameHistoryList> gameHistoryResult;
    private LiveData<LoadingStatus> loadingStatus;
    private LiveData<String> errorMessage;
    private SummonerInfoRepository repository;

    public SummonerInfoViewModel(String API_BASE_URL) {
        this.repository = new SummonerInfoRepository(API_BASE_URL);
        this.summonerResult = this.repository.getSummonerData();
        this.gameHistoryResult = this.repository.getGameHistoryData();
        this.loadingStatus = this.repository.getLoadingStatus();
        this.errorMessage = this.repository.getErrorMessage();
    }

    public void loadSummonerSearchResults(String summonerName, String apiKey, boolean getMatchHistory) {
        this.repository.loadSummonerSearchResults(summonerName, apiKey, getMatchHistory);
    }

    public LiveData<Summoner> getSummonerData() {
        return this.summonerResult;
    }

    public LiveData<GameHistoryList> getGameHistoryData() {
        return this.gameHistoryResult;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public LiveData<String> getErrorMessage() {
        return this.errorMessage;
    }
}
