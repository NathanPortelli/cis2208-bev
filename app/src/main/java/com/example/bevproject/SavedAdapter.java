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

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder>
{
    Context context;
    List<Pinned> pinnedList;
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

    public SavedAdapter(Context context, List<Pinned> pinnedList, RecyclerView rvArticles, ArticleViewClickListener listener)
    {
        this.context = context;
        this.pinnedList = pinnedList;
        this.rvArticles = rvArticles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_article, parent, false);
        //view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position)
    {
        Pinned pinnedArticles = pinnedList.get(position);
        holder.articleTitle.setText(pinnedArticles.getPinnedTitle());
    }

    @Override
    public int getItemCount() {
        return pinnedList.size();
    }

    public interface ArticleViewClickListener
    {
        public void onClick(View v, int pos);
    }
}

