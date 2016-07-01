package com.example.android.youtubeplaylist1.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hernandez on 6/5/2016.
 */
public class VideoItem implements Parcelable {

    // Private member variables

    private int mRank;
    private String mTitle;
    private String mAuthor;
    private int mYear;
    private String mVideoID;
    private boolean isSelected;

    // Constructors

    public VideoItem(){

    }

    public VideoItem(int rank, String title, String author, int year, String videoID){

        this.mRank = rank;
        this.mTitle = title;
        this.mAuthor = author;
        this.mYear = year;
        this.mVideoID = videoID;
        this.isSelected = false;

    }

    // Accessor and mutator methods


    public int getRank() {
        return mRank;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public String getVideoID() {
        return mVideoID;
    }

    public void setVideoID(String videoID) {
        mVideoID = videoID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(mRank);
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
        dest.writeInt(mYear);
        dest.writeString(mVideoID);

    }

    private VideoItem(Parcel in){

        mRank = in.readInt();
        mTitle = in.readString();
        mAuthor = in.readString();
        mYear = in.readInt();
        mVideoID = in.readString();

    }

    public static final Creator<VideoItem>CREATOR = new Creator<VideoItem>() {

        @Override
        public VideoItem createFromParcel(Parcel source) {
            return new VideoItem(source);
        }

        @Override
        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };

}
