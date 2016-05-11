package com.example.efaa.iee;

/**
 * Created by Neri Ortez on 09/05/2016.
 */

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList> {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    AdaptadorArrayClase adaptador;
    ListView lista;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager lManager;
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


    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void run() {
        //Conseguimos el array
        ArrayList array = new ArrayList();
        array = dataSource.queryPasadasODisponibles(dob, COLUMNA, "1", getContext());

        //Creamos el adaptador para ese array
//       adaptador = new AdaptadorArrayClase(getContext(), array);
        cAdaptador = new ClaseRecyclerAdaptador(array);
        recyclerView.setAdapter(cAdaptador);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
//        recyclerView(progressBar);


        //Inflar la pestaña con el fragmento que contiene el ListView o el RecyclerView
//        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        View rootView = inflater.inflate(R.layout.reciclador_tab, container, false);

        //Conseguir el listView o RecyclerView
//        lista = (ListView) rootView.findViewById(R.id.listViewFragment);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.reciclador);

        recyclerView.setHasFixedSize(true);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        //Establecer el tipo de vista: Cursadas, Disponibles o especial
        String DISPONIBLE = dataSource.Columnas.DISPONIBLE;
        String columna = DISPONIBLE;
        String CURSADA = dataSource.Columnas.CURSADA;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                columna = CURSADA;
        }
        COLUMNA = columna;

//        String columns[] = new String[]{"_id", dataSource.Columnas.NOMBRE, dataSource.Columnas.CODIGO,
//                dataSource.Columnas.PORcURSAR, CURSADA, DISPONIBLE,
//                dataSource.Columnas.UV, dataSource.Columnas.INDICE};


//        String selection = columna + " = " + "1" + " ";//WHERE author = ?


        //Establecemos adaptadores
//        lista.setAdapter(adapter);
        lManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(lManager);
        recyclerView.setAdapter(cAdaptador);


        //Creamos los listener para clicks
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        };
        View.OnClickListener escucha = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                return;
                            }
                        }).show();
            }
        };


        //Seteamos esos escuchas
//        lista.setOnItemClickListener(listener);
        recyclerView.setOnClickListener(escucha);
        recyclerView.addView(progressBar);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        Bundle bundle1 = new Bundle();
        bundle1.putString("COLUMNA", columna);
        getLoaderManager().initLoader(0, bundle1,
                (android.support.v4.app.LoaderManager.LoaderCallbacks<Object>) getContext());
        return rootView;
    }



    // Called when a new Loader needs to be created
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        final String columna = args.getString("COLUMNA");

        return new AsyncTaskLoader<ArrayList>(getContext()) {
            @Override
            public ArrayList<Clase> loadInBackground() {
                ArrayList array = new ArrayList();
                array = dataSource.queryPasadasODisponibles(dob, COLUMNA, "1", getContext());
                return array;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList> loader, ArrayList data) {
        cAdaptador = new ClaseRecyclerAdaptador(data);
        recyclerView.swapAdapter(cAdaptador, true);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<ArrayList> loader) {

    }

}

