package com.example.madu_project.partida;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.madu_project.R;

import java.util.ArrayList;

public class PartidaAdapter extends RecyclerView.Adapter<PartidaAdapter.ViewHolder>
{
    private ArrayList<Partida> partidas;

    public PartidaAdapter(ArrayList<Partida> partidas)
    {
        this.partidas = partidas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imagenMedalla;
        TextView posicionRanking;
        ImageView avatarJugador;
        TextView nombreJugador;
        TextView puntuacionJugador;

        public ViewHolder(View item)
        {
            super(item);

            imagenMedalla = item.findViewById(R.id.imagenMedalla);
            posicionRanking = item.findViewById(R.id.posicionRanking);
            avatarJugador = item.findViewById(R.id.avatarJugador);
            nombreJugador = item.findViewById(R.id.nombreJugador);
            puntuacionJugador = item.findViewById(R.id.puntuacionJugador);
        }

        void bindPartida(Partida partida, int medalla, int position)
        {
            if(medalla == 0) //Se añade partida sin medalla
            {
                posicionRanking.setText(position + ". ");
                avatarJugador.setImageResource(partida.getJugador().getAvatar());
                nombreJugador.setText(partida.getJugador().getNombre());
                puntuacionJugador.setText(partida.getPuntuacion() + "p.");
            }
            else //Se añade partida con medalla
            {
                imagenMedalla.setImageResource(medalla);
                posicionRanking.setText(position + ". ");
                avatarJugador.setImageResource(partida.getJugador().getAvatar());
                nombreJugador.setText(partida.getJugador().getNombre());
                puntuacionJugador.setText(partida.getPuntuacion() + "p.");
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.partida_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        int medalla = 0;

        //Medalla de partida (si tiene)
        switch(position)
        {
            case 0: //Posicion 1 - medalla oro
                medalla = R.drawable.moneda_oro;
                break;
            case 1: //Posicion 2 - medalla plata
                medalla = R.drawable.moneda_plata;
                break;
            case 2: //Posicion 3 - medalla bronce
                medalla = R.drawable.moneda_bronze;
                break;
        }

        holder.bindPartida(partidas.get(position), medalla, position + 1);
    }

    @Override
    public int getItemCount()
    {
        return partidas.size();
    }

}
