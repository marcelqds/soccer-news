package com.mqds.soccernews.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mqds.soccernews.domain.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("Select * FROM news WHERE favorite = 1")
    LiveData<List<News>> findFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(News... news);

    @Delete
    void delete(News news);

    @Query("DELETE from news")
    void deleteAll();
}
