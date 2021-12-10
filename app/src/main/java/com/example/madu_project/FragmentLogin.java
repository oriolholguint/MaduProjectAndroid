package com.example.madu_project;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
   Boolean mayorEdad = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        avatares = llenarAvatares();
        activity = (MainActivity) getActivity();


        txtNombreUsuario = view.findViewById(R.id.txtNombreUsuario);
        cbxMayorEdad = view.findViewById(R.id.cbxMayorEdad);
        btnLogin = view.findViewById(R.id.btnLogin);
        gvAvatares = view.findViewById(R.id.gvAvatares);
        btnSelectAvatar = view.findViewById(R.id.btnSelecAvatar);

        imgAvatarSelect = view.findViewById(R.id.imgAvatarSelect);

        AvatarAdapter avatarAdapter = new AvatarAdapter(getContext(),avatares,imgAvatarSelect,gvAvatares,activity.jugador);
        gvAvatares.setAdapter(avatarAdapter);


        btnSelectAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gvAvatares.setVisibility(View.VISIBLE);
            }
        });


        cbxMayorEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbxMayorEdad.isChecked()){
                    mayorEdad = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(Boolean.toString(mayorEdad))
                            .setTitle("Aviso");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    mayorEdad = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(Boolean.toString(mayorEdad))
                            .setTitle("Aviso");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtNombreUsuario.getText().toString().equals(""))
                {
                    MensajeAlerta();
                } else {
                    activity.layout = "Menu";
                    activity.status = "Juego";
                    Group grpDificultad = activity.settingsDialog.findViewById(R.id.grpDificultad);

                    if(mayorEdad){
                        activity.findViewById(R.id.lblEdad).setVisibility(View.VISIBLE);
                    }

                    activity.jugador.setNombre(txtNombreUsuario.getText().toString());
                    activity.jugador.setEsMayorEdad(mayorEdad);

                    activity.LblNombreJugador.setText(activity.jugador.getNombre());
                    activity.imgAvatar.setImageResource(activity.jugador.getAvatar());

                    FragmentManager mg = getFragmentManager();
                    FragmentTransaction fragmentTransaction = mg.beginTransaction();

                    FragmentMenu fragmentMenu = new FragmentMenu();
                    fragmentTransaction.replace(R.id.ContenedorFragmentsPricipales,fragmentMenu);
                    fragmentTransaction.commit();

                    activity.findViewById(R.id.grpDatosUsuario).setVisibility(View.VISIBLE);
                    grpDificultad.setVisibility(View.VISIBLE);
                }


            }
        });




        return view;
    }

    public int [] llenarAvatares(){
        int [] avatares = {R.drawable.avatar1h,R.drawable.avatar2h,R.drawable.avatar3h,R.drawable.avatar4h,R.drawable.avatar5h,R.drawable.avatar6m,R.drawable.avatar7m,R.drawable.avatar8m,R.drawable.avatar9m,R.drawable.avatar10m};

        return avatares;
    }


    public void MensajeAlerta()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("introduce un nombre")
                .setTitle("Aviso");

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}