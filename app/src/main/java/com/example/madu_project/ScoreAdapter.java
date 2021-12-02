package com.example.madu_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> implements View.OnClickListener  {
    private Partida[] scores;
    private View.OnClickListener listener;
    public ScoreAdapter(Partida[] sortedScores) {
        this.scores = sortedScores;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView SingleScore;

        public ViewHolder(@NonNull View item) {
            super(item);
            SingleScore = item.findViewById(R.id.SingleScore);
        }

        void bindScore(Partida score) {

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
            SingleScore.setText(score.getPuntuacion());
        }

    }


        @Override
        public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.score_item, parent, false);

            item.setOnClickListener(this);

            return new ScoreAdapter.ViewHolder(item);
        }


        public void onBindViewHolder(ScoreAdapter.ViewHolder holder, int position) {
            holder.bindScore(this.scores[position]);
        }

        public int getItemCount() {
            return scores.length;
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

