package com.example.madu_project.introduccion;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.madu_project.R;
import com.example.madu_project.introduccion.AdapterIntroduccion;

public class FragmentTutorial extends Fragment
{
    ViewPager sliderIntroduccion;
    AdapterIntroduccion adapterIntroduccion;

    LinearLayout dotsLayout;
    TextView[] dots;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        //Slider
        sliderIntroduccion = (ViewPager) view.findViewById(R.id.slider_introduccion);

        //Linear Layout de circulos de estado
        dotsLayout = (LinearLayout) view.findViewById(R.id.dots_layout);

        //Adapter
        adapterIntroduccion = new AdapterIntroduccion(getContext());

        sliderIntroduccion.setAdapter(adapterIntroduccion);

        setUpIndicator(0);
        sliderIntroduccion.addOnPageChangeListener(viewListener);

        return view;
    }

    public void setUpIndicator(int position)
    {
        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for(int i = 0; i < dots.length; i++)
        {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive));
            dotsLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.active));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position)
        {
            setUpIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}