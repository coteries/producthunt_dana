package com.example.kianfar.producthunt_danakianfar.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private final String[] create_script = {"" +
            "CREATE TABLE Post (_id INTEGER PRIMARY KEY, votes_count INTEGER, name TEXT, tagline TEXT, day_ TEXT, created_at TEXT, redirect_url TEXT, user_id INTEGER, foreign key (user_id) references User (_id)) ;",
            "CREATE TABLE User (_id INTEGER PRIMARY KEY, name TEXT, imageurl TEXT) ; ",
            "CREATE TABLE Maker(_id INTEGER PRIMARY KEY AUTOINCREMENT, post_id INTEGER, user_id INTEGER, foreign key (post_id) references Post (_id), foreign key (user_id) references User (_id)) ;",
            "CREATE UNIQUE INDEX user_index ON User (_id) ; " ,
            "CREATE UNIQUE INDEX post_index ON Post (_id) ; " ,
            "CREATE UNIQUE INDEX maker_index ON Maker (user_id, post_id);"};
    public static final String select_latest_products =    "SELECT p._id, p.votes_count, p.name, p.tagline, p.day_, p.created_at, p.redirect_url, p.user_id, u2.name as user_name, u._id as makerid, u.name as makername, u.imageurl as makerimage " +
            "FROM Post p JOIN Maker m JOIN User u JOIN User u2 on p._id = m.post_id AND u._id = m.user_id AND p.user_id = u2._id";


    private final String drop_all_tables = "DROP TABLE IF EXISTS User; " +
            "DROP TABLE IF EXISTS Post; " +
            "DROP TABLE IF EXISTS Maker";


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "producthunt.db";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
//        getReadableDatabase().close(); // force creates the db
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SQLite","Creating tables...");
        db.execSQL(create_script[0]);
        db.execSQL(create_script[1]);
        db.execSQL(create_script[2]);

        Cursor cursor = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';", null);
//        int count = cursor.getInt(0);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_all_tables);
//        db.delete("USER", null, null);
//        db.delete("POST", null, null);
//        db.delete("MAKER", null, null);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void storePostsInDB(List<Post> posts) {
        Log.d("Database Helper", "Storing Post objects in sqlite database...");

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        for (Post post : posts) {
            insertPost(post, db);
        }
        databaseHelper.close();

        Log.d("Database Helper", "Done storing to database.");
    }


    // Inserts a Post object into the database
    private void insertPost(Post post, SQLiteDatabase db) {
        // put post
        ContentValues values = new ContentValues();
        values.put("_id", post.getId());
        values.put("name", post.getName());
        values.put("votes_count", post.getVotes_count());
        values.put("tagline", post.getTagline());
        values.put("day_", post.getDay());
        values.put("created_at", post.getCreatedAt());
        values.put("redirect_url", post.getRedirectUrl());
        values.put("user_id", post.getUser().getId());
        values.put("tagline", post.getTagline());
        db.insertWithOnConflict("Post", null, values, SQLiteDatabase.CONFLICT_IGNORE);

        // put user
        values = new ContentValues();
        values.put("_id", post.getUser().getId());
        values.put("name", post.getUser().getName());
        values.put("imageurl", post.getUser().getImageUrl().getImageUrl());
        db.insertWithOnConflict("User", null, values, SQLiteDatabase.CONFLICT_IGNORE);

        // put makers
        for (User user : post.getMakers()) {
            values = new ContentValues();
            values.put("_id", user.getId());
            values.put("name", user.getName());
            values.put("imageurl", user.getImageUrl().getImageUrl());
            db.insertWithOnConflict("User", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }

        // put connections
        for (User user : post.getMakers()) {
            values = new ContentValues();
            values.put("post_id", post.getId());
            values.put("user_id", user.getId());
            db.insertWithOnConflict("Maker", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }
    }

}