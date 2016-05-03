package com.example.cantucky.tcdmx;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText user, pass;
    private String placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText)findViewById(R.id.User);
        pass = (EditText)findViewById(R.id.Pass);

        setTitle("Log In");
    }
    public void seVa(View view) {

        String userString = user.getText().toString();
        String passString = pass.getText().toString();

        String usuarioBase = null, usuarioPass = null, usuarioPlaca = null, usuarioId = null;

        UsuarioSQLHelper usuarioSQLHelper = new UsuarioSQLHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usuarioSQLHelper.getWritableDatabase();


        Cursor consulta = db.rawQuery(" SELECT id, nombreUsuario, passUsuario, placa  FROM Usuarios WHERE nombreUsuario "+"="+"'"+userString+"'"+"", null);
        if (consulta.moveToFirst()) {
            usuarioId = consulta.getString(0);
            usuarioBase = consulta.getString(1);
            usuarioPass = consulta.getString(2);
            usuarioPlaca = consulta.getString(3);
            placa = usuarioPlaca;

        }
        if (userString.equals(usuarioBase)) {

            if (passString.equals(usuarioPass)) {
                Intent it = new Intent(this, MenuPerfilPrincipal.class);
                it.putExtra("Usuario",user.getText().toString());
                it.putExtra("placa",placa);
                startActivity(it);
                Toast.makeText(getApplicationContext(), "Bienvenido: " + userString,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Contraseña Incorrecta",
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Usuario Incorrecto",
                    Toast.LENGTH_SHORT).show();
        }

        lanzaNotificacion("CERO");
    }
    public void seRegistra(View view){
        Intent it =new  Intent(this, RegisterActivity.class);

        startActivity(it);
    }

    public void lanzaNotificacion(String res){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("Hoy no Circula");
        mBuilder.setContentText(getString(R.string.app_name));
        mBuilder.setTicker("Mensaje Nuevo");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setAutoCancel(true);
        Intent intento = new Intent(this,NoCirculaNotificacion.class);

        //
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        System.out.println(month);
        //
        if(res.equals("CERO")){
            String mensaje  = "No Circulas mañana";
            intento.putExtra("mensaje", mensaje);

        }else{
            if(res.equals("DOBLECER)")){
                String mensaje = "No Circulas Hoy";
                intento.putExtra("mensaje", mensaje);

            }
        }


        //
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,500,intento,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5007,mBuilder.build());
    }


}
