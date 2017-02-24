package com.example.efaa.iee.adaptadores;

/**
 * Created by Neri Ortez on 21/05/2016.
 */

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.efaa.iee.Materias.Clase;
import com.example.efaa.iee.R;

import java.util.ArrayList;
import java.util.Random;





/**
 * Adaptador para mostrar las que ya se han seleccionado de las disponibles en el ChuncheActivity
 */
public class AdaptadorDeInfo extends RecyclerView.Adapter<AdaptadorDeInfo.InfoViewHolder> {
    public ArrayList<Clase> LISTA;

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
    public interface CommInfo {
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
