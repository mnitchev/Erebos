package com.mnitchev.erebos.panel;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

class MainLoop implements Runnable {

    private final int MAX_FPS = 30;
    private final int CONST = 1000000;
    private final int TARGET_TIME = 1000/MAX_FPS;
    private final ErebosPanel panel;
    private final SurfaceHolder surface;
    private boolean running;
    private boolean paused;

    public MainLoop(SurfaceHolder surface, ErebosPanel panel) {
        this.surface = surface;
        this.panel = panel;
        this.running = true;
        this.paused = false;
    }

    @Override
    public void run() {
        while(running){
            if(!paused) {
                long startTime = System.nanoTime();

                final Canvas canvas = surface.lockCanvas();
                try {
                    synchronized (surface) {
                        panel.update();
                        panel.draw(canvas);
                    }
                }finally {
                    surface.unlockCanvasAndPost(canvas);
                }

                long execTime = (System.nanoTime() - startTime) / CONST;
                long waitTime = TARGET_TIME - execTime;

                    try {
                        if (waitTime > 0){
                            Thread.sleep(waitTime);
                        }
                    } catch (InterruptedException e) {
                        Log.e("MainLoop", "Failed to sleep");
                    }
            }
        }
    }

    public boolean isPaused(){
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
