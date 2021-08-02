package com.example.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsShowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    NewsData newsData;

    public NewsShowFragment() {
        // Required empty public constructor
    }
    public NewsShowFragment(NewsData news)
    {
        newsData=news;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsShowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsShowFragment newInstance(String param1, String param2) {
        NewsShowFragment fragment = new NewsShowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView browser=view.findViewById(R.id.webView);
        browser.setWebViewClient(new WebViewClient());
        browser.setWebChromeClient(new WebChromeClient());
        browser.loadUrl(newsData.getNewsUrl());
        DataBaseHandler db=new DataBaseHandler(getContext());
        ArrayList<NewsData> starredNews=db.getStarredNews();
        boolean isStarred=false;
        for(int x=0;x<starredNews.size();x++)
        {
            if(newsData.getNewsUrl().equals(starredNews.get(x).getNewsUrl()))
            {
                isStarred=true;
            }
        }
        ImageButton star=view.findViewById(R.id.starButton);
        if(isStarred)
        {
            star.setImageResource(R.drawable.ic_star);
        }
        boolean finalIsStarred = isStarred;
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalIsStarred)
                {
                    db.deleteNews(newsData.getNewsUrl());
                    star.setImageResource(R.drawable.ic_starred);
                }
                else
                {
                    db.addNews(newsData);
                    star.setImageResource(R.drawable.ic_star);
                }

            }
        });

    }

}