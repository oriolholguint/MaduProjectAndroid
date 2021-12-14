package com.example.madu_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> implements View.OnClickListener {

    private Ranking[] ranks;
    private View.OnClickListener listener;

    public RankingAdapter(Ranking[] ranks) {
        this.ranks = ranks;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreRanking;
        ImageView ImageGenre;

        public ViewHolder(@NonNull View item) {
            super(item);
            txtNombreRanking = item.findViewById(R.id.txtNombreRanking);
            //ImageGenre = item.findViewById(R.id.ImageGenre);
        }

        void bindRanking(Ranking rank) {

            rank = new Ranking(rank.getNombreGenero(), rank.getPartidasRank());
            String ranking = rank.getNombreGenero() + "\n\n";
            Partida[] procesadas = RankingManager.bubbleSort(rank.getPartidasRank());
            ScoreAdapter sa = new ScoreAdapter(procesadas);
            RecyclerView rv = itemView.findViewById(R.id.ScoreItem);
            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.VERTICAL,false));
            rv.setAdapter(sa);
//            File imgFile = new  File("/data/data/com.example.madu_project/files/images/"+FragmentRanking.GetPathImageFromGeneroString(rank.getNombreGenero()));
//
//            if(imgFile.exists()){
//
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//
//                ImageGenre.setImageBitmap(myBitmap);
//
//            }
            txtNombreRanking.setText(ranking);


        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ranking_item, parent, false);

        item.setOnClickListener(this);

        return new RankingAdapter.ViewHolder(item);
    }


    public void onBindViewHolder(RankingAdapter.ViewHolder holder, int position) {
        holder.bindRanking(ranks[position]);

    }

    public int getItemCount() {
        return ranks.length;
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }


}

