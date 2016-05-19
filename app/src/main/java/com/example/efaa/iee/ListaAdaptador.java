package com.example.efaa.iee;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Neri Ortez on 19/05/2016.
 */

public class ListaAdaptador extends ArrayAdapter {
    public ListaAdaptador(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        return super.getView(position, v, parent);
    }
}
