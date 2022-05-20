package com.example.iTube.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iTube.adapter.PlayListRecyclerViewAdapter;
import com.example.iTube.data.DatabaseHelper;
import static com.example.iTube.util.Util.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.iTube.R;
import com.example.iTube.model.PlayList;

import java.util.ArrayList;
import java.util.List;

public class PlayListActivity extends AppCompatActivity {

    int userId = 0;
    DatabaseHelper database;

    RecyclerView recyclerView;
    PlayListRecyclerViewAdapter recyclerViewAdapter;

    List<PlayList> playLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        Intent intent = getIntent();
        userId = intent.getExtras().getInt(USER_ID);

        database = new DatabaseHelper(this);

        playLists = database.fetchAllPlayLists(userId);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new PlayListRecyclerViewAdapter(playLists, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        LinearLayoutManager topLayoutManager = new LinearLayoutManager(PlayListActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(topLayoutManager);

        if (playLists.size() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(PlayListActivity.this).create();
            alertDialog.setTitle("");
            alertDialog.setMessage("There's no saved playlists");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }
    }

    public void play(PlayList playList) {
        Intent playerIntent = new Intent(PlayListActivity.this, YoutubePlayerActivity.class);
        playerIntent.putExtra(URL_STRING_KEY, playList.getUrlString());
        startActivity(playerIntent);
    }
}