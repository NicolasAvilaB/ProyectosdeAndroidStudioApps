package com.example.nicolas.androidsqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class clasecrearbd extends SQLiteOpenHelper {

    String misql = "CREATE TABLE clientes (rut TEXT PRIMARY KEY, nombre TEXT, direccion TEXT, telefono INTEGER)";


    public clasecrearbd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(misql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL(misql);
    }
}
