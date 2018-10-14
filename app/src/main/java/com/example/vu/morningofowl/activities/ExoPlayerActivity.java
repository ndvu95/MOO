package com.example.vu.morningofowl.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerActivity extends Activity {
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

        //Toast.makeText(this, Link, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initPlayer();
    }

    private void initPlayer() {
        try {
            Intent intent = getIntent();
            String Link = intent.getStringExtra("Link");

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory trackSelectorfactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(trackSelectorfactory);
            //khởi tạo player
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            //khởi tạo exoplayerview
            SimpleExoPlayerView simpleExoPlayerView = findViewById(R.id.exoplayer);
            simpleExoPlayerView.setPlayer(player);

            //chuẩn bị datasource để player có thể load
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Test ExoPlayer"));
            //giải mã data
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            //Đường dẫn để player load video
            Uri videoUri = Uri.parse(Link);
            MediaSource videoSource = new ExtractorMediaSource(videoUri,dataSourceFactory,extractorsFactory,null,null);
            player.prepare(videoSource);
            player.setPlayWhenReady(true);

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
        //super.onBackPressed();

        tapBack++;
        if (tapBack == 2) {
            tapBack = 0;
            finish();
        } else {
            Toast.makeText(this, "Nhấn Back Một Lần Nữa Để Thoát", Toast.LENGTH_SHORT).show();
        }
    }
}
