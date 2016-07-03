package com.example.android.youtubeplaylist1.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.youtubeplaylist1.R;
import com.example.android.youtubeplaylist1.model.Playlist;
import com.example.android.youtubeplaylist1.model.VideoDbHelper;
import com.example.android.youtubeplaylist1.model.VideoItem;

public class MainActivity extends ActionBarActivity {

    // Data structures

    private VideoItem mVideoItem;
    private int mRowNumber;
    private Playlist mVideoList = new Playlist();

    Context context;
    VideoDbHelper videoDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    private Button storeVideoButton, displayPlaylistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeVideoButton = (Button) findViewById(R.id.storeVideoButton);
        displayPlaylistButton = (Button) findViewById(R.id.displayPlaylistButton);

        // When the storeVideoButton is clicked, the StoreActivity is invoked.

        storeVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, StoreActivity.class);
                startActivity(intent);

            }
        });


        // Initialize video item

        mVideoItem = new VideoItem();

        //Initialize VideoDbHelper and SQLiteDB

        videoDbHelper = new VideoDbHelper(getApplicationContext());
        sqLiteDatabase = videoDbHelper.getReadableDatabase();

        cursor = videoDbHelper.getVideoItem(sqLiteDatabase);

        // Initialize the Row Number

        mRowNumber = 0;

        if(cursor.moveToFirst()){

            do{

                String video_ID, title, author;
                int rank, year;

                // These corresponds to the columns in the videoDbHelper: video_ID (column 0),
                // rank (col. 1), title (col. 2), author (col. 3), and year (col. 4)

                // See below:

                /*
                private static final String CREATE_QUERY = "CREATE TABLE " + VideoListDB.NewVideoItem.TABLE_NAME +
                "(" + VideoListDB.NewVideoItem.VIDEO_ID + " TEXT," +
                VideoListDB.NewVideoItem.RANK + " INTEGER," +
                VideoListDB.NewVideoItem.TITLE + " TEXT," +
                VideoListDB.NewVideoItem.AUTHOR + " TEXT," +
                VideoListDB.NewVideoItem.YEAR + " INTEGER);"; */

                video_ID = cursor.getString(0);
                rank = cursor.getInt(1);
                title = cursor.getString(2);
                author = cursor.getString(3);
                year = cursor.getInt(4);

                mVideoItem = new VideoItem(rank, title, author, year, video_ID);

                mVideoList.addVideoItem(mVideoItem, mRowNumber);

                mRowNumber++;

            }

            while(cursor.moveToNext());

        }

        displayPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);

                // Next, I will pass in the array of video items, mVideoList, a Playlist object
                // to DisplayActivity.java


                intent.putExtra(getString(R.string.ROW_NUMBER), mRowNumber);

                intent.putExtra(getString(R.string.VIDEO_LIST), mVideoList.mVideoItem);

                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
