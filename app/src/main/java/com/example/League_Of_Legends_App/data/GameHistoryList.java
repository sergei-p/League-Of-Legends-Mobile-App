package com.example.League_Of_Legends_App.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameHistoryList {
    @SerializedName("matches")
    private List<GameHistory> gameHistoryItems;

    public GameHistoryList () {
        this.gameHistoryItems = null;
    }

    public List<GameHistory> getGameHistoryItems(){
        return this.gameHistoryItems;
    }
}
