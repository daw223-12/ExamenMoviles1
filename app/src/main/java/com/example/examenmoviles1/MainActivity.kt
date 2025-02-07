package com.example.examenmoviles1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.examenmoviles1.database.Bar
import com.example.examenmoviles1.database.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHandler: DatabaseHelper




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHandler = DatabaseHelper(this)

        addDisco("Baresito", 10, 12, 12)

        val baresList = dbHandler.getAllBares();
        Log.d("BBDD-Qué hay", "ANTES DEL FOR")
        for (bar in baresList) {
            Log.d("BBDD-Qué hay", bar.nombre)
        }

//        addDisco("BardeEjemplo", 10, 12, 12)
//        addDisco("Cuñaos", 10, 12, 12)
//        addDisco("Futbolin", 10, 12, 12)
//        addDisco("Billar", 10, 12, 12)
    }

    private fun addDisco(nombre: String, puntuacion: Int, ubicacionX: Int, ubicacionY: Int) {
        // Obtiene el texto de los EditText y lo convierte en String.
        val nombre = nombre
        val puntuacion = puntuacion.toString()
        val ubicacionX = ubicacionX.toString()
        val ubicacionY = ubicacionY.toString()
        // Verifica que los campos no estén vacíos.
        if (nombre.isNotEmpty() && puntuacion.isNotEmpty() && ubicacionX.isNotEmpty() && ubicacionY.isNotEmpty()) {
            // Crea un objeto Disco y lo añade a la base de datos.
            val bar = Bar(
                nombre = nombre,
                puntuacion = puntuacion.toInt(),
                ubicacionX = ubicacionX.toInt(),
                ubicacionY = ubicacionY.toInt()
            )
            val status = dbHandler.addBar(bar)
            // Verifica si la inserción fue exitosa.
            if (status > -1) {
                Toast.makeText(applicationContext, "Disco agregado", Toast.LENGTH_LONG).show()
            }
        } else {
            // Muestra un mensaje si los campos están vacíos.
            Toast.makeText(applicationContext, "Nombre y Año son requeridos", Toast.LENGTH_LONG)
                .show()
        }
    }
}