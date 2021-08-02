package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ArrayList<NewsData> newsArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsArray=new ArrayList<NewsData>();
        if (savedInstanceState == null) {
            NewsFragment nf=new NewsFragment(newsArray);
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.fragmentContainer,NewsFragment.class,null).addToBackStack(null).commit();

        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        DownloadTask task=new DownloadTask(newsArray,this.getSupportFragmentManager());
        task.execute("http://api.mediastack.com/v1/news?access_key=bf16586f9e026ecf0875a5c0196a646f&languages=en");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (id) {
            case R.id.news: {
                NewsFragment nf=new NewsFragment(newsArray);
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragmentContainer, nf, null).setReorderingAllowed(true).commit();

                break;
            }
            case R.id.Starrednews: {
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragmentContainer, StarredNewsFragment.class, null).setReorderingAllowed(true).commit();
                break;
            }
            case R.id.notes: {
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragmentContainer, NotesFragment.class, null).setReorderingAllowed(true).commit();
                break;
            }
            case R.id.about: {
                fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragmentContainer, AboutFragment.class, null).setReorderingAllowed(true).commit();
                break;
            }
        }
        return true;
    }
}