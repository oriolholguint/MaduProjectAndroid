package com.example.madu_project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

public class FragmentLogin extends Fragment {

    View view;
    int [] avatares;
    EditText txtNombreUsuario;
    CheckBox cbxMayorEdad;
    GridView gvAvatares;
    ImageView imgAvatarSelect;
    Button btnSelectAvatar;
    MainActivity activity;
    Button btnLogin;
    Boolean mayorEdad = false, visible = false;
    FragmentManager mg;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        avatares = llenarAvatares();
        activity = (MainActivity) getActivity();
        mg = getFragmentManager();

        txtNombreUsuario = view.findViewById(R.id.txtNombreUsuario);
        cbxMayorEdad = view.findViewById(R.id.cbxMayorEdad);
        btnLogin = view.findViewById(R.id.btnLogin);
        gvAvatares = view.findViewById(R.id.gvAvatares);
        btnSelectAvatar = view.findViewById(R.id.btnSelecAvatar);

        imgAvatarSelect = view.findViewById(R.id.imgAvatarSelect);

        AvatarAdapter avatarAdapter = new AvatarAdapter(getContext(),avatares,imgAvatarSelect,gvAvatares,activity.jugador);
        gvAvatares.setAdapter(avatarAdapter);

        activity.clBackgroundApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OcultarTecladoVirtual();
            }
        });

        btnSelectAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OcultarTecladoVirtual();
                if (!visible){
                    visible = true;
                    btnSelectAvatar.setText(R.string.deseleccionar_avatar);
                    gvAvatares.setVisibility(View.VISIBLE);
                } else {
                    visible = false;
                    btnSelectAvatar.setText(R.string.seleccionar_avatar);
                    gvAvatares.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnSelectAvatar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    /*case MotionEvent.ACTION_UP:
                        btnSelectAvatar.startAnimation(activity.buttonUp);
                        break;*/
                    case MotionEvent.ACTION_DOWN:
                        btnSelectAvatar.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });

        txtNombreUsuario.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                txtNombreUsuario.setBackgroundColor(Color.WHITE);
            }
        });

        cbxMayorEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OcultarTecladoVirtual();
                mayorEdad = cbxMayorEdad.isChecked();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OcultarTecladoVirtual();
                if(!comprobarNombre(txtNombreUsuario.getText().toString())) {
                    txtNombreUsuario.setBackgroundColor(Color.RED);
                }
                else if(imgAvatarSelect.getDrawable() == null) {
                    if(gvAvatares.getVisibility() == View.INVISIBLE)
                    {
                        btnSelectAvatar.callOnClick();
                    }
                    txtNombreUsuario.setBackgroundColor(Color.WHITE);
                }
                else {
                    activity.clBackgroundApp.setBackgroundResource(R.drawable.fondo_menu_madu);
                    activity.layout = "Menu";
                    activity.status = "Juego";
                    //activity.dialogSettings.setBackgroundResource(R.drawable.pergamino_settings);
                    Group grpDificultad = activity.settingsDialog.findViewById(R.id.grpDificultad);

                    activity.jugador.setNombre(txtNombreUsuario.getText().toString());
                    activity.jugador.setEsMayorEdad(mayorEdad);

                    activity.LblNombreJugador.setText(activity.jugador.getNombre());
                    activity.imgAvatar.setImageResource(activity.jugador.getAvatar());

                    irAMenu();

                    activity.findViewById(R.id.grpDatosUsuario).setVisibility(View.VISIBLE);

                    if(mayorEdad){
                        activity.findViewById(R.id.LblEdad).setVisibility(View.VISIBLE);
                    }else {
                        activity.findViewById(R.id.LblEdad).setVisibility(View.INVISIBLE);
                    }

                    grpDificultad.setVisibility(View.VISIBLE);
                }
            }
        });

        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    /*case MotionEvent.ACTION_UP:
                        btnLogin.startAnimation(activity.buttonUp);
                        break;*/
                    case MotionEvent.ACTION_DOWN:
                        btnLogin.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });

        return view;
    }

    private int [] llenarAvatares(){
        int [] avatares = {R.drawable.avatar1h,R.drawable.avatar2h,R.drawable.avatar3h,R.drawable.avatar4h,R.drawable.avatar5h,R.drawable.avatar6m,R.drawable.avatar7m,R.drawable.avatar8m,R.drawable.avatar9m,R.drawable.avatar10m};

        return avatares;
    }

    private void OcultarTecladoVirtual() {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            txtNombreUsuario.clearFocus();
            activity.ocultarBarrasDispositivo();
        }
    }

    /**
     * Comprueba el nombre del usuario, no puede contener espacios ni estar vacio
     * @param nombre nombre a comprobar
     * @return TRUE = nombre correcto, FALSE = nombre incorrecto
     */
    private boolean comprobarNombre(String nombre)
    {
        boolean nombreCorrecto = true;

        for(int i = 0; i < nombre.length(); i++)
        {
            if(nombre.charAt(i) == 32)
            {
                nombreCorrecto = false;
            }
        }

        if(nombre.isEmpty())
        {
            nombreCorrecto = false;
        }

        return nombreCorrecto;
    }

    private void irAMenu(){

        fragmentTransaction = mg.beginTransaction();

        FragmentMenu fragmentMenu = new FragmentMenu();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,R.anim.exit_left_to_right);
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentMenu);
        fragmentTransaction.commit();
    }
}