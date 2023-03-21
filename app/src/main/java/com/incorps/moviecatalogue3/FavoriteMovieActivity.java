package com.incorps.moviecatalogue3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incorps.moviecatalogue3.R;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.incorps.moviecatalogue3.MovieDatabaseHelper.mapCursorToArrayList;

public class FavoriteMovieActivity extends AppCompatActivity {
    private RecyclerView rvMovies;
    private MovieFavAdapter adapter;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        try {
            Cursor dataCursor = getContentResolver().query(MovieDatabaseEntry.MovieEntry.CONTENT_URI, null, null, null, null);
            movies = mapCursorToArrayList(dataCursor);

            Log.d("Yang diambil",movies.get(0).getTitle());

            rvMovies = findViewById(R.id.recyclerView);
            rvMovies.setHasFixedSize(true);
            rvMovies.setLayoutManager(new LinearLayoutManager(this));

            adapter = new MovieFavAdapter(movies,this);
            rvMovies.setAdapter(adapter);
        } catch (Exception e){
            e.printStackTrace();
            String empty = getString(R.string.empty);
            Toast.makeText(FavoriteMovieActivity.this, empty , Toast.LENGTH_SHORT).show();
        }
    }
}
