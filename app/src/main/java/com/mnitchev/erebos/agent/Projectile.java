package com.mnitchev.erebos.agent;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.sprite.SpriteObject;

public class Projectile {

    private int speed;
    private SpriteObject sprite;
    private Point position;
    private int direction;
    private boolean destroyed;
    private int damage;


    public Projectile(Drawable drawable, Point startPosition, int direction, int speed, int damage, float scale){
        this.sprite = new SpriteObject(drawable, scale, scale);
        this.position = new Point(startPosition.x - sprite.getWidth() / 2, startPosition.y);
        this.direction = direction;
        this.destroyed = false;
        this.speed = speed;
        this.damage = damage;
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, position);
    }

    public void update(){
        if (!isDestroyed()){
            position.y += direction * speed;
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
        return damage;
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
