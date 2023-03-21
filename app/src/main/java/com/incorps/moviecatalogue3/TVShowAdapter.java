package com.incorps.moviecatalogue3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {
    private ArrayList<TVShow> mData = new ArrayList<>();
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<TVShow> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new TVShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder tvShowViewHolder, int position) {
        final TVShow tvShow = mData.get(position);
        tvShowViewHolder.bind(mData.get(position));
        tvShowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTVShowActivity.class);
                intent.putExtra("id", tvShow.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView txtTitle;
        TextView txtDescription;
        TextView txtDirector;

        TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDescription = itemView.findViewById(R.id.txt_description);
            txtDirector = itemView.findViewById(R.id.txt_popularity);
        }

        void bind(TVShow tvItems) {
            Glide.with(context).load(tvItems.getPhoto()).into(imgPhoto);
            txtTitle.setText(tvItems.getTitle());
            txtDescription.setText(tvItems.getDescription());
            txtDirector.setText(tvItems.getPopularity() + " Fans");
        }
    }
}