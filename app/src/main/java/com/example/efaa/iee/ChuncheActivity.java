package com.example.efaa.iee;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.efaa.iee.Materias.Clase;
import com.example.efaa.iee.Materias.CustomActivities;
import com.example.efaa.iee.adaptadores.AdaptadorDeDisponibles;
import com.example.efaa.iee.adaptadores.AdaptadorDeInfo;

import java.util.ArrayList;

public class ChuncheActivity extends CustomActivities implements AdaptadorDeDisponibles.Comm, AdaptadorDeInfo.CommInfo {
    RecyclerView Disponibles;
    AdaptadorDeDisponibles dAdapter;

    RecyclerView Info;
    AdaptadorDeInfo iAdapter;

    RecyclerView.LayoutManager lManager;
    ArrayList<String> array;
    ArrayList<Clase> arrayClases;
    int totalIndice;
    int totalUV;
    TextView tu;
    boolean inicio = false;
    boolean reprobadas_aparecen;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fd", 1);
        /*for (Clase clase1 :
                dAdapter.LISTA) {
            arra
        }
        outState.putStringArray("Array", dAdapter.LISTA.toArray(array));*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        array = getIntent().getExtras().getStringArrayList("Array");
        totalIndice = getIntent().getExtras().getInt("totalIndice");
        totalUV = getIntent().getExtras().getInt("totalUV");
        reprobadas_aparecen = getIntent().getExtras().getBoolean("reprobadas_aparecen");

        if (!inicio) {
            String f = getString(R.string.chunche_explanation_1) + (String.valueOf(totalUV)) +
                    getString(R.string.chunche_explanation_2) +
                    getString(R.string.chunche_explanation_3) + array;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.chunche_message_title).setMessage(Html.fromHtml(f))
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            Snackbar.make(tu, R.string.snackBar_message, Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .create()
                    .show();
        }


        setContentView(R.layout.activity_cunche);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String f = ChuncheActivity.this.getString(R.string.chunche_fab_message);
                    new AlertDialog.Builder(view.getContext()).setMessage(Html.fromHtml(f)).setTitle(
                            R.string.chunche_informacion).create().show();
                }
            });
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Info = (RecyclerView) findViewById(R.id.recyclerInfo);
//        Info.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        Info.setHasFixedSize(false);
        Info.setLayoutManager(layoutManager);

//        Info.setLayoutManager(new LinearLayoutManager(this));
        iAdapter = new AdaptadorDeInfo(new ArrayList<Clase>());

        Disponibles = ((RecyclerView) findViewById(R.id.recyclerDisponibles));
//        Disponibles.setHasFixedSize(true);

//        lManager = new LinearLayoutManager(this);
        Disponibles.setLayoutManager(new LinearLayoutManager(this));
//        Disponibles.setLayoutManager(lManager);

        dAdapter = new AdaptadorDeDisponibles(new ArrayList<Clase>());
        Disponibles.setAdapter(dAdapter);
//        ListView expan = (ListView) findViewById(R.id.expan);
//        expan.setAdapter(new ArrayAdapter<String>(array));
//        expan.setAdapter(new ListaAdaptador(getApplicationContext(), array));

        /*TextView ti = (TextView) findViewById(R.id.t);
        ti.setText("Indice= " + totalIndice );*/
        tu = (TextView) findViewById(R.id.tu);
        assert tu != null;
        tu.setText(R.string.UVDisponibles);
        tu.setText(String.valueOf(tu.getText()) + "\n" + String.valueOf(totalUV));
        tu.setTextSize(28);
        if (Info.getAdapter() == null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public Loader<ArrayList<Clase>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Clase>>(this) {
            @Override
            public void onStartLoading() {
                if (Info.getAdapter() != null) return;
                forceLoad();
            }

            @Override
            public ArrayList<Clase> loadInBackground() {
                ArrayList<Clase> array;
//                SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
                array = DataSource.queryPasadasODisponibles(dataSource.Columnas.DISPONIBLE, "1", ChuncheActivity.this);


                if (reprobadas_aparecen) {
                    ArrayList<Clase> noDisponibles = DataSource.queryDisponiblesConReprobadas(ChuncheActivity.this);
                    for (Clase c :
                            noDisponibles) {
                        Log.i("ChuncheActivity", "loadInBackground: Reprobada: " + c.toString());
                    }
                    return noDisponibles;
                }

//                dob.releaseReference();
                return array;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Clase>> loader, ArrayList<Clase> data) {
        dAdapter = new AdaptadorDeDisponibles(data);
        dAdapter.notifyDataSetChanged();
        Disponibles.swapAdapter(dAdapter, true);
//        recyclerDisponibles.setAdapter(dAdapter);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Clase>> loader) {
        Disponibles.setAdapter(null);
    }

    @Override
    public void agregarAInfo(Clase clase, int pos) {
        if (clase.UV <= totalUV) {
            totalUV -= clase.UV;
            AdaptadorDeDisponibles adapter = (AdaptadorDeDisponibles) Disponibles.getAdapter();
            Boolean t = adapter.LISTA.remove(clase);
            Disponibles.getAdapter().notifyItemRemoved(pos);


            tu.setText(R.string.UVDisponibles);
            tu.setText(String.valueOf(tu.getText()) + "\n" + String.valueOf(totalUV));

            for (Clase clase1 :
                    adapter.LISTA) {
                //            if (clase1.UV > totalUV) clase1.CURSADA = true;
            }

            if (Info.getAdapter() != null) {
                ((AdaptadorDeInfo) Info.getAdapter()).LISTA.add(clase);
                Info.getAdapter().notifyItemInserted(((AdaptadorDeInfo) Info.getAdapter()).LISTA.lastIndexOf(clase));
            } else {
                ArrayList<Clase> array = new ArrayList<>();
                array.add(clase);
                Info.setAdapter(new AdaptadorDeInfo(array));
            }
        } else Snackbar.make(tu, "No se puede che", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void agregarADisponibles(Clase clase, int pos) {
        if (true) {
            Boolean t = ((AdaptadorDeInfo) Info.getAdapter()).LISTA.remove(clase);
            Info.getAdapter().notifyItemRemoved(pos);

            totalUV += clase.UV;
            tu.setText(R.string.UVDisponibles);
            tu.setText(String.valueOf(tu.getText()) + "\n"
                    + String.valueOf(totalUV));

            for (Clase clase1 :
                    ((AdaptadorDeInfo) Info.getAdapter()).LISTA) {
                //            if (clase1.UV > totalUV) clase1.CURSADA = true;
            }

            if (Disponibles.getAdapter() != null) {
                ((AdaptadorDeDisponibles) Disponibles.getAdapter()).LISTA.add(clase);
                Disponibles.getAdapter().notifyItemInserted(((AdaptadorDeDisponibles) Disponibles.getAdapter()).LISTA.lastIndexOf(clase));
            } else {
                ArrayList<Clase> array = new ArrayList<>();
                array.add(clase);
                Disponibles.setAdapter(new AdaptadorDeDisponibles(array));
            }
        }
    }
}
