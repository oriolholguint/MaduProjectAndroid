package com.example.madu_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GeneroAdapter extends RecyclerView.Adapter<GeneroAdapter.ViewHolder> implements View.OnClickListener, View.OnTouchListener
{
    private Genero [] generos;
    private View.OnClickListener listener;
    private View.OnTouchListener touchListener;
    private Animation buttonUp;

    public GeneroAdapter(Genero [] generos)
    {
        this.generos = generos;
    }

    public void startAnimation(Animation buttonUp) {
        this.buttonUp = buttonUp;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView lblNombreGeneroPag;
        ConstraintLayout clImgFondoGenero;

        public ViewHolder(@NonNull View item) {
            super(item);
            lblNombreGeneroPag = item.findViewById(R.id.lblNombreGeneroPag);
            clImgFondoGenero = item.findViewById(R.id.clImgFondoGenero);
        }

        void bindGenero(Genero genero){

            lblNombreGeneroPag.setText(genero.getNombre());
            BitmapDrawable menuGeneroUrl = new BitmapDrawable("/data/data/com.example.madu_project/files/images/"+ genero.getImagenMenu());
            clImgFondoGenero.setBackground(menuGeneroUrl);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.genero_page_item,parent,false);

        item.setOnClickListener(this);

        return new ViewHolder(item);
    }


    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bindGenero(generos[position]);
    }

    public int getItemCount()
    {
        return generos.length;
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


    public void setOnTouchListener(View.OnTouchListener touchListener)
    {
        this.touchListener = touchListener;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(touchListener != null){
            touchListener.onTouch(view,motionEvent);
        }

        return false;
    }


}
