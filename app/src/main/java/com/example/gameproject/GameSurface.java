package com.example.gameproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    public GameThread gameThread;

    public List<ChibiCharacter> chibiList = new ArrayList<ChibiCharacter>();
    private List<Explosion> explosionList = new ArrayList<Explosion>();

    private static final int MAX_STREAMS=100;
    private int soundIdExplosion;
    private int soundIdBackground;

    public boolean soundPoolLoaded;
    public SoundPool soundPool;
    public Button btnScore;
    public int score;
    public GameSurface(Context context, Button btnScore)  {
        super(context);
        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);
        this.btnScore = btnScore;
        this.score = 0;
        // Sét callback.
        this.getHolder().addCallback(this);
        this.setBackground(getResources().getDrawable(R.drawable.arkaplan));
        //this.setBackground(getResources().getDrawable(R.color.teal_700));
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        this.initSoundPool();
    }

    public void initSoundPool()  {
        // With Android API >= 21.
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // With Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When SoundPool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;

                // Playing background sound.
                playSoundBackground();
            }
        });

        // Load the sound background.mp3 into SoundPool
        this.soundIdBackground= this.soundPool.load(this.getContext(), R.raw.background,1);

        // Load the sound explosion.wav into SoundPool
        this.soundIdExplosion = this.soundPool.load(this.getContext(), R.raw.explosion,1);
    }

    public void playSoundExplosion()  {
        if(this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            // Play sound explosion.wav
            int streamId = this.soundPool.play(this.soundIdExplosion,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundBackground()  {
        if(this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            // Play sound explosion.wav
            int streamId = this.soundPool.play(this.soundIdBackground,leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int x=  (int)event.getX();
            int y = (int)event.getY();

            Iterator<ChibiCharacter> iterator= this.chibiList.iterator();
            Log.i("LOGTAG", "x: " + x + " y:  " + y);

            while(iterator.hasNext()) {
                ChibiCharacter chibi = iterator.next();
                if( chibi.getX() < x && x < chibi.getX() + chibi.getWidth()
                        && chibi.getY() < y && y < chibi.getY()+ chibi.getHeight())  {
                    // Remove the current element from the iterator and the list.
                    iterator.remove();
                    this.score++;
                    this.btnScore.setText("Puan: " + this.score);
                    // Create Explosion object.
                    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.explosion);
                    Explosion explosion = new Explosion(this, bitmap,chibi.getX(),chibi.getY());

                    this.explosionList.add(explosion);
                }
            }


            for(ChibiCharacter chibi: chibiList) {
                int movingVectorX =x-  chibi.getX() ;
                int movingVectorY =y-  chibi.getY() ;
                chibi.setMovingVector(movingVectorX, movingVectorY);
            }
            return true;
        }
        return false;
    }

    public void update()  {

        for(ChibiCharacter chibi: chibiList) {
            chibi.update();
        }
        for(Explosion explosion: this.explosionList)  {
            explosion.update();
        }

        Iterator<Explosion> iterator= this.explosionList.iterator();
        while(iterator.hasNext())  {
            Explosion explosion = iterator.next();

            if(explosion.isFinish()) {
                // If explosion finish, Remove the current element from the iterator & list.
                iterator.remove();
                continue;
            }
        }
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        for(ChibiCharacter chibi: chibiList)  {
            chibi.draw(canvas);
        }

        for(Explosion explosion: this.explosionList)  {
            explosion.draw(canvas);
        }

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

            if(gameThread != null){

                create_anime();

                this.gameThread = new GameThread(this, holder);
                this.gameThread.setRunning(true);
                this.gameThread.start();
                //Toast.makeText(getContext(), "adad", Toast.LENGTH_LONG).show();
            }
            else{
                create_anime();
                this.gameThread = new GameThread(this, holder);
                this.gameThread.setRunning(true);
                this.gameThread.start();
            }
    }
    public void create_anime(){
        Random randomNum = new Random();
        int enemy_size = randomNum.nextInt(10);
        int x = 50, y = 50;
        for (int i = 0; i < enemy_size+5; i++) {
            Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi1);
            ChibiCharacter chibi1 = new ChibiCharacter(this, chibiBitmap1, randomNum.nextInt(this.getWidth()) + x, randomNum.nextInt(this.getHeight()) + y);
            chibi1.setSpeed(randomNum.nextInt(5), randomNum.nextInt(5));
            chibi1.setAlive(true);
            this.chibiList.add(chibi1);

            x += 50;
            y += 50;
        }


        Bitmap chibiBitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi2);
        ChibiCharacter chibi2 = new ChibiCharacter(this, chibiBitmap2, 300, 150);
        chibi2.setSpeed(randomNum.nextInt(5), randomNum.nextInt(5));
        chibi2.setAlive(true);
        this.chibiList.add(chibi2);
    }
    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        // and release the surface
        this.gameThread.setRunning(false);
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

    public void close_sound(){
        soundPool.release();
    }
    public void speed_increase_for_enemy(){
        for(ChibiCharacter chibi: chibiList)  {
            chibi.setSpeed(chibi.getSpeedX() + 2, 1 + chibi.getSpeedY());
        }
    }

    public void speed_decrease_for_enemy(){
        for(ChibiCharacter chibi: chibiList)  {
            chibi.setSpeed(chibi.getSpeedX() - 2, chibi.getSpeedY() - 1);
        }
    }
    public void set_background(){
        Resources res = getResources();
        int []color = new int[7];
        Random rnd = new Random();
        color[0] = R.color.purple_200;
        color[1] = R.color.purple_500;
        color[2] = R.color.purple_700;
        color[3] = R.color.teal_200;
        color[4] = R.color.teal_700;
        color[5] = R.color.black;
        color[6] = R.color.white;

        this.setBackground(getResources().getDrawable(color[rnd.nextInt(6)]));

        // Set the parent view background color. This can not set surfaceview background color.
        //this.setBackgroundColor(Color.BLUE);

        // Set current surfaceview at top of the view tree.
        this.setZOrderOnTop(true);

        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        this.gameThread.setTime_mix(5);
        //this.setZOrderOnTop(true);
        //this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        //this.gameThread.setRunning(false);
        //this.gameThread.interrupt();
        //this.gameThread = null;
        //this.gameThread = new GameThread(this, this.getHolder());
        //this.gameThread.setRunning(true);
        //this.gameThread.start();
    }
}
