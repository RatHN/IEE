package com.example.efaa.iee;

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
        int count = 0;
        if (f != null && !f.flag) {
            f.update();

            return POSITION_NONE;
        }
        return super.getItemPosition(object);
//        return POSITION_NONE;
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
