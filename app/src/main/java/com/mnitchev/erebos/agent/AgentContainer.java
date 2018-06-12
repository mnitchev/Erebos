package com.mnitchev.erebos.agent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mnitchev.erebos.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentContainer {

    private Player player;
    private final List<Projectile> playerProjectiles;
    private Drawable projectileDrawable;

    public AgentContainer(Context context, int canvasWidth, int canvasHeight){
        this.player = new Player(context, canvasWidth, canvasHeight);
        this.playerProjectiles = new CopyOnWriteArrayList<>();
        this.projectileDrawable = context.getResources().getDrawable(R.drawable.projectile);
    }

    public void draw(Canvas canvas) {
        player.draw(canvas);
        final List<Projectile> proj = getPlayerProjectiles();
        for (Projectile projectile : proj){
            projectile.draw(canvas);
        }
    }

    public void update(){
        player.update();
        shootPlayerProjectile();
        final List<Projectile> proj = getPlayerProjectiles();
        for (Projectile projectile : proj) {
            projectile.update();
            if (projectile.isOffScreen()){
                playerProjectiles.remove(projectile);
            }
        }
    }

    public void movePlayer(int x){
        player.move(x);
    }

    public void setPlayerIsShooting(boolean isShooting) {
        player.setIsShooting(isShooting);
    }

    private void shootPlayerProjectile(){
        if (player.canShoot()){
            player.shoot();
            playerProjectiles.add(createPlayerProjectile());
        }
    }

    private Projectile createPlayerProjectile(){
        final Point playerCenter = player.getCenter();
        return new Projectile(projectileDrawable, playerCenter);
    }

    private List<Projectile> getPlayerProjectiles(){
        synchronized (playerProjectiles) {
             return Collections.unmodifiableList(new ArrayList<>(playerProjectiles));
        }
    }
}
