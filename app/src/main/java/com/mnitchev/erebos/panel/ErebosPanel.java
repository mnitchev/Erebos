package com.mnitchev.erebos.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.mnitchev.erebos.R;
import com.mnitchev.erebos.agent.AgentContainer;
import com.mnitchev.erebos.agent.Player;
import com.mnitchev.erebos.sprite.SpriteObject;

import org.w3c.dom.Text;

public class ErebosPanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TAG = "ErebosPanel";
    private MainLoop mainLoop;
    private SpriteObject background;
    private AgentContainer container;
    private Thread mainLoopThread;
    private TextView scoreView;
    private TextView healthView;
    private Runnable playerDeadCallback;
    private Runnable updateHealthCallback;
    private Runnable updateScoreCallback;

    public ErebosPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        getHolder().addCallback(this);
        this.background = new SpriteObject(context.getResources().getDrawable(R.drawable.space));
        this.mainLoop = new MainLoop(getHolder(), this);
        this.mainLoopThread = new Thread(mainLoop);
        invalidate();
    }

    public void update(){
        container.update();
        updateScore(container.getScore());
        updateHealth(container.getHealth());
        verifyPlayerAlive();
    }

    public void verifyPlayerAlive(){
        if (container.getHealth() <= 0) {
            this.playerDeadCallback.run();
        }
    }

    public void updateScore(int score){
        this.updateScoreCallback.run();
    }

    public void updateHealth(int health) {
        this.updateHealthCallback.run();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final DrawFilter filter = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, 0);
        canvas.setDrawFilter(filter);
        this.background.draw(canvas, new Point(0,0));
        this.container.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mainLoop.isPaused()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    this.container.setPlayerIsShooting(true);
                    this.container.movePlayer((int) event.getX());
                    break;
                case MotionEvent.ACTION_UP:
                    this.container.setPlayerIsShooting(false);
                    break;
            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(TAG, "Surface created");
        init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stop();
    }

    public void init(){
        final Canvas canvas = getHolder().lockCanvas();
        this.container = new AgentContainer(getContext(), canvas.getWidth(), canvas.getHeight());
        getHolder().unlockCanvasAndPost(canvas);
        this.mainLoop = new MainLoop(getHolder(), this);
        this.mainLoopThread = new Thread(mainLoop);
        this.mainLoop.setRunning(true);
        this.mainLoopThread.start();
    }

    public void stop() {
        try {
            this.mainLoop.setRunning(false);
            this.mainLoopThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Failed to stop main loop thread", e);
        }
    }

    public void pause(){
        this.mainLoop.setPaused(true);
    }

    public void unpause(){
        this.mainLoop.setPaused(false);
    }

    public void setUpdateScoreCallback(Runnable callback){
        this.updateScoreCallback = callback;
    }

    public void setUpdateHealthCallback(Runnable callback){
        this.updateHealthCallback = callback;
    }

    public void setHealthView(TextView healthView) {
        this.healthView = healthView;
    }

    public int getScore(){
        return container.getScore();
    }

    public int getHealth() {
        return container.getHealth();
    }

    public void setPlayerDeadCallback(Runnable callback){
        this.playerDeadCallback = callback;
    }

}
