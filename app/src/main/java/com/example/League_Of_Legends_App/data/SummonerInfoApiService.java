package com.example.League_Of_Legends_App.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SummonerInfoApiService {
    @GET("summoner/v4/summoners/by-name/{name}")
    Call<Summoner> searchSummonerInfo(@Path("name") String summonerName, @Query("api_key") String apiKey);

    @GET("match/v4/matchlists/by-account/{accountID}")
    Call<GameHistoryList> getMatchHistory(@Path("accountID") String accountID,
                                           @Query("endIndex") String endIndex,
                                           @Query("beginIndex") String beginIndex,
                                           @Query("api_key") String apiKey);

}
