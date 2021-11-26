package com.example.madu_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> implements View.OnClickListener{

    private Ranking[] ranks;
    private View.OnClickListener listener;

    public RankingAdapter(Ranking[] ranks)
    {
        this.ranks = ranks;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtNombreRanking;

        public ViewHolder(@NonNull View item) {
            super(item);
            txtNombreRanking = item.findViewById(R.id.txtNombreRanking);
        }

        void bindRanking(Ranking rank){
            txtNombreRanking.setText(rank.getNombreGenero());
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ranking_item,parent,false);

        item.setOnClickListener(this);

        return new RankingAdapter.ViewHolder(item);
    }


    public void onBindViewHolder(RankingAdapter.ViewHolder holder, int position)
    {
        holder.bindRanking(ranks[position]);
    }

    public int getItemCount()
    {
        return ranks.length;
    }


    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }


}

