package com.example.efaa.iee;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ClaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_clase, parent, false);
        return new ClaseViewHolder(v);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ClaseViewHolder holder, int position) {
        //La clase que se va a usar, sacado de la posicion
        Clase clase = lista.get(position);
        //Seteando datos, tomando el holder.
        holder.codigo.setText(clase.CODIGO);
        holder.indice.setText(String.valueOf(clase.INDICE));
        holder.uv.setText(String.valueOf(clase.UV));
        holder.nombre.setText(clase.NOMBRE);
        holder.nombre.setChecked(clase.CURSADA);

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
            indice = (EditText) v.findViewById(R.id.editText);
            nombre = (CheckBox) v.findViewById(R.id.Nombre_de_Clase);
            codigo = (TextView) v.findViewById(R.id.Codigo);
            uv = (TextView) v.findViewById(R.id.UV);

        }
    }


}