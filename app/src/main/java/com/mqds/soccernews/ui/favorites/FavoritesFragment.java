package com.mqds.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mqds.soccernews.MainActivity;
import com.mqds.soccernews.adapter.NewsAdapter;
import com.mqds.soccernews.data.local.AppDatabase;
import com.mqds.soccernews.databinding.FragmentFavoritesBinding;
import com.mqds.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel = new ViewModelProvider(this)
        .get(FavoritesViewModel.class);
        db = ((MainActivity) getActivity()).getDb();
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        loadFavoritesNews();
        return binding.getRoot();
    }

    private  void loadFavoritesNews(){
        List<News> favoritesNews = db.newsDao().findFavorites();
        if(!favoritesNews.isEmpty()) {
            binding.rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvFavorites.setAdapter(new NewsAdapter(favoritesNews, updatedNews -> {
                db.newsDao().save(updatedNews);
                loadFavoritesNews();
            }));
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}