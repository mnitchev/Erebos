package com.mnitchev.erebos.agent;

import android.graphics.Canvas;
import android.graphics.Point;

import com.mnitchev.erebos.sprite.SpriteObject;

import java.util.Collection;

public abstract class Agent {

    protected static final int DOWN_DIRECTION = 1;
    protected static final int UP_DIRECTION = -1;

    protected final Point position;
    protected final SpriteObject sprite;
    protected int nextShotCountdown;
    protected int hitPoints;

    public Agent(SpriteObject sprite, Point position) {
        this.position = position;
        this.sprite = sprite;
        this.nextShotCountdown = 0;
        this.hitPoints = 100;
    }

    public abstract Collection<Projectile> shoot();
    public abstract void update();

    public void draw(Canvas canvas) {
        this.sprite.draw(canvas, position);
    }

    public void takeDamage(int amount) {
        this.hitPoints -= amount;
    }

    public boolean isDead(){
        return this.hitPoints <= 0;
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public Point getPosition() {
        return position;
    }

    public int getHealth() {
        return hitPoints;
    }

    public Point getCenter(){
        return new Point(position.x + sprite.getWidth() / 2, position.y);
    }
}
