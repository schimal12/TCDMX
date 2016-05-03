package com.example.cantucky.tcdmx;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    private EditText name, username, mail, pass, pass2, placa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        pass2 = (EditText)findViewById(R.id.confirm);
        placa = (EditText)findViewById(R.id.penplate);

        setTitle("Registro");
    }
    public void seValel(View view){

        if(!pass.getText().toString().equals(pass2.getText().toString())){
            Toast.makeText(getApplicationContext(), "La contraseña no es igual, vuelve a intentarlo",
                    Toast.LENGTH_SHORT).show();
        }else {
            int id = hash(this.username.getText().toString());
            //Conexion SQL
            UsuarioSQLHelper usuarioSQLHelper = new UsuarioSQLHelper(this, "DBUsuarios", null, 1);
            SQLiteDatabase database = usuarioSQLHelper.getWritableDatabase();
            if (database != null) {
                database.execSQL("INSERT INTO Usuarios (id, nombreUsuario,passUsuario, placa)"+"VALUES ("+"'"+id+"'"+",'"+username.getText().toString()+"'"+",'"+pass.getText().toString()+"'"+","+"'"+placa.getText().toString()+"')");
                database.close();
            }
            Intent it = new Intent(this, LoginActivity.class);
            startActivity(it);
        }
    }

    public int hash(String s){
        int hash = (int)Math.random();
        for(int i = 0; i<s.length();i++){
            hash=hash*31+s.charAt(i);
        }
        return hash;
    }

}