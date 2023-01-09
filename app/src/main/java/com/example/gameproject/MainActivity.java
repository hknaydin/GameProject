package com.example.gameproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar customActionBar = getSupportActionBar();
        customActionBar.setDisplayShowCustomEnabled(true);
        customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        customActionBar.setCustomView(R.layout.custom_action_bar_main);

        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game_activity = new Intent(MainActivity.this, GameActivity.class);
                startActivity(game_activity);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onRestart() {
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game_activity = new Intent(MainActivity.this, GameActivity.class);
                startActivity(game_activity);
            }
        });
        super.onRestart();
    }
}