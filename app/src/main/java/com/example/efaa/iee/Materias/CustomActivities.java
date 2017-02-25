package com.example.efaa.iee.Materias;

import android.support.v7.app.AppCompatActivity;

import com.example.efaa.iee.dataSource;

import java.util.ArrayList;

/**
 * Created by Neri Ortez on 25/02/2017.
 */
public abstract class CustomActivities extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Clase>>{
    dataSource DataSource;

    protected static final int GET_CLASSES_LOADER_CODE = 987;
    protected static final java.lang.String COLUMN_ARG = "columna_en_el_argumento_de_Loader";


    @Override
    protected void onPause() {
        super.onPause();
        DataSource.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataSource.open();
    }


    public dataSource getBD(){
        return this.DataSource;
    }

    void setDB(dataSource DataSource){
        this.DataSource = DataSource;
    }

}
