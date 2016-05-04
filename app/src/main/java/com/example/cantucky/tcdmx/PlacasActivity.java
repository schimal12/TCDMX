package com.example.cantucky.tcdmx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import android.view.inputmethod.InputMethodManager;

public class PlacasActivity extends AppCompatActivity {

    public EditText placas;
    public TextView modelo,marca,submarca,numeroinfraccionesT,tieneadeudosT;
    public Context cont;
    public View view;
    public Carro carro;
    public int numeroInfracciones;
    public String tieneadeudos;
    private ProgressDialog progressDialog;
    private String PlacaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placas);
        placas = (EditText)findViewById(R.id.placas);
        modelo = (TextView)findViewById(R.id.modelocarro);
        marca = (TextView)findViewById(R.id.marcaCarro);
        submarca = (TextView)findViewById(R.id.submarca);
        numeroinfraccionesT = (TextView)findViewById(R.id.numeroinfracciones);
        tieneadeudosT = (TextView)findViewById(R.id.tieneadeudos);
        Bundle extras = getIntent().getExtras();
        PlacaUsuario = extras.getString("placa");
        Log.d("placa",PlacaUsuario);
        placas.setText(PlacaUsuario);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Data . . . ");

        setTitle("Consultar Placas");
    }

    public void cargarDatos(View v){
        String placas1 = placas.getText().toString();
        //Validacion
        if(placas1.toString().length()>6){
            Context context = getApplicationContext();
            CharSequence text = "Placa Incorrecta";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            placas.setText("");
        }else {
            if (isConnected()) {
                placas.setBackgroundColor(0xFF00CC00);
            }
            // call AsynTask to perform network operation on separate thread
            /*
            HttpAsyncTask asyncTask = new HttpAsyncTask();

            asyncTask.execute(url);*/
            String url = "http://datos.labplc.mx/movilidad/vehiculos/" + placas1 + ".json";
            consumirJsonPlacas(url);
        }
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void consumirJsonPlacas(String url) {
        progressDialog.show();
        Log.d("Entre", "entre");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("R",response.toString());
                try {
                    Log.d("Si ando aqui","Si");
                    JSONObject consulta = (JSONObject)response.getJSONObject("consulta");
                    JSONObject tenencias = (JSONObject)consulta.getJSONObject("tenencias");
                    tieneadeudos = tenencias.getString("tieneadeudos");
                    JSONArray infracciones = consulta.getJSONArray("infracciones");
                    numeroInfracciones = infracciones.length();
                    JSONArray verificaciones = consulta.getJSONArray("verificaciones");
                    for (int i = 0; i < verificaciones.length(); i++) {
                        JSONObject jsonObejctDos = verificaciones.getJSONObject(i);
                        String marca = jsonObejctDos.getString("marca");
                        String resultado = jsonObejctDos.getString("resultado");
                        String submarca = jsonObejctDos.getString("submarca");
                        String modelo = jsonObejctDos.getString("modelo");
                        carro = new Carro(marca, modelo, submarca,resultado);
                    }
                    modelo.setText("Modelo: " + carro.getModelo().toString().toLowerCase());
                    marca.setText("Marca: " + carro.getMarca());
                    submarca.setText("SubMarca: " + carro.getSubmarca());
                    tieneadeudosT.setText("Tiene adeudos: " + tieneadeudos);
                    numeroinfraccionesT.setText("Numero de Infracciones: " + numeroInfracciones);
                    Log.d("Carro",carro.getResultado());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int timeOut = 10000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(timeOut,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    /*public static String GET(String url){
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try{
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode==200){
                HttpEntity entity = httpResponse.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                inputStream.close();
            }else{
               Log.d("Json","Fallo la descarga");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
*/

    /*private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private Context ct;
        private View view;

        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override

        /*protected void onPostExecute(String result) {

            try {
                JSONObject json = new JSONObject(result);
                JSONObject consulta = json.getJSONObject("consulta");
                JSONObject tenencias = consulta.getJSONObject("tenencias");
                tieneadeudos = tenencias.getString("tieneadeudos");
                JSONArray infracciones = consulta.getJSONArray("infracciones");
                numeroInfracciones = infracciones.length();
                JSONArray verificaciones = consulta.getJSONArray("verificaciones");
                for (int i = 0; i < verificaciones.length(); i++) {
                    JSONObject jsonObejctDos = verificaciones.getJSONObject(i);
                    String marca = jsonObejctDos.getString("marca");
                    Log.d("Cosa", marca);
                    String submarca = jsonObejctDos.getString("submarca");
                    String modelo = jsonObejctDos.getString("modelo");
                    carro = new Carro(marca, modelo, submarca);
                }
                modelo.setText("Modelo: " + carro.getModelo().toString().toLowerCase());
                marca.setText("Marca: " + carro.getMarca());
                submarca.setText("SubMarca: " + carro.getSubmarca());
                tieneadeudosT.setText("Tiene adeudos: " + tieneadeudos);
                numeroinfraccionesT.setText("Numero de Infracciones: " + numeroInfracciones);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    } */
}