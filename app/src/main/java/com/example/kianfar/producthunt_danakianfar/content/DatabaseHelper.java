package com.example.kianfar.producthunt_danakianfar.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
}