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
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.youtubeplaylist1.R;
import com.example.android.youtubeplaylist1.model.Playlist;
import com.example.android.youtubeplaylist1.model.VideoDbHelper;
import com.example.android.youtubeplaylist1.model.VideoItem;

public class StoreActivity extends ActionBarActivity {

    // Views to be bound

    private EditText titleEditText, authorEditText, yearEditText, videoIDEditText, rankEditText;
    private Button storeButton, clearButton, displayButton;

    public static final String TAG = StoreActivity.class.getSimpleName();

    // Data structures

    private VideoItem mVideoItem;
    private int mRowNumber;
    private Playlist mVideoList = new Playlist();

    Context context;
    VideoDbHelper videoDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    // Member variables

    private String mVideoID, mRank, mTitle, mAuthor, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        authorEditText = (EditText) findViewById(R.id.authorEditText);
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        videoIDEditText = (EditText) findViewById(R.id.videoIDEditText);
        rankEditText = (EditText) findViewById(R.id.rankEditText);
        storeButton = (Button) findViewById(R.id.storeButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        displayButton = (Button) findViewById(R.id.displayButton);

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addItem(v);
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

        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StoreActivity.this, DisplayActivity.class);

                // Next, I will pass in the array of video items, mVideoList, a Playlist object
                // to DisplayActivity.java


                intent.putExtra(getString(R.string.ROW_NUMBER), mRowNumber);

                intent.putExtra(getString(R.string.VIDEO_LIST), mVideoList.mVideoItem);

                startActivity(intent);

            }
        });

    }

    public void addItem(View view) {

        context = this;

        // Perform DB insertion...

        // Initialize videoDbhelper object and SQLiteDatabase object.

        videoDbHelper = new VideoDbHelper(context);
        sqLiteDatabase = videoDbHelper.getWritableDatabase();

        mVideoID = videoIDEditText.getText().toString();
        mRank = rankEditText.getText().toString();
        mTitle = titleEditText.getText().toString();
        mAuthor = authorEditText.getText().toString();
        mYear = yearEditText.getText().toString();

        // Insert the item details in the database
        videoDbHelper.addItem(mVideoID, mRank, mTitle, mAuthor, mYear, sqLiteDatabase);

        Toast.makeText(StoreActivity.this, "Video Item Saved", Toast.LENGTH_LONG).show();

        videoDbHelper.close();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store, menu);
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
