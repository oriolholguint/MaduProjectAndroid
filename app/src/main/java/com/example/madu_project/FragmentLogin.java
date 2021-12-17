package com.example.madu_project;

import android.app.AlertDialog;
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
                if (visible == false){
                    visible = true;
                    btnSelectAvatar.setText("Deseleccionar Avatar");
                    gvAvatares.setVisibility(View.VISIBLE);
                } else {
                    visible = false;
                    btnSelectAvatar.setText("Seleccionar Avatar");
                    gvAvatares.setVisibility(View.INVISIBLE);
                }

            }
        });

        btnSelectAvatar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        btnSelectAvatar.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnSelectAvatar.startAnimation(activity.buttonDown);
                        break;
                }
                return false;
            }
        });


        cbxMayorEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OcultarTecladoVirtual();
                if(cbxMayorEdad.isChecked()){
                    mayorEdad = true;
                } else {
                    mayorEdad = false;
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OcultarTecladoVirtual();
                if(txtNombreUsuario.getText().toString().equals("") || imgAvatarSelect.getDrawable() == null)
                {
                    MensajeAlerta();
                } else {
                    activity.clBackgroundApp.setBackgroundResource(R.drawable.fondo_menu_madu);
                    activity.layout = "Menu";
                    activity.status = "Juego";
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
                    case MotionEvent.ACTION_UP:
                        btnLogin.startAnimation(activity.buttonUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        btnLogin.startAnimation(activity.buttonDown);
                        break;
                }

                return false;
            }
        });



        return view;
    }

    public int [] llenarAvatares(){
        int [] avatares = {R.drawable.avatar1h,R.drawable.avatar2h,R.drawable.avatar3h,R.drawable.avatar4h,R.drawable.avatar5h,R.drawable.avatar6m,R.drawable.avatar7m,R.drawable.avatar8m,R.drawable.avatar9m,R.drawable.avatar10m};

        return avatares;
    }

    public void OcultarTecladoVirtual() {
        if(activity.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void MensajeAlerta()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("falta un nombre o una imagen")
                .setTitle("Aviso");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void irAMenu(){

        fragmentTransaction = mg.beginTransaction();

        FragmentMenu fragmentMenu = new FragmentMenu();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,R.anim.exit_left_to_right);
        fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentMenu);
        fragmentTransaction.commit();
    }


}