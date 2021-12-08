package com.example.madu_project;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> implements View.OnClickListener  {
    private Partida[] scores;
    private View.OnClickListener listener;
    public ScoreAdapter(Partida[] sortedScores) {
        this.scores = sortedScores;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView SingleScore;
        ImageView avatarScore;
        public static final int[] RankColorList =  {Color.rgb(230,194,0),Color.GRAY,Color.rgb(205,127,50),Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE};
        public ViewHolder(@NonNull View item) {
            super(item);
            SingleScore = item.findViewById(R.id.SingleScore);
            avatarScore = item.findViewById(R.id.avatarScore);
        }

        void bindScore(Partida score,int position) {
            this.avatarScore.setImageResource(score.getJugador().getAvatar());
            String date = new SimpleDateFormat("MM/dd/yyyy").format(score.getFecha());
            String info = (score.getJugador().getNombre());
            String space = "                                     ";
            this.SingleScore.setText(info+space.substring(0,(34-info.length())/2)+date+space.substring(0,(30-info.length())/2)+score.getPuntuacion()+"  p");

            this.SingleScore.setTextColor(RankColorList[position]);

        }

    }

        //int score = ((dificultad * 25) + 25) + ((tiempo * 3.3 ) - 100);
        @Override
        public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.score_item, parent, false);

            item.setOnClickListener(this);

            return new ScoreAdapter.ViewHolder(item);
        }


        public void onBindViewHolder(ScoreAdapter.ViewHolder holder, int position) {

                holder.bindScore(this.scores[position],position);

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

