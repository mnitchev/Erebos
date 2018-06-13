package com.mnitchev.erebos.agent;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.sprite.SpriteObject;

public class Projectile {

    private static final int STEP = 100;
    private static final int DAMAGE = 25;

    private SpriteObject sprite;
    private Point position;
    private int direction;
    private boolean destroyed;

    public Projectile(Drawable drawable, Point startPosition, int direction){
        this.sprite = new SpriteObject(drawable);
        this.position = new Point(startPosition.x - sprite.getWidth() / 2, startPosition.y);
        this.direction = direction;
        this.destroyed = false;
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, position);
    }

    public void update(){
        if (!isDestroyed()){
            position.y += direction * STEP;
        }
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight(){
        return sprite.getHeight();
    }

    public Point getPosition(){
        return position;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void destroy(){
        destroyed = true;
    }

    public int getDamage(){
        return DAMAGE;
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
