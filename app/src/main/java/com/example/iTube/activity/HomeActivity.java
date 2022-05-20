package com.example.iTube.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.iTube.data.DatabaseHelper;
import static com.example.iTube.util.Util.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;

import com.example.iTube.R;
import com.example.iTube.model.PlayList;
import com.example.iTube.model.User;
import com.example.iTube.util.Util;

public class HomeActivity extends AppCompatActivity {

    int userId = 0;
    DatabaseHelper database;

    EditText editTextURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        userId = intent.getExtras().getInt(USER_ID);

        database = new DatabaseHelper(this);

        editTextURL = findViewById(R.id.editTextURL);
    }

    public void playClick(View view) {
        String urlString = editTextURL.getText().toString().trim();
        if (TextUtils.isEmpty(urlString)) {
            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
            alertDialog.setTitle("");
            alertDialog.setMessage("Please input an URL");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        } else {

            if (URLUtil.isValidUrl(urlString)) {
                Intent playerIntent = new Intent(HomeActivity.this, YoutubePlayerActivity.class);
                playerIntent.putExtra(URL_STRING_KEY, urlString);
                startActivity(playerIntent);
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("");
                alertDialog.setMessage("Please input a valid URL. The URL should be like: https://www.youtube.com/watch?v=LmkKFCfmnhQ&list=PLWz5rJ2EKKc9mxIBd0DRw9gwXuQshgmn2");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
        }
    }

    public void addToPlayListClick(View view) {
        String urlString = editTextURL.getText().toString().trim();
        if (TextUtils.isEmpty(urlString)) {
            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
            alertDialog.setTitle("");
            alertDialog.setMessage("Please input an URL");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        } else {

            if (URLUtil.isValidUrl(urlString)) {

                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();

                if (database.fetchPlayList(urlString, userId) >= 0) {
                    alertDialog.setTitle("");
                    alertDialog.setMessage("It's already added!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            (dialog, which) -> dialog.dismiss());
                    alertDialog.show();
                    return;
                }

                PlayList playList = new PlayList(userId, urlString);
                int insertedPlaylistID = (int)database.insertPlayList(playList);


                if (insertedPlaylistID >= 0) {
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Added successfully!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            (dialog, which) -> dialog.dismiss());
                } else {
                    alertDialog.setTitle("Oops!");
                    alertDialog.setMessage("Error happened");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            (dialog, which) -> dialog.dismiss());
                }
                alertDialog.show();

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("");
                alertDialog.setMessage("Please input a valid URL. The URL should be like: https://www.youtube.com/watch?v=W4hTJybfU7s");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
        }
    }

    public void myPlayListClick(View view) {
        Intent playListIntent = new Intent(HomeActivity.this, PlayListActivity.class);
        playListIntent.putExtra(Util.USER_ID, userId);
        startActivity(playListIntent);
    }
}