package com.example.iTube.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.iTube.model.PlayList;
import com.example.iTube.model.User;
import com.example.iTube.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Util.DATABASE_NAME, factory, Util.DATABASE_VERSION);
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME_USER +
                "("
                + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.USER_USERNAME + " TEXT, "
                + Util.USER_PASSWORD + " TEXT, "
                + Util.USER_FULLNAME + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + Util.TABLE_NAME_PLAYLIST +
                "("
                + Util.PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.USER_ID + " INTEGER, "
                + Util.PLAYLIST_URL + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER_USERNAME, user.getUsername());
        contentValues.put(Util.USER_PASSWORD, user.getPassword());
        contentValues.put(Util.USER_FULLNAME, user.getFullName());

        long newRowId = db.insert(Util.TABLE_NAME_USER, null, contentValues);
        db.close();

        return newRowId;
    }

    public int fetchUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME_USER, new String[]{Util.USER_ID}, Util.USER_USERNAME + "=? and " + Util.USER_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        int numberOfRows = cursor.getCount();

        if (numberOfRows > 0 && cursor.moveToFirst()) {
            int userId = Integer.valueOf(cursor.getString(0));
            db.close();
            return userId;
        } else {
            db.close();
            return -1;
        }
    }

    public List<User> fetchAllUsers() {
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME_USER;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setFullName(cursor.getString(3));
                userList.add(user);

            } while (cursor.moveToNext());
        }

        return userList;
    }

    public long insertPlayList(PlayList playList) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER_ID, playList.getUserId());
        contentValues.put(Util.PLAYLIST_URL, playList.getUrlString());

        long newRowId = db.insert(Util.TABLE_NAME_PLAYLIST, null, contentValues);
        db.close();

        return newRowId;
    }

    public int fetchPlayList(String urlString, Integer userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME_PLAYLIST, new String[]{Util.PLAYLIST_ID}, Util.PLAYLIST_URL + "=? and " + Util.USER_ID + "=?",
                new String[]{urlString, String.valueOf(userID)}, null, null, null);
        int numberOfRows = cursor.getCount();

        if (numberOfRows > 0 && cursor.moveToFirst()) {
            int userId = Integer.valueOf(cursor.getString(0));
            db.close();
            return userId;
        } else {
            db.close();
            return -1;
        }
    }

    public List<PlayList> fetchAllPlayLists(int userId) {
        List<PlayList> playLists = new ArrayList<PlayList>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + Util.TABLE_NAME_PLAYLIST + " WHERE " + Util.USER_ID + " = " + userId;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                PlayList playList = new PlayList();
                playList.setId(cursor.getInt(0));
                playList.setUserId(cursor.getInt(1));
                playList.setUrlString(cursor.getString(2));
                playLists.add(playList);

            } while (cursor.moveToNext());
        }

        return playLists;
    }
}
