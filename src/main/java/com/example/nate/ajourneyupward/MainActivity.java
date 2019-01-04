package com.example.nate.ajourneyupward;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * The startup activity. It contains two buttons which launch activities accordingly.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Button that launches TouchGame
        Button bTouch = (Button) findViewById(R.id.btnTouch);
        bTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TouchGameActivity.class));
            }
        });

        //Button that launces TiltGame
        Button bTilt = (Button) findViewById(R.id.btnTilt);
        bTilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TiltGameActivity.class));
            }
        });
    }
}