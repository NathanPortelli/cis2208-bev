package com.example.bevproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment
{
    DBHelper db;
    RecyclerView rvArticles;
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Articles> articleList = new ArrayList<>();
    private ArticleAdapter.ArticleViewClickListener listener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_social, container, false);
        rvArticles = (RecyclerView) view.findViewById(R.id.rvArticles);
        db = new DBHelper(getActivity());
        articleList = db.getSocialArticles();
        rvArticles.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvArticles.setLayoutManager(layoutManager);
        populateRvArticles();
        return view;
    }

    private void populateRvArticles()
    {
        setOnClickListener();
        articleAdapter = new ArticleAdapter(getActivity(), articleList, rvArticles, listener);
        rvArticles.setAdapter(articleAdapter);
    }

    private void setOnClickListener()
    {
        listener = new ArticleAdapter.ArticleViewClickListener() {
            @Override
            public void onClick(View v, int pos)
            {
                Bitmap bmp = articleList.get(pos).getImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 30, baos);

                Intent intent = new Intent(getActivity().getApplicationContext(), ArticleItem.class);
                intent.putExtra("articleTitle", articleList.get(pos).getTitle());
                intent.putExtra("articleCateg", articleList.get(pos).getCateg());
                intent.putExtra("articleText", articleList.get(pos).getContent());
                intent.putExtra("articleImage", baos.toByteArray());
                startActivity(intent);
            }
        };
    }
}