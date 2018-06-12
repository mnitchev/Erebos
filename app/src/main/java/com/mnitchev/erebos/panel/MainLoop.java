package com.mnitchev.erebos.panel;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.mnitchev.erebos.render.Renderer;
import com.mnitchev.erebos.sprite.SpriteObject;

class MainLoop implements Runnable {

    private final ErebosPanel panel;
    private final SurfaceHolder surface;
    private boolean running;

    public MainLoop(SurfaceHolder surface, ErebosPanel panel) {
        this.surface = surface;
        this.panel = panel;
        this.running = true;
    }


    @Override
    public void run() {
        while(running){
            final Canvas canvas = surface.lockCanvas();
            panel.update();
            panel.draw(canvas);
            surface.unlockCanvasAndPost(canvas);
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
