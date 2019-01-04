package com.example.nate.ajourneyupward;

import android.graphics.Canvas;

/**
 * A simple Game Development interface that is used to create any game object.
 */

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
