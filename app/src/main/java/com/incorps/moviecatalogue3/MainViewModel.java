package com.incorps.moviecatalogue3;

import android.preference.PreferenceActivity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = "63fea4c709da1f1496b7a1ca7a3c6083";
    private static final String LANGUAGE_EN = "en-US";
    private static final String LANGUAGE_ID = "id-ID";
    private static final String URL_MOVIES = "https://api.themoviedb.org/3/discover/movie?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US";
    private static final String URL_TVSHOWS = "https://api.themoviedb.org/3/discover/tv?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US";
    private static final String URL_MOVIES_ID = "https://api.themoviedb.org/3/discover/movie?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=id-ID";
    private static final String URL_TVSHOWS_ID = "https://api.themoviedb.org/3/discover/tv?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=id-ID";
    private static final String URL_MOVIES_FR = "https://api.themoviedb.org/3/discover/movie?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=fr-FR";
    private static final String URL_TVSHOWS_FR = "https://api.themoviedb.org/3/discover/tv?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=fr-FR";
    private static final String URL_MOVIES_SEARCH = "https://api.themoviedb.org/3/search/movie?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US&query=";
    private static final String URL_TVSHOWS_SEARCH = "https://api.themoviedb.org/3/search/tv?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US&query=";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShow>> listTVShows = new MutableLiveData<>();
    private ArrayList<Movie> listItems = new ArrayList<>();
    private ArrayList<TVShow> listTVItems = new ArrayList<>();
    private TVShow tvItems;
    private Movie movieItems;

    void setMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = URL_MOVIES;
        Log.d("Bahasa ",Locale.getDefault().getLanguage());
        if (Locale.getDefault().getLanguage() == "in") {
            url = URL_MOVIES_ID;
        } else if (Locale.getDefault().getLanguage() == "fr") {
            url = URL_MOVIES_FR;
        }
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
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }


    void setTVShows() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShow> listItems = new ArrayList<>();
        String url = URL_TVSHOWS;
        if (Locale.getDefault().getLanguage() == "id") {
            url = URL_TVSHOWS_ID;
        } else if (Locale.getDefault().getLanguage() == "fr") {
            url = URL_TVSHOWS_FR;
        }
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        TVShow tvItems = new TVShow();
                        tvItems.setId(movie.getString("id"));
                        tvItems.setUserScore(movie.getString("vote_average"));
                        tvItems.setPhoto("https://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
                        tvItems.setTitle(movie.getString("original_name"));
                        tvItems.setDescription(movie.getString("overview"));
                        tvItems.setPopularity(movie.getString("popularity"));
                        tvItems.setReleaseDate(movie.getString("first_air_date"));
                        listItems.add(tvItems);
                    }
                    listTVShows.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<TVShow>> getTVShows() {
        return listTVShows;
    }


    TVShow getTVShowsItem(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/tv/" + id + "?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US";
        if (Locale.getDefault().getLanguage() == "id") {
            url = "https://api.themoviedb.org/3/tv/" + id + "?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=id-ID";
        }
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    tvItems = new TVShow();
                    tvItems.setId(responseObject.getString("id"));
                    tvItems.setUserScore(responseObject.getString("vote_average"));
                    tvItems.setPhoto("https://image.tmdb.org/t/p/w185" + responseObject.getString("poster_path"));
                    tvItems.setTitle(responseObject.getString("original_name"));
                    tvItems.setDescription(responseObject.getString("overview"));
                    tvItems.setPopularity(responseObject.getString("popularity"));
                    tvItems.setReleaseDate(responseObject.getString("first_air_date"));
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        return tvItems;
    }


    Movie getMovieItem(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=en-US";
        if (Locale.getDefault().getLanguage() == "id") {
            url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=63fea4c709da1f1496b7a1ca7a3c6083&language=id-ID";
        }
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    movieItems = new Movie();
                    movieItems.setId(responseObject.getString("id"));
                    movieItems.setUserScore(responseObject.getString("vote_average"));
                    movieItems.setPhoto(responseObject.getString("poster_path"));
                    movieItems.setTitle(responseObject.getString("original_title"));
                    movieItems.setDescription(responseObject.getString("overview"));
                    movieItems.setPopularity(responseObject.getString("popularity"));
                    movieItems.setReleaseDate(responseObject.getString("release_date"));
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        return movieItems;
    }

    ArrayList<Movie> getMoviesSearch(String query) {
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
                    listMovies.postValue(listItems);
                    Log.d("Contoh Output", listItems.get(1).getTitle());
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        return listItems;
    }

    ArrayList<Movie> getMoviesBack() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = URL_MOVIES;
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
                    listMovies.postValue(listItems);
                    Log.d("Contoh Output", listItems.get(1).getTitle());
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        return listItems;
    }

    public ArrayList<TVShow> getTVShowSearch(String query) {
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
                        tvItems.setTitle(tvShow.getString("original_title"));
                        tvItems.setDescription(tvShow.getString("overview"));
                        tvItems.setPopularity(tvShow.getString("popularity"));
                        tvItems.setReleaseDate(tvShow.getString("release_date"));
                        listTVItems.add(tvItems);
                    }
                    listTVShows.postValue(listTVItems);
                    Log.d("Contoh Output", listTVItems.get(1).getTitle());
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        return listTVItems;
    }

    ArrayList<TVShow> getTVShowBack() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = URL_TVSHOWS;
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
                        tvItems.setTitle(tvShow.getString("original_title"));
                        tvItems.setDescription(tvShow.getString("overview"));
                        tvItems.setPopularity(tvShow.getString("popularity"));
                        tvItems.setReleaseDate(tvShow.getString("release_date"));
                        listTVItems.add(tvItems);
                    }
                    listTVShows.postValue(listTVItems);
                    Log.d("Contoh Output", listTVItems.get(1).getTitle());
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        return listTVItems;
    }
}