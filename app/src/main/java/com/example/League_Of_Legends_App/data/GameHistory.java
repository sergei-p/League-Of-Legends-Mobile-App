package com.example.League_Of_Legends_App.data;

import com.google.gson.annotations.SerializedName;

public class GameHistory {
    @SerializedName("platformId")
    private String platformId;

    @SerializedName("gameId")
    private String gameId;

    @SerializedName("champion")
    private String champion; // actually int, but easier to work with string

    @SerializedName("queue")
    private int queue;

    @SerializedName("season")
    private int season;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("role")
    private String role;

    @SerializedName("lane")
    private String lane;

    private ChampionIdMap championIdMap;

    public GameHistory() {
        this.platformId = null;
        this.gameId = null;
        this.champion = null;
        this.queue = 0;
        this.season = 0;
        this.timestamp = null;
        this.role = null;
        this.lane = null;
        this.championIdMap = new ChampionIdMap();
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getChampion() {
        return champion;
    }

    public int getQueue() {
        return queue;
    }

    public int getSeason() {
        return season;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimeStamp() {
        long epoch = Long.valueOf(this.timestamp);
        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (epoch));
        return date; // modify later
    }

    public String getRole() {
        return role;
    }

    public String getLane() {
        return lane;
    }

    public String getChampionName() {
        return championIdMap.getChampionByID(this.champion);
    }

    public String getChampionIonUrl() {
        return "https://ddragon.leagueoflegends.com/cdn/11.5.1/img/champion/" + championIdMap.getChampionByID(this.champion) + ".png";
    }
}
