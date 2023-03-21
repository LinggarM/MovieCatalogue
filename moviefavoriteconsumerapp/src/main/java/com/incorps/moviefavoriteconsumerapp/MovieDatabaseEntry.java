package com.incorps.moviefavoriteconsumerapp;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieDatabaseEntry {
    public static final String AUTHORITY = "com.incorps.moviecatalogue3";
    private static final String SCHEME = "content";

    private MovieDatabaseEntry() {}

    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "fav_movie_1";
        public static final String TABLE_NAME_TV = "fav_tv_1";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_PHOTO = "photo";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_POPULARITY = "popularity";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";

        // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
