package com.incorps.moviecatalogue3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TVDatabaseHelper extends SQLiteOpenHelper {
    //Jika kamu mengubah skema database maka harus dinaikkan versi databasenya.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dbfavoritetv";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieDatabaseEntry.MovieEntry.TABLE_NAME_TV + " (" +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_SCORE + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_PHOTO + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_DESC + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_POPULARITY + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_RELEASE_DATE + " TEXT )";
    private final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieDatabaseEntry.MovieEntry.TABLE_NAME;

    public TVDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Method ini untuk melakukan proses upgrade pada perubahan tabel dan skema database. Fokus migrasi data akan dilakukan disini
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addTVFav(TVShow tvShow){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID, tvShow.getId());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_SCORE, tvShow.getUserScore());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_PHOTO, tvShow.getPhoto());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_TITLE, tvShow.getTitle());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_DESC, tvShow.getDescription());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_POPULARITY, tvShow.getPopularity());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_RELEASE_DATE, tvShow.getReleaseDate());

        db.insert(MovieDatabaseEntry.MovieEntry.TABLE_NAME_TV, null, values);
        db.close();
    }

    public void deleteTVFav(TVShow tvShow){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MovieDatabaseEntry.MovieEntry.TABLE_NAME_TV, "id = ?", new String[] {tvShow.getId()});
        db.close();
    }

    public ArrayList<TVShow> getAllTV() {
        ArrayList<TVShow> tvShowList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + MovieDatabaseEntry.MovieEntry.TABLE_NAME_TV;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                TVShow tvShow = new TVShow();
                tvShow.setId(cursor.getString(0));
                tvShow.setUserScore(cursor.getString(1));
                tvShow.setPhoto(cursor.getString(2));
                tvShow.setTitle(cursor.getString(3));
                tvShow.setDescription(cursor.getString(4));
                tvShow.setPopularity(cursor.getString(5));
                tvShow.setReleaseDate(cursor.getString(6));

                tvShowList.add(tvShow);
            } while(cursor.moveToNext());
        }

        return tvShowList;
    }
}
