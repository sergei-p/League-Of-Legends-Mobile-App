package com.example.League_Of_Legends_App.data;

import com.google.gson.annotations.SerializedName;

public class Summoner {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("puuid")
    private String puuid;

    @SerializedName("accountId")
    private String accountId;

    @SerializedName("profileIconId")
    private String profileIconId;

    @SerializedName("summonerLevel")
    private String summonerLevel;

    public Summoner () {
        this.name = null;
        this.id = null;
        this.puuid = null;
        this.accountId = null;
        this.profileIconId = null;
        this.summonerLevel = null;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getPuuid() {
        return this.puuid;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public String getProfileIconId() {
        return this.profileIconId;
    }

    public String getProfileIconURL() {
        return "https://ddragon.leagueoflegends.com/cdn/11.5.1/img/profileicon/" + this.profileIconId + ".png";
    }

    public String getSummonerLevel() {
        return this.summonerLevel;
    }
}


