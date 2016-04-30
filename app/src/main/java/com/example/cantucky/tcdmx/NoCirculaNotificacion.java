package com.example.cantucky.tcdmx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NoCirculaNotificacion extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_circula_notificacion);
        textView = (TextView)findViewById(R.id.nocirculaplaca);
        textView.setText(getIntent().getStringExtra("mensaje"));
    }
}
