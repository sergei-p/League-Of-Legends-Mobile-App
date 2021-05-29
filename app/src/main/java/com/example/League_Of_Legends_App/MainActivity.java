package com.example.League_Of_Legends_App;


import android.content.Intent;
import android.content.SharedPreferences;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.League_Of_Legends_App.data.GameHistory;
import com.example.League_Of_Legends_App.data.GameHistoryList;
import com.example.League_Of_Legends_App.data.LoadingStatus;
import com.example.League_Of_Legends_App.data.Summoner;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity
    implements GameHistoryAdapter.OnGameHistoryItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private SummonerInfoViewModel summonerInfoViewModel;
    private RecyclerView gameHistoryListRV;
    private EditText searchBoxET;

    private ImageView summonerIconIV;
    private TextView summonerNameTV;
    private TextView summonerLevelTV;

    private LinearLayout summonerInfoLL;
    private LinearLayout matchHistoryTitleLL;
    private TextView summonerLevelHeaderTV;
    private LinearLayout matchHistoryContainerLL;

    private TextView errorMessageTV;

    private GameHistoryAdapter gameHistoryAdapter;

    private String API_KEY = "RGAPI-d0f5ed5b-afff-49ac-ad32-8815eab36c57";


    private String API_BASE_URL;
    float x1, y1, x2, y2;


    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("lol_preferences",MODE_PRIVATE);
        if(sharedPreferences.getString("theme", "Light Theme").contains("Light Mode")) {
            setTheme(android.R.style.Theme_DeviceDefault_Light);
        }
        else {
            setTheme(android.R.style.Theme_Holo);
        }

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //shared prefernces keeps track of there prefered server
        API_BASE_URL = sharedPreferences.getString("API_BASE_URL", "https://na1.api.riotgames.com/lol/");
        Log.d("API_BASE_URL", API_BASE_URL);



        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_champions, R.id.navigation_settings)
                .build();

        ctx = this;

        this.searchBoxET = (EditText) findViewById(R.id.search_summoner_et);

        // Views
        this.summonerIconIV = findViewById(R.id.summoner_icon_iv);
        this.summonerNameTV = findViewById(R.id.summoner_name_main_tv);
        this.summonerLevelTV = findViewById(R.id.summoner_level_tv);

        this.summonerInfoLL = findViewById(R.id.summoner_info_ll);
        this.summonerLevelHeaderTV = findViewById(R.id.summoner_level_header_tv);
        this.matchHistoryTitleLL = findViewById(R.id.summoner_info_ll);
        this.matchHistoryContainerLL = findViewById(R.id.match_history_container_ll);

        this.errorMessageTV = findViewById(R.id.error_message_main_tv);


        // Recycler View
        this.gameHistoryListRV = findViewById(R.id.match_history_rv);
        this.gameHistoryListRV.setLayoutManager(new LinearLayoutManager(this));
        this.gameHistoryListRV.setHasFixedSize(true);

        // Adapter
        this.gameHistoryAdapter = new GameHistoryAdapter(this);
        this.gameHistoryListRV.setAdapter(this.gameHistoryAdapter);


        // View Model
        this.summonerInfoViewModel = new ViewModelProvider(
                                    this,
                                    new SummonerInfoViewModelFactory(API_BASE_URL))
                                    .get(SummonerInfoViewModel.class);



        this.summonerInfoViewModel.getLoadingStatus().observe(
                this,
                new Observer<LoadingStatus>() {
                    @Override
                    public void onChanged(LoadingStatus loadingStatus) {
                        if(loadingStatus == LoadingStatus.LOADING) {
                            Log.d(TAG, "Forecast data is in the process of being retrieved...");
                        } else if(loadingStatus == LoadingStatus.SUCCESS) {
                            Log.d(TAG, "Forecast data has been successfully retrieved");
                            summonerInfoLL.setVisibility(View.VISIBLE);
                            summonerLevelHeaderTV.setVisibility(View.INVISIBLE);
                            matchHistoryTitleLL.setVisibility(View.VISIBLE);
                            matchHistoryContainerLL.setVisibility(View.VISIBLE);
                            errorMessageTV.setVisibility(View.INVISIBLE);
                        } else {
                            Log.d(TAG, "An error occurred while retrieving forecast data");
                            summonerInfoLL.setVisibility(View.INVISIBLE);
                            summonerLevelHeaderTV.setVisibility(View.INVISIBLE);
                            matchHistoryTitleLL.setVisibility(View.INVISIBLE);
                            matchHistoryContainerLL.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.VISIBLE);
                        }
                    }

                }
        );

        this.summonerInfoViewModel.getErrorMessage().observe(
                this,
                new Observer<String>() {
                    @Override
                    public void onChanged(String errorMessage) {
                        errorMessageTV.setText(errorMessage);
                    }
                }
        );

       summonerInfoViewModel.getSummonerData().observe(
                this,
                new Observer<Summoner>() {
                    @Override
                    public void onChanged(Summoner summoner) {
                        if(summoner != null) {
                            Glide.with(ctx).load(summoner.getProfileIconURL()).into(summonerIconIV);
                            summonerNameTV.setText(summoner.getName());
                            summonerLevelTV.setText(summoner.getSummonerLevel());
                        }
                    }
                }
        );

        this.summonerInfoViewModel.getGameHistoryData().observe(
                this,
                new Observer<GameHistoryList>() {
                    @Override
                    public void onChanged(GameHistoryList gameHistoryList) {
                        if(gameHistoryList != null) {
                            Log.d(TAG, "getGameHistoryData() accessed");
                            gameHistoryAdapter.updateAdapter(gameHistoryList);
                        }

                    }
                }
        );

        Button searchButton = (Button)findViewById(R.id.search_button_b);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchBoxET.getText().toString();
                summonerInfoViewModel.loadSummonerSearchResults(searchQuery, API_KEY, true);
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_champions:
                        Intent intent__ = new Intent(MainActivity.this, ChampionListActivity.class);
                        startActivity(intent__);

                        return true;
                    case R.id.navigation_settings:
                        Intent intent_ = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(intent_);
                        return true;
                }
                return false;
            }
        });

    }

    // GameHistory is a single item, GameHistoryList is a list of game history items
    @Override
    public void onGameHistoryItemClick(GameHistory gameHistory){
        // open specific game info
        Intent intent = new Intent(this, MatchDetailActivity.class);
        intent.putExtra(MatchDetailActivity.EXTRA_MATCH_ID, gameHistory.getGameId());
        startActivity(intent);
    }




    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){

                }else if(x1 > x2){
                    Intent i = new Intent(this, ChampionListActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

}