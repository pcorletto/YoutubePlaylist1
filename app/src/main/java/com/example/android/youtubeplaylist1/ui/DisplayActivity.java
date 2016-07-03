package com.example.android.youtubeplaylist1.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.android.youtubeplaylist1.R;
import com.example.android.youtubeplaylist1.model.VideoDbHelper;
import com.example.android.youtubeplaylist1.model.VideoItem;
import com.example.android.youtubeplaylist1.model.VideoItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private ListView listview;
    private VideoItem[] mVideoItems;
    private List<VideoItem> list = new ArrayList<>();
    private VideoItemAdapter mAdapter;
    VideoDbHelper videoDbHelper;
    SQLiteDatabase sqLiteDatabase;
    private int mRowNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        listview = (ListView) findViewById(android.R.id.list);

        Intent intent = getIntent();

        mRowNumber = intent.getIntExtra(getString(R.string.ROW_NUMBER), 0);

        Parcelable[] parcelables = intent.getParcelableArrayExtra(getString(R.string.VIDEO_LIST));

        mVideoItems = Arrays.copyOf(parcelables, mRowNumber, VideoItem[].class);

        for(int i=0; i<mRowNumber; i++){

            list.add(mVideoItems[i]);

        }

        mAdapter = new VideoItemAdapter(DisplayActivity.this, list);

        mAdapter.notifyDataSetChanged();

        listview.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
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
