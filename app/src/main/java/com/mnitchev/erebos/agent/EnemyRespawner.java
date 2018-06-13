package com.mnitchev.erebos.agent;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.R;

import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;
import java.util.List;

public class EnemyRespawner {

    public Drawable enemyDrawalbe;
    public Drawable enemyProjectileDrawable;
    public final int canvasWidth;

    public EnemyRespawner(Drawable enemyDrawalbe, Drawable enemyProjectileDrawable, int canvasWidth){
        this.enemyDrawalbe = enemyDrawalbe;
        this.enemyProjectileDrawable = enemyProjectileDrawable;
        this.canvasWidth = canvasWidth;
    }

    public List<Agent> respawn(List<Agent> enemies) {
        final List<Agent> newEnemies = new ArrayList<>();
        if (enemies.isEmpty()){
            final Enemy enemy = new Enemy(enemyDrawalbe,
                    enemyProjectileDrawable, new Point(canvasWidth / 2, 200),canvasWidth);
            enemies.add(enemy);
        }
        return newEnemies;
    }
}
