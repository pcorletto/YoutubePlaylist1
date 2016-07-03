package com.example.android.youtubeplaylist1.model;

/**
 * Created by hernandez on 6/5/2016.
 */
public class Playlist {

    // The class Playlist is only going to hold a user's top ten favorite videos
    // taken from a pool of videos stored on the VideoList, which is an
    // SQLite database of videos you store. In VideoList, a user can store
    // as many videos as he or she likes. However, Playlist only gets the favorite 10.

    public VideoItem[] mVideoItem = new VideoItem[10];

    public void addVideoItem(VideoItem videoItem, int rowNumber){

        mVideoItem[rowNumber] = videoItem;

    }

    public String getVideoItem(int i){
        return mVideoItem[i].getTitle() +
                " " + mVideoItem[i].getAuthor()+ "\n\n";
    }
}
