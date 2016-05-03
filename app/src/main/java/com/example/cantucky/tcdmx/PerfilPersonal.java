package com.example.cantucky.tcdmx;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PerfilPersonal extends AppCompatActivity {

    private TextView bienvenido, coche, placa, infracciones;

    private String usuarioIntent, placo;


    private String usuarioBase = null, usuarioPass = null, usuarioPlaca = null, usuarioId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_personal);
        bienvenido = (TextView)findViewById(R.id.BienvenidoPerfil);
        coche = (TextView)findViewById(R.id.CochePerfil);
        placa = (TextView)findViewById(R.id.PlacaPerfil);
        infracciones = (TextView)findViewById(R.id.InfraccionesPerfil);
        Bundle extras = getIntent().getExtras();
        usuarioIntent= extras.getString("Usuario1");
        llenarConConsultaSQL();
        setTitle("Tu Perfil");
        // placo = extras.getString("placa");
    }

    private void llenarConConsultaSQL() {
        //Consulta SQL

        Log.d("I",usuarioIntent);

        UsuarioSQLHelper usuarioSQLHelper = new UsuarioSQLHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usuarioSQLHelper.getWritableDatabase();


        Cursor consulta = db.rawQuery(" SELECT id, nombreUsuario, passUsuario, placa  FROM Usuarios WHERE nombreUsuario "+"="+"'"+usuarioIntent+"'", null);
        if (consulta.moveToFirst()) {
            usuarioId = consulta.getString(0);
            usuarioBase = consulta.getString(1);
            usuarioPass = consulta.getString(2);
            usuarioPlaca = consulta.getString(3);

        }
        //Consulta Infracciones
        HttpAsyncTask asyncTask = new HttpAsyncTask();
        String url = "http://datos.labplc.mx/movilidad/vehiculos/" + usuarioPlaca + ".json";
        asyncTask.execute(url);
    }
    public static String GET(String url){
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
                Log.d("Json", "Fallo la descarga");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private Context ct;
        private View view;

        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override

        protected void onPostExecute(String result) {

            int numeroInfracciones;
            String marca = null;

            try {
                JSONObject json = new JSONObject(result);
                JSONObject consulta = json.getJSONObject("consulta");
                JSONObject tenencias = consulta.getJSONObject("tenencias");
                JSONArray infracciones1 = consulta.getJSONArray("infracciones");
                numeroInfracciones = infracciones1.length();
                JSONArray verificaciones = consulta.getJSONArray("verificaciones");
                for (int i = 0; i < verificaciones.length(); i++) {
                    JSONObject jsonObejctDos = verificaciones.getJSONObject(i);
                    marca = jsonObejctDos.getString("marca");
                }
                bienvenido.setText("Bienvenido: "+usuarioBase);
                coche.setText("Marca: "+marca);
                infracciones.setText("Infracciones: "+numeroInfracciones);
                placa.setText("Placa: "+usuarioPlaca);
                bienvenido.setText("Bienvenido: "+usuarioBase);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void goMain(View view){
        Intent it =new  Intent(this, MainActivity.class);
        it.putExtra("placa",usuarioPlaca);
        startActivity(it);
    }
}
