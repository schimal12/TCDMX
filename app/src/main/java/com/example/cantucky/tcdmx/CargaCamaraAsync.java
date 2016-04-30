package com.example.cantucky.tcdmx;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by sebastianchimal on 07/03/16.
 */
public class CargaCamaraAsync extends AsyncTask<String, Void, ArrayList<Camaras>> {

    private Context ct;
    private ProgressDialog dialogo;
    private GoogleMap googleMap;

    public CargaCamaraAsync(Context c, GoogleMap googleMap) {
        ct = c;
        dialogo = new ProgressDialog(ct);
        this.googleMap = googleMap;
    }

    @Override
    protected ArrayList<Camaras> doInBackground(String... params) {
        String archivo = params[0];
        AssetManager manager = ct.getAssets();

        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<Camaras> resultado = new ArrayList<>();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(manager.open(archivo));
            bufferedReader = new BufferedReader(inputStreamReader);
            String data = "";
            while(bufferedReader.ready()){
                data += bufferedReader.readLine();
            }

            bufferedReader.close();
            bufferedReader = null;
            resultado = procesaJSON(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        dialogo.setTitle("Procesando...");
        dialogo.setCancelable(false);
        dialogo.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Camaras> result) {
        if (result.size() > 0) {
            for (Camaras c : result) {
                LatLng latLng = new LatLng(c.getLatitude(), c.getLongitud());
                this.googleMap.addMarker(new MarkerOptions()
                        .position(latLng).title(c.getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            }
            this.googleMap.moveCamera(CameraUpdateFactory.zoomTo(8.0f));
        }
        if(dialogo != null && dialogo.isShowing()){
            dialogo.dismiss();
        }
    }

    public ArrayList<Camaras> procesaJSON(String datos){
        ArrayList<Camaras> data = new ArrayList<>();
        try{
            JSONObject object = new JSONObject(datos);
            JSONArray coleccion = object.getJSONArray("pois");
            for (int i = 0; i < coleccion.length(); i++) {
                JSONObject ob = coleccion.getJSONObject(i);
                String nombre = ob.getString("name");
                double latitude = ob.getDouble("latitude");
                double longitude = ob.getDouble("longitude");

                Camaras camara = new Camaras();
                camara.setNombre(nombre);
                camara.setLatitude(latitude);
                camara.setLongitud(longitude);

                data.add(camara);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return data;

    }
}
