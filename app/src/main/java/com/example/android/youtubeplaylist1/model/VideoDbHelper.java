package com.example.android.youtubeplaylist1.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hernandez on 6/19/2016.
 */
public class VideoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "VIDEOLIST.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY = "CREATE TABLE " + VideoListDB.NewVideoItem.TABLE_NAME +
            "(" + VideoListDB.NewVideoItem.VIDEO_ID + " TEXT," +
            VideoListDB.NewVideoItem.RANK + " INTEGER," +
            VideoListDB.NewVideoItem.TITLE + " TEXT," +
            VideoListDB.NewVideoItem.AUTHOR + " TEXT," +
            VideoListDB.NewVideoItem.YEAR + " INTEGER);";

    // Default Constructor:

    public VideoDbHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "Database created / opened ...");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created ...");

    }

    // Insert the item  next. Method for inserting the video item.

    public void addItem(String id, String rank, String title,
                        String author, String year, SQLiteDatabase db){

        // Map key-values

        ContentValues contentValues = new ContentValues();
        contentValues.put(VideoListDB.NewVideoItem.VIDEO_ID, id);
        contentValues.put(VideoListDB.NewVideoItem.RANK, rank);
        contentValues.put(VideoListDB.NewVideoItem.TITLE, title);
        contentValues.put(VideoListDB.NewVideoItem.AUTHOR, author);
        contentValues.put(VideoListDB.NewVideoItem.YEAR, year);

        // Save all these into the database

        db.insert(VideoListDB.NewVideoItem.TABLE_NAME, null, contentValues);

        Log.e("DATABASE OPERATIONS", "One row is inserted ...");

    }

    public Cursor getVideoItem(SQLiteDatabase db){

        // The return type of Object is "Cursor"
        Cursor cursor;

        // Create projections, or the needed column names
        String[] projections = {VideoListDB.NewVideoItem.VIDEO_ID,
                VideoListDB.NewVideoItem.RANK,
                VideoListDB.NewVideoItem.TITLE,
                VideoListDB.NewVideoItem.AUTHOR,
                VideoListDB.NewVideoItem.YEAR};

        // We only need the table name and projection parameters. No conditions will be specified,
        // so, we will pass in null for the last five parameters.

        cursor = db.query(VideoListDB.NewVideoItem.TABLE_NAME, projections, null, null, null, null, null);

        return cursor;

    }


    public void deleteVideoItem(String video_ID, SQLiteDatabase sqLiteDatabase){

        String selection = VideoListDB.NewVideoItem.VIDEO_ID + " LIKE ?";

        String[] selection_args = {video_ID};

        sqLiteDatabase.delete(VideoListDB.NewVideoItem.TABLE_NAME, selection, selection_args);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
