package com.mnitchev.erebos.agent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.mnitchev.erebos.R;
import com.mnitchev.erebos.sprite.SpriteObject;

public class Player {

    private static final int STARTING_OFFSET = 400;
    private static final int PLAYER_WIDTH = 158;
    private static final int PLAYER_HEIGHT = 147;


    private final Point position;
    private final SpriteObject sprite;

    public Player(Context context, int canvasWidth, int canvasHeight) {
        this.sprite = new SpriteObject(context.getResources().getDrawable(R.drawable.erebos));
        this.position = new Point(canvasWidth / 2, canvasHeight - STARTING_OFFSET);
    }

    public void move(int x){
        position.set(x - PLAYER_WIDTH / 2, position.y);
    }

    public void draw(Canvas canvas){
        final Rect bonds = new Rect(position.x, position.y,
                position.x + PLAYER_WIDTH * 2, position.y + PLAYER_HEIGHT * 2);
        this.sprite.draw(canvas, bonds);
    }

}
