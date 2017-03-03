package com.example.efaa.iee;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stephentuso.welcome.WelcomePage;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeSlidingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeSlidingFragment extends Fragment implements WelcomePage.OnChangeListener{


    private LinearLayout card;

    public WelcomeSlidingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WelcomeSlidingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WelcomeSlidingFragment newInstance(String param1, String param2) {
        return new WelcomeSlidingFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.card = ((LinearLayout) this.view.findViewById(R.id.car_view));
    }


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: fragmento iniciado");

        return inflater.inflate(R.layout.fragment_welcome_sliding, container, false);
    }

    @Override
    public void onWelcomeScreenPageScrolled(int pageIndex, float offset, int offsetPixels) {

    }

    @Override
    public void onWelcomeScreenPageSelected(int pageIndex, int selectedPageIndex) {
        if (pageIndex== selectedPageIndex) {
            ((TextView) view.findViewById(R.id.nombre_de_Clase)).setText("Espa\u00f1ol General");
            ((TextView) view.findViewById(R.id.codigo)).setText("EG-011");
            ((TextView) view.findViewById(R.id.welcome_uv)).setText("4");

            ObjectAnimator alpha = ObjectAnimator.ofFloat(card, "alpha", 1.0f, 0.5f);
            alpha.setDuration(1300);
            alpha.setRepeatCount(ValueAnimator.INFINITE);
            ObjectAnimator trans = ObjectAnimator.ofFloat(card, "translationX", 0f, 200f);
            trans.setDuration(1300);
            trans.setRepeatCount(ValueAnimator.INFINITE);

            alpha.setRepeatMode(ValueAnimator.REVERSE);
            trans.setRepeatMode(ValueAnimator.REVERSE);

            trans.start();
            alpha.start();
        }
    }


    @Override
    public void onWelcomeScreenPageScrollStateChanged(int pageIndex, int state) {

    }
}
