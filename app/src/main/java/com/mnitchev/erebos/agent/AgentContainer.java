package com.mnitchev.erebos.agent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.R;
import com.mnitchev.erebos.sprite.Background;
import com.mnitchev.erebos.sprite.Explosion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentContainer {

    private static final int POINTS = 10;

    private final Player player;
    private final Background background;
    private final List<Projectile> playerProjectiles;
    private final List<Projectile> enemyProjectiles;
    private final List<Agent> enemies;
    private final CollisionDetector collisionDetector;
    private final EnemyRespawner respawner;
    private final List<Explosion> explosions;
    private final List<Drawable> explosionFrames;
    private final int canvasHeight;
    private int score;

    public AgentContainer(Context context, int canvasWidth, int canvasHeight){
        this.canvasHeight = canvasHeight;
        this.collisionDetector = new CollisionDetector();
        this.explosionFrames = generateExplosion(context);
        this.player = new Player(context, canvasWidth, canvasHeight);
        this.playerProjectiles = new CopyOnWriteArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        final Drawable enemyProjectileDrawable = context.getResources().getDrawable(R.drawable.enemy_projectile);
        this.respawner = new EnemyRespawner(context.getResources().getDrawable(R.drawable.enemy),
                context.getResources().getDrawable(R.drawable.boss),
                enemyProjectileDrawable, canvasWidth);
        this.enemies = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.background = new Background(context.getResources().getDrawable(R.drawable.space), canvasHeight, canvasWidth);
        this.score = 0;
    }

    public void draw(Canvas canvas) {
        background.draw(canvas);
        player.draw(canvas);
        final List<Projectile> proj = getPlayerProjectiles();
        for (Projectile projectile : proj){
            projectile.draw(canvas);
        }
        for (Projectile projectile : enemyProjectiles){
            projectile.draw(canvas);
        }
        for (Agent enemy : enemies){
            enemy.draw(canvas);
        }
        for (Explosion explosion : explosions){
            explosion.draw(canvas);
        }
    }

    public void update(){
        background.update();
        enemies.addAll(respawner.respawn(enemies));
        final List<Projectile> proj = getPlayerProjectiles();
        collisionDetector.collide(enemyProjectiles, player);
        int collisions = collisionDetector.collide(proj, enemies);
        score += collisions * POINTS;
        player.update();
        playerProjectiles.addAll(player.shoot());
        for (Projectile projectile : proj) {
            projectile.update();
            if (projectileDestroyed(projectile)){
                playerProjectiles.remove(projectile);
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            final Agent enemy = enemies.get(i);
            enemy.update();
            enemyProjectiles.addAll(enemy.shoot());
            if(enemy.isDead()){
                enemies.remove(i);
                float modifier = 1.1f;
                if (enemy instanceof Boss){
                    modifier = 2.2f;
                }
                explosions.add(new Explosion(explosionFrames, enemy.getPosition(), modifier, modifier));
            }
        }
        for (int i = 0; i < enemyProjectiles.size(); i++) {
            final Projectile projectile = enemyProjectiles.get(i);
            projectile.update();
            if (projectileDestroyed(projectile)){
                enemyProjectiles.remove(projectile);
            }
        }
        for (int i = 0; i < explosions.size(); i++) {
            final Explosion explosion = explosions.get(i);
            explosion.update();
            if(explosion.isDone()) {
                explosions.remove(i);
            }
        }
    }

    public void movePlayer(int x){
        player.move(x);
        background.move(x);
    }

    public void setPlayerIsShooting(boolean isShooting) {
        player.setIsShooting(isShooting);
    }

    public int getHealth(){
        return player.getHealth();
    }

    public int getScore(){
        return score;
    }

    private List<Projectile> getPlayerProjectiles(){
        synchronized (playerProjectiles) {
             return Collections.unmodifiableList(new ArrayList<>(playerProjectiles));
        }
    }

    private boolean projectileDestroyed(Projectile projectile) {
        return projectile.isDestroyed() || projectile.getPosition().y <= 0 || projectile.getPosition().y >= canvasHeight;
    }

    private List<Drawable> generateExplosion(Context context){
        final List<Drawable> frames = new LinkedList<>();
        frames.add(context.getResources().getDrawable(R.drawable.explosion1));
        frames.add(context.getResources().getDrawable(R.drawable.explosion2));
        frames.add(context.getResources().getDrawable(R.drawable.explosion3));
        frames.add(context.getResources().getDrawable(R.drawable.explosion4));
        frames.add(context.getResources().getDrawable(R.drawable.explosion5));
        frames.add(context.getResources().getDrawable(R.drawable.explosion6));
        frames.add(context.getResources().getDrawable(R.drawable.explosion7));
        frames.add(context.getResources().getDrawable(R.drawable.explosion8));
        frames.add(context.getResources().getDrawable(R.drawable.explosion9));
        frames.add(context.getResources().getDrawable(R.drawable.explosion10));
        frames.add(context.getResources().getDrawable(R.drawable.explosion11));
        frames.add(context.getResources().getDrawable(R.drawable.explosion12));

        return frames;
    }
}
