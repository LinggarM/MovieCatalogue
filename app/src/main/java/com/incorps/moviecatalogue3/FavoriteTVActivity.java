package com.incorps.moviecatalogue3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteTVActivity extends AppCompatActivity {
    private RecyclerView rvTVShow;
    private TVFavAdapter adapter;
    private ArrayList<TVShow> tvShows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_tv);

        try {
            TVDatabaseHelper tvDatabaseHelper = new TVDatabaseHelper(this);
            tvShows = tvDatabaseHelper.getAllTV();

            rvTVShow = findViewById(R.id.recyclerView);
            rvTVShow.setHasFixedSize(true);
            rvTVShow.setLayoutManager(new LinearLayoutManager(this));

            adapter = new TVFavAdapter(tvShows, this);
            rvTVShow.setAdapter(adapter);
        } catch (Exception e){
            e.printStackTrace();
            String empty = getString(R.string.empty);
            Toast.makeText(FavoriteTVActivity.this, empty , Toast.LENGTH_SHORT).show();
        }
    }
}
