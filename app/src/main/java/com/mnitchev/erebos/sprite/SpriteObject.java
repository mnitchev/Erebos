package com.mnitchev.erebos.sprite;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class SpriteObject {
    private final Drawable drawable;
    private final float xMultiplier;
    private final float yMultiplier;

    public SpriteObject(Drawable drawable){
        this(drawable, 1, 1);
    }

    public SpriteObject(Drawable drawable, float xMultiplier, float yMultiplier){
        this.drawable = drawable;
        this.xMultiplier = xMultiplier;
        this.yMultiplier = yMultiplier;
    }

    public void draw(Canvas canvas, Point position) {
        final Rect bounds = new Rect(position.x, position.y,
                position.x + getWidth(), position.y + getHeight());
        this.drawable.setBounds(bounds);
        this.drawable.draw(canvas);
    }

    public int getWidth(){
        return (int)(drawable.getIntrinsicWidth() * xMultiplier);
    }

    public int getHeight(){
        return (int)(drawable.getIntrinsicHeight() * yMultiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpriteObject object = (SpriteObject) o;

        if (Float.compare(object.xMultiplier, xMultiplier) != 0) return false;
        if (Float.compare(object.yMultiplier, yMultiplier) != 0) return false;
        return drawable != null ? drawable.equals(object.drawable) : object.drawable == null;
    }

    @Override
    public int hashCode() {
        int result = drawable != null ? drawable.hashCode() : 0;
        result = 31 * result + (xMultiplier != +0.0f ? Float.floatToIntBits(xMultiplier) : 0);
        result = 31 * result + (yMultiplier != +0.0f ? Float.floatToIntBits(yMultiplier) : 0);
        return result;
    }
}
