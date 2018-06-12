package com.mnitchev.erebos;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.mnitchev.erebos.panel.ErebosPanel;

import java.util.logging.Logger;

public class ErebosActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ErebosActivity","Starting erebos activity");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new ErebosPanel(this));
    }
}
