package com.mnitchev.erebos.agent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentContainer {

    private static final int POINTS = 10;

    private Player player;
    private final List<Projectile> playerProjectiles;
    private final List<Projectile> enemyProjectiles;
    private final List<Agent> enemies;
    private final CollisionDetector collisionDetector;
    private final EnemyRespawner respawner;
    private final int canvasHeight;
    private int score;

    public AgentContainer(Context context, int canvasWidth, int canvasHeight){
        this.canvasHeight = canvasHeight;
        this.collisionDetector = new CollisionDetector();
        this.player = new Player(context, canvasWidth, canvasHeight);
        this.playerProjectiles = new CopyOnWriteArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        final Drawable enemyProjectileDrawable = context.getResources().getDrawable(R.drawable.enemy_projectile);
        this.respawner = new EnemyRespawner(context.getResources().getDrawable(R.drawable.enemy),
                enemyProjectileDrawable, canvasWidth);
        this.enemies = new ArrayList<>();
        this.score = 0;
    }

    public void draw(Canvas canvas) {
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
    }

    public void update(){
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
            }
        }
        for (int i = 0; i < enemyProjectiles.size(); i++) {
            final Projectile projectile = enemyProjectiles.get(i);
            projectile.update();
            if (projectileDestroyed(projectile)){
                enemyProjectiles.remove(projectile);
            }
        }
    }

    public void movePlayer(int x){
        player.move(x);
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
}
