package com.incorps.moviefavoriteconsumerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.HandlerThread;
import android.database.ContentObserver;
import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvMovies;
    private MovieFavAdapter adapter;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor dataCursor = getContentResolver().query(MovieDatabaseEntry.MovieEntry.CONTENT_URI, null, null, null, null);
        movies = MovieDatabaseHelper.mapCursorToArrayList(dataCursor);

        Log.d("Contohnya",movies.get(0).getTitle());

        rvMovies = findViewById(R.id.recyclerView);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MovieFavAdapter(movies,this);
        rvMovies.setAdapter(adapter);
    }
}
