package com.example.efaa.iee;

/**
 * Created by Neri Ortez on 21/05/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adaptador para mostrar las clases de disponibles del lado de las disponibles
 */
class AdaptadorDeDisponibles extends RecyclerView.Adapter<AdaptadorDeDisponibles.DisponibleViewHolder> {
    ArrayList<Clase> LISTA;
    Comm iPadre;

    public AdaptadorDeDisponibles(ArrayList<Clase> data) {
        LISTA = data;
    }

    @Override
    public DisponibleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_layout, parent, false);
        return new DisponibleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DisponibleViewHolder holder, int position) {
        Clase clase = LISTA.get(position);
        holder.CLASE = clase;
        holder.codigo.setText(clase.CODIGO);
        holder.nombre.setText(clase.NOMBRE);
        holder.uv.setText("Unidades Valorativas: " + String.valueOf(clase.UV));

    }

    @Override
    public int getItemCount() {
        return LISTA.size();
    }

    class DisponibleViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView codigo;
        TextView uv;
        Comm iPadre;
        Clase CLASE;

        public DisponibleViewHolder(View view) {
            super(view);
            iPadre = ((Comm) view.getContext());
            nombre = ((TextView) view.findViewById(R.id.Nombre_de_Clase));
            codigo = ((TextView) view.findViewById(R.id.Codigo));
            uv = (TextView) view.findViewById(R.id.UV);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iPadre.agregarAInfo(CLASE, LISTA.lastIndexOf(CLASE));
                }
            });
        }
    }

    /**
     * Interfas de comunicacion de este adaptador con su padre
     */
    interface Comm {
        void agregarAInfo(Clase clase, int position);
    }

}


/**
 * Adaptador para mostrar las que ya se han seleccionado de las disponibles
 */
class AdaptadorDeInfo extends RecyclerView.Adapter<AdaptadorDeInfo.InfoViewHolder> {
    ArrayList<Clase> LISTA;

    public AdaptadorDeInfo(ArrayList<Clase> array) {
        LISTA = array;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InfoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        Clase clase = LISTA.get(position);
        holder.CLASE = clase;
        holder.codigo.setText(clase.CODIGO);
        holder.nombre.setText(clase.NOMBRE);
        holder.uv.setText(String.valueOf(clase.UV));

    }

    @Override
    public int getItemCount() {
        return LISTA.size();
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView codigo;
        TextView uv;
        CommInfo iPadre;
        Clase CLASE;

        public InfoViewHolder(View view) {
            super(view);
            iPadre = ((CommInfo) view.getContext());
            nombre = ((TextView) view.findViewById(R.id.Nombre_de_Clase));
            codigo = ((TextView) view.findViewById(R.id.Codigo));
            uv = (TextView) view.findViewById(R.id.UV);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iPadre.agregarADisponibles(CLASE, LISTA.lastIndexOf(CLASE));
                }
            });
        }
    }

    /**
     * Interfas de comunicacion de este adaptador con su padre
     */
    interface CommInfo {
        void agregarADisponibles(Clase clase, int position);
    }
}
