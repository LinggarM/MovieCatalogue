package com.incorps.moviecatalogue3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.SearchManager;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainViewModel;
    AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainViewModel = new MainViewModel();

        alarmReceiver = new AlarmReceiver();
        if (!alarmReceiver.isStatus()) {
            alarmReceiver.setRepeatingAlarm(this);
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_bar, menu);
//
////        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
////
////        if (searchManager != null) {
////            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
////            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
////            searchView.setQueryHint(getResources().getString(R.string.search_hint));
////            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////                @Override
////                public boolean onQueryTextSubmit(String query) {
////                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
////                    if (query.equals("")){
////                        mainViewModel.setMovies();
////                        mainViewModel.setTVShows();
////                    } else {
////                        mainViewModel.setMoviesSearch(query);
////                        mainViewModel.setTVShowSearch(query);
////                    }
////                    return true;
////                }
////                @Override
////                public boolean onQueryTextChange(String newText) {
////                    return false;
////                }
////            });
////        }
//
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case (R.id.menu_setting) :
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            case (R.id.menu_fav_movie) :
                Intent mIntentFavMovie = new Intent(this, FavoriteMovieActivity.class);
                startActivity(mIntentFavMovie);
                break;
            case (R.id.menu_fav_tv) :
                Intent mIntentFavTV = new Intent(this, FavoriteTVActivity.class);
                startActivity(mIntentFavTV);
                break;
            case (R.id.menu_setting_reminder) :
                Intent mIntentSettingReminder = new Intent(this, SettingActivity.class);
                startActivity(mIntentSettingReminder);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
