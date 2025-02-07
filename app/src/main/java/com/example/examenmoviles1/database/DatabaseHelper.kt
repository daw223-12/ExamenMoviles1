package com.example.examenmoviles1.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Nombre de la base de datos.
        private const val DATABASE_NAME = "BaresDatabase"
        // Versión de la base de datos, útil para manejar actualizaciones esquemáticas.
        private const val DATABASE_VERSION = 1
        // Nombre de la tabla donde se almacenarán los discos.
        private const val TABLE_BARES = "bares"
        // Nombres de las columnas de la tabla.
        private const val KEY_ID = "id"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_PUNTUACION = "puntuacion"
        private const val KEY_UBICACIONX = "ubicacionX"
        private const val KEY_UBICACIONY = "ubicacionY"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createDiscosTable = ("CREATE TABLE " + TABLE_BARES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOMBRE + " TEXT,"
                + KEY_PUNTUACION + " INTEGER," + KEY_UBICACIONX + " INTEGER,"
                + KEY_UBICACIONY + " INTEGER" +  ")")
        // Ejecuta la sentencia SQL para crear la tabla.
        db!!.execSQL(createDiscosTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_BARES")
        onCreate(db)
    }

    fun getAllBares(): ArrayList<Bar> {
        // Lista para almacenar y retornar los discos.
        val discosList = ArrayList<Bar>()
        // Consulta SQL para seleccionar todos los discos.
        val selectQuery = "SELECT  * FROM $TABLE_BARES"

        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            // Ejecuta la consulta SQL.
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            // Maneja la excepción en caso de error al ejecutar la consulta.
            db.execSQL(selectQuery)
            return ArrayList()
        }

        // Variables para almacenar los valores de las columnas.
        var id: Int
        var nombre: String
        var puntuacion: Int
        var ubicacionX: Int
        var ubicacionY: Int

        // Itera a través del cursor para leer los datos de la base de datos.
        if (cursor.moveToFirst()) {
            do {
                // Obtiene los índices de las columnas.
                val idIndex = cursor.getColumnIndex(KEY_ID)
                val nombreIndex = cursor.getColumnIndex(KEY_NOMBRE)
                val puntuacionIndex = cursor.getColumnIndex(KEY_PUNTUACION)
                val ubicacionXIndex = cursor.getColumnIndex(KEY_UBICACIONX)
                val ubicacionYIndex = cursor.getColumnIndex(KEY_UBICACIONY)

                // Verifica que los índices sean válidos.
                if (idIndex != -1 && nombreIndex != -1 && puntuacionIndex != -1 && ubicacionXIndex != -1 && ubicacionYIndex != -1) {
                    // Lee los valores y los añade a la lista de discos.
                    id = cursor.getInt(idIndex)
                    nombre = cursor.getString(nombreIndex)
                    puntuacion = cursor.getInt(puntuacionIndex)
                    ubicacionX = cursor.getInt(ubicacionXIndex)
                    ubicacionY = cursor.getInt(ubicacionYIndex)

                    val bar = Bar(id = id, nombre = nombre, puntuacion = puntuacion, ubicacionX = ubicacionX, ubicacionY = ubicacionY)
                    discosList.add(bar)
                }
            } while (cursor.moveToNext())
        }

        // Cierra el cursor para liberar recursos.
        cursor.close()
        return discosList
    }

    fun addBar(bar: Bar): Long {
        try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            // Prepara los valores a insertar.
            contentValues.put(KEY_NOMBRE, bar.nombre)
            contentValues.put(KEY_PUNTUACION, bar.puntuacion)
            contentValues.put(KEY_UBICACIONX, bar.ubicacionX)
            contentValues.put(KEY_UBICACIONY, bar.ubicacionY)

            // Inserta el nuevo disco y retorna el ID del nuevo disco o -1 en caso de error.
            val success = db.insert(TABLE_BARES, null, contentValues)
            db.close()
            return success
        } catch (e: Exception) {
            // Maneja la excepción en caso de error al insertar.
            Log.e("Error", "Error al agregar disco", e)
            return -1
        }
    }
}