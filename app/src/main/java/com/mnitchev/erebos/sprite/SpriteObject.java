package com.mnitchev.erebos.sprite;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class SpriteObject {
    private final Drawable drawable;

    public SpriteObject(Drawable drawable) {
        this.drawable = drawable;

    }

    public void draw(Canvas canvas, Rect bounds) {
        this.drawable.setBounds(bounds);
        this.drawable.draw(canvas);
    }
}
