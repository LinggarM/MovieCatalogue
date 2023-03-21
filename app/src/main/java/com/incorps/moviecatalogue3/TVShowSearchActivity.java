package com.incorps.moviecatalogue3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TVShowSearchActivity extends AppCompatActivity {
    private RecyclerView rvMovies;
    private TVFavAdapter adapter;
    private ArrayList<TVShow> listTVItems = new ArrayList<>();
    private static final String URL_TVSHOWS_SEARCH = "https://api.themoviedb.org/3/search/tv?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US&query=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_search);

        try {
            String query = getIntent().getStringExtra("query");

            rvMovies = findViewById(R.id.recyclerView);
            rvMovies.setHasFixedSize(true);
            rvMovies.setLayoutManager(new LinearLayoutManager(this));

            AsyncHttpClient client = new AsyncHttpClient();
            String url = URL_TVSHOWS_SEARCH + query;
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray list = responseObject.getJSONArray("results");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject tvShow = list.getJSONObject(i);
                            TVShow tvItems = new TVShow();
                            tvItems.setId(tvShow.getString("id"));
                            tvItems.setUserScore(tvShow.getString("vote_average"));
                            tvItems.setPhoto("https://image.tmdb.org/t/p/w185" + tvShow.getString("poster_path"));
                            tvItems.setTitle(tvShow.getString("original_name"));
                            tvItems.setDescription(tvShow.getString("overview"));
                            tvItems.setPopularity(tvShow.getString("popularity"));
                            tvItems.setReleaseDate(tvShow.getString("first_air_date"));
                            listTVItems.add(tvItems);
                        }
                        Log.d("Contoh Output", listTVItems.get(1).getTitle());
                        adapter = new TVFavAdapter(listTVItems,TVShowSearchActivity.this);
                        rvMovies.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("onFailure", error.getMessage());
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            String empty = getString(R.string.empty);
            Toast.makeText(TVShowSearchActivity.this, empty , Toast.LENGTH_SHORT).show();
        }
    }
}
