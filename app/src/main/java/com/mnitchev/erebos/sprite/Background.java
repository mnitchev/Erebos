package com.mnitchev.erebos.sprite;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Background {

    private static final int SPEED = 10;

    private final Drawable sprite;
    private final int canvasHeight;
    private final int canvasWidth;
    private final Point position;

    public Background(Drawable sprite, int canvasHeight, int canvasWidth){
        this.sprite = sprite;
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.position = new Point((canvasWidth / 2) - (sprite.getIntrinsicWidth() / 2),
                canvasHeight - sprite.getIntrinsicHeight());
    }

    public void update(){
        position.y += SPEED;
    }

    public void move(int x){
        int movement  = ((canvasWidth / 2) - x) / 50;
        if(position.x + movement >= 0 ||
                position.x + sprite.getIntrinsicWidth() + movement <= canvasWidth){
            return;
        }
        position.x += movement;
    }

    public void draw(Canvas canvas) {
        if (position.y >= 0){
            drawBackground(canvas, new Point(position.x, position.y - sprite.getIntrinsicHeight()));
        }
        if (position.y >= canvasHeight){
            position.y = canvasHeight - sprite.getIntrinsicHeight();
        }
        drawBackground(canvas, position);
    }

    private void drawBackground(Canvas canvas, Point position){
        sprite.setBounds(new Rect(position.x, position.y,
                position.x + sprite.getIntrinsicWidth(),
                position.y + sprite.getIntrinsicHeight()));
        sprite.draw(canvas);
    }
}
