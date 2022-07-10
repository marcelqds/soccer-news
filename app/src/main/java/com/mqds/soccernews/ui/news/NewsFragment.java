package com.mqds.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.mqds.soccernews.R;
import com.mqds.soccernews.adapter.NewsAdapter;
import com.mqds.soccernews.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState
    ){

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvNews.setLayoutManager(linearLayoutManager);

        observeNews();
        setupStates();

        binding.srlNews.setOnRefreshListener(() -> {
            observeNews();
            binding.srlNews.setRefreshing(false);
        });

        return root;
    }

    private void observeNews(){
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, favoriteNews -> {
                newsViewModel.save(favoriteNews);
            }));
        });
    }

    private void setupStates(){
        newsViewModel.getState().observe(getViewLifecycleOwner(),state -> {
            switch (state){
                case DOING:
                    binding.srlNews.setRefreshing(true);
                    break;
                case DONE:
                    binding.srlNews.setRefreshing(false);
                    break;
                case ERROR:
                    binding.srlNews.setRefreshing(false);
                    Snackbar.make(binding.srlNews, R.string.error_network,Snackbar.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
//Hilt/Dagger -> Injeção de dependência

//SOLID -> Single Responsibility / Open & Close / Liskov Substitution / Interface Template / Dependency Inversion