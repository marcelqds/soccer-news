package com.mqds.soccernews.ui.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.mqds.soccernews.MainActivity;
import com.mqds.soccernews.adapter.NewsAdapter;
import com.mqds.soccernews.data.local.AppDatabase;
import com.mqds.soccernews.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    AppDatabase db;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState
    ){

        db = ((MainActivity) getActivity()).getDb();
        NewsViewModel newsViewModel = new ViewModelProvider(this)
                .get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvNews.setLayoutManager(linearLayoutManager);

        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, favoriteNews -> {
                if(db != null)
                    db.newsDao().save(favoriteNews);
            }));
        });

        newsViewModel.getState().observe(getViewLifecycleOwner(),state -> {
            switch (state){
                case DOING:
                    //TODO: Incluir SwipeRefreshLayout (Loading)
                    break;
                case DONE:
                    //TODO: Finalizar SwipeRefreshLayout (Loading);
                    break;
                default:
                    //TODO: Finalizar SwipeRefershLayout (Loading);
                    //TODO: Mostrar error
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}