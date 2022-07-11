package com.mqds.soccernews.ui.news;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mqds.soccernews.data.SoccerNewsRepository;
import com.mqds.soccernews.domain.News;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {
    public enum State{ DOING, DONE , ERROR }

    private final MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();
    private final MutableLiveData<List<News>> localNews = new MutableLiveData<>();

    public NewsViewModel() { this.loadLocalNews(); this.findNews(); }

    public void findNews(){
        state.setValue(State.DOING);
        SoccerNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new Callback<List<News>>() {

            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                if(response.isSuccessful()){
                    /*if(localNews != null) {
                        for (News newVer : localNews) {
                            int search = response.body().lastIndexOf(newVer);
                            Log.i("POSITION"," "+ search);
                            response.body().set(search, newVer);
                        }
                    }*/
                    mNews.setValue(response.body());
                    state.setValue(State.DONE);
                }else{
                    state.setValue(State.ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, Throwable error) {
                error.printStackTrace();
                state.setValue(State.ERROR);
            }
        });
    }

    public void save(News news){
        Executor executors = Executors.newSingleThreadExecutor();
        executors.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }

    public LiveData<News> findNewsWithLink(String link){
        return SoccerNewsRepository.getInstance().getLocalDb().newsDao().findWithLink(link);
    }

    public LiveData<List<News>> loadFavorites(){
        return localNews;
    }

    public void loadLocalNews(){
        this.localNews.setValue(SoccerNewsRepository.getInstance().getLocalDb().newsDao().findFavorites().getValue());
    }

    public void deleteAll(){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->SoccerNewsRepository.getInstance().getLocalDb().newsDao().deleteAll());
    }

    public LiveData<List<News>> getNews() {
        return mNews;
    }

    public LiveData<List<News>> getLocalNews(){
        return localNews;
    }

    public LiveData<State> getState(){ return state; }
}