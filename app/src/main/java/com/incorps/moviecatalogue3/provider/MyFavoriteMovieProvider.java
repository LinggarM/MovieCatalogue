package com.incorps.moviecatalogue3.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.UriMatcher;
import androidx.annotation.NonNull;

import com.incorps.moviecatalogue3.MovieDatabaseEntry;
import com.incorps.moviecatalogue3.MovieDatabaseHelper;


public class MyFavoriteMovieProvider extends ContentProvider {
    public static final String AUTHORITY = "com.incorps.moviecatalogue3";
    public static final String TABLE_NAME = "fav_movie_1";

    /*
    Integer digunakan sebagai identifier antara select all sama select by id
     */
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private MovieDatabaseHelper movieDatabaseHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*
    Uri matcher untuk mempermudah identifier dengan menggunakan integer
     */
    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);

        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        movieDatabaseHelper = MovieDatabaseHelper.getInstance(getContext());
        movieDatabaseHelper.open();
        return true;
    }

    /*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieDatabaseHelper.queryAll();
                break;
            case MOVIE_ID:
                cursor = movieDatabaseHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieDatabaseHelper.insert(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(MovieDatabaseEntry.MovieEntry.CONTENT_URI, null);

        return Uri.parse(MovieDatabaseEntry.MovieEntry.CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = movieDatabaseHelper.update(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(MovieDatabaseEntry.MovieEntry.CONTENT_URI, null);

        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = movieDatabaseHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(MovieDatabaseEntry.MovieEntry.CONTENT_URI, null);

        return deleted;
    }
}
