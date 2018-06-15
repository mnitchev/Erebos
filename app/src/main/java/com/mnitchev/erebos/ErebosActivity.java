package com.mnitchev.erebos;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mnitchev.erebos.panel.ErebosPanel;

public class ErebosActivity extends Activity {

    private ErebosPanel panel;
    private MediaPlayer mediaPlayer;
    private TextView scoreView;
    private TextView healthView;
    private View pauseMenu;
    private View continueButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ErebosActivity","Starting erebos activity");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_erebos);

        this.panel = findViewById(R.id.canvas);
        this.healthView = findViewById(R.id.health);
        this.scoreView = findViewById(R.id.score);
        this.continueButton = findViewById(R.id.continueButton);
        this.pauseMenu = findViewById(R.id.menu);
        this.panel.setPlayerDeadCallback(new Runnable(){
            @Override
            public void run() {
                pauseOnPlayerDeath();
            }
        });
        this.panel.setUpdateScoreCallback(new Runnable(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scoreView.setText("Score: " + panel.getScore());
                    }
                });
            }
        });
        this.panel.setUpdateHealthCallback(new Runnable(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        healthView.setText("Health: " + panel.getHealth());
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        this.panel.pause();
        this.mediaPlayer.pause();

        super.onPause();
    }


    @Override
    public void onResume() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        this.mediaPlayer.setLooping(true);

        this.mediaPlayer.start();
        this.panel.unpause();

        super.onResume();
    }

    public void pauseOnPlayerDeath(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pause();
            }
        });
    }

    public void pause() {
        this.panel.pause();
        continueButton.setVisibility(View.GONE);
        pauseMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        this.panel.pause();
        this.pauseMenu.setVisibility(View.VISIBLE);
    }

    public void continueGame(View view){
        this.pauseMenu.setVisibility(View.GONE);
        this.panel.unpause();
    }

    public void restart(View view) {
        this.pauseMenu.setVisibility(View.GONE);
        this.continueButton.setVisibility(View.VISIBLE);
        this.panel.init();
    }

    public void mainMenu(View view) {
        Log.i("ErebosActivity", "Stopping game");
        this.panel.stop();
        this.mediaPlayer.stop();
        Intent myIntent = new Intent(ErebosActivity.this, MainMenuActivity.class);
        ErebosActivity.this.startActivity(myIntent);
    }

    public void toggleMusic(View view) {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
            final TextView text = findViewById(R.id.musicButton);
            text.setText("Music: Off");
        } else {
            this.mediaPlayer.start();
            final TextView text = findViewById(R.id.musicButton);
            text.setText("Music: On");
        }
    }

}
