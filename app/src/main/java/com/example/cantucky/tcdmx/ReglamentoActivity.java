package com.example.cantucky.tcdmx;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class ReglamentoActivity extends AppCompatActivity {

    private TextView texto1, texto2, texto3, reglamento;
    private ArrayList<Articulo> listaArticulos = new ArrayList<>();
    private ArrayList<Articulo> encontrados = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglamento);
        texto1 = (TextView) findViewById(R.id.texto1);
        texto2 = (TextView) findViewById(R.id.texto2);
        texto3 = (TextView) findViewById(R.id.texto3);
        reglamento = (TextView)findViewById(R.id.articulos);
    }

    public void cargaDatos(View view) {

        listaArticulos.clear();
        encontrados.clear();

        String textoA = texto1.getText().toString();
        String textoB = texto2.getText().toString();
        String textoC = texto3.getText().toString();

        if (textoA.length() > 15 || textoB.length() > 15 || textoC.length() > 15) {
            Context context = getApplicationContext();
            CharSequence text = "Usa palabras más cortas";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            texto1.setText("");
            texto2.setText("");
            texto3.setText("");
        } else {
            leerJson();
            buscarPalabras(textoA, textoB, textoC);
            mostrarArticulos();
        }
    }

    private void mostrarArticulos() {
        String general = "";
        Iterator iteradorEncontrados = encontrados.iterator();
        while(iteradorEncontrados.hasNext()){
            Articulo encontradoTmp = (Articulo)iteradorEncontrados.next();
            float multa = encontradoTmp.getSansion()*71.68f;
            general += encontradoTmp.toString()+"\nMulta: $"+multa+"\n\n";
        }
        reglamento.setText(general);
    }

    private void buscarPalabras(String texto1, String texto2, String texto3) {
        String textoA = texto1.toUpperCase();
        String textoB = texto2.toUpperCase();
        String textoC = texto3.toUpperCase();

        Iterator iteradorArticulos = listaArticulos.iterator();
        while(iteradorArticulos.hasNext()){
            Articulo articuloTmp = (Articulo)iteradorArticulos.next();
            String descripcion = articuloTmp.getDescripcion();
            if(textoA.length()>0 && textoB.length()>0&&textoC.length()>0){
                if(descripcion.contains(textoA) && descripcion.contains(textoB)&&descripcion.contains(textoC)) {
                    Log.d("i", "" + articuloTmp.getNumeroArticulo());
                    encontrados.add(articuloTmp);
                }
            }else{
                if(textoA.length()>0 && textoB.length()<=0&&textoC.length()<=0){
                    if(descripcion.contains(textoA)) {
                        Log.d("i", "" + articuloTmp.getNumeroArticulo());
                        encontrados.add(articuloTmp);
                    }
                }else{
                    if(textoA.length()>0 && textoB.length()>0&&textoC.length()<=0){
                        if(descripcion.contains(textoA)&&descripcion.contains(textoB)) {
                            Log.d("i", "" + articuloTmp.getNumeroArticulo());
                            encontrados.add(articuloTmp);
                        }
                    }
                }
            }
        }
    }

    private void leerJson() {

        AssetManager assetManager = getApplicationContext().getAssets();
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open("articulos.json"));
            bufferedReader = new BufferedReader(inputStreamReader);
            while (bufferedReader.ready()) {
                stringBuffer.append(bufferedReader.readLine());
            }
            bufferedReader.close();
            bufferedReader = null;
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("articulos");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObejctDos = jsonArray.getJSONObject(i);
                int numeroArticulo = jsonObejctDos.getInt("articulo");
                String fraccion = remove1(jsonObejctDos.getString("fraccion"));
                String parrafo = remove1(jsonObejctDos.getString("parrafo"));
                String incisos = remove1(jsonObejctDos.getString("inciso"));
                String corralon = remove1(jsonObejctDos.getString("corralon"));
                int puntos = jsonObejctDos.getInt("puntos");
                String descripcion = remove1(jsonObejctDos.getString("descripcion"));
                int sancion = jsonObejctDos.getInt("dias_sansion");
                Articulo articulo = new Articulo(numeroArticulo,fraccion,parrafo,incisos,corralon,puntos,descripcion,sancion);
                listaArticulos.add(articulo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public  String remove1(String input) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
}
