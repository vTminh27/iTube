package com.example.iTube.activity;

import static com.example.iTube.util.Util.URL_STRING_KEY;
import static com.example.iTube.util.Util.USER_ID;

import androidx.appcompat.app.AppCompatActivity;
import com.example.iTube.R;
import com.example.iTube.YouTubeConfig;
import com.example.iTube.util.Util;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class YoutubePlayerActivity extends YouTubeBaseActivity {
    String urlString = "";
    String playlist_id = "";
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        Intent intent = getIntent();
        urlString = intent.getExtras().getString(URL_STRING_KEY);

        // Parse url to get video id
        playlist_id = urlString.split("list=")[1];
        Log.d(this.toString(), "Video URL = " + urlString);
        Log.d(this.toString(), "Playlist ID = " + playlist_id);
        youTubePlayerView = findViewById(R.id.videoPlayer);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                Log.d(this.toString(), "Finish initialization");
                if (youTubePlayer == null) {
                    return;
                }

                if (!wasRestored) {
                    youTubePlayer.loadPlaylist(playlist_id);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(this.toString(), "Fail to initialize: " + youTubeInitializationResult.toString());
            }
        };

        youTubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
    }
}
