package com.example.efaa.iee;

/**
 * Created by Neri Ortez on 09/05/2016.
 */

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Clase>>,
        ClaseRecyclerAdaptador.EscuchadorDeInteraccion{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ClaseRecyclerAdaptador.EscuchadorDeInteraccion meEscucha;

    AdaptadorArrayClase adaptador;
    ListView lista;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager lManager;
    Bundle ARGS;
    ClaseRecyclerAdaptador cAdaptador;

    SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
    public View.OnClickListener checkBoxClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.Nombre_de_Clase);
            LinearLayout e = (LinearLayout) view.getParent();
            TextView codigo = (TextView) view.findViewById(R.id.Codigo);
            String cod = (String) codigo.getText();
            String dato = null;

            if (checkBox.isChecked()) {
                dato = "1";
            } else {
                dato = "0";
            }
            String result = new dataSource().insertarUnoOCero(dob, cod, dataSource.Columnas.CURSADA, dato,
                    new dataSource().queryCrearClase(dob, cod, view.getContext()), view.getContext());
            if (result == "-1") {
                checkBox.setChecked(true);
            }

//        ClassFragment fra = getShe
            else if ((result != "0" || result != "1")
                    && getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

            }
            dob.releaseReference();


        }
    };

    String COLUMNA;



    public PlaceHolderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceHolderFragment newInstance(int sectionNumber) {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void run(View parent) {
        //Conseguimos el array
        ArrayList array = new ArrayList();
        array = dataSource.queryPasadasODisponibles(dob, COLUMNA, "1", getContext());

        //Creamos el adaptador para ese array
//       adaptador = new AdaptadorArrayClase(getContext(), array);
        cAdaptador = new ClaseRecyclerAdaptador(array);
        recyclerView.setAdapter(cAdaptador);
    }


    @Override
    public void onStart(){
        super.onStart();
        getLoaderManager().initLoader(0, ARGS, this).forceLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflar la pestaña con el fragmento que contiene el ListView o el RecyclerView
//        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        View rootView = inflater.inflate(R.layout.reciclador_tab, container, false);

        //Conseguir el listView o RecyclerView
//        lista = (ListView) rootView.findViewById(R.id.listViewFragment);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.reciclador);


        recyclerView.setHasFixedSize(true);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        //Establecer el tipo de vista: Cursadas, Disponibles o especial
        String columna = dataSource.Columnas.DISPONIBLE;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                columna = dataSource.Columnas.CURSADA;
        }
        COLUMNA = columna;

//        String columns[] = new String[]{"_id", dataSource.Columnas.NOMBRE, dataSource.Columnas.CODIGO,
//                dataSource.Columnas.PORcURSAR, CURSADA, DISPONIBLE,
//                dataSource.Columnas.UV, dataSource.Columnas.INDICE};


//        String selection = columna + " = " + "1" + " ";//WHERE author = ?


        //Creamos un adaptador vacio
        ArrayList<Clase> ar = new ArrayList<>();
        ar.add(new Clase("NADA", "NADA", null, getContext()));
        cAdaptador = new ClaseRecyclerAdaptador(ar);


        //Establecemos adaptadores
//        lista.setAdapter(adapter);
        lManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(lManager);
        recyclerView.setAdapter(cAdaptador);


        //Creamos los listener para clicks
        RecyclerView.OnItemTouchListener escucha = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    Snackbar.make(rv, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    return;
                                }
                            }).show();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };


        //Seteamos esos escuchas
//        lista.setOnItemClickListener(listener);
        recyclerView.addOnItemTouchListener(escucha);
//        recyclerView.addView(progressBar);


        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        ARGS = new Bundle();
        ARGS.putString("COLUMNA", columna);

        return rootView;
    }



    // Called when a new Loader needs to be created

    @Override
    public android.support.v4.content.Loader<ArrayList<Clase>> onCreateLoader(int id, Bundle args) {
        final String columna = args.getString("COLUMNA");

        return new android.support.v4.content.AsyncTaskLoader<ArrayList<Clase>>(getActivity()) {
            @Override
            public ArrayList<Clase> loadInBackground() {
                ArrayList array = new ArrayList();
                array = dataSource.queryPasadasODisponibles(dob, COLUMNA, "1", getContext());
                return array;
            }
        };

    }

    /**
 * Soporte con arraylist
 */
    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Clase>> loader,
                               ArrayList<Clase> data) {
//        if (cAdaptador.getItemCount() != adaptador1.getItemCount()){
            recyclerView.setAdapter(new ClaseRecyclerAdaptador(data));
//        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Clase>> loader) {
        recyclerView.setAdapter(null);
    }


    @Override
    public void Interaccion(ClaseRecyclerAdaptador.ClaseViewHolder holder) {

    }

    @Override
    public void Interaccion(int position) {

    }

    @Override
    public void Interaccion(View view) {
        recyclerView.removeView(view);
    }
}

