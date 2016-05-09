package com.example.efaa.iee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

//import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String NOMBRE_DE_CLASE = "param1";
    public static final String CODIGO_CLASE = "param2";
    public static final String ACTIVADO = "activado";
    public static final String UV = "uv";
    public static final String INDICE = "indice";
    public Clase cLase = null;
    public View.OnClickListener cListener;
    // TODO: Rename and change types of parameters
    private String nombreClase;
    private boolean activado;
    private String codigoClase;
    private int uv;
    private int indice;
    private OnFragmentInteractionListener mListener;


    public ClassFragment() {
        // Required empty public constructor
    }

    public ClassFragment(Clase clase){
        cLase = clase;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clase Parameter 1.
     * @param codigo Parameter 2.
     * @return A new instance of fragment ClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassFragment newInstance(String clase, String codigo, boolean activada, String uv) {
        ClassFragment fragment = new ClassFragment();
        Bundle args = new Bundle();
        args.putString(NOMBRE_DE_CLASE, clase);
        args.putString(CODIGO_CLASE, codigo);
        args.putBoolean(ACTIVADO, activada);
        args.putString(UV, uv);
        fragment.setArguments(args);
        return fragment;
    }

    public void animar() {
        TranslateAnimation trans = new TranslateAnimation(
                0,
                300 * (int) getActivity().getResources().getDisplayMetrics().density,
                0,
                0);
        trans.setDuration(500);

        trans.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                remover();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        getView().startAnimation(trans);
    }

    private void remover() {
        ((ActividadCalculador) getActivity()).removerFragmento(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombreClase = getArguments().getString(NOMBRE_DE_CLASE);
            codigoClase = getArguments().getString(CODIGO_CLASE);
            activado = getArguments().getBoolean(ACTIVADO);
            uv = getArguments().getInt(UV);
            indice = getArguments().getInt(INDICE);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);

    }

    public void onStart(){
        super.onStart();
        CheckBox nombre = (CheckBox) getView().findViewById(R.id.Nombre_de_Clase);
        Log.w(cLase.CODIGO, String.valueOf(indice));
        nombre.setText(nombreClase);

        nombre.setChecked(activado);


//        botn.setText(textoBoton);

        final TextView codigo = (TextView) getView().findViewById(R.id.Codigo);
        codigo.setText(codigoClase);

        ((TextView) getView().findViewById(R.id.UV)).setText(String.valueOf(uv));

        final EditText editText = ((EditText) getView().findViewById(R.id.editText));
        editText.setText(String.valueOf(cLase.INDICE));

        ((Button) getView().findViewById(R.id.per100Boton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                v.clearFocus();
                Log.e("OREJAS", "TENEMOS OREJAS...! QUE ALEGRIA: " + editText.getText() + "     "
                        + cLase.CODIGO);
                ContentValues values = new ContentValues();
                values.put(dataSource.Columnas.INDICE,
                        String.valueOf(editText.getText()));
                String selection = dataSource.Columnas.CODIGO + " = ?";
                String[] selectionArgs = {String.valueOf(codigo.getText())};


                Log.e("OREJAS OTRA VEZ", String.valueOf(SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null)
                        .update(dataSource.TABLE, values, selection, selectionArgs)));
//                v.requestFocus(View.FOCUS_RIGHT);

            }
        });

        LinearLayout grid = (LinearLayout) getView().findViewById(R.id.grid);

    }

    public void algo() {
        int indice = Integer.parseInt(String.valueOf(((EditText) getView().findViewById(R.id.editText)).getText()));

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        //getActivity().finish();
    }

    public void Guardar(View view) {
        return;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
       public void onFragmentInteraction(Uri uri);
    }


}
