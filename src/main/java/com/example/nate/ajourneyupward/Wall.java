package com.example.nate.ajourneyupward;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * The Wall class implements GameObject to create the obstacles.
 * The obstacles are two separate rectangles drawn with a gap between them.
 */

public class Wall implements GameObject {

    private Rect rect;
    private Rect rect2;
    private int color;

    public Rect getRect(){
        return rect;
    }

    public void incY(float y){
        rect.top += y;
        rect.bottom += y;

        rect2.top += y;
        rect2.bottom += y;
    }

    public Wall(int rHeight, int c, int startX, int startY, int pg){
        this.color = c;
        rect = new Rect(0, startY, startX, startY + rHeight);
        rect2 = new Rect(startX + pg, startY, Constants.SCREEN_WIDTH, startY + rHeight);
    }

    public boolean ballCollide(Player b){
        return Rect.intersects(rect, b.getRect()) || Rect.intersects(rect2, b.getRect());
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
        canvas.drawRect(rect2, paint);
    }

    @Override
    public void update() {}
}