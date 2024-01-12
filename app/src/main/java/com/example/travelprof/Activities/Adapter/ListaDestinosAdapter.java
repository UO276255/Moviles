package com.example.travelprof.Activities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travelprof.Activities.DetalleDestinoActivity;
import com.example.travelprof.Activities.DetalleDestinoDesdePerfilActivity;
import com.example.travelprof.Activities.LoginActivity;
import com.example.travelprof.R;
import com.squareup.picasso.Picasso;
import com.example.travelprof.modelo.Destino;

import java.util.ArrayList;
import java.util.List;

public class ListaDestinosAdapter extends RecyclerView.Adapter<ListaDestinosAdapter.ViewHolder> {
    private List<Destino> listaOriginal;
    private List<Destino> copia;

    private Context context;

    boolean visible;
    public ListaDestinosAdapter(Context context, List<Destino> destinos) {
        this.listaOriginal = destinos;
        copia = new ArrayList<>();
        copia.addAll(listaOriginal);
        this.context = context;
    }

    public ListaDestinosAdapter(Context context, List<Destino> destinos,boolean visible) {
        this.listaOriginal = destinos;
        copia = new ArrayList<>();
        copia.addAll(listaOriginal);
        this.context = context;
        this.visible = visible;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.destino_recomendado, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Destino destino = listaOriginal.get(position);
        holder.destino_name.setText(destino.getNombre());
        holder.descripcionTextView.setText(destino.getDescripcion());
        Picasso.get().load(destino.getUrlImagen()).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetalleDestinoActivity.class);
                if(visible){
                    intent = new Intent(context, DetalleDestinoDesdePerfilActivity.class);

                }

                // Pasa los datos del destino seleccionado al intent
                intent.putExtra("nombre", destino.getNombre());
                intent.putExtra("descripcion", destino.getDescripcionDetalle());
                intent.putExtra("imagen", destino.getUrlImagen());
                // Inicia la actividad de detalle
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaOriginal.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView destino_name;
        public TextView descripcionTextView;
        public CheckBox box;
        public ImageView image;
        public ImageButton but;

        public ViewHolder(View itemView) {
            super(itemView);
            destino_name = itemView.findViewById(R.id.destino_rec);
            descripcionTextView = itemView.findViewById(R.id.descripcion);
            box = itemView.findViewById(R.id.Check);
            but = itemView.findViewById(R.id.calendario);
            image = itemView.findViewById(R.id.imageView11);
        }
    }

    public void filtrado(String filtro) {
        int longitud = filtro.length();
        if(longitud == 0){
            listaOriginal.clear();
            listaOriginal.addAll(copia);
        } else {
            listaOriginal.clear();
            for(Destino d : copia){
                if(d.getNombre().toLowerCase().contains(filtro.toLowerCase())){
                    listaOriginal.add(d);
                }
            }
        }
            notifyDataSetChanged();
        }

    public void filtrado(List<String> filtro) {
        int longitud = filtro.size();
        if(longitud == 0){
            listaOriginal.clear();
            listaOriginal.addAll(LoginActivity.dataBase.getDestinoDAO().getAll());
        } else {
            listaOriginal.clear();



                for (String g: filtro) {
                    List<Destino>parcial=LoginActivity.dataBase.getCategoriaDestinoDAO().findDestinoByCategory(g.toLowerCase());
                    for(Destino dest:parcial){
                        if(!(listaOriginal.contains(dest))){
                            listaOriginal.add(dest);
                        }
                    }
                }

        }
        notifyDataSetChanged();
    }

    }