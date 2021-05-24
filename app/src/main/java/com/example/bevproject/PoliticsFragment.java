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

//Used when Politics Category fragment is in use
public class PoliticsFragment extends Fragment
{
    //Database reference
    DBHelper db;
    RecyclerView rvArticles;
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Articles> articleList = new ArrayList<>();
    private ArticleAdapter.ArticleViewClickListener listener;

    //Creates, populates, and returns view hierarchy of politics
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_politics, container, false);
        rvArticles = (RecyclerView) view.findViewById(R.id.rvArticles);
        db = new DBHelper(getActivity());
        articleList = db.getPoliticsArticles(); //gets all articles listed under 'Politics' category
        rvArticles.setHasFixedSize(true); //ensures change of size in recyclerview is constant
        layoutManager = new LinearLayoutManager(getActivity());
        rvArticles.setLayoutManager(layoutManager);
        populateRvArticles();
        return view;
    }

    //Uses ArticleAdapter class
    private void populateRvArticles()
    {
        setOnClickListener();
        articleAdapter = new ArticleAdapter(getActivity(), articleList, rvArticles, listener);
        rvArticles.setAdapter(articleAdapter);
    }

    //Once user clicks on article
    private void setOnClickListener()
    {
        listener = new ArticleAdapter.ArticleViewClickListener() {
            @Override
            public void onClick(View v, int pos)
            {
                //Sends data related to article to article activity
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