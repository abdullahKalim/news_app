package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
interface  OnItemClickListner
{
    void onItemClick(int position);

}
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    public ArrayList<NewsData> newsArray;
    public Context context;
    public OnItemClickListner listner;
    public CardAdapter(Context context,ArrayList<NewsData> newsArray,OnItemClickListner listner)
    {
        this.context=context;
        this.newsArray=newsArray;
        this.listner=listner;
    }
    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        ViewGroup.LayoutParams params= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view,listner);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        NewsData newsData=newsArray.get(position);
        holder.author.setText("Author: "+newsData.getAuthor());
        holder.source.setText("Source: "+ newsData.getSource());
        holder.title.setText(newsData.getTitle());
        Glide.with(context).load(newsData.getImageUrl()).placeholder(R.drawable.ic_default_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return newsArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title,author,source;
        OnItemClickListner onItemClickListner;
        public ViewHolder(@NonNull View itemView,OnItemClickListner onItemClickListner) {
            super(itemView);
            imageView = itemView.findViewById(R.id.newsImage);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            source=itemView.findViewById(R.id.source);
            this.onItemClickListner=onItemClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListner.onItemClick(getAdapterPosition());

        }
    }
}
