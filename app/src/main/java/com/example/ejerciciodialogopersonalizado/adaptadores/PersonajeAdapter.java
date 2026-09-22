package com.example.ejerciciodialogopersonalizado.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ejerciciodialogopersonalizado.model.Personaje;
import com.example.ejerciciodialogopersonalizado.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonajeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Personaje> listaPersonajes;

    public PersonajeAdapter(Context context, ArrayList<Personaje> listaPersonajes) {
        this.context = context;
        this.listaPersonajes = listaPersonajes;
    }

    @Override
    public int getCount() {
        return listaPersonajes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPersonajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaPersonajes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_personajes, parent, false);
        }

        Personaje personaje = listaPersonajes.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView tvNombre = convertView.findViewById(R.id.textView2);
        TextView tvTipo = convertView.findViewById(R.id.textView4);

        tvNombre.setText(personaje.getNombre());
        tvTipo.setText(personaje.getTipo());

        // Cargar imagen desde URL de internet usando Picasso
        if (personaje.getFotoUrl() != null && !personaje.getFotoUrl().isEmpty()) {
            Picasso.get()
                    .load(personaje.getFotoUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery) // Imagen por defecto mientras carga
                    .error(android.R.drawable.ic_delete)           // Imagen en caso de error
                    .into(imageView);
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        return convertView;
    }
}