package com.mnitchev.erebos.agent;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.sprite.SpriteObject;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Agent {

    private static final int COUNTDOWN_DURATION = 20;
    private static final int DEFAULT_SPEED = 25;

    private final int canvasWidth;
    private int direction;
    private final int speed;
    private final Drawable projectile;
    private int hitPoints;

    public Enemy(Drawable sprite, Drawable projectile, Point startPosition, int canvasWidth){
        super(new SpriteObject(sprite, 0.5f, 0.5f), startPosition);
        this.canvasWidth = canvasWidth;
        this.speed = DEFAULT_SPEED;
        this.direction = -1;
        this.projectile = projectile;
        this.hitPoints = 100;
    }

    public void update(){
        if (nextShotCountdown > 0 ){
            nextShotCountdown--;
        }
        move();
    }

    private void move() {
        if (position.x <= 0) {
            direction = 1;
        }
        if (position.x + sprite.getWidth() >= canvasWidth) {
            direction = -1;
        }
        position.x += direction * speed;
    }

    private boolean canShoot() {
        return nextShotCountdown == 0;
    }

    public List<Projectile> shoot() {
        final List<Projectile> shots = new ArrayList<>();
        if (canShoot()) {
            nextShotCountdown = COUNTDOWN_DURATION;
            shots.add(new Projectile(projectile, getCenter(), DOWN_DIRECTION));
        }
        return shots;
    }

    public void takeDamage(int amount) {
        this.hitPoints -= amount;
    }

    public boolean isDead(){
        return this.hitPoints <= 0;
    }

    public Point getCenter(){
        return new Point(position.x + sprite.getWidth() / 2, position.y);
    }

}
