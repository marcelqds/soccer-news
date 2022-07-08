package com.mqds.soccernews.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mqds.soccernews.domain.News;

@Database(entities = {News.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
