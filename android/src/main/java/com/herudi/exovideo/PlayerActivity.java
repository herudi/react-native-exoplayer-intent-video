package com.herudi.exovideo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by herudi-sahimar on 24/04/2017.
 */

public class PlayerActivity extends Activity {
    PlayerController playerCtrl;
    private static final String TAG = "PlayerActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerCtrl = new PlayerController(this);
        playerCtrl.fetchVideo(
                getIntent().getStringExtra("url"),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("subtitle")
        );
        setContentView(playerCtrl.getPlayerView());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG,"onStop()...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG,"onStart()...");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG,"onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG,"onPause()...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG,"onDestroy()...");
        playerCtrl.getPlayer().release();
    }

}


