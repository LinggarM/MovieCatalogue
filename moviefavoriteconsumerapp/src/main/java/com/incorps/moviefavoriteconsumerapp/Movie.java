package com.incorps.moviefavoriteconsumerapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String id;
    private String userScore;
    private String photo;
    private String title;
    private String description;
    private String popularity;
    private String releaseDate;

    public Movie() {
    }

    public Movie(String id, String userScore, String photo, String title, String description, String popularity, String releaseDate) {
        this.id = id;
        this.userScore = userScore;
        this.photo = photo;
        this.title = title;
        this.description = description;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        userScore = in.readString();
        photo = in.readString();
        title = in.readString();
        description = in.readString();
        popularity = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userScore);
        dest.writeString(photo);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(popularity);
        dest.writeString(releaseDate);
    }
}
