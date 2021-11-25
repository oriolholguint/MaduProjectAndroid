package com.example.madu_project.idioma;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madu_project.R;

public class IdiomasAdapter extends RecyclerView.Adapter<IdiomasAdapter.ViewHolder> implements View.OnClickListener
{
    private Idioma [] idiomas;
    private View.OnClickListener listener;

    public IdiomasAdapter(Idioma[] idiomas)
    {
        this.idiomas = idiomas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageButton imageButton;

        public ViewHolder(@NonNull View item)
        {
            super(item);
            imageButton = item.findViewById(R.id.btnIdioma);
        }

        void bindIdiomas(Idioma idioma)
        {
            Bitmap bMap = BitmapFactory.decodeFile("/data/data/com.example.madu_project/files/images/" + idioma.getImageButton());
            imageButton.setImageBitmap(bMap);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.idioma_layout, parent, false);
        item.setOnClickListener(listener);

        return new ViewHolder(item);
    }

    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bindIdiomas(idiomas[position]);
    }

    public int getItemCount()
    {
        return idiomas.length;
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        if(listener != null)
        {
            listener.onClick(view);
        }
    }
}