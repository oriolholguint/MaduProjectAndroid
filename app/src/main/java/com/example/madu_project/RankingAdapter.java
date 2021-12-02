package com.example.madu_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> implements View.OnClickListener {

    private Ranking[] ranks;
    private View.OnClickListener listener;

    public RankingAdapter(Ranking[] ranks) {
        this.ranks = ranks;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreRanking;

        public ViewHolder(@NonNull View item) {
            super(item);
            txtNombreRanking = item.findViewById(R.id.txtNombreRanking);
        }

        void bindRanking(Ranking rank) {

/*            Partida[] pruebaDeSort = new Partida[]{
                    new Partida(10,"farsi",null,null),
                    new Partida(102,"farsi",null,null),
                    new Partida(230,"farsi",null,null),
                    new Partida(320,"farsi",null,null),
                    new Partida(420,"farsi",null,null),
                    new Partida(560,"farsi",null,null),
                    new Partida(670,"farsi",null,null),
                    new Partida(780,"farsi",null,null),
                    new Partida(890,"farsi",null,null),
                    new Partida(900,"farsi",null,null),
                    new Partida(340,"farsi",null,null),
                    new Partida(1440,"farsi",null,null),
                    new Partida(131230,"farsi",null,null),
                    new Partida(13875680,"farsi",null,null),
                    new Partida(13210,"farsi",null,null),
                    new Partida(130,"farsi",null,null),
                    new Partida(1542353440,"farsi",null,null)
            };*/
            rank = new Ranking(rank.getNombreGenero(), rank.getPartidasRank());
            String ranking = rank.getNombreGenero() + "\n\n";
            int cont = 0;
            Partida[] procesadas = RankingManager.bubbleSort(rank.getPartidasRank());
            /*for (Partida e : procesadas) {
                if (e != null) {
                    if (e.check()) {//retona true si tot esta correcte

                        ranking += ++cont + ". " + e.getJugador().getNombre().toUpperCase() + " Score: " + e.getPuntuacion() + " Dificultad: "+e.getDificultad()+"\n";
                    }
                }else{
                    ranking += "\n";
                }
            }*/
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

