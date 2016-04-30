package com.example.cantucky.tcdmx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        placa = extras.getString("placa");
    }
    public void goReglamento(View view){
        Intent it =new  Intent(this, ReglamentoActivity.class);
        startActivity(it);
    }
    public void goCamaras(View view){
        Intent it =new  Intent(this, CamarasActivity.class);
        startActivity(it);
    }
    public void goMapa(View view){
        Intent it =new  Intent(this, MapaActivity.class);
        startActivity(it);
    }
    public void goSop(View view){
        Intent it =new  Intent(this, LeagueActivity.class);
        startActivity(it);
    }
    public void goPlaca(View view){
        Intent it =new  Intent(this, PlacasActivity.class);
        it.putExtra("placa",placa);
        startActivity(it);
    }
}
