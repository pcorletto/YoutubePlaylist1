package com.example.android.youtubeplaylist1.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.youtubeplaylist1.R;
import com.example.android.youtubeplaylist1.ui.PlayVideoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hernandez on 7/2/2016.
 */
public class VideoItemAdapter extends ArrayAdapter<VideoItem> {

    private final List<VideoItem> list;

    ArrayList<Boolean> positionArray;

    private Context mContext;

    public VideoItemAdapter(Context context, List<VideoItem> list) {
        super(context, R.layout.video_list_item, list);
        this.mContext = context;
        this.list = list;

        positionArray = new ArrayList<Boolean>(list.size());
        for(int i = 0; i < list.size();i++){
            positionArray.add(false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public VideoItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        VideoItem videoItem = list.get(position);

        if(convertView == null){
            //brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.video_list_item, null);

            holder = new ViewHolder();
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.rankEditText = (EditText) convertView.findViewById(R.id.rankEditText);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
            holder.authorTextView = (TextView) convertView.findViewById(R.id.authorTextView);
            holder.yearTextView = (TextView) convertView.findViewById(R.id.yearTextView);
            holder.videoIDTextView = (TextView) convertView.findViewById(R.id.videoIDTextView);
            holder.playButton = (Button) convertView.findViewById(R.id.playButton);

            convertView.setTag(holder);

        }

        else{
            // We have these views set up.
            holder = (ViewHolder) convertView.getTag();
            holder.checkBox.setOnCheckedChangeListener(null);

        }

        // Now, set the data:

        holder.rank = this.getItem(position).getRank();
        holder.title = this.getItem(position).getTitle();
        holder.author = this.getItem(position).getAuthor();
        holder.year = this.getItem(position).getYear();
        holder.videoID = this.getItem(position).getVideoID();

        holder.rankEditText.setText(holder.rank + "");
        holder.titleTextView.setText(holder.title);
        holder.authorTextView.setText(holder.author);
        holder.yearTextView.setText(holder.year + "");
        holder.videoIDTextView.setText(holder.videoID);

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // When the user clicks on the button for an item, play the youtube
                // video with the associated video ID

                // Send the videoID for this video item via Intent to another
                // activity called PlayVideoActivity, which contains
                // a YouTube player

                Intent intent = new Intent(mContext, PlayVideoActivity.class);

                intent.putExtra("YOUTUBE_VIDEO_ID", holder.videoID);

                mContext.startActivity(intent);

            }


        });

        holder.checkBox.setFocusable(false);
        holder.checkBox.setChecked(positionArray.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked ){

                    positionArray.add(position, true);
                    list.get(position).setSelected(true);

                }else {

                    positionArray.add(position, false);
                    list.get(position).setSelected(false);

                }

            }
        });

        return convertView;
    }

    private static class ViewHolder{

        int rank;
        String title;
        String author;
        int year;
        String videoID;

        CheckBox checkBox;
        EditText rankEditText;
        TextView titleTextView;
        TextView authorTextView;
        TextView yearTextView;
        TextView videoIDTextView;
        Button playButton;

    }

}
