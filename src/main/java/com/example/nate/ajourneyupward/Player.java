package com.example.nate.ajourneyupward;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * The player class construction a colored rectangle and includes methods to
 * move the player position and draw the player on the canvas.
 */

public class Player implements GameObject{
    private Rect rect;
    private int color;

    public Player(Rect r, int c){
        rect = r;
        color = c;
    }

    public Rect getRect(){return rect;}

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update() {}

    public void update(Point p){
        rect.set(p.x - rect.width()/2, p.y - rect.height()/2, p.x + rect.width()/2, p.y + rect.height()/2);
    }
}
