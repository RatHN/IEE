package com.example.efaa.iee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.google.android.gms.location.places.Place;

import java.util.List;

/**
 * Created by Neri Ortez on 09/05/2016.
 */
public class ClaseRecyclerAdaptador extends RecyclerView.Adapter<ClaseRecyclerAdaptador.ClaseViewHolder> {

    private Context context;
    private List<Clase> lista;

    public interface InterfaceEscuchador {
        void Escuchador(boolean actualizarCursada);
    }


    public ClaseRecyclerAdaptador(List<Clase> Lista) {
            lista = Lista;
    }


    @Override
    public ClaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (lista.get(0).CODIGO.compareTo("NADA") == 0) {
            ProgressBar progressBar = new ProgressBar(parent.getContext());
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
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


    public class ClaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos de la clase
        public EditText indice;
        public CheckBox nombre;
        public TextView codigo;
        public TextView uv;
        public CardView mCardView;
        public OnClickListener listener;
        public InterfaceEscuchador escuchador;


        public ClaseViewHolder(View view) {
            super(view);
            escuchador = (TabActivity) view.getContext();
            if (lista.get(0).CODIGO.compareTo("NADA") == 0) {
                return;
            } else {
                indice = (EditText) view.findViewById(R.id.editText);
                nombre = (CheckBox) view.findViewById(R.id.Nombre_de_Clase);
                codigo = (TextView) view.findViewById(R.id.Codigo);
                uv = (TextView) view.findViewById(R.id.UV);

                //Cambiamos la escucha y seteamos el escucha de click
                listener = this;
                nombre.setOnClickListener(listener);
                escuchador = (TabActivity) view.getContext();
            }
        }

        @Override
        public void onClick(View v) {
            remove_at(getAdapterPosition());
        }

        private void remove_at(int position) {

            lista.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, lista.size());
            escuchador.Escuchador(lista.get(position).CURSADA);
        }
    }


}
