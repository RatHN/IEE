package com.example.efaa.iee;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.efaa.iee.dataSource.Columnas;

import java.util.ArrayList;

/**
 * Created by Neri Ortez on 09/05/2016.
 */
public class ClassCursorAdapter extends SimpleCursorAdapter {

    Cursor items;
    SQLiteDatabase dob;
    private int mSelectedPosition;
    private Context context;
    private int layout;

    public ClassCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, SQLiteDatabase db) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
        dob = db;
    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent) {

//        Cursor c = getCursor();

        final LayoutInflater inflater = LayoutInflater.from(context);
        //View v = inflater.inflate(layout, parent, false);


        String clase = null;
        String codigo = null;
        String porCursar = null;
        int uv = 0;
        int indice = 0;
        try {
            clase = c.getString(c.getColumnIndexOrThrow(Columnas.NOMBRE));
            codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
            porCursar = c.getString(c.getColumnIndexOrThrow(Columnas.PORcURSAR));
            uv = c.getInt(c.getColumnIndexOrThrow(Columnas.UV));
            try {
                indice = c.getInt(c.getColumnIndex(Columnas.INDICE));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("IEI-UNAH_VS", "Hubo un problema con el indice de la clase: " + codigo);
                indice = 80;
            }
            String cursada = c.getString(c.getColumnIndexOrThrow(Columnas.CURSADA));
            String disponible = c.getString(c.getColumnIndexOrThrow(Columnas.DISPONIBLE));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ArrayList<Clas> porcursar = dataSource.crearListaDependenciasAndParse(porCursar, dob, context);
//            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();

        Clase clase1 = new Clase(clase, codigo, porcursar, uv, indice);
//        return (View) new ClaseView(context);
        return new ClassFragment(clase1).getView();


//        int nameCol = c.getColumnIndex("phrase");
//        String name = c.getString(nameCol);
//
//        TextView name_text = (TextView) v.findViewById(R.id.phrase);
//        if (name_text != null) {
//            name_text.setText(name);
//        }
//        return v;
    }

    @Override
    public void bindView(View v, Context context, Cursor c) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        //View v = inflater.inflate(layout, parent, false);


        String clase = null;
        String codigo = null;
        String porCursar = null;
        int uv = 0;
        int indice = 0;
        try {
            clase = c.getString(c.getColumnIndexOrThrow(Columnas.NOMBRE));
            codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
            porCursar = c.getString(c.getColumnIndexOrThrow(Columnas.PORcURSAR));
            uv = c.getInt(c.getColumnIndexOrThrow(Columnas.UV));
            try {
                indice = c.getInt(c.getColumnIndex(Columnas.INDICE));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("IEI-UNAH_VS", "Hubo un problema con el indice de la clase: " + codigo);
                indice = 80;
            }
            String cursada = c.getString(c.getColumnIndexOrThrow(Columnas.CURSADA));
            String disponible = c.getString(c.getColumnIndexOrThrow(Columnas.DISPONIBLE));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ArrayList<Clas> porcursar = dataSource.crearListaDependenciasAndParse(porCursar, dob, context);
//            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();

        Clase clase1 = new Clase(clase, codigo, porcursar, uv, indice);
        v = new ClassFragment(clase1).getView();


//        int position = c.getPosition();
//        if (mSelectedPosition == position) {
//            v.setBackgroundResource(R.drawable.listviewbackground);
//            v.getBackground().setDither(true);
//        } else {
//            v.setBackgroundColor(Color.BLACK);
//        }

    }


    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();

    }

//    public ClassCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
//        super(context, layout, c, from, to, flags);
//    }

//    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstaceState){
//        //Inflar el layout
//        ViewGroup rootView = (ViewGroup) layoutInflater.inflate(R.layout.fragment_tab,container, false);
//        //Proveer una base de datos para el adaptador
//        ArrayList<Clase> datsource = new dataSource().queryPasadasODisponibles(dob,Columnas.DISPONIBLE, "1", getActivity());
//        //Crear adaptador
//        ArrayAdapter<Clase> adapter = new ArrayAdapter<Clase>(getActivity(),)
//    }
}
