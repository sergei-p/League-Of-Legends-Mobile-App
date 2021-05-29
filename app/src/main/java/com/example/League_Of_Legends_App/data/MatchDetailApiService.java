package com.example.League_Of_Legends_App.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MatchDetailApiService {
    @GET("match/v4/matches/{match_id}")
    Call<Match> getMatchDetails(@Path("match_id") String matchId, @Query("api_key") String apiKey);
}
