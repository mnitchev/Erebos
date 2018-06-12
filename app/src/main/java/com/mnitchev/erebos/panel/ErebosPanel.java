package com.mnitchev.erebos.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mnitchev.erebos.R;
import com.mnitchev.erebos.agent.AgentContainer;
import com.mnitchev.erebos.agent.Player;
import com.mnitchev.erebos.sprite.SpriteObject;

public class ErebosPanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TAG = "ErebosPanel";
    private MainLoop mainLoop;
    private SpriteObject background;
    private AgentContainer container;
    private Thread mainLoopThread;

    public ErebosPanel(Context context) {
        super(context);
        setFocusable(true);
        getHolder().addCallback(this);
        this.background = new SpriteObject(context.getResources().getDrawable(R.drawable.space));
        this.mainLoop = new MainLoop(getHolder(), this);
        this.mainLoopThread = new Thread(mainLoop);
    }

    public void update(){
        container.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final DrawFilter filter = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, 0);
        canvas.setDrawFilter(filter);
        Log.i(TAG, "Drawing");
        this.background.draw(canvas, new Point(0,0));
        this.container.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                this.container.setPlayerIsShooting(true);
                this.container.movePlayer((int)event.getX());
                break;
            case MotionEvent.ACTION_UP:
                this.container.setPlayerIsShooting(false);
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(TAG, "Surface created");
        final Canvas canvas = surfaceHolder.lockCanvas();
        this.container = new AgentContainer(getContext(), canvas.getWidth(), canvas.getHeight());
        surfaceHolder.unlockCanvasAndPost(canvas);
        this.mainLoop = new MainLoop(getHolder(), this);
        this.mainLoopThread = new Thread(mainLoop);
        this.mainLoop.setRunning(true);
        this.mainLoopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        try {
            this.mainLoop.setRunning(false);
            this.mainLoopThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Failed to stop main loop thread", e);
        }
    }

}
