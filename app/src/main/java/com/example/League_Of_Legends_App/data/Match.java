package com.example.League_Of_Legends_App.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private final String TAG = Match.class.getSimpleName();

    @SerializedName("gameMode")
    private String gameMode;

    @SerializedName("teams")
    private List<Team> teams;

    @SerializedName("participants")
    private List<Participant> participants;

    @SerializedName("participantIdentities")
    private List<ParticipantID> participantIDS;

    public Match(){
        this.teams = null;
        this.participants = null;
    }

    public String getGameMode() {
        return gameMode;
    }
    public List<Participant> getParticipants() {
        return participants;
    }
    public List<ParticipantID> getParticipantIDS() {
        return participantIDS;
    }
    public List<Team> getTeams() {
        return teams;
    }

    public List<Participant> getParticipantsOnTeam(int team) {
        List<Participant> ret = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++){
            if (participants.get(i).getTeamId().equals((team + 1) + "00")) {
                ret.add(participants.get(i));
            }
        }
        return ret;
    }

    public List<ParticipantID> getParticipantIdsOnTeam(int team) {
        List<ParticipantID> ret = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++){
            if (participants.get(i).getTeamId().equals((team + 1) + "00")) {
                ret.add(participantIDS.get(i));
            }
        }
        return ret;
    }

    public class Participant {
        @SerializedName("participantId")
        private String participantId;

        @SerializedName("teamId")
        private String teamId;

        @SerializedName("championId")
        private String championId;

        @SerializedName("stats")
        private Stats stats;

        public String getChampionUrl() {
            ChampionIdMap championIdMap = new ChampionIdMap();
            return "https://ddragon.leagueoflegends.com/cdn/11.5.1/img/champion/" + championIdMap.getChampionByID(this.championId) + ".png";
        }

        public String getChampionId() {
            return championId;
        }

        public String getParticipantId() {
            return participantId;
        }

        public String getTeamId() {
            return teamId;
        }

        public Stats getStats() {
            return stats;
        }

        public Participant(){
            participantId = null;
            teamId = null;
            championId = null;
            stats = null;
        }

        public class Stats {
            @SerializedName("kills")
            private String kills;

            @SerializedName("deaths")
            private String deaths;

            @SerializedName("assists")
            private String assists;

            @SerializedName("champLevel")
            private String champLevel;

            public Stats(){
                this.kills = null;
                this.assists = null;
                this.deaths = null;
                this.champLevel = null;
            }

            public String getAssists() {
                return assists;
            }

            public String getChampLevel() {
                return champLevel;
            }

            public String getDeaths() {
                return deaths;
            }

            public String getKills() {
                return kills;
            }
        }
    }

    public class ParticipantID {

        @SerializedName("player")
        private Player player;

        public ParticipantID(){
            this.player = null;
        }

        public Player getPlayer() {
            return player;
        }

        public class Player {
            @SerializedName("summonerName")
            private String summonerName;

            public Player(){
                this.summonerName = null;
            }

            public String getSummonerName() {
                return summonerName;
            }
        }
    }

    public class Team {
        @SerializedName("teamId")
        private String teamId;

        @SerializedName("win")
        private String win;

        @SerializedName("firstBlood")
        private boolean firstBlood;

        @SerializedName("towerKills")
        private int towerKills;

        public Team(){
            teamId = null;
            win = null;
            firstBlood = false;
            towerKills = 0;
        }

        public String getTeamId() {
            return teamId;
        }

        public int getTowerKills() {
            return towerKills;
        }

        public String getWin() {
            return win;
        }
    }

}
