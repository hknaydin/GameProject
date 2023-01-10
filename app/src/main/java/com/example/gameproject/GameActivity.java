package com.example.gameproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    private GameSurface gameSurface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        gameSurface = new GameSurface(this);
        this.setContentView(gameSurface);
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        gameSurface.close_sound();
        gameSurface.gameThread.setRunning(false);
        gameSurface.chibiList = null;
        gameSurface.gameThread = null;
        gameSurface.close_sound();
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        gameSurface.gameThread = null;
        gameSurface.chibiList = null;
        gameSurface.close_sound();
        super.onPause();
    }
    protected void onResume() {
        super.onResume();
    }

}

