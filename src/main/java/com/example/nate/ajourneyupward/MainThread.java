package com.example.nate.ajourneyupward;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * An extension of the Thread class in the Java library. It calls the draw and update methods
 * of the Game class in its run method.
 *
 * Created by Nate on 12/10/2017.
 */

public class MainThread extends Thread{
    public static final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private TouchGame touchGame;
    private TiltGame tiltGame;
    private boolean running;
    public static Canvas canvas;

    public void setRunning(boolean running){
        this.running = running;
    }

    //The two constructors take a SurfaceHolder (SurfaceView Helper) and
    //one of the two game types.

    public MainThread(SurfaceHolder surfaceHolder, TouchGame touchGame){
        super();
        this.surfaceHolder = surfaceHolder;
        this.touchGame = touchGame;
    }

    public MainThread(SurfaceHolder surfaceHolder, TiltGame tiltGame){
        super();
        this.surfaceHolder = surfaceHolder;
        this.tiltGame = tiltGame;
    }


    //The threads run method: draws the canvas each iteration, taking into account any
    //updates to object positions.
    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    if(touchGame == null){
                        this.tiltGame.update();
                        this.tiltGame.draw(canvas);
                    }else{
                        this.touchGame.update();
                        this.touchGame.draw(canvas);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){e.printStackTrace();}
                }
            }

            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try{
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            }catch(Exception e){e.printStackTrace();}

            totalTime += System.nanoTime() + startTime;
            frameCount++;
            if(frameCount == MAX_FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
}