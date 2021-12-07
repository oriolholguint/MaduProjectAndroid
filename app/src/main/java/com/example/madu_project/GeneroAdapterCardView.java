package com.example.madu_project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

public class GeneroAdapterCardView extends PagerAdapter {

    private Context context;
    private Genero [] generos;

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
                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

// 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("mensaje")
                        .setTitle(nombre);

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                AlertDialog dialog = builder.create();
                dialog.show();
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
