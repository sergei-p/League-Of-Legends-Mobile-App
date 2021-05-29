package com.example.League_Of_Legends_App.data;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatchDetailRepository {
    private static final String TAG = MatchDetailRepository.class.getSimpleName();
    private static String BASE_URL = "https://na1.api.riotgames.com/lol/";

    private String matchId;

    private MutableLiveData<Match> match;

    private MatchDetailApiService matchDetailApiService;

    public MatchDetailRepository(SharedPreferences prefs){
        BASE_URL = prefs.getString("API_BASE_URL", "https://na1.api.riotgames.com/lol/");
        this.match = new MutableLiveData<>();
        this.match.setValue(null);

        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.matchDetailApiService = retrofit.create(MatchDetailApiService.class);

    }

    public LiveData<Match> getMatch(){ return this.match; };

    public void loadMatchDetails(String _matchId, String apiKey){
        if (_matchId == this.matchId){
            Log.d(TAG, "Match Data Already Loaded!");
        }
        else {
            Log.d(TAG, "Fetching Match Data!");
            this.matchId = _matchId;
            Call<Match> req = this.matchDetailApiService.getMatchDetails(_matchId, apiKey);
            req.enqueue(new Callback<Match>() {
                @Override
                public void onResponse(Call<Match> call, Response<Match> response) {
                    if (response.code() == 200) {
                        Log.d(TAG, "Collected Match Data!");
                        match.setValue(response.body());
                    } else {
                        Log.d(TAG, "unsuccessful API request: " + call.request().url());
                        Log.d(TAG, "  -- response status code: " + response.code());
                        Log.d(TAG, "  -- response: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Match> call, Throwable t) {
                    Log.d(TAG, "unsuccessful API request: " + call.request().url());
                    t.printStackTrace();
                }
            });
        }
    }
}
