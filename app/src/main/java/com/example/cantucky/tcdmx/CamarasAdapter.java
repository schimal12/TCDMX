package com.example.cantucky.tcdmx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
/**
 * Created by sebastianchimal on 07/03/16.
 */
public class CamarasAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> itemsArrayList;


    public CamarasAdapter(Context context, ArrayList<String> listaCamaras) {
        super(context, R.layout.rengloncamaras, listaCamaras);
        this.context = context;
        this.itemsArrayList = listaCamaras;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Crear inflador
        LayoutInflater inflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //El renglon del view
        View rowView = inflate.inflate(R.layout.rengloncamaras,parent,false);
        //Obtener la Iamge View y el TextView
        TextView informacionCamaras = (TextView)rowView.findViewById(R.id.ubicacionCamara);
        ImageView imagenComida = (ImageView)rowView.findViewById(R.id.imagenCamara);
        informacionCamaras.setText(itemsArrayList.get(position).toString());

        return  rowView;
    }
}
