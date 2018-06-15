package com.mnitchev.erebos.agent;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.sprite.SpriteObject;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Agent {

    private static final int DAMAGE = 5;
    private int countdownDuration;
    private final int leftBorder;
    private final int rightBorder;
    private int direction;
    private final int speed;
    private final Drawable projectile;

    public Enemy(Drawable sprite, Drawable projectile, Point startPosition, int leftBorder, int rightBorder){
        super(new SpriteObject(sprite, 0.5f, 0.5f), startPosition);
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.speed = randomWithRange(1, 20);
        this.direction = -1;
        this.projectile = projectile;
        this.countdownDuration = randomWithRange(15, 50);
        this.nextShotCountdown = randomWithRange(0, countdownDuration);
    }

    public void update(){
        if (nextShotCountdown > 0 ){
            nextShotCountdown--;
        }
        move();
    }

    private void move() {
        if (position.x <= leftBorder) {
            direction = 1;
        }
        if (position.x + sprite.getWidth() >= rightBorder) {
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
            nextShotCountdown = countdownDuration;
            shots.add(new Projectile(projectile, getCenter(), DOWN_DIRECTION, randomWithRange(10, 50), DAMAGE, 0.7f));
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
