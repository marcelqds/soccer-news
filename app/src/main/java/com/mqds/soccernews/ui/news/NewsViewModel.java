package com.mqds.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mqds.soccernews.data.remote.ISoccerNewsApi;
import com.mqds.soccernews.data.remote.SoccerNewsApi;
import com.mqds.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {
    ISoccerNewsApi api = SoccerNewsApi.getApi().create(ISoccerNewsApi.class);
    private final MutableLiveData<List<News>> mNews = new MutableLiveData<>();

    public NewsViewModel() {
        findNews();
    }

    private void findNews(){
        api.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.isSuccessful()){
                    mNews.setValue(response.body());
                }else{
                    //TODO Pensar na estratégia de para os erros
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                //TODO Pensar na estratégia de para os erros
            }
        });
    }

    public LiveData<List<News>> getNews() {
        return mNews;
    }
}