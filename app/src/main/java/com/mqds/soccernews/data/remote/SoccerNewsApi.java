package com.mqds.soccernews.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsApi {
        private static Retrofit retrofit;

        private SoccerNewsApi(){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://marcelqds.github.io/soccer-news-api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static Retrofit getApi(){
            if(retrofit != null) return retrofit;
            SoccerNewsApi soccerNewsApi = new SoccerNewsApi();
            return retrofit;
        }
}
