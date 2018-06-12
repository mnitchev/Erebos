package com.mnitchev.erebos.agent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.mnitchev.erebos.R;
import com.mnitchev.erebos.sprite.SpriteObject;

public class Player {

    private static final int STARTING_OFFSET = 400;
    private static final int COUNTDOWN_DURATION = 5;

    private final Point position;
    private final SpriteObject sprite;
    private boolean isShooting;
    private int nextShotCountdown;

    public Player(Context context, int canvasWidth, int canvasHeight) {
        this.sprite = new SpriteObject(context.getResources().getDrawable(R.drawable.erebos));
        this.position = new Point(canvasWidth / 2, canvasHeight - STARTING_OFFSET);
        this.nextShotCountdown = 0;
        this.isShooting = false;
    }

    public void move(int x){
        position.set(x - sprite.getWidth() / 2, position.y);
    }

    public void update(){
        if (nextShotCountdown > 0 ){
            nextShotCountdown--;
        }
    }

    public void draw(Canvas canvas){
        this.sprite.draw(canvas, position);
    }

    public boolean canShoot(){
        return nextShotCountdown == 0 && isShooting;
    }

    public void setIsShooting(boolean isShooting){
        this.isShooting = isShooting;
    }

    public void shoot(){
        nextShotCountdown = COUNTDOWN_DURATION;
    }

    public Point getCenter(){
        return new Point(position.x + sprite.getWidth() / 2, position.y);
    }

}
