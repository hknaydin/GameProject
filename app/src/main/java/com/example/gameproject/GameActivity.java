package com.example.gameproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameActivity extends Activity {

    private GameSurface gameSurface = null;
    private LinearLayout canvasLayout = null;
    int sound_check;
    Button btnPuan;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.game_activity);

        btnPuan = findViewById(R.id.btnPuan);
        btnPuan.setClickable(false);
        gameSurface = new GameSurface(this, btnPuan);
        sound_check = 1;
        // This layout is used to contain custom surfaceview object.
        if(canvasLayout == null){
            canvasLayout = (LinearLayout)findViewById(R.id.customViewLayout);
        }

        canvasLayout.addView(gameSurface);
        Button btnSound = findViewById(R.id.soundButton);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sound_check == 1) {
                    gameSurface.close_sound();
                    gameSurface.soundPool = null;
                    sound_check = 0;
                    btnSound.setText("Sound Open");
                    Toast.makeText(GameActivity.this, "ses kapandı", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(gameSurface.soundPool == null)
                        gameSurface.initSoundPool();
                    Toast.makeText(GameActivity.this, "ses acildi", Toast.LENGTH_SHORT).show();
                    sound_check = 1;
                }
            }
        });

        findViewById(R.id.greenButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameSurface.soundPool != null) {
                    gameSurface.set_background();
                }
            }
        });

        findViewById(R.id.speedIncreaseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameSurface != null)
                gameSurface.speed_increase_for_enemy();
            }
        });

        findViewById(R.id.speedDecreaseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameSurface != null)
                    gameSurface.speed_decrease_for_enemy();
            }
        });
        /*
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        gameSurface = new GameSurface(this);
        this.setContentView(gameSurface);

        */
        makeHandler();
    }

    private void makeHandler() {
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                    final Handler handler=new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(GameActivity.this, "Puan: " + gameSurface.score, Toast.LENGTH_SHORT).show();
                        }
                    },500);

                // making image visible at random positions
                handler.postDelayed(this,500);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        gameSurface.close_sound();
        gameSurface.gameThread.setRunning(false);
        gameSurface.chibiList = null;
        gameSurface.close_sound();
        sound_check = 0;
        gameSurface.gameThread = null;

        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        gameSurface.chibiList = null;
        gameSurface.close_sound();
        sound_check = 0;
        gameSurface.gameThread = null;
        super.onPause();
    }
    protected void onResume() {
        super.onResume();
    }

}

