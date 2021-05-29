package com.example.League_Of_Legends_App;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.League_Of_Legends_App.data.Match;

import java.util.List;

public class MatchParticipantAdaptor extends RecyclerView.Adapter<MatchParticipantAdaptor.MatchParticipantItemViewHolder> {
    private static final String TAG = MatchParticipantAdaptor.class.getSimpleName();

    private List<Match.ParticipantID> matchParticipantIDS;
    private List<Match.Participant> matchParticipants;


    @NonNull
    @Override
    public MatchParticipantItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.participant_detail_item, parent, false);
        return new MatchParticipantAdaptor.MatchParticipantItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchParticipantItemViewHolder holder, int position) {
        holder.bind(this.matchParticipantIDS.get(position), this.matchParticipants.get(position));
    }

    public void updateMatchParticipantItemViewHolder(Match match, int team) {
        this.matchParticipantIDS = match.getParticipantIdsOnTeam(team);
        this.matchParticipants = match.getParticipantsOnTeam(team);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (this.matchParticipantIDS == null){
            return 0;
        } else {
            return this.matchParticipantIDS.size();
        }
    }

    class MatchParticipantItemViewHolder extends RecyclerView.ViewHolder {
        final private TextView tv_summoner_name;
        final private TextView tv_summoner_champ_level;
        final private TextView tv_summoner_kills;
        final private TextView tv_summoner_deaths;
        final private TextView tv_summoner_assists;

        final private ImageView iv_champion_icon;


        public MatchParticipantItemViewHolder(@NonNull View itemView){
            super(itemView);
            tv_summoner_name = itemView.findViewById(R.id.tv_match_participant_name);
            tv_summoner_champ_level = itemView.findViewById(R.id.tv_match_participant_champ_level);
            tv_summoner_kills = itemView.findViewById(R.id.tv_match_participant_kills);
            tv_summoner_deaths = itemView.findViewById(R.id.tv_match_participant_deaths);
            tv_summoner_assists = itemView.findViewById(R.id.tv_match_participant_assists);
            iv_champion_icon = itemView.findViewById(R.id.iv_match_participant_icon);
        }

        public void bind (Match.ParticipantID participantIDS, Match.Participant participants) {
            Context ctx = this.itemView.getContext();

            tv_summoner_name.setText(participantIDS.getPlayer().getSummonerName());
            tv_summoner_champ_level.setText(participants.getStats().getChampLevel());
            tv_summoner_kills.setText(participants.getStats().getKills());
            tv_summoner_deaths.setText(participants.getStats().getDeaths());
            tv_summoner_assists.setText(participants.getStats().getAssists());

            Glide.with(ctx).load(participants.getChampionUrl()).into(iv_champion_icon);
        }
    }

}
