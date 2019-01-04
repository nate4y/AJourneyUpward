package com.example.nate.ajourneyupward;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * The Tilt Game Activity is just like the TouchGame, but uses the TiltGame engine instead.
 */

public class TiltGameActivity extends AppCompatActivity{
    private Sensor s;
    private SensorManager sm;
    private SensorEventListener sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        s = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);

        Intent i = new Intent(this, MainActivity.class);
        setContentView(new TiltGame(this, i, s));
    }
}