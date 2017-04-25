package com.herudi.exovideo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by herudi-sahimar on 25/04/2017.
 */
public class ExoPlayerIntentModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    public final int TAG = 1;

    public ExoPlayerIntentModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void showVideoPlayer(String url) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity != null) {
            Intent i = new Intent(this.getReactApplicationContext(),PlayerActivity.class);
            i.putExtra("url", url);
            currentActivity.startActivityForResult(i, TAG);
        }
    }

    @Override
    public String getName() {
        return "ExoPlayerManager";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAG) {
            getCurrentActivity().finish();
        }
    }
}
