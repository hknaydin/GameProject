package com.example.gameproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashScreen extends Activity
{
    ImageView imageView;
    AnimationDrawable progressAnimation;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread mSplashThread;//thread classdan obje olustrduk uygulamann 4 saniye uyutulmasi icin
        mSplashThread = new Thread(){
            @Override public void run(){
                try {

                    synchronized(this){
                        wait(3500);
                    }
                }catch(InterruptedException ex){

                }
                finally{

                    //Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        };//thread objesini olustrduk ve istedmz sekilde sekillendrdik
        mSplashThread.start();// thread objesini calistriyoruz

    }
}
