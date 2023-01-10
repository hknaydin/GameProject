package com.example.gameproject;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button btnStart;
    MediaPlayer mp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp3.stop();
                mp3 = null;
                Intent game_activity = new Intent(MainActivity.this, GameActivity.class);
                startActivity(game_activity);
            }
        });

        mp3 = MediaPlayer.create(MainActivity.this, R.raw.azer);
        mp3.start();
    }
    @Override
    protected void onPause() {
        mp3 = null;
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onRestart() {
        btnStart = findViewById(R.id.btnStart);
        if(mp3 == null) {
            mp3 = MediaPlayer.create(MainActivity.this, R.raw.azer);
            mp3.start();
        }
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp3.stop();
                mp3 = null;
                Intent game_activity = new Intent(MainActivity.this, GameActivity.class);
                startActivity(game_activity);
            }
        });
        super.onRestart();
    }
}