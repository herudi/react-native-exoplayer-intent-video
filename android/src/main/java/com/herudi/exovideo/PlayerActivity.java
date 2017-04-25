package com.herudi.exovideo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by herudi-sahimar on 24/04/2017.
 */
public class PlayerActivity extends Activity {
    private static final String TAG = "PlayerActivity";
    private BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    private TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
    private TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
    private LoadControl loadControl = new DefaultLoadControl();
    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        relativeLayout = new RelativeLayout(this);
        progressBar = new ProgressBar(this);
        simpleExoPlayerView = new SimpleExoPlayerView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        simpleExoPlayerView.setLayoutParams(new SimpleExoPlayerView.LayoutParams(
                SimpleExoPlayerView.LayoutParams.MATCH_PARENT,
                SimpleExoPlayerView.LayoutParams.MATCH_PARENT
        ));
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));
        relativeLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black));
        relativeLayout.addView(simpleExoPlayerView);
        relativeLayout.addView(progressBar,params);
        setContentView(relativeLayout);
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);
        Intent i= getIntent();
        Uri mp4VideoUri = Uri.parse(i.getStringExtra("url"));
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "rnexoplayer"), (TransferListener<? super DataSource>) bandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        final MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri,dataSourceFactory, extractorsFactory, null, null);
        player.prepare(videoSource);
        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.v(TAG,"onTimelineChanged");
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.v(TAG,"onTracksChanged");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.v(TAG,"onLoadingChanged");
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState){
                    case 2:{
                        progressBar.setVisibility(View.VISIBLE);
                    }break;
                    case 3:{
                        progressBar.setVisibility(View.GONE);
                    }break;
                    case 4:{
                        progressBar.setVisibility(View.GONE);
                    }break;
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG,error.getMessage());
            }

            @Override
            public void onPositionDiscontinuity() {
                Log.v(TAG,"onPositionDiscontinuity");
            }
        });
        player.setPlayWhenReady(true);

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
        player.release();
    }

}

