package com.mqds.soccernews.ui.news;

import android.app.Application;

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
    public enum State{
        DOING, DONE , ERROR
    }

    ISoccerNewsApi api = SoccerNewsApi.getApi().create(ISoccerNewsApi.class);
    private final MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();


    public NewsViewModel() {

        this.findNews();
    }

    private void findNews(){
        state.setValue(State.DOING);
        api.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.isSuccessful()){
                    mNews.setValue(response.body());
                    state.setValue(State.DONE);
                }else{
                    state.setValue(State.ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                state.setValue(State.ERROR);
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<News>> getNews() {
        return mNews;
    }

    public LiveData<State> getState(){ return state; }
}