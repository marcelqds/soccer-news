package com.mqds.soccernews.data.remote;

import com.mqds.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ISoccerNewsApi {
    @GET("data-news.json")
    Call<List<News>> getNews();
}
