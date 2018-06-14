package com.mnitchev.erebos.agent;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.R;

import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EnemyRespawner {

    private static final int OFFSET = 50;

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
            final int enemyWidth = enemyDrawalbe.getIntrinsicWidth();
            final int enemyHeight = (int)(enemyDrawalbe.getIntrinsicHeight() * 0.5f);
            final int numberOfEnemies = canvasWidth / enemyWidth;
            for (int j = 0; j < 4; j ++) {
                for (int i = 0; i < numberOfEnemies; i++) {
                    newEnemies.add(new Enemy(enemyDrawalbe, enemyProjectileDrawable,
                            new Point(i * enemyWidth + enemyWidth / 2, j * enemyHeight + OFFSET),
                                0, canvasWidth));
//                            i * enemyWidth, (i + 1) * enemyWidth));
                }
            }
        }
        return newEnemies;
    }

}
