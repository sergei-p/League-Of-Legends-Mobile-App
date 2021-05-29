package com.example.League_Of_Legends_App;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;



public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SharedPreferences sharedPreferences;
    float x1,x2,y1,y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("lol_preferences",MODE_PRIVATE);
        if(sharedPreferences.getString("theme", "Light Theme").contains("Light Mode")) {
            setTheme(android.R.style.Theme_DeviceDefault_Light);
        }
        else {
            setTheme(android.R.style.Theme_Holo);
        }

        setContentView(R.layout.settings_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
                        Intent intent_ = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent_);
                        return true;
                    case R.id.navigation_settings:

                }
                return false;
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.available_servers, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(sharedPreferences.getInt("position", 1));
        spinner.setOnItemSelectedListener(this);





// initiate a Switch
        Switch simpleSwitch = (Switch) findViewById(R.id.switch1);

        simpleSwitch.setChecked(!sharedPreferences.getString("theme", "Light Theme").contains("Light Mode"));
        TextView textView = findViewById(R.id.mode);
        textView.setText(sharedPreferences.getString("mode", "Light Mode"));



        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(isChecked) {
                    textView.setText("Dark Mode");
                    editor.putString("theme", "Dark Mode");
                }
                else {
                    textView.setText("Light Mode");
                    editor.putString("theme", "Light Mode");

                }
                editor.commit();
                Intent intent_ = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent_);

            }
        });



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
                Intent i = new Intent(this, ChampionListActivity.class);
                startActivity(i);
            }else if(x1 > x2){
            }
            break;
        }
        return false;
    }






    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        parent.getItemAtPosition(pos);
        SharedPreferences sharedPreferences = getSharedPreferences("lol_preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch(pos) {
            case 0:
                editor.putString("API_BASE_URL", "https://kr.api.riotgames.com/lol/");
                break;
            case 1:
                editor.putString("API_BASE_URL", "https://na1.api.riotgames.com/lol/");
                break;
            case 2:
                editor.putString("API_BASE_URL", "https://br1.api.riotgames.com/lol/");
                break;
            case 3:
                editor.putString("API_BASE_URL", "https://euw1.api.riotgames.com/lol/");
                break;
            case 4:
                editor.putString("API_BASE_URL", "https://oc1.api.riotgames.com/lol/");
                break;
            case 5:
                editor.putString("API_BASE_URL", "https://eun1.api.riotgames.com/lol/");

                break;
        }
        editor.putInt("position", pos);
            editor.commit();
        editor.apply();
//                Korea, North America, Brazil, Europe West, Oceania, and Europe Nordic and East
        Log.d("server_set", sharedPreferences.getString("API_BASE_URL","nothing"));

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}