package com.example.gameproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion extends GameObject {

    private int rowIndex = 0 ;
    private int colIndex = -1 ;

    private boolean finish= false;
    private GameSurface gameSurface;

    public Explosion(GameSurface GameSurface, Bitmap image, int x, int y) {
        super(image, 5, 5, x, y);

        this.gameSurface= GameSurface;
    }

    public void update()  {
        this.colIndex++;

        // Play sound explosion.wav.
        if(this.colIndex==0 && this.rowIndex==0) {
            this.gameSurface.playSoundExplosion();
        }

        if(this.colIndex >= this.colCount)  {
            this.colIndex =0;
            this.rowIndex++;

            if(this.rowIndex>= this.rowCount)  {
                this.finish= true;
            }
        }
    }

    public void draw(Canvas canvas)  {
        if(!finish)  {
            Bitmap bitmap= this.createSubImageAt(rowIndex,colIndex);
            canvas.drawBitmap(bitmap, this.x, this.y,null);
        }
    }

    public boolean isFinish() {
        return finish;
    }

}