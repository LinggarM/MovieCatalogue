package com.incorps.moviefavoriteconsumerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_TABLE = MovieDatabaseEntry.MovieEntry.TABLE_NAME;
    private static SQLiteDatabase database;
    private static MovieDatabaseHelper INSTANCE;

    //Jika kamu mengubah skema database maka harus dinaikkan versi databasenya.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dbfavorite.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieDatabaseEntry.MovieEntry.TABLE_NAME + " (" +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_SCORE + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_PHOTO + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_DESC + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_POPULARITY + " TEXT, " +
                    MovieDatabaseEntry.MovieEntry.COLUMN_NAME_RELEASE_DATE + " TEXT )";
    private final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieDatabaseEntry.MovieEntry.TABLE_NAME;

    public MovieDatabaseHelper(Context context) {
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

    public void addMovieFav(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID, movie.getId());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_SCORE, movie.getUserScore());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_PHOTO, movie.getPhoto());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_DESC, movie.getDescription());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_POPULARITY, movie.getPopularity());
        values.put(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());

        db.insert(MovieDatabaseEntry.MovieEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteMovieFav(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MovieDatabaseEntry.MovieEntry.TABLE_NAME, "id = ?", new String[] {movie.getId()});
        db.close();
    }

    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> movieList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + MovieDatabaseEntry.MovieEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getString(0));
                movie.setUserScore(cursor.getString(1));
                movie.setPhoto(cursor.getString(2));
                movie.setTitle(cursor.getString(3));
                movie.setDescription(cursor.getString(4));
                movie.setPopularity(cursor.getString(5));
                movie.setReleaseDate(cursor.getString(6));

                movieList.add(movie);
            } while(cursor.moveToNext());
        }

        return movieList;
    }

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }
    public void close() {
        this.close();
        if (database.isOpen())
            database.close();
    }
    public static MovieDatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieDatabaseHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public Cursor queryAll() {
        return database.query(MovieDatabaseEntry.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "id");
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null
                , MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID + " = ?", new String[]{id});
    }

    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            String id = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID));
            String userScore = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_SCORE));
            String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_PHOTO));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_DESC));
            String popularity = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_POPULARITY));
            String releaseDate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_RELEASE_DATE));
            notesList.add(new Movie(id, userScore, photo, title, description, popularity, releaseDate));
        }

        return notesList;
    }

    public static Movie mapCursorToObject(Cursor notesCursor) {
        notesCursor.moveToFirst();
        String id = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_ID));
        String userScore = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_SCORE));
        String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_PHOTO));
        String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_TITLE));
        String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_DESC));
        String popularity = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_POPULARITY));
        String releaseDate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(MovieDatabaseEntry.MovieEntry.COLUMN_NAME_RELEASE_DATE));
        return new  Movie(id, userScore, photo, title, description, popularity, releaseDate);
    }
}
