package com.example.madu_project.introduccion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.madu_project.R;

public class AdapterIntroduccion extends PagerAdapter
{
    Context context;

    int imagenes[] = {
            R.drawable.intro_music,
            R.drawable.intro_resources_image_sound,
            R.drawable.intro_dificulty,
            R.drawable.intro_ranking
    };

    int titulos[] = {
            R.string.titulo_introduccion_1,
            R.string.titulo_introduccion_2,
            R.string.titulo_introduccion_3,
            R.string.titulo_introduccion_4
    };

    int descripciones[] = {
            R.string.introduccion_1,
            R.string.introduccion_2,
            R.string.introduccion_3,
            R.string.introduccion_4
    };

    public AdapterIntroduccion(Context context)
    {
        this.context = context;

    }

    @Override
    public int getCount()
    {
        return titulos.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_introduccion, container, false);

        ImageView imagen_introduccion_1 = (ImageView) view.findViewById(R.id.imagen_introduccion);
        TextView titulo_introduccion_1 = (TextView) view.findViewById(R.id.titulo_introduccion);
        TextView descripcion_introduccion_1 = (TextView) view.findViewById(R.id.descripcion_introduccion);

        imagen_introduccion_1.setImageResource(imagenes[position]);
        titulo_introduccion_1.setText(titulos[position]);
        descripcion_introduccion_1.setText(descripciones[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((LinearLayout) object);
    }
}
