package com.example.android.youtubeplaylist1.model;

/**
 * Created by hernandez on 6/19/2016.
 */
public class VideoListDB {

    // In the class VideoListDB a user can store as many videos as
    // he or she would like. However, Playlist only holds the favorite 10.

    public static abstract class NewVideoItem{


        // VIDEO_ID is the primary key
        public static final String VIDEO_ID = "video_id";
        public static final String RANK = "rank";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String YEAR = "year";

        // Table name

        public static final String TABLE_NAME = "video_list";


    }

}
