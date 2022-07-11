package com.mqds.soccernews.data;

import androidx.room.Room;

import com.mqds.soccernews.App;
import com.mqds.soccernews.data.local.AppDatabase;
import com.mqds.soccernews.data.remote.ISoccerNewsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepository {
        //region Constantes
        private static final String REMOTE_API_URL = "https://marcelqds.github.io/soccer-news-api/";
        private static final String LOCAL_DB_NAME = "soccer_news_db";
        //endregion


        //region Atributos encapsula o acesso a nossa API (Retrofit) e banco de dados local (Room)
        private ISoccerNewsApi remoteApi;
        private AppDatabase localDb;

        public ISoccerNewsApi getRemoteApi(){ return remoteApi; }
        public AppDatabase getLocalDb() { return localDb; }
        //endregion


        //region Singleton: garante uma instância única dos atributos relacionados ao Retrofit e Room;
        private SoccerNewsRepository(){
                remoteApi = new Retrofit.Builder()
                        .baseUrl(REMOTE_API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ISoccerNewsApi.class);

                localDb = Room.databaseBuilder(App.getInstance(), AppDatabase.class, LOCAL_DB_NAME)
                        .build();
        }

        public static SoccerNewsRepository getInstance() { return LazyHolder.INSTANCE; }

        private static class LazyHolder{
                private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
        }


        //endregion
}
