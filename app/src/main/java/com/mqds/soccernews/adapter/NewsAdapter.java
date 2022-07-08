package com.mqds.soccernews.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mqds.soccernews.R;
import com.mqds.soccernews.databinding.NewsItemsBinding;
import com.mqds.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private final List<News> news;
    private final FavoriteListener favoritesListener;

    public NewsAdapter(List<News> news, FavoriteListener favoritesListener){
        this.news = news;
        this.favoritesListener = favoritesListener;
    }

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
        Context context = holder.itemView.getContext();
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

        holder.newsItemsBinding.ivShare.setOnClickListener( view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,news.getTitle());
            intent.putExtra(Intent.EXTRA_TEXT,news.toString());
            holder.itemView.getContext().startActivity(Intent.createChooser(intent,""));
        });

        //Evento delegado para o framento
        holder.newsItemsBinding.ivFavorites.setOnClickListener(view -> {
            boolean isFavorite = !news.isFavorite();
            news.setFavorite(isFavorite);

            this.favoritesListener.click(news);
            notifyItemChanged(position);
        });

        int favoriteColor =  news.isFavorite() ? R.color.p_200 : R.color.favorite_default;
        holder.newsItemsBinding.ivFavorites.setColorFilter(context.getResources().getColor(favoriteColor));
    }

    @Override
    public int getItemCount() { return news.size(); }

    public interface FavoriteListener{
        void click(News news);
    }

}
