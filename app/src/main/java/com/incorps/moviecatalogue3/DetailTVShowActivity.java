package com.incorps.moviecatalogue3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class DetailTVShowActivity extends AppCompatActivity {
    private String EXTRA_MOVIE = "EXTRA_MOVIE";
    private TVShow tvShow = new TVShow();
    private ProgressBar progressBar;
    private ImageView imgPhoto;
    private TextView txtTitle, txtRating, txtDescription, txtPopularity, txtReleaseDate;
    private ArrayList<TVShow> tvShows;
    private TVDatabaseHelper tvDatabaseHelper;
    private Button btnAddFav, btnDelFav;
    private boolean exist;
    private boolean haveLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        tvDatabaseHelper = new TVDatabaseHelper(this);

        tvShows = tvDatabaseHelper.getAllTV();

        btnAddFav = findViewById(R.id.btn_addtofavorite);
        btnDelFav = findViewById(R.id.btn_deletefromfavorite);
        progressBar = findViewById(R.id.progressBar);
        imgPhoto = findViewById(R.id.img_photo);
        txtTitle = findViewById(R.id.txt_title);
        txtRating = findViewById(R.id.txt_rating);
        txtDescription = findViewById(R.id.txt_description);
        txtPopularity = findViewById(R.id.txt_popularity);
        txtReleaseDate = findViewById(R.id.txt_release_date);

        btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (haveLoaded) {
                    tvDatabaseHelper.addTVFav(tvShow);
                    String added = getString(R.string.added);
                    Toast.makeText(DetailTVShowActivity.this, added , Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailTVShowActivity.this, "Halaman belum selesai diload", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (haveLoaded) {
                    tvDatabaseHelper.deleteTVFav(tvShow);
                    String deleted = getString(R.string.deleted);
                    Toast.makeText(DetailTVShowActivity.this, deleted, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailTVShowActivity.this, "Halaman belum selesai diload", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String idFilm = getIntent().getStringExtra("id");

        if (savedInstanceState == null) {
            imgPhoto.setVisibility(View.INVISIBLE);
            txtTitle.setVisibility(View.INVISIBLE);
            txtRating.setVisibility(View.INVISIBLE);
            txtDescription.setVisibility(View.INVISIBLE);
            txtPopularity.setVisibility(View.INVISIBLE);
            txtReleaseDate.setVisibility(View.INVISIBLE);
            showLoading(true);

            AsyncHttpClient client = new AsyncHttpClient();
            String url = "https://api.themoviedb.org/3/tv/" + idFilm + "?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US";
            if (Locale.getDefault().getLanguage() == "id") {
                url = "https://api.themoviedb.org/3/tv/" + idFilm + "?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=id-ID";
            } else if (Locale.getDefault().getLanguage() == "fr") {
                url = "https://api.themoviedb.org/3/tv/" + idFilm + "?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=fr-FR";
            }
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        tvShow.setId(responseObject.getString("id"));
                        tvShow.setUserScore(responseObject.getString("vote_average"));
                        tvShow.setPhoto("https://image.tmdb.org/t/p/w185" + responseObject.getString("poster_path"));
                        tvShow.setTitle(responseObject.getString("original_name"));
                        tvShow.setDescription(responseObject.getString("overview"));
                        tvShow.setPopularity(responseObject.getString("popularity"));
                        tvShow.setReleaseDate(responseObject.getString("first_air_date"));
                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
                    }

                    showLoading(false);
                    imgPhoto.setVisibility(View.VISIBLE);
                    txtTitle.setVisibility(View.VISIBLE);
                    txtRating.setVisibility(View.VISIBLE);
                    txtDescription.setVisibility(View.VISIBLE);
                    txtPopularity.setVisibility(View.VISIBLE);
                    txtReleaseDate.setVisibility(View.VISIBLE);

                    haveLoaded = true;

                    Glide.with(DetailTVShowActivity.this).load(tvShow.getPhoto()).into(imgPhoto);
                    txtTitle.setText(tvShow.getTitle());
                    txtRating.setText(tvShow.getUserScore());
                    txtDescription.setText(tvShow.getDescription());
                    txtPopularity.setText(tvShow.getPopularity());
                    txtReleaseDate.setText(tvShow.getReleaseDate());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("onFailure", error.getMessage());
                }
            });
        } else {
            tvShow = savedInstanceState.getParcelable("TVShowSimpan");
            Glide.with(DetailTVShowActivity.this).load(tvShow.getPhoto()).into(imgPhoto);
            txtTitle.setText(tvShow.getTitle());
            txtRating.setText(tvShow.getUserScore());
            txtDescription.setText(tvShow.getDescription());
            txtPopularity.setText(tvShow.getPopularity());
            txtReleaseDate.setText(tvShow.getReleaseDate());
        }

        btnDelFav.setVisibility(View.GONE);
        exist = false;
        for (int a = 0; a < tvShows.size(); a++) {
            if (tvShows.get(a).getId().equals(idFilm)){
                exist = true;
            }
        }
        if (exist) {
            btnAddFav.setVisibility(View.GONE);
            btnDelFav.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("TVShowSimpan",tvShow);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
