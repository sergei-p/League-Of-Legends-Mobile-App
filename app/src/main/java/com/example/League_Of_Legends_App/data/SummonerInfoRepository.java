package com.example.League_Of_Legends_App.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SummonerInfoRepository {
    private static final String TAG = SummonerInfoRepository.class.getSimpleName();
    // private static final String API_KEY = ""

    private MutableLiveData<Summoner> summonerResult;
    private MutableLiveData<GameHistoryList> gameHistoryResult;
    private MutableLiveData<LoadingStatus> loadingStatus;
    private MutableLiveData<String> errorMessage;
    private SummonerInfoApiService summonerInfoApiService;

    public SummonerInfoRepository(String API_BASE_URL) {
        this.summonerResult = new MutableLiveData<>();
        //this.summonerResult.setValue(null);

        this.gameHistoryResult = new MutableLiveData<>();
        //this.gameHistoryResult.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);

        this.errorMessage = new MutableLiveData<>();
        //this.errorMessage.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.summonerInfoApiService = retrofit.create(SummonerInfoApiService.class);
    }

    public LiveData<Summoner> getSummonerData() { return this.summonerResult; }

    public LiveData<GameHistoryList> getGameHistoryData() { return this.gameHistoryResult; }

    public LiveData<LoadingStatus> getLoadingStatus() { return this.loadingStatus; }

    public  LiveData<String> getErrorMessage() { return this.errorMessage; }

    public void loadSummonerSearchResults(String summonerName, String apiKey, boolean getMatchHistory) {
        executeSummonerSearch(summonerName, apiKey, getMatchHistory);
    }

    private void executeSummonerSearch(String summonerName, String apiKey, boolean getMatchHistory) {
        Call<Summoner> resultSummoner;

        resultSummoner = summonerInfoApiService.searchSummonerInfo(summonerName, apiKey);
        //loadingStatus.setValue(LoadingStatus.LOADING);
        resultSummoner.enqueue(new Callback<Summoner>() {
            @Override
            public void onResponse(Call<Summoner> call, Response<Summoner> response) {
                if(response.code() == 200) {
                    Log.d(TAG, "Summoner Data HTTP request was successful");
                    summonerResult.setValue(response.body());
                    loadingStatus.setValue(LoadingStatus.SUCCESS);
                    if(getMatchHistory){
                        executeMatchHistorySearch(response.body().getAccountId(), apiKey);
                    }
                } else if(response.code() == 404) {
                    loadingStatus.setValue(LoadingStatus.ERROR);
                    Log.d(TAG, "Error " + response.code() + ", summoner not found. Try different server.");
                    errorMessage.setValue("Error " + response.code() + ", summoner not found. Try different server.");
                } else {
                    loadingStatus.setValue(LoadingStatus.ERROR);
                    Log.d(TAG, "Error " + response.code() + " while attempting HTTP request.");
                    errorMessage.setValue("Error " + response.code() + " while attempting HTTP request.");
                }
            }

            @Override
            public void onFailure(Call<Summoner> call, Throwable t) {
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
                Log.d(TAG, "HTTP Request Failed: " + t.getCause());
                errorMessage.setValue("HTTP Request Failed: " + t.getCause());
            }
        });
    }

    private void executeMatchHistorySearch(String accountId, String apiKey) {
        Call<GameHistoryList> resultGameHistory;

        // hard coded to retrieve the last 20 games for summoner of specified accountID
        resultGameHistory = summonerInfoApiService.getMatchHistory(accountId, "20", "0", apiKey);

        resultGameHistory.enqueue(new Callback<GameHistoryList>() {
            @Override
            public void onResponse(Call<GameHistoryList> call, Response<GameHistoryList> response) {
                if(response.code() == 200) {
                    Log.d(TAG, "Match History HTTP request was successful");
                    loadingStatus.setValue(LoadingStatus.SUCCESS);
                    gameHistoryResult.setValue(response.body());
                } else if(response.code() == 404) {
                    loadingStatus.setValue(LoadingStatus.ERROR);
                    Log.d(TAG, "Error " + response.code() + ", match history not found. Try different summoner.");
                    errorMessage.setValue("Error " + response.code() + ", match history not found. Try different summoner.");
                } else {
                    loadingStatus.setValue(LoadingStatus.ERROR);
                    Log.d(TAG, "Error " + response.code() + " while attempting HTTP request.");
                    errorMessage.setValue("Error " + response.code() + " while attempting HTTP request.");
                }
            }

            @Override
            public void onFailure(Call<GameHistoryList> call, Throwable t) {
                t.printStackTrace();
                loadingStatus.setValue(LoadingStatus.ERROR);
                Log.d(TAG, "HTTP Request Failed: " + t.getCause());
                errorMessage.setValue("HTTP Request Failed: " + t.getCause());
            }
        });
    }
}
