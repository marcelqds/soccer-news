package com.mqds.soccernews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mqds.soccernews.databinding.NewsItemsBinding;
import com.mqds.soccernews.domain.News;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private List<News> news;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        NewsItemsBinding binding;
        public ViewHolder(@NonNull NewsItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public FavoritesAdapter(List<News> news){
        this.news = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemsBinding binding = NewsItemsBinding.inflate(layoutInflater,parent,true);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
