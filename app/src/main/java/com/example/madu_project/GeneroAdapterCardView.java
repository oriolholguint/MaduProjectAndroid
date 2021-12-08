package com.example.madu_project;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class GeneroAdapterCardView extends PagerAdapter {

    private Context context;
    private Genero [] generos;
    private MainActivity activity;
    private Fragment fragment;


    public GeneroAdapterCardView(Context context, Genero[] generos) {
        this.context = context;
        this.generos = generos;
    }


    @Override
    public int getCount() {
        return generos.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public Object instantiateItem( ViewGroup container, int position)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.genero_page_item,container,false);

        TextView lblNombreGeneroPag = view.findViewById(R.id.lblNombreGeneroPag);
        //ImageView imgGenero = view.findViewById(R.id.imgGenero);

        Genero genero = generos[position];
        String nombre = genero.getNombre();

        lblNombreGeneroPag.setText(nombre);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putSerializable("genero",genero);
                fragment.getParentFragmentManager().setFragmentResult("genero",bundle);

                FragmentManager mg = fragment.getFragmentManager();
                FragmentTransaction fragmentTransaction = mg.beginTransaction();

                FragmentPreguntas fragmentPreguntas = new FragmentPreguntas();
                FragmentBotones fragmentBotones = new FragmentBotones();
                fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentBotones);
                fragmentTransaction.commit();




            }
        });

        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View)object);
    }




}
