package com.example.cantucky.tcdmx;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    GPSTracker gps;
    Button btnShowLocation;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private GoogleMap googleMap;
    private double latitude1;
    private double longitude1;
    private String[] Calles80 = {"Interior","Periferico","Poniente","Insgurgentes Norte","Zaragoza","Tlalpan","Constituyentes","Nacional","Serdan"};
    private String[] Calles50 = {"Eje","Central","Reforma","Mexico Xochimilco","Universidad","Camarones","Molina","Guadalupe","Observatorio"};
    private TextView calle, velocidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        calle = (TextView)findViewById(R.id.Calle);
        velocidad = (TextView)findViewById(R.id.velocidad);
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);
        setTitle("Mapa de Velocidades");

        btnShowLocation = (Button) findViewById(R.id.button);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                gps = new GPSTracker(MapaActivity.this);

                if (gps.canGetLocation()) {

                    LatLng pruebaFer = new LatLng(19.284082,-99.1391692);

                    latitude1 = pruebaFer.latitude;
                    longitude1 = pruebaFer.longitude;


                    Location loc;
                    loc = new Location("Loc");
                    loc.setLatitude(latitude1);
                    loc.setLongitude(longitude1);

                    LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Aqui ando").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));


                    String cityName = null;
                    String prueba1 = null;
                    String prueba2 = null;
                    String prueba3 = null;
                    Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                    List<Address> addresses;
                    try {
                        addresses = gcd.getFromLocation((Double)loc.getLatitude(),
                                (Double)loc.getLongitude(), 1);
                        if (addresses.size() > 0) {
                            System.out.println(addresses.get(0).getLocality());
                            cityName = addresses.get(0).getLocality();
                            prueba1 = addresses.get(0).getAddressLine(0);

                            //Revisar las velocidades

                            for(int i=0;i<Calles50.length;i++){
                                String tmp = Calles50[i];
                                if(prueba1!=null) {
                                    if (prueba1.contains(tmp)) {
                                        velocidad.setText("Maxima 50 km/hr");
                                        calle.setText(prueba1);
                                    } else {
                                        velocidad.setText("Maxima 40 km/hr");
                                        calle.setText(prueba1);
                                    }
                                }else{
                                    calle.setText("No hay vialidades conocidas");
                                    velocidad.setText("No hay vialidades conocidas");
                                }
                            }

                            for(int j = 0;j<Calles80.length;j++){
                                String tmp = Calles80[j];
                                if(prueba1!=null) {
                                    if (prueba1.contains(tmp)) {
                                        velocidad.setText("Maxima 80 km/hr");
                                        calle.setText(prueba1);
                                    } else {
                                        velocidad.setText("Maxima 40 km/hr");
                                        calle.setText(prueba1);
                                    }
                                }else{
                                    calle.setText("No hay vialidades conocidas");
                                    velocidad.setText("No hay vialidades conocidas");
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String s = longitude1 + "\n" + latitude1 + "\n\nMy Current City is: "
                            + cityName;
                    Log.d("Localizacion", "Loc: " + s);
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude1 + "\nLong: " + longitude1, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Mapa Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.cantucky.tcdmx/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Mapa Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.cantucky.tcdmx/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(19.2835851, -99.1375987);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        this.googleMap.addMarker(new MarkerOptions().position(latLng).title("Le Tec").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

    }

}

