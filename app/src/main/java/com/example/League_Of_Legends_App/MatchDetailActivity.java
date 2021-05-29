package com.example.League_Of_Legends_App;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.League_Of_Legends_App.data.Match;
import com.example.League_Of_Legends_App.data.MatchDetailViewModelFactory;

public class MatchDetailActivity extends AppCompatActivity {
    private static final String TAG = MatchDetailActivity.class.getSimpleName();

    public static final String EXTRA_MATCH_ID = "MatchDetailActivity.MatchID";

    private String matchID;

    private MatchDetailViewModel matchDetailViewModel;

    private String API_KEY = "RGAPI-d0f5ed5b-afff-49ac-ad32-8815eab36c57";

    private TextView tv_team0_win;
    private TextView tv_team1_win;

    private RecyclerView rv_team0;
    private RecyclerView rv_team1;

    private MatchParticipantAdaptor matchParticipantAdaptor0;
    private MatchParticipantAdaptor matchParticipantAdaptor1;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_MATCH_ID)) {
            this.matchID = (String)intent.getSerializableExtra(EXTRA_MATCH_ID);
        }

        prefs = getSharedPreferences("lol_preferences",MODE_PRIVATE);
        if(prefs.getString("theme", "Light Theme").contains("Light Mode")) {
            setTheme(android.R.style.Theme_DeviceDefault_Light);
        }
        else {
            setTheme(android.R.style.Theme_Holo);
        }


        this.matchDetailViewModel = new ViewModelProvider(
                this,
                new MatchDetailViewModelFactory(prefs)
        ).get(MatchDetailViewModel.class);

        this.matchDetailViewModel.loadMatchDetails(this.matchID, this.API_KEY);

        tv_team0_win = (TextView)findViewById(R.id.tv_match_team0_win);
        tv_team1_win = (TextView)findViewById(R.id.tv_match_team1_win);

        rv_team0 = findViewById(R.id.rv_match_team0_participants);
        rv_team0.setLayoutManager(new LinearLayoutManager(this));
        rv_team0.setHasFixedSize(true);

        rv_team1 = findViewById(R.id.rv_match_team1_participants);
        rv_team1.setLayoutManager(new LinearLayoutManager(this));
        rv_team1.setHasFixedSize(true);

        this.matchParticipantAdaptor0 = new MatchParticipantAdaptor();
        this.rv_team0.setAdapter(this.matchParticipantAdaptor0);

        this.matchParticipantAdaptor1 = new MatchParticipantAdaptor();
        this.rv_team1.setAdapter(this.matchParticipantAdaptor1);


        this.matchDetailViewModel.getMatch().observe(
                this,
                new Observer<Match>() {
                    @Override
                    public void onChanged(Match match) {
                        if (match != null) {
                            tv_team0_win.setText(match.getTeams().get(0).getWin());
                            tv_team1_win.setText(match.getTeams().get(1).getWin());
                            matchParticipantAdaptor0.updateMatchParticipantItemViewHolder(match, 0);
                            matchParticipantAdaptor1.updateMatchParticipantItemViewHolder(match, 1);
                        }
                    }
                }
        );
    }
}