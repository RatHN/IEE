package com.example.efaa.iee.adaptadores;

/**
 * Created by Neri Ortez on 24/02/2017.
 */

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.efaa.iee.Materias.Clase;
import com.example.efaa.iee.R;

import java.util.ArrayList;

/**
 * Adaptador para mostrar las clases de disponibles del lado de las disponibles en el ChuncheActivity
 */
public class AdaptadorDeDisponibles extends RecyclerView.Adapter<AdaptadorDeDisponibles.DisponibleViewHolder> {
    public ArrayList<Clase> LISTA;


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
    public interface Comm {
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
                    v.performHapticFeedback(0x4);
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