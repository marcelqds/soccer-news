package com.mqds.soccernews.ui.favorites;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.mqds.soccernews.App;
import com.mqds.soccernews.data.SoccerNewsRepository;
import com.mqds.soccernews.data.local.AppDatabase;
import com.mqds.soccernews.domain.News;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    public FavoritesViewModel() {
    }
    AppDatabase db = Room.databaseBuilder(App.getInstance(),AppDatabase.class,"soccer_news_db").build();
    public LiveData<List<News>> loadFavoritesNews() {
        return SoccerNewsRepository
                .getInstance()
                .getLocalDb()
                .newsDao()
                .findFavorites();
    }

    public void save(News news){
        AsyncTask.execute(() ->db.newsDao().save(news));
    }

}