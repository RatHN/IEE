package com.example.efaa.iee;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Neri Ortez on 09/05/2016.
 */
public class ClaseRecyclerAdaptador extends RecyclerView.Adapter<ClaseRecyclerAdaptador.ClaseViewHolder> {

    private Context context;
    private List<Clase> lista;


    public ClaseRecyclerAdaptador(List<Clase> Lista) {

            lista = Lista;

    }


    @Override
    public ClaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (lista.get(0).CODIGO.compareTo("NADA") == 0) {
            ProgressBar progressBar = new ProgressBar(parent.getContext());
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER));
            progressBar.setIndeterminate(true);
            v = progressBar;
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_clase, parent, false);
        }
        return new ClaseViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ClaseViewHolder holder, int position) {
        if (lista.get(0).CODIGO.compareTo("NADA") == 0) {
            return;
        } else {
            //La clase que se va a usar, sacado de la posicion
            Clase clase = lista.get(position);
            //Seteando datos, tomando el holder.
            holder.codigo.setText(clase.CODIGO);
            holder.indice.setText(String.valueOf(clase.INDICE));
            holder.uv.setText(String.valueOf(clase.UV));
            holder.nombre.setText(clase.NOMBRE);
            holder.nombre.setChecked(clase.CURSADA);
        }

    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ClaseViewHolder extends RecyclerView.ViewHolder {
        // Campos de la clase
        public EditText indice;
        public CheckBox nombre;
        public TextView codigo;
        public TextView uv;

        public ClaseViewHolder(View v) {
            super(v);
            if (lista.get(0).CODIGO.compareTo("NADA") == 0) {
                return;
            } else {
                indice = (EditText) v.findViewById(R.id.editText);
                nombre = (CheckBox) v.findViewById(R.id.Nombre_de_Clase);
                codigo = (TextView) v.findViewById(R.id.Codigo);
                uv = (TextView) v.findViewById(R.id.UV);
            }
        }
    }


}
