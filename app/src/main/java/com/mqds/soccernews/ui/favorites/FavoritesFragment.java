package com.mqds.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mqds.soccernews.adapter.FavoritesAdapter;
import com.mqds.soccernews.data.SoccerNewsRepository;
import com.mqds.soccernews.ui.MainActivity;
import com.mqds.soccernews.adapter.NewsAdapter;
import com.mqds.soccernews.data.local.AppDatabase;
import com.mqds.soccernews.databinding.FragmentFavoritesBinding;
import com.mqds.soccernews.domain.News;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FavoritesViewModel favoritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvFavorites.setLayoutManager(layoutManager);

        loadFavoritesNews();

        return root;
    }

    private void loadFavoritesNews(){
        favoritesViewModel.loadFavoritesNews().observe(getViewLifecycleOwner(), localNews -> {
            if(!localNews.isEmpty()) {
                binding.rvFavorites.setAdapter(new NewsAdapter(localNews, updatedNews -> {
                    favoritesViewModel.save(updatedNews);
                    loadFavoritesNews();
                }));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}