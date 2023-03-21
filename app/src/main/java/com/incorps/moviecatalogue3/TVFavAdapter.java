package com.incorps.moviecatalogue3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;

public class TVFavAdapter extends RecyclerView.Adapter<TVFavAdapter.ListViewHolder> {

    private ArrayList<TVShow> listTV;
    private Context context;

    public void setData(ArrayList<TVShow> items) {
        listTV.clear();
        listTV.addAll(items);
        notifyDataSetChanged();
    }

    public TVFavAdapter(ArrayList<TVShow> list, Context context) {
        this.listTV = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        final TVShow tvShow = listTV.get(position);
        holder.txtTitle.setText(tvShow.getTitle());
        holder.txtDescription.setText(tvShow.getDescription());
        holder.txtFans.setText(tvShow.getPopularity() + " Fans");
        Glide.with(context).load(tvShow.getPhoto()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return listTV.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView txtTitle;
        TextView txtDescription;
        TextView txtFans;

        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDescription = itemView.findViewById(R.id.txt_description);
            txtFans = itemView.findViewById(R.id.txt_popularity);
        }
    }
}
