package com.example.android.youtubeplaylist1.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    // Need a newList, for when videos are deleted, a new list is created
    private List<VideoItem> newList = new ArrayList<>();

    View footerView;

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

        footerView = View.inflate(getBaseContext(), R.layout.footer_layout, null);

        listview.addFooterView(footerView);

        ViewHolder holder = new ViewHolder();

        holder.deleteSelectedItemsBtn = (Button) footerView.findViewById(R.id.deleteSelectedItemsBtn);
        holder.returnToMainBtn = (Button) footerView.findViewById(R.id.returnToMainBtn);

        holder.deleteSelectedItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).isSelected()) {

                        // For ListView: Skip checked or selected items. These will be deleted and will not
                        // be added to the new listview.

                        // For SQLiteDatabase: Delete this item here, if checked.

                        String item_for_DB_deletion = list.get(i).getVideoID();

                        // Initialize the videoDbHelper object

                        videoDbHelper = new VideoDbHelper(getApplicationContext());

                        // Initialize the SQLiteDatabase object

                        sqLiteDatabase = videoDbHelper.getReadableDatabase();

                        videoDbHelper.deleteVideoItem(item_for_DB_deletion, sqLiteDatabase);

                        Toast.makeText(getApplicationContext(), "Video item deleted", Toast.LENGTH_LONG).show();


                    } else {

                        // Add the item to the listview, because it won't be deleted.

                        newList.add(list.get(i));

                    }

                }

                mAdapter.notifyDataSetChanged();

                mAdapter = new VideoItemAdapter(DisplayActivity.this, newList);

                listview.setAdapter(mAdapter);

                // Add Footer

                listview.addFooterView(footerView);

            }

        });


        holder.returnToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);

                startActivity(intent);

            }

        });


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

    public final class ViewHolder{

        // This ViewHolder is for the footer, to hold the Delete selected videos button and
        // return to main button

        public Button deleteSelectedItemsBtn, returnToMainBtn;

    }
}
