package com.example.gameproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class Text extends GameObject{
    int score;
    public Text(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
    }
    public void draw(Canvas canvas)  {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        paint.setTextSize(40);
        canvas.drawText("Score : "+this.score, canvas.getWidth()-250, 45, paint);
        // Last draw time.
    }

    public void update(){

    }
    public void set_score(int score){
        this.score = score;
    }
    public void set_rect(){

    }
}
