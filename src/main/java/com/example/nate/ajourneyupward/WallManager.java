package com.example.nate.ajourneyupward;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates an ArrayList of walls and draws them moving downward.
 * Also handles score increase and display.
 */

public class WallManager {
    private Random r;
    private ArrayList<Wall> walls;
    private int playerGap; //gap player is meant to pass through
    private int obstacleGap; //distance between wall obstacles
    private int obstacleHeight; //thickness of wall
    private int color; //

    private long startTime;
    private long initTime;

    double score = 0;

    public WallManager(int pg, int og, int oh, int c){
        this.playerGap = pg;
        this.obstacleGap = og;
        this.obstacleHeight = oh;
        this.color = c;

        r = new Random();

        startTime = initTime = System.currentTimeMillis();

        walls = new ArrayList<>();
        makeWalls();
    }

    public boolean collision(Player b){
        for(Wall o: walls){
            if(o.ballCollide(b)){
                return true;
            }
        }
        return false;
    }

    private void makeWalls(){
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while(currY < 0){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            walls.add(new Wall(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update(){
        if(startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)Math.sqrt(1 + (startTime - initTime)/2000.0) * Constants.SCREEN_HEIGHT / 10000.0f;
        for(Wall o: walls){
            o.incY(speed * elapsedTime);
        }
        int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
        if(walls.get(walls.size()-1).getRect().top >= Constants.SCREEN_HEIGHT){
            walls.add(0,
                    new Wall(obstacleHeight, color, xStart,
                            walls.get(0).getRect().top - obstacleGap - obstacleHeight, playerGap));

            walls.remove(walls.size() - 1);
        }
        score = (System.currentTimeMillis()-initTime)/1000.0;
    }

    public void draw(Canvas canvas){
        for(Wall o: walls){
            o.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.WHITE);
        canvas.drawText("Score: " + score, 25, 50 + paint.descent() - paint.ascent(), paint);
    }
}
