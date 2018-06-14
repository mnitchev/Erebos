package com.mnitchev.erebos.agent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.R;
import com.mnitchev.erebos.sprite.SpriteObject;

import java.util.ArrayList;
import java.util.List;

public class Player extends Agent {

    private static final int DAMAGE = 25;

    private static final int STARTING_OFFSET = 155;
    private static final int COUNTDOWN_DURATION = 10;
    private static final int PROJECTILE_SPEED = 35;

    private Drawable projectileDrawable;
    private boolean isShooting;
    private int nextShotCountdown;

    public Player(Context context, int canvasWidth, int canvasHeight) {
        super(new SpriteObject(context.getResources().getDrawable(R.drawable.erebos), 0.7f, 0.7f),
                new Point(canvasWidth / 2, canvasHeight - STARTING_OFFSET));
        this.projectileDrawable = context.getResources().getDrawable(R.drawable.projectile);
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

    private boolean canShoot(){
        return nextShotCountdown == 0 && isShooting;
    }

    public void setIsShooting(boolean isShooting){
        this.isShooting = isShooting;
    }

    public List<Projectile> shoot(){
        final List<Projectile> shots = new ArrayList<>();
        if (canShoot()){
            nextShotCountdown = COUNTDOWN_DURATION;
            shots.add(new Projectile(projectileDrawable, getCenter(), UP_DIRECTION, PROJECTILE_SPEED, DAMAGE, 0.7f));
        }
        return shots;
    }

}
