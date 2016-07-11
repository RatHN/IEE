package com.example.efaa.iee;

/**
 * Created by Neri Ortez on 21/05/2016.
 */

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
        holder.enab.setEnabled(!clase.CURSADA);

    }

    @Override
    public int getItemCount() {
        return LISTA.size();
    }

    /**
     * Inhabilitar una vista en base a su posicion
     */
    public void inhabilitar(int pos) {

    }

    /**
     * Interfas de comunicacion de este adaptador con su padre
     */
    interface Comm {
        void agregarAInfo(Clase clase, int position);
    }

    class DisponibleViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView codigo;
        TextView uv;
        Comm iPadre;
        Clase CLASE;
        View enab;

        public DisponibleViewHolder(View view) {
            super(view);
            enab = view;
            iPadre = ((Comm) view.getContext());
            nombre = ((TextView) view.findViewById(R.id.Nombre_de_Clase));
            codigo = ((TextView) view.findViewById(R.id.Codigo));
            uv = (TextView) view.findViewById(R.id.UV);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK);
                    iPadre.agregarAInfo(CLASE, LISTA.lastIndexOf(CLASE));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (v.getElevation() != 10) {
                            (v).setElevation(10);
                        } else {
                            v.setElevation(4);
                        }
                    }
                    return true;
                }
            });
        }

        public void setEnablear(Boolean dale) {

        }
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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_layout, parent, false);
        int[] array = new int[]
                {Color.parseColor("lime"),
                        /*Color.parseColor("aqua"),
                        Color.GRAY,
                        Color.parseColor("silver"),
                        Color.parseColor("olive"),
                        Color.parseColor("teal"),
                        Color.LTGRAY,
                        Color.YELLOW,
                        Color.WHITE*/};

        int rnd = new Random().nextInt(array.length);

        ((CardView) v).setCardBackgroundColor(array[rnd]);
        return new InfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        Clase clase = LISTA.get(position);
        holder.CLASE = clase;
        holder.nombre.setText(clase.NOMBRE);
        holder.uv.setText("Unidades Valorativas: " + String.valueOf(clase.UV));
        if (holder.codigo != null) {
//            holder.codigo.setText(clase.CODIGO);
            holder.codigo.setText(null);
//            holder.uv.setText(String.valueOf(clase.UV));
//            holder.uv.setText(null);
        }
    }

    @Override
    public int getItemCount() {
        return LISTA.size();
    }

    /**
     * Interfas de comunicacion de este adaptador con su padre
     */
    interface CommInfo {
        void agregarADisponibles(Clase clase, int position);
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
//            codigo = null;
            uv = (TextView) view.findViewById(R.id.UV);
//            uv = null;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iPadre.agregarADisponibles(CLASE, LISTA.lastIndexOf(CLASE));

                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (v.getElevation() != 10) {
                            (v).setElevation(10);
                        } else {
                            v.setElevation(2);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
