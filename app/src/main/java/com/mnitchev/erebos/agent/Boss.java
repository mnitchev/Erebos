package com.mnitchev.erebos.agent;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.sprite.SpriteObject;

import java.util.ArrayList;
import java.util.Collection;

public class Boss extends Agent {

    private static final int STARTING_OFFSET = 50;
    private static final int COOLDOWN_DURATION = 40;
    private static final int FIRST_OFFSET = 110;
    private static final int SECOND_OFFSET = 24;
    private static final int DAMAGE = 5;

    private Drawable projectileDrawable;
    private int leftBorder;
    private int rightBorder;
    private int direction = 1;
    private int speed = 10;
    private int firstCooldown;
    private int secondCooldown;

    public Boss(Drawable bossDrawable, Drawable projectileDrawable, int canvasWidth){
        super(new SpriteObject(bossDrawable),
                new Point(canvasWidth / 2, STARTING_OFFSET));
        this.leftBorder = -10;
        this.rightBorder = canvasWidth + 10;
        this.firstCooldown = COOLDOWN_DURATION / 2;
        this.secondCooldown = COOLDOWN_DURATION;
        this.projectileDrawable = projectileDrawable;
        this.hitPoints = 1000;
    }

    @Override
    public Collection<Projectile> shoot() {
        final Collection<Projectile> shots = new ArrayList<>();
        if(firstCooldown <= 0){
            firstCooldown = COOLDOWN_DURATION;
            shots.add(getProjectile(FIRST_OFFSET));
            shots.add(getProjectile(-FIRST_OFFSET));
        }
        if(secondCooldown <= 0) {
            secondCooldown = COOLDOWN_DURATION;
            shots.add(getProjectile(SECOND_OFFSET));
            shots.add(getProjectile(-SECOND_OFFSET));
        }
        return shots;
    }

    private Projectile getProjectile(int offset){
        return new Projectile(projectileDrawable, new Point(position.x + sprite.getWidth() / 2 + offset,
                position.y + sprite.getHeight()), DOWN_DIRECTION, randomWithRange(25, 50), DAMAGE, 1.0f);
    }

    @Override
    public void update() {
        move();
        firstCooldown--;
        secondCooldown--;
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

}
