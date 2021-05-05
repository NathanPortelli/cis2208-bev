package com.example.bevproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>
{
    Context context;
    List<Articles> articlesList;
    RecyclerView rvArticles;
    //final View.OnClickListener onClickListener = new ArticleOnClickListener();

    private ArticleViewClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView articleTitle;
        ImageView articleImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleImage = itemView.findViewById(R.id.articleImg);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v)
        {
            listener.onClick(v, getBindingAdapterPosition());
        }
    }

    public ArticleAdapter(Context context, List<Articles> articlesList, RecyclerView rvArticles, ArticleViewClickListener listener)
    {
        this.context = context;
        this.articlesList = articlesList;
        this.rvArticles = rvArticles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_article, parent, false);
        //view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position)
    {
        Articles article = articlesList.get(position);
        holder.articleTitle.setText(article.getTitle());
        holder.articleImage.setImageBitmap(article.getImage());
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public interface ArticleViewClickListener
    {
        public void onClick(View v, int pos);
    }
}

