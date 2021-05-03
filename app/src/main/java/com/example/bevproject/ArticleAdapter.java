package com.example.bevproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    final View.OnClickListener onClickListener = new ArticleOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView articleTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleTitle = itemView.findViewById(R.id.articleTitle);
        }
    }

    public ArticleAdapter(Context context, List<Articles> articlesList, RecyclerView rvArticles)
    {
        this.context = context;
        this.articlesList = articlesList;
        this.rvArticles = rvArticles;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_article, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position)
    {
        Articles article = articlesList.get(position);
        holder.articleTitle.setText(article.getTitle());
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    private class ArticleOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            int itemPosition = rvArticles.getChildLayoutPosition(v);
            String item = articlesList.get(itemPosition).getTitle();
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
        }
    }
}

