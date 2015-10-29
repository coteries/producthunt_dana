package com.example.kianfar.producthunt_danakianfar.content;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kianfar.producthunt_danakianfar.DataPool;

import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private final String create_script = "CREATE TABLE USER (id int, name varchar(255), imageurl varchar(255), primary key (id)); " +
            "CREATE TABLE POST (id int, votes_count int, name varchar(255), tagline varchar(255), day_ varchar(255), created_at varchar(255), redirect_url varchar(255), user_id int, foreign key (user_id) references USER (id), primary key (id)); " +
            "CREATE TABLE MAKER (id int, post_id int, user_id int, foreign key (post_id) references POST (id), foreign key (user_id) references USER (id), primary key (id));" +
            "CREATE INDEX user_index ON USER (id);" +
            "CREATE INDEX post_index ON POST (id);" +
            "CREATE INDEX maker_index ON MAKER (user_id, post_id);";
    public static final String select_latest_products =    "SELECT p.id, p.votes_count, p.name, p.tagline, p.day_, p.created_at, p.redirect_url, p.user_id, u2.name as user_name, u.id as makerid, u.name as makername, u.imageurl as makerimage " +
            "FROM POST p JOIN MAKER m JOIN USER u JOIN USER u2 on p.id = m.post_id AND u.id = m.user_id AND p.user_id = u2.id";


    private final String drop_all_tables = "select 'drop table ' || name || ';' from sqlite_master where type = 'table';";


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "producthunt_kianfar.db";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_script);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_all_tables);
        db.execSQL(create_script);
        onCreate(db);
    }

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
        db.close();

        Log.d("Database Helper", "Done storing to database.");
    }


    // Inserts a Post object into the database
    private void insertPost(Post post, SQLiteDatabase db) {
        // put post
        ContentValues values = new ContentValues();
        values.put("id", post.getId());
        values.put("name", post.getName());
        values.put("votes_count", post.getVotes_count());
        values.put("tagline", post.getTagline());
        values.put("day_", post.getDay());
        values.put("created_at", post.getCreatedAt());
        values.put("redirect_url", post.getRedirectUrl());
        values.put("user_id", post.getUser().getId());
        values.put("tagline", post.getTagline());
        db.insert("POST", null, values);

        // put user
        values = new ContentValues();
        values.put("id", post.getUser().getId());
        values.put("name", post.getUser().getName());
        values.put("imageurl", post.getUser().getImageUrl().get100px());
        db.insert("USER", null, values);

        // put makers
        for (User user : post.getMakers()) {
            values = new ContentValues();
            values.put("id", user.getId());
            values.put("name", user.getName());
            values.put("imageurl", user.getImageUrl().get100px());
            db.insert("USER", null, values);
        }

        // put connections
        for (User user : post.getMakers()) {
            values = new ContentValues();
            values.put("post_id", post.getId());
            values.put("user_id", user.getId());
            db.insert("MAKER", null, values);
        }
    }

}