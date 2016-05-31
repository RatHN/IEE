package com.example.efaa.iee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<PlaceHolderFragment> fragments = new ArrayList<>();
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO: Establecer el update a modo  que se actualize el fragmento contrario...
        // O mejor que se actualizen los dos, pero de manera suave.
        PlaceHolderFragment f = (PlaceHolderFragment) object;
        if (!(f != null && !f.flag)) {
            return super.getItemPosition(object);
        }

        else {
            f.update();
            return POSITION_NONE;
        }

    }
    public void update(boolean cursada, int pos) {
        Clase clase1 = null;
        for (Fragment frag : fragments) {

            if (!cursada && frag.toString().compareTo(dataSource.Columnas.DISPONIBLE) == 0){
                clase1 = ((ClaseRecyclerAdaptador) ((PlaceHolderFragment) frag).recyclerView.getAdapter()).LISTA.get(pos);
                clase1.CURSADA = true;
                ((ClaseRecyclerAdaptador) ((PlaceHolderFragment) frag).recyclerView.getAdapter()).LISTA.remove(pos);
                ((PlaceHolderFragment) frag).recyclerView.getAdapter().notifyItemRemoved(pos);
                for (Fragment f : fragments){
                    if (f.toString().contentEquals(frag.toString())){
                        f.getLoaderManager().initLoader(0, ((PlaceHolderFragment)f).ARGS, ((PlaceHolderFragment)f)).forceLoad();
                    } else {
                        f.getLoaderManager().initLoader(0, ((PlaceHolderFragment)f).ARGS, ((PlaceHolderFragment)f)).forceLoad();
                    }
                }
            }

            else if (cursada && frag.toString().compareTo(dataSource.Columnas.CURSADA) == 0){
                clase1 = ((ClaseRecyclerAdaptador) ((PlaceHolderFragment) frag).recyclerView.getAdapter()).LISTA.get(pos);
                clase1.CURSADA = false;
                ((ClaseRecyclerAdaptador) ((PlaceHolderFragment) frag).recyclerView.getAdapter()).LISTA.remove(pos);
                ((PlaceHolderFragment) frag).recyclerView.getAdapter().notifyItemRemoved(pos);
                for (Fragment f : fragments){
                    if (f.toString().contentEquals(frag.toString())){
                        ;
                    } else {
                        f.getLoaderManager().initLoader(0, ((PlaceHolderFragment)f).ARGS, ((PlaceHolderFragment)f)).forceLoad();
                    }
                }
            }
        }
//        notifyDataSetChanged();
    }
    
    

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        PlaceHolderFragment fragment = PlaceHolderFragment.newInstance(position + 1);
        fragments.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    final public static String CURSADAS = "YA PASADAS";
    final public static String DISPONIBLES = "Las que podes llevar";
    final public static String CALCULADORA = "EL CHUNCHE!";

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return DISPONIBLES;
            case 1:
                return CURSADAS;
            case 2:
                return CALCULADORA;
        }
        return null;
    }


}
