package com.example.madu_project;

import java.util.Arrays;

public class RankingManager {
    public static int numeroPartidasEnElTop = 10;


    public static Partida[] bubbleSort(Partida[] top) {
        boolean swap = true;
        while (swap) {
            swap = false;
            for (int pos = 0; pos < top.length - 2; pos++) {
                if (top[pos].getPuntuacion() > top[pos + 1].getPuntuacion()) {
                    Partida tmp = top[pos];
                    top[pos] = top[pos+1];
                    top[pos+1] = tmp;
                    swap = true;

                }
            }


        }
        Partida[] finalProcessed = new Partida[(top.length < 10 ? top.length : 10)];
        int cont = 0;
        for (Partida e : finalProcessed){
            e = top[cont];
            cont++;
        }
        return finalProcessed;

/*
            for (int pos = 0; pos < top.length - 1; pos++) {


                for (Partida p : todasLasPartidas) {
                    if (p.getPuntuacion() > (top[pos] == null ? 0 : top[pos].getPuntuacion())) {
                        top = moverALaDerecha(pos, top);
                        top[pos + 1] = top[pos];

                        top[pos] = p;
                    }

                }
            }
            for (Partida p : top) {
                System.out.println(p.getPuntuacion());
            }
            return top;

        }
        public static Partida[] moverALaDerecha ( int position, Partida[] partidas){
            for (int pos = partidas.length - 1; pos <= position; pos--) {
                partidas[pos] = partidas[pos - 1];
            }
            return partidas;*/

    }

}
