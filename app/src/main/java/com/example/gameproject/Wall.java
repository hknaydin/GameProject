package com.example.gameproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Wall extends GameObject{
    public Bitmap image;
    public Wall(GameSurface GameSurface, Bitmap image, int x, int y) {
        super(image, 5, 5, x, y);
        this.image = image;
    }
    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.image;
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
    }

    public void update(){

    }
}
