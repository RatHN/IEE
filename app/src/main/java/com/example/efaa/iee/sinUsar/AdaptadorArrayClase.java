package com.example.efaa.iee.sinUsar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.efaa.iee.Materias.Clase;
import com.example.efaa.iee.R;

import java.util.List;

/**
 * Created by Neri Ortez on 09/05/2016.
 */
public class AdaptadorArrayClase extends ArrayAdapter<Clase> {

    Cursor items;
    SQLiteDatabase dob;
    private int mSelectedPosition;
    private Context context;
    private List<Clase> objects;
    private int layout;
    private int i;
    private View v;
    private ViewGroup parent;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public AdaptadorArrayClase(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public AdaptadorArrayClase(Context context, List<Clase> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }
//    /**
//     * @param context
//     * @param layout
//     * @param c
//     * @param from
//     * @param to
//     * @param db
//     */
//    public ClassCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, SQLiteDatabase db) {
//        super(context, layout, c, from, to);
//        this.context = context;
//        this.layout = layout;
//        dob = db;
//    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        this.i = position;
//        this.v = v;
//        this.parent = parent;

//        Cursor c = getCursor();

        final LayoutInflater inflater = LayoutInflater.from(context);
        View item = v;

        //Comprobando si el View no existe
        if (null == v) {
            //Si no existe, entonces inflarlo con fragmento
            item = inflater.inflate(
                    R.layout.fragment_blank,
                    parent,
                    false);
        }
        //Obteniendo la clase
        Clase clase1 = getItem(position);

        //Obteniendo elementos
        CheckBox Nombre = (CheckBox) item.findViewById(R.id.Nombre_de_Clase);
        TextView Codigo = (TextView) item.findViewById(R.id.Codigo);
        TextView UV = (TextView) item.findViewById(R.id.UV);
        EditText Indice = (EditText) item.findViewById(R.id.editText);

        //Seteando
        Nombre.setText(clase1.NOMBRE);
        Codigo.setText(clase1.CODIGO);
        UV.setText(String.valueOf(clase1.UV));
        Indice.setText(String.valueOf(clase1.INDICE));


//        String clase = null;
//        String codigo = null;
//        String porCursar = null;
//        int uv = 0;
//        int indice = 0;
//        try {
//            clase = c.getString(c.getColumnIndexOrThrow(Columnas.NOMBRE));
//            codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
//            porCursar = c.getString(c.getColumnIndexOrThrow(Columnas.PORcURSAR));
//            uv = c.getInt(c.getColumnIndexOrThrow(Columnas.UV));
//            try {
//                indice = c.getInt(c.getColumnIndex(Columnas.INDICE));
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("IEI-UNAH_VS", "Hubo un problema con el indice de la clase: " + codigo);
//                indice = 80;
//            }
//            String cursada = c.getString(c.getColumnIndexOrThrow(Columnas.CURSADA));
//            String disponible = c.getString(c.getColumnIndexOrThrow(Columnas.DISPONIBLE));
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        ArrayList<Clas> porcursar = dataSource.crearListaDependenciasAndParse(porCursar, dob, context);
////            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();
//
//        Clase clase1 = new Clase(clase, codigo, porcursar, uv, indice);
//        dob.releaseReference();
//        return (View) new ClaseView(context);
//        return new ClassFragment(clase1).getView();


//        int nameCol = c.getColumnIndex("phrase");
//        String name = c.getString(nameCol);
//
//        TextView name_text = (TextView) v.findViewById(R.id.phrase);
//        if (name_text != null) {
//            name_text.setText(name);
//        }
//        return v;
        return item; //DEvolvemos el item con los datos de la clase ya puestos.
    }


//    public void bindView(View v, Context context, Cursor c) {
//        final LayoutInflater inflater = LayoutInflater.from(context);
//        //View v = inflater.inflate(layout, parent, false);
//
//
//        String clase = null;
//        String codigo = null;
//        String porCursar = null;
//        int uv = 0;
//        int indice = 0;
//        try {
//            clase = c.getString(c.getColumnIndexOrThrow(Columnas.NOMBRE));
//            codigo = c.getString(c.getColumnIndexOrThrow(Columnas.CODIGO));
//            porCursar = c.getString(c.getColumnIndexOrThrow(Columnas.PORcURSAR));
//            uv = c.getInt(c.getColumnIndexOrThrow(Columnas.UV));
//            try {
//                indice = c.getInt(c.getColumnIndex(Columnas.INDICE));
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("IEI-UNAH_VS", "Hubo un problema con el indice de la clase: " + codigo);
//                indice = 80;
//            }
//            String cursada = c.getString(c.getColumnIndexOrThrow(Columnas.CURSADA));
//            String disponible = c.getString(c.getColumnIndexOrThrow(Columnas.DISPONIBLE));
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        ArrayList<Clas> porcursar = dataSource.crearListaDependenciasAndParse(porCursar, dob, context);
////            ArrayList<Clas> DEPENDENCIAS = new ArrayList<>();
//
//        Clase clase1 = new Clase(clase, codigo, porcursar, uv, indice);
//        v = new ClassFragment(clase1).getView();
//
//
////        int position = c.getPosition();
////        if (mSelectedPosition == position) {
////            v.setBackgroundResource(R.drawable.listviewbackground);
////            v.getBackground().setDither(true);
////        } else {
////            v.setBackgroundColor(Color.BLACK);
////        }
//
//    }


    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();

    }

}
