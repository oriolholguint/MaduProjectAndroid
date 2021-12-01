package com.example.madu_project;

import java.util.Arrays;

public class RankingManager {
    public static int numeroPartidasEnElTop = 10;


    public static Partida[] bubbleSort(Partida[] top) {
        boolean swap = true;
        while (swap) {
            swap = false;
            for (int pos = 0; pos < top.length - 2; pos++) {
                if (top[pos].getPuntuacion() < top[pos + 1].getPuntuacion()) {
                    Partida tmp = top[pos];
                    top[pos] = top[pos+1];
                    top[pos+1] = tmp;
                    swap = true;
                }
            }
        }
        Partida[] finalProcessed = Arrays.copyOfRange(top,0,9);
        return finalProcessed;
    }

}
