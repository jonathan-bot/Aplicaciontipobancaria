package com.example.aplicacintipobancaria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDatos extends SQLiteOpenHelper {

    final String tabla = "CREATE TABLE cliente (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, " +
            "apellido TEXT, identificacion TEXT, correo TEXT, contrasena TEXT, cuenta TEXT, monto DOUBLE, estado TEXT)";

    public BDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
