package com.mqds.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import com.mqds.soccernews.domain.News;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> mNews;

    public NewsViewModel() {
        mNews = new MutableLiveData<>();

        //TODO Remover Mock de Notícias
        List<News> listNews = new ArrayList<News>(){{
            add(new News("Novas responsabilidades","Pia distribui liderança na Seleção com ausência da capitã Marta na Copa América"));
            add(new News("Duas mudanças","Natascha e Duda Sampaio são chamadas para disputar a Copa América nas vagas de Letícia Izidoro e Gabi Nunes, cortadas por lesão"));
            add(new News("Otimismo","Apesar de derrotas, Pia mantém confiança na seleção: \"Estaremos prontas para a Copa América"));
            add(new News("Copa América x Euro","Conmebol e Uefa anunciam versão feminina da Finalíssima e Intercontinental Sub-20"));
        }};
        mNews.setValue(listNews);
    }

    public LiveData<List<News>> getNews() {
        return mNews;
    }
}