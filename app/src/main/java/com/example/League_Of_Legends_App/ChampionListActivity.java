package com.example.League_Of_Legends_App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.League_Of_Legends_App.data.ChampionIdMap;
import com.example.League_Of_Legends_App.data.championListData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChampionListActivity extends AppCompatActivity implements ChampionListAdapter.OnChampionListItemClickListener {

    private RecyclerView champion_listRV;
    private ChampionListAdapter championListAdapter;
    private List championList;

    private ChampionIdMap champMap;

    private final String CHAMP_BASE_URL = "https://ddragon.leagueoflegends.com/cdn/11.5.1/img/champion/";
    private final String REDIRECT_BASE_URL = "https://na.leagueoflegends.com/en-us/champions/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_list2);
        this.champMap = new ChampionIdMap();
        this.championList = new ArrayList<>();
        championListDataPrepare();

        this.champion_listRV = findViewById(R.id.champion_list_RV);
        this.championListAdapter = new ChampionListAdapter(championList, this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        this.champion_listRV.setLayoutManager(manager);
        //this.champion_listRV.setHasFixedSize(true);
        this.champion_listRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.champion_listRV.setAdapter(championListAdapter);

        this.championListAdapter.setOnChampionListClick(ChampionListActivity.this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_champions:
                        Intent intent__ = new Intent(getApplicationContext(), ChampionListActivity.class);
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
    // function to fill championlistdata list from the championIdMap data.
    private void championListDataPrepare() {

        for (Map.Entry<String, String> entry : champMap.getChampionMap().entrySet()) {
            String img_url = CHAMP_BASE_URL + entry.getValue() + ".png";
            String dir_url = REDIRECT_BASE_URL + entry.getValue().toLowerCase() + "/";

            championListData champData = new championListData(entry.getValue(), img_url, dir_url);
            championList.add(champData);
            Log.d("name", entry.getValue());
            Log.d("img url", img_url);
            Log.d("dir url", dir_url);
        }
    }


    @Override
    public void onChampionListClick(championListData data) {
        if (data != null){
            Uri lolUri = Uri.parse(data.getRedirect_url());
            Intent intent = new Intent(Intent.ACTION_VIEW, lolUri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.d("TAG", "onChampionListClicked: ERROR");
            }
        }
    }
}