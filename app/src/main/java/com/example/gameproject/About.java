package com.example.gameproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_about);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent main_acticity = new Intent(About.this, MainActivity.class);
        startActivity(main_acticity);
        finish();
        super.onBackPressed();
    }
}