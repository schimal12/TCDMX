package com.example.cantucky.tcdmx;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sebastianchimal on 23/04/16.
 */
public class UsuarioSQLHelper extends SQLiteOpenHelper {



    private static  final String creacion = "CREATE TABLE Usuarios (id INTEGER, nombreUsuario TEXT, passUsuario TEXT, placa TEXT)";


    public UsuarioSQLHelper(Context context,String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,nombre,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(creacion);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table IF Exists " + "Usuarios");
        db.execSQL(creacion);
    }
}
