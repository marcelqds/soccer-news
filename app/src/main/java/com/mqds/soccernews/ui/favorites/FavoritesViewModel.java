package com.mqds.soccernews.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mqds.soccernews.data.SoccerNewsRepository;
import com.mqds.soccernews.domain.News;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoritesViewModel extends ViewModel {
    public FavoritesViewModel() {}

    public LiveData<List<News>> loadFavoritesNews() {
        return SoccerNewsRepository.getInstance().getLocalDb().newsDao().findFavorites();
    }

    public void save(News news){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }
}