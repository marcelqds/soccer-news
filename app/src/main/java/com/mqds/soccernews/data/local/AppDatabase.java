package com.mqds.soccernews.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mqds.soccernews.domain.News;

@Database(entities = {News.class},
        version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}

////Room.databaseBuilder(App.getInstance(),AppDatabase.class,"soccer_news_db").build();