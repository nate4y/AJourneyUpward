package com.example.nate.ajourneyupward;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Game Engine for touch based games. Extends SurfaceView for drawing and also allows us to
 * pass this as a ContentView in the GameActivity class.
 */

public class TouchGame extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private Intent i; //need to receive an intent in the constructor to navigate back to home
    private MainThread thread;

    private Player player;
    private Point p;
    private WallManager wManager;

    private boolean moving = false; //if finger is touching player this is true

    private boolean gameOver = false;

    //Constructor initializes fields and GameObjects
    public TouchGame(Context context, Intent intent){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        player = new Player(new Rect(100, 100, 200, 200), Color.CYAN);
        p = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(p);
        wManager = new WallManager(200, 350, 75, Color.BLACK);
        setFocusable(true);
        this.context = context;
        i = intent;
    }

    //unused but override required
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){}

    //when the game is initialized, start the thread and set initial time
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    //stores position when game is left and reentered
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){e.printStackTrace();}
            retry = false;
        }
    }

    //Listens for touch events and processes accordingly
    //Player can only move if user is touching and dragging the player
    public boolean onTouchEvent(MotionEvent e){
        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRect().contains((int)e.getX(), (int)e.getY()))
                    moving = true;
                if(gameOver){
                    context.startActivity(i);
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(moving && !gameOver)
                    p.set((int)e.getX(), (int)e.getY());
                break;
            case MotionEvent.ACTION_UP:
                moving = false;
                break;
        }
        return true;
    }

    //Moves player and walls, checks for collision
    public void update(){
        if(!gameOver) {
            player.update(p);
            wManager.update();
            if(wManager.collision(player))
                gameOver = true;
        }
    }

    //draws walls and player, displays game over if that's the case
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.DKGRAY);
        player.draw(canvas);
        wManager.draw(canvas);

        if(gameOver){
            Paint paint = new Paint();
            paint.setColor(Color.MAGENTA);
            paint.setTextSize(100);
            canvas.drawText("GAME OVER", 100, 300, paint);
        }
    }
}