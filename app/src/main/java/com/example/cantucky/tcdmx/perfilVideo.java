package com.example.cantucky.tcdmx;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuforia.VIEW;

import org.w3c.dom.Text;

public class perfilVideo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textView, textView1;
    private String usuario;
    private ImageView imageView;
    private String placa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        usuario= extras.getString("Usuario");
        placa = extras.getString("placa");
        textView = (TextView)findViewById(R.id.textView5);
        textView1 = (TextView)findViewById(R.id.textView4);
        imageView = (ImageView)findViewById(R.id.image);
        textView.setText(usuario);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.perfil_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera){

            Bundle bundle = new Bundle();
            bundle.putString("Usuario1", usuario);
            FragmentoPerfil fragmentoPerfil = new FragmentoPerfil();
            fragmentoPerfil.setArguments(bundle);
            textView.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragmentoPerfil).commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new InicioFragment()).commit();
        } else if (id == R.id.nav_gallery){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new fragmentoYoutube()).commit();
        } else if(id == R.id.menuPrincipal){
            Intent it1 = new Intent(this, MainActivity.class);
            it1.putExtra("placa",placa);
            startActivity(it1);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
