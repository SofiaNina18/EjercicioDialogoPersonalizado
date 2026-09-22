package com.example.ejerciciodialogopersonalizado.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ejerciciodialogopersonalizado.model.Personaje;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "personajes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PERSONAJES = "personajes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_TIPO = "tipo";
    public static final String COLUMN_FOTO_URL = "foto_url";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PERSONAJES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_TIPO + " TEXT, " +
                COLUMN_FOTO_URL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAJES);
        onCreate(db);
    }

    // Insertar un nuevo personaje
    public long insertarPersonaje(Personaje personaje) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, personaje.getNombre());
        values.put(COLUMN_TIPO, personaje.getTipo());
        values.put(COLUMN_FOTO_URL, personaje.getFotoUrl());

        long id = db.insert(TABLE_PERSONAJES, null, values);
        db.close();
        return id;
    }

    // Obtener todos los personajes o filtrar por tipo
    public ArrayList<Personaje> obtenerPersonajes(String tipoFiltrado) {
        ArrayList<Personaje> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT * FROM " + TABLE_PERSONAJES;
        String[] selectionArgs = null;
        
        if (tipoFiltrado != null && !tipoFiltrado.isEmpty()) {
            query += " WHERE " + COLUMN_TIPO + " = ?";
            selectionArgs = new String[]{tipoFiltrado};
        }

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO));
                String fotoUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOTO_URL));

                lista.add(new Personaje(id, nombre, tipo, fotoUrl));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    // Actualizar un personaje
    public int actualizarPersonaje(Personaje personaje) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, personaje.getNombre());
        values.put(COLUMN_TIPO, personaje.getTipo());
        values.put(COLUMN_FOTO_URL, personaje.getFotoUrl());

        int filasAfectadas = db.update(TABLE_PERSONAJES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(personaje.getId())});
        db.close();
        return filasAfectadas;
    }

    // Eliminar un personaje
    public int eliminarPersonaje(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasAfectadas = db.delete(TABLE_PERSONAJES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return filasAfectadas;
    }
}