package com.example.cantucky.tcdmx;

import android.app.ListActivity;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CamarasActivity extends ListActivity implements OnMapReadyCallback {

    private TextView textView;
    private GoogleMap googleMap;
    private  ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CamarasAdapter camarasAdapter = new CamarasAdapter(this, generarCategorias());
        setListAdapter(camarasAdapter);
        setContentView(R.layout.activity_camaras);

        textView = (TextView)findViewById(R.id.camaraTitulo);
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapaFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(19.2835851, -99.1375987);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        this.googleMap.addMarker(new MarkerOptions().position(latLng).title("Le Tec").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

        CargaCamaraAsync cargaCamaraAsync = new CargaCamaraAsync(this, this.googleMap);
        cargaCamaraAsync.execute("cositas.json");
    }


    private ArrayList<String> generarCategorias() {
        items = new ArrayList<String>();

        AssetManager assetManager = getApplicationContext().getAssets();
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open("cositas.json"));
            bufferedReader = new BufferedReader(inputStreamReader);
            while (bufferedReader.ready()) {
                stringBuffer.append(bufferedReader.readLine());
            }
            bufferedReader.close();
            bufferedReader = null;
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            JSONArray coleccion = jsonObject.getJSONArray("pois");
            for (int i = 0; i < coleccion.length(); i++) {
                JSONObject ob = coleccion.getJSONObject(i);
                String nombre = ob.getString("name");
                items.add(nombre);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
