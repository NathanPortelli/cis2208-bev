package com.example.bevproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>
{
    Context context;
    List<Articles> articlesList;
    RecyclerView rvArticles;

    private ArticleViewClickListener listener;

    //View for RecyclerView used for article list
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView articleTitle; //Article Title
        ImageView articleImage; //Article Image

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleImage = itemView.findViewById(R.id.articleImg);
            itemView.setOnClickListener(this);
        }
        //Access to article content after clicking on recyclerview
        @Override
        public void onClick(View v)
        {
            listener.onClick(v, getBindingAdapterPosition());
        }
    }

    //Constructor
    public ArticleAdapter(Context context, List<Articles> articlesList, RecyclerView rvArticles, ArticleViewClickListener listener)
    {
        this.context = context;
        this.articlesList = articlesList;
        this.rvArticles = rvArticles;
        this.listener = listener;
    }

    //Retrieval of instance to create a new viewholder
    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_article, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //Updates contents with the items of the given position
    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position)
    {
        Articles article = articlesList.get(position);
        holder.articleTitle.setText(article.getTitle());
        holder.articleImage.setImageBitmap(article.getImage());
    }

    //Gets total amount of articles
    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    //Available action
    public interface ArticleViewClickListener
    {
        public void onClick(View v, int pos);
    }
}

