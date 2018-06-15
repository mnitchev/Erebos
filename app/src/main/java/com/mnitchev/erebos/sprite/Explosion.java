package com.mnitchev.erebos.sprite;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Explosion {

    private static final int TICKS_PER_FRAME = 5;

    private final Queue<Drawable> frames;
    private final Point position;
    private final float modifierX;
    private final float modifierY;
    private int nextFrameCountdown;

    public Explosion(List<Drawable> frames, Point position, float modifierX, float modifierY){
        this.frames = new LinkedList<>(frames);
        this.nextFrameCountdown = TICKS_PER_FRAME;
        this.position = position;
        this.modifierX = modifierX;
        this.modifierY = modifierY;
    }

    public void draw(Canvas canvas){
        final Drawable frame = frames.peek();
        frame.setBounds(new Rect(position.x, position.y,
                position.x + (int)(frame.getIntrinsicWidth() * modifierX),
                position.y + (int)(frame.getIntrinsicHeight() * modifierY)));
        frame.draw(canvas);
    }

    public void update(){
        if (nextFrameCountdown == 0){
            frames.remove();
            nextFrameCountdown = TICKS_PER_FRAME;
        }
        nextFrameCountdown--;
    }

    public boolean isDone(){
        return frames.isEmpty();
    }
}
