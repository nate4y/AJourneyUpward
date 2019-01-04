package com.example.nate.ajourneyupward;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * The Same as TouchGame, but uses accelerometer to control player instead of touch.
 *
 * Accelerometer is not functional at this time as many complications with it arose.
 */

public class TiltGame extends SurfaceView implements SurfaceHolder.Callback{
    private Context context;
    private Intent i;
    private MainThread thread;

    private Sensor s;
    private SensorManager sm;
    private SensorEventListener sel;

    private Player player;
    private Point p;
    private WallManager wManager;

    private boolean movingBall = false;

    private boolean gameOver = false;
    private long gameOverTime;

    public TiltGame(Context context, Intent intent, Sensor sensor){
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
        s = sensor;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){}

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

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

    //Uses accelerometer to offset point of player
    public void update(){
        sel = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                p.offset((int)-x, (int)y);
                player.update(p);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        if(!gameOver) {
            player.update(p);
            wManager.update();
            if(wManager.collision(player)){
                gameOver = true;
                gameOverTime = System.currentTimeMillis();

            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.DKGRAY);
        player.draw(canvas);
        wManager.draw(canvas);
    }

    public void reset(){
        p = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(p);
        wManager = new WallManager(200, 350, 75, Color.BLACK);
        movingBall = false;
    }
}