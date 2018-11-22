package com.example.vu.morningofowl.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.media.session.MediaController;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerActivity extends Activity  {
    private int tapBack = 0;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exo_player);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
        progressBar  = (ProgressBar)findViewById(R.id.progressBar);
        //Toast.makeText(this, Link, Toast.LENGTH_SHORT).show();



        String Link = getIntent().getStringExtra("Link");
        String Sub = getIntent().getStringExtra("Link_Sub");


        initPlayer(Link, Sub);


    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initPlayer(String Link, String Sub) {
        try {


            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory trackSelectorfactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(trackSelectorfactory);
            //khởi tạo player
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            //khởi tạo exoplayerview
            SimpleExoPlayerView simpleExoPlayerView = findViewById(R.id.exoplayer);


            //chuẩn bị datasource để player có thể load
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Test ExoPlayer"));
            //giải mã data
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            //Đường dẫn để player load video
            Uri videoUri = Uri.parse(Link);
            Uri subtitleUri = Uri.parse(Sub);
            MediaSource videoSource = new ExtractorMediaSource(videoUri,
                    dataSourceFactory,
                    extractorsFactory,
                    null,
                    null);


            // Build the subtitle MediaSource.
            Format subtitleFormat = Format.createTextSampleFormat(
                    null, // An identifier for the track. May be null.
                    MimeTypes.APPLICATION_SUBRIP, // The mime type. Must be set correctly.
                    null,
                    Format.NO_VALUE,
                    Format.NO_VALUE,
                    null,
                    null); // The subtitle language. May be null.
            MediaSource subtitleSource = new SingleSampleMediaSource(subtitleUri, dataSourceFactory, subtitleFormat, C.TIME_UNSET);


            MergingMediaSource mergedSource = new MergingMediaSource(videoSource, subtitleSource);

            simpleExoPlayerView.setPlayer(player);

            if (!Link.equals("") && !Sub.equals("")) {
                player.prepare(mergedSource);
            } else if (!Link.equals("") && Sub.equals("")) {
                player.prepare(videoSource);
            }

            player.setPlayWhenReady(true);


            player.addListener(new ExoPlayer.EventListener() {
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
                    if(playbackState == player.STATE_BUFFERING){
                        progressBar.setVisibility(View.VISIBLE);
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity() {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }
            });

        } catch (Exception e) {
            Log.e("MainActivity", "exoplayer error" + e.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onBackPressed() {

        tapBack++;
        if (tapBack == 2) {
            tapBack = 0;
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(this, "Nhấn Back Một Lần Nữa Để Thoát", Toast.LENGTH_SHORT).show();
        }
    }


}
