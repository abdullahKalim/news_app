package com.example.newsapp;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DownloadTask extends AsyncTask<String,Integer,ArrayList<NewsData>> {
    ArrayList<NewsData> news;
    FragmentManager fragmentManager;

    public DownloadTask(ArrayList<NewsData> arrayList,FragmentManager fragmentManager)
    {
        news=arrayList;
        this.fragmentManager=fragmentManager;
    }
    @Override
    protected ArrayList<NewsData> doInBackground(String... urlIn) {
        URL url;
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlIn[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int d = reader.read();
            result = "";
            while (d != -1) {
                char ch = (char) d;
                result = result + ch;
                d = reader.read();
            }
            JSONObject ob = new JSONObject(result);
            JSONArray arr = new JSONArray(ob.getString("data"));
            for (int x = 0; x < arr.length(); x++) {
                JSONObject object = new JSONObject(arr.getString(x));
                NewsData newsData = new NewsData(object.getString("title"), object.getString("author"), object.getString("image"), object.getString("url"), object.getString("source"));
                news.add(newsData);
                //res=res+"\nSource: "+object.getString("source")+"\n Title: "+object.getString("title")+"\nAuthor :"+object.getString("author")+"\nUrlImage :"+object.getString("image")+"\nUrl: "+object.getString("url");
            }

            return news;

        } catch (Exception e) {
            e.printStackTrace();
            return news;

        }
        //news.add(new NewsData("Inside Teslaville: Elon Musk to build US’s first solar-powered town near Austin","Jade Bremner", "https://static.independent.co.uk/2021/07/13/14/2.3.png?width=1200&auto=webp&quality=75", "https://www.independent.co.uk/climate-change/news/elon-musk-solar-town-austin-b1883322.html","Unkonown"));
        //news.add(new NewsData("June Consumer Prices Rose Sharply Again as Economy Rebounded - The Wall Street Journal","Gwynn Guilford","https://images.wsj.net/im-368006/social","https://www.wsj.com/articles/us-inflation-consumer-price-index-june-2021-11626125947","Unkonown"));
        //news.add(new NewsData("Cadoo gets $1.5M to gamify fitness with betting challenges","Natasha Lomas","https://techcrunch.com/wp-content/uploads/2017/06/gettyimages-162221496.jpg?w=574","https://techcrunch.com/2021/07/13/cadoo-gets-1-5m-to-gamify-fitness-with-betting-challenges/","Unknown"));
        //news.add(new NewsData("Electrify America plans to 'more than double' its EV charger network by late 2025","feedfeeder",null, "https://slashdot.org/firehose.pl?op=view&amp;id=149010471","Unknown"));
        //return  news;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsData> arrayList) {
        super.onPostExecute(arrayList);
        Fragment fragment=fragmentManager.findFragmentById(R.id.fragmentContainer);
        if(fragment.equals( new NewsFragment())) {
            NewsFragment nf = new NewsFragment(arrayList);
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragmentContainer, nf, null).addToBackStack(null).setReorderingAllowed(true).commit();
        }
    }
}
