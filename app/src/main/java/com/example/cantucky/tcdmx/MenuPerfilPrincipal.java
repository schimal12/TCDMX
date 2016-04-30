package com.example.cantucky.tcdmx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuPerfilPrincipal extends AppCompatActivity {

    private String usuario = null;
    private String placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_perfil_principal);
        Bundle extras = getIntent().getExtras();
        usuario= extras.getString("Usuario");
        placa = extras.getString("placa");
    }

    public void onPerfil(View v){
        Intent it = new Intent(this, PerfilPersonal.class);
        it.putExtra("Usuario1",usuario);
        startActivity(it);
    }
    public void onMenu(View v){
        Intent it1 = new Intent(this, MainActivity.class);
        it1.putExtra("placa",placa);
        startActivity(it1);
    }
}
