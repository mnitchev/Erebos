package com.mnitchev.erebos.panel;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ErebosPanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final Logger LOGGER = Logger.getLogger(ErebosPanel.class.getCanonicalName());
    private final MainLoop mainLoop = new MainLoop();
    private Thread mainLoopThread;

    public ErebosPanel(Context context) {
        super(context);
        setFocusable(true);
        getHolder().addCallback(this);

        this.mainLoopThread = new Thread(mainLoop);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mainLoopThread = new Thread(mainLoop);
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
            LOGGER.log(Level.SEVERE, "Failed to stop main loop thread", e);
        }
    }

}
