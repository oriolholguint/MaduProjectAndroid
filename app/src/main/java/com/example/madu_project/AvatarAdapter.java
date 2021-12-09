package com.example.madu_project;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AvatarAdapter extends BaseAdapter{

    private Context context;
    private int[] avatares;
    private ImageView imagenSelect;
    private GridView gvAvatares;
    private Jugador jugador;



    public AvatarAdapter(Context context, int[] avatares,ImageView imagenSelect,GridView gvAvatares,Jugador jugador) {
        this.context = context;
        this.avatares = avatares;
        this.imagenSelect = imagenSelect;
        this.gvAvatares = gvAvatares;
        this.jugador = jugador;
    }

    @Override
    public int getCount() {
        return avatares.length;
    }

    @Override
    public Object getItem(int position) {
        return avatares[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if (convertView == null) {
            /*
            Crear un nuevo Image View de 90x90
            y con recorte alrededor del centro
             */
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(90,90));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        //Setear la imagen desde el recurso drawable
        imageView.setImageResource(avatares[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imagenSelect.setImageResource(avatares[position]);
                gvAvatares.setVisibility(View.INVISIBLE);
                jugador.setAvatar(avatares[position]);
            }
        });

        return imageView;


    }


}
