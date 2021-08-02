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
interface  OnListClickListner
{
    void onItemClick(int position);

}
public class EditListAdapter extends RecyclerView.Adapter<EditListAdapter.ViewHolder> {
    public int imageList[];
    public Context context;
    public OnListClickListner listner;
    public EditListAdapter(Context context,int list[],OnListClickListner listner)
    {
        this.context=context;
        imageList=list;
        this.listner=listner;
    }
    @NonNull
    @Override
    public EditListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_list_layout,parent,false);
       ViewGroup.LayoutParams params= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view,listner);
    }

    @Override
    public void onBindViewHolder(@NonNull EditListAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageList[position]);
    }

    @Override
    public int getItemCount() {
        return imageList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        OnListClickListner onlistClickListner;
        public ViewHolder(@NonNull View itemView,OnListClickListner onlistClickListner) {
            super(itemView);
            imageView = itemView.findViewById(R.id.listImage);
            this.onlistClickListner=onlistClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onlistClickListner.onItemClick(getAdapterPosition());

        }
    }
}
