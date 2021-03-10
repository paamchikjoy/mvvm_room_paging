package com.example.mvvm_room_practice_2.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvvm_room_practice_2.R;
import com.example.mvvm_room_practice_2.listeners.Listener;
import com.example.mvvm_room_practice_2.model.Movies;

public class MoviesListAdapter extends PagedListAdapter<Movies,MoviesListAdapter.MoviesViewHolder> {
    private Context context;
    private static Listener listener;
    public MoviesListAdapter (Context context,Listener listener)
    {
        super(itemCallback);
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.each_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

        Movies movies=getItem(position);
        holder.nameTV.setText(movies.getMovies_name());
        holder.rating.setText(""+movies.getMovies_rating());
        Glide.with(context).load(movies.getMovies_poster())
                .into(holder.poster);

    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView nameTV,rating;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            poster=itemView.findViewById(R.id.poster_img);
            nameTV=itemView.findViewById(R.id.movie_name);
            rating=itemView.findViewById(R.id.movie_rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(getAdapterPosition());
                }
            });
        }

    }
    static DiffUtil.ItemCallback<Movies> itemCallback= new DiffUtil.ItemCallback<Movies>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movies oldItem, @NonNull Movies newItem) {
            return oldItem.getDatabase_id() == newItem.getDatabase_id();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movies oldItem, @NonNull Movies newItem) {
            return oldItem.equals(newItem);
        }
    };

}
