package com.mnitchev.erebos.agent;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.sprite.SpriteObject;

public class Projectile {

    private static final int STEP = 100;

    private SpriteObject sprite;
    private Point position;

    public Projectile(Drawable drawable, Point startPosition){
        this.sprite = new SpriteObject(drawable);
        this.position = startPosition;
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, position);
    }

    public void update(){
        if (!isOffScreen()){
            position.y -= STEP;
        }
    }

    public boolean isOffScreen(){
        return position.y <= -sprite.getHeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Projectile that = (Projectile) o;

        if (sprite != null ? !sprite.equals(that.sprite) : that.sprite != null) return false;
        return position != null ? position.equals(that.position) : that.position == null;
    }

    @Override
    public int hashCode() {
        int result = sprite != null ? sprite.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
