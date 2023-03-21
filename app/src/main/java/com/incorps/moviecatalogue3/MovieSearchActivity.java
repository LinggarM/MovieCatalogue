package com.incorps.moviecatalogue3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.incorps.moviecatalogue3.MovieDatabaseHelper.mapCursorToArrayList;

public class MovieSearchActivity extends AppCompatActivity {
    private RecyclerView rvMovies;
    private MovieFavAdapter adapter;
    private ArrayList<Movie> listItems = new ArrayList<>();
    private static final String URL_MOVIES_SEARCH = "https://api.themoviedb.org/3/search/movie?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US&query=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        try {
            String query = getIntent().getStringExtra("query");

            rvMovies = findViewById(R.id.recyclerView);
            rvMovies.setHasFixedSize(true);
            rvMovies.setLayoutManager(new LinearLayoutManager(this));

            AsyncHttpClient client = new AsyncHttpClient();
            String url = URL_MOVIES_SEARCH + query;
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray list = responseObject.getJSONArray("results");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject movie = list.getJSONObject(i);
                            Movie movieItems = new Movie();
                            movieItems.setId(movie.getString("id"));
                            movieItems.setUserScore(movie.getString("vote_average"));
                            movieItems.setPhoto("https://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
                            movieItems.setTitle(movie.getString("original_title"));
                            movieItems.setDescription(movie.getString("overview"));
                            movieItems.setPopularity(movie.getString("popularity"));
                            movieItems.setReleaseDate(movie.getString("release_date"));
                            listItems.add(movieItems);
                        }
                        Log.d("Contoh Output", listItems.get(1).getTitle());
                        adapter = new MovieFavAdapter(listItems,MovieSearchActivity.this);
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
            Toast.makeText(MovieSearchActivity.this, empty , Toast.LENGTH_SHORT).show();
        }
    }
}
