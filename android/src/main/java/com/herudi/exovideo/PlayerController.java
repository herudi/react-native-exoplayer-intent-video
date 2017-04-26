package com.herudi.exovideo;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by herudi-sahimar on 26/04/2017.
 */
public class PlayerController {

    private String TAG_NAME = "rnexoplayer";
    private Uri videoUri;
    private Uri subtitleUri;
    private BandwidthMeter bandwidthMeter;
    private ExtractorsFactory extractorsFactory;
    private TrackSelection.Factory trackSelectionFactory;
    private DataSource.Factory dataSourceFactory;
    private TrackSelector trackSelector;
    private LoadControl loadControl;
    private SimpleExoPlayer player;
    private PlayerView playerView;

    public PlayerController(Context context) {
        this.bandwidthMeter = new DefaultBandwidthMeter();
        this.loadControl = new DefaultLoadControl();
        this.extractorsFactory = new DefaultExtractorsFactory();
        this.trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        this.dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context.getApplicationContext(), TAG_NAME), (TransferListener<? super DataSource>) bandwidthMeter);
        this.trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        this.player = ExoPlayerFactory.newSimpleInstance(context, this.trackSelector, this.loadControl);
        this.playerView = new PlayerView(context,this.player);
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public void EventListener(){
        this.player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState){
                    case 2:{
                        playerView.getProgressBar().setVisibility(View.VISIBLE);
                    }break;
                    case 3:{
                        playerView.getProgressBar().setVisibility(View.GONE);
                    }break;
                    case 4:{
                        playerView.getProgressBar().setVisibility(View.GONE);
                    }break;
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }
        });
        this.player.setPlayWhenReady(true);
    }

    public void fetchVideo(String uri,String title, String sub) {
        if (title!=null){
            this.playerView.getTextView().setText(title);
        }
        this.setVideoUri(Uri.parse(uri));
        if (sub!=null){
            this.setSubtitleUri(Uri.parse(sub));
            this.player.prepare(this.getMergingMediaSource());
        }else{
            this.player.prepare(this.getMediaSource());
        }
        this.EventListener();
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }

    public Uri getSubtitleUri() {
        return subtitleUri;
    }

    public void setSubtitleUri(Uri subtitleUri) {
        this.subtitleUri = subtitleUri;
    }

    public ExtractorsFactory getExtractorsFactory() {
        return extractorsFactory;
    }

    public DataSource.Factory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public MediaSource getMediaSource(){
        return new ExtractorMediaSource(this.getVideoUri(),this.getDataSourceFactory(), this.getExtractorsFactory(), null, null);
    }

    public MediaSource getMediaSourceSubtitle(){
        return new SingleSampleMediaSource(this.getSubtitleUri(),this.getDataSourceFactory(), this.getFormat(), C.TIME_UNSET);
    }

    public Format getFormat(){
        return Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP,null, Format.NO_VALUE, Format.NO_VALUE, "indonesia", null);
    }

    public MergingMediaSource getMergingMediaSource(){
        return new MergingMediaSource(this.getMediaSource(), this.getMediaSourceSubtitle());
    }


}
