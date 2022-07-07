package com.mqds.soccernews.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mqds.soccernews.databinding.NewsItemsBinding;
import com.mqds.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private List<News> news;

    public NewsAdapter(List<News> news){ this.news = news; }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final NewsItemsBinding newsItemsBinding;

        public ViewHolder(@NonNull NewsItemsBinding newsItemsBinding) {
            super(newsItemsBinding.getRoot());
            this.newsItemsBinding = newsItemsBinding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemsBinding binding = NewsItemsBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.newsItemsBinding.tvTitle.setText(news.getTitle());
        holder.newsItemsBinding.tvDescription.setText(news.getDescription());
        Picasso.get()
                .load(news.getImage())
                .centerCrop(20)
                .fit()
                .into(holder.newsItemsBinding.ivThumbnail);

        holder.newsItemsBinding.btOpenLink.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(news.getLink()));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


}
