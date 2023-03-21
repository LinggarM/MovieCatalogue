package com.incorps.moviecatalogue3;

import android.os.Parcel;
import android.os.Parcelable;

public class TVShow implements Parcelable {
    private String id;
    private String userScore;
    private String photo;
    private String title;
    private String description;
    private String releaseDate;
    private String popularity;

    public TVShow() {
    }

    public TVShow(String id, String userScore, String photo, String title, String description, String numEpisodes, String releaseDate, String popularity) {
        this.id = id;
        this.userScore = userScore;
        this.photo = photo;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    protected TVShow(Parcel in) {
        id = in.readString();
        userScore = in.readString();
        photo = in.readString();
        title = in.readString();
        description = in.readString();
        releaseDate = in.readString();
        popularity = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };


    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userScore);
        dest.writeString(photo);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(id);
        dest.writeString(releaseDate);
        dest.writeString(popularity);
    }
}