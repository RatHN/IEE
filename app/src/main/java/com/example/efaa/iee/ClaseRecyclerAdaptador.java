package com.example.efaa.iee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

//import com.google.android.gms.location.places.Place;

/**
 * Created by Neri Ortez on 09/05/2016.
 */
public class ClaseRecyclerAdaptador extends RecyclerView.Adapter<ClaseRecyclerAdaptador.ClaseViewHolder> {

    private Context context;
    public List<Clase> LISTA;
    final int INDICE_MINIMO = 65;
    final int COLOR_PASADO = Color.WHITE;
    final int COLOR_NO_PASADO = Color.LTGRAY;

    public ClaseRecyclerAdaptador(List<Clase> Lista) {
        LISTA = Lista;
    }

    public List<Clase> getLISTA() {
        return LISTA;
    }

    public void cambiarLista(List<Clase> Lista) {
        this.LISTA.clear();
        this.LISTA = Lista;

    }

    @Override
    public ClaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
//        if (LISTA.get(0).CODIGO.compareTo("NADA") == 0) {
//            ProgressBar progressBar = new ProgressBar(parent.getContext());
//            progressBar.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    Gravity.CENTER));
//
//            progressBar.setIndeterminate(true);
//            v = progressBar;
//        } else {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_clase, parent, false);
        /*CheckBox c = (CheckBox)v.findViewById(R.id.Nombre_de_Clase);
        c.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((ClaseViewHolder)v.getParent().getParent()).onClick(v);
                ((RecyclerView)v.getParent().getParent().getParent().getParent().getParent()).getAdapter()
            }
        });*/
//        }
        return new ClaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClaseViewHolder holder, int position) {

        if (LISTA.get(0).CODIGO.compareTo("NADA") == 0) {
            return;
        } else {
            //La clase que se va a usar, sacado de la posicion
            Clase clase = LISTA.get(position);
            //Seteando datos, tomando el holder.
            holder.codigo.setText(clase.CODIGO);
            holder.indice.setText(String.valueOf(clase.INDICE));

            if (clase.INDICE < INDICE_MINIMO) {
                ((CardView) holder.v.findViewById(R.id.car_view)).setCardBackgroundColor(COLOR_NO_PASADO);
            } else {
                ((CardView) holder.v.findViewById(R.id.car_view)).setCardBackgroundColor(COLOR_PASADO);
            }

            holder.uv.setText(String.valueOf(clase.UV));
            holder.nombre.setText(clase.NOMBRE);
//            holder.nombre.setChecked(clase.CURSADA);

        }

    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return LISTA.size();
    }


    public class ClaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos de la clase
        public EditText indice;
        public TextView nombre;
        public TextView codigo;
        public TextView uv;
        public Button guardar;
        public CardView mCardView;
        public OnClickListener checkListener;
        public InterfaceEscuchador escuchador;
        public InterfaceSetearIndice setearIndice;
        public boolean cursada;
        public int position;
        private View v;
        private View mRemoveableView;


        public ClaseViewHolder(View view) {
            super(view);
            this.v = view;
            escuchador = (InterfaceEscuchador) view.getContext();
            setearIndice = (InterfaceSetearIndice) view.getContext();
            if (LISTA.get(0).CODIGO.compareTo("NADA") == 0) {
                return;
            } else {
                indice = (EditText) view.findViewById(R.id.editText);
                nombre = (TextView) view.findViewById(R.id.Nombre_de_Clase);
                codigo = (TextView) view.findViewById(R.id.Codigo);
                uv = (TextView) view.findViewById(R.id.UV);

                view.findViewById(R.id.per100Boton).setOnClickListener(botonClicked());

                ((EditText) view.findViewById(R.id.editText)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        botonClicked().onClick(v);
                        return true;
                    }
                });

/*
                //Cambiamos la escucha y seteamos el escucha de click
                checkListener = this;
                nombre.setOnClickListener(checkListener);
*/

            }
            mRemoveableView = view;
        }

        OnClickListener botonClicked() {
            return new View.OnClickListener() {
                public void onClick(View v) {
                    int ind = Integer.parseInt(indice.getText().toString());
                    if (ind < 0 || ind > 100) {
                        setIndiceError(v);
                        return;
                    }
                    do {
                        if (v instanceof CardView) {
                            break;
                        }
                        if (v != null) {
                            // Else, we will loop and crawl up the view hierarchy and try to find a parent
                            final ViewParent parent = v.getParent();
                            v = parent instanceof View ? (View) parent : null;
                        }
                    } while (v != null);

                    assert v != null;
                    if (ind < INDICE_MINIMO)
                        ((CardView) v.findViewById(R.id.car_view)).setCardBackgroundColor(COLOR_NO_PASADO);
                    else
                        ((CardView) v.findViewById(R.id.car_view)).setCardBackgroundColor(COLOR_PASADO);

                    Log.i("OREJAS", "TENEMOS OREJAS...! QUE ALEGRIA: " + indice.getText() + "     "
                            + codigo.getText());
                    ContentValues values = new ContentValues();
                    values.put(dataSource.Columnas.INDICE,
                            String.valueOf(indice.getText()));
                    String selection = dataSource.Columnas.CODIGO + " = ?";
                    String[] selectionArgs = {String.valueOf(codigo.getText())};
                    setearIndice.setearIndice(selection, selectionArgs, values, v);

//            Log.e("OREJAS OTRA VEZ", String.valueOf());
                }
            };
        }

        @Override
        public void onClick(View v) {
            remove_at(v.getContext(), (TextView) v, getAdapterPosition());
        }


        private void remove_at(Context context1, TextView v, int position) {
            Clase clase = LISTA.get(position);
            dataSource source = new dataSource();
            SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
            String resultado;
            if (clase.CURSADA) {
                resultado = source.insertarUnoOCero(dob, clase.CODIGO, dataSource.Columnas.CURSADA, "0", clase, context1);
            } else {
                resultado = source.insertarUnoOCero(dob, clase.CODIGO, dataSource.Columnas.CURSADA, "1", clase, context1);
            }

            dob.releaseReference();
            if (resultado == "-1") {
//                ((CheckBox) v).setChecked(true);
                Snackbar.make(v, "Esta asignatura es requisito de una asignatura que ya ha sido cursada," +
                                " por favor desmarque primero asignaturas dependientes y luego sus requisitos",
                        Snackbar.LENGTH_SHORT).show();
                return;
            }
            cursada = LISTA.get(position).CURSADA;

            // Se inhabilitó porque exite la manera de deshabilitar esto despues de la animacion... Naaaaaa
            escuchador.Escuchador(cursada, position);
            this.position = position;


//            Snackbar.make(v.getRootView(), "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
        }

        void remover(Context context1, View v, int position) {
            Clase clase = LISTA.get(position);
            dataSource source = new dataSource();
            SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
            String resultado;
            if (clase.CURSADA) {
                resultado = source.insertarUnoOCero(dob, clase.CODIGO, dataSource.Columnas.CURSADA, "0", clase, context1);
            } else {
                resultado = source.insertarUnoOCero(dob, clase.CODIGO, dataSource.Columnas.CURSADA, "1", clase, context1);
            }

            dob.releaseReference();
            cursada = LISTA.get(position).CURSADA;

            if (resultado == "-1") {
//                ((CheckBox) v.findViewById(R.id.Nombre_de_Clase)).setChecked(true);
                Snackbar.make(v, "Esta asignatura es requisito de una asignatura que ya ha sido cursada," +
                                " por favor desmarque primero asignaturas dependientes y luego sus requisitos",
                        Snackbar.LENGTH_SHORT).show();
                Bundle b = new Bundle();
                Main2Activity context11 = (Main2Activity) context1;
                b.putString("COLUMNA", context11.placeHolderFragment.COLUMNA);

                context11.placeHolderFragment.getLoaderManager().initLoader(0, b, context11.placeHolderFragment);
                return;
            }


            // Se inhabilitó porque exite la manera de deshabilitar esto despues de la animacion... Naaaaaa
            escuchador.Escuchador(cursada, position);
            this.position = position;


            Snackbar.make(this.nombre, "Exitoso", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        public void eso(View v) {
            TranslateAnimation trans = new TranslateAnimation(
                    0,
//                    300 * (int)getResources().getDisplayMetrics().density,
                    300 * this.v.getContext().getResources().getDisplayMetrics().density,
                    0,
                    0);
            trans.setDuration(500);

            trans.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    remover();
                    escuchador.Escuchador(cursada, position);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

        private void setIndiceError(View view) {
//            Snackbar.make(view, "El valor es invalido", Snackbar.LENGTH_LONG).show();
            new AlertDialog.Builder(view.getContext()).setPositiveButton("Pucha...!", null).setMessage("El valor es invalido").create().show();
        }

        public View getSwipableView() {
            return mRemoveableView;
        }
    }


    public interface InterfaceEscuchador {
        void Escuchador(boolean actualizarCursada);

        void Escuchador(boolean actualizarCursada, int pos);

        void EsconderTeclado();
    }

    public interface InterfaceSetearIndice {
        void setearIndice(String selection, String[] selectionArgs, ContentValues values, View v);
    }

}
