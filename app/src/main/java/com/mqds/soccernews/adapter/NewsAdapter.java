package com.mqds.soccernews.adapter;

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
    private List<News> localNews;
    private final NewsListener newsListener;

    public NewsAdapter(List<News> news, List<News> localNews, NewsListener newsListener){
        this.news = news;
        this.newsListener = newsListener;
        this.localNews = localNews;
    }

    public NewsAdapter(List<News> news, NewsListener newsListener){
        this.news = news;
        this.newsListener = newsListener;
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

        News news = this.news.get(position);
        holder.newsItemsBinding.tvTitle.setText(news.getTitle());
        holder.newsItemsBinding.tvDescription.setText(news.getDescription());

        Picasso.get()
                .load(news.getImage())
                .centerCrop(50)
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
            holder.itemView.getContext()
                .startActivity(Intent.createChooser(intent,holder.itemView.getContext().getString(R.string.intentShare)));
        });

        holder.newsItemsBinding.ivFavorites.setOnClickListener(view -> {
            news.setFavorite(!(news.isFavorite()));
            this.newsListener.click(news);
            notifyItemChanged(position);
        });

        int favoriteColor =  news.isFavorite() ? R.color.p_200 : R.color.favorite_default;
        holder.newsItemsBinding
                .ivFavorites
                .setColorFilter(
                        holder.itemView
                        .getContext()
                        .getResources()
                        .getColor(favoriteColor)
                );
    }

    @Override
    public int getItemCount() { return this.news.size(); }

    public interface NewsListener{
        void click(News news);
    }

}
