package com.example.ecompost

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StatsActivity : AppCompatActivity() {

    private lateinit var btnHistorial: Button
    private lateinit var btnStats: Button
    private lateinit var btnEstadisticas: ImageButton
    private lateinit var btnHome: ImageButton
    private lateinit var btnAjustes: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        // Inicializar botones
        btnHistorial = findViewById(R.id.btnHistorial)
        btnStats = findViewById(R.id.btnStats) // Ajustado el ID según el XML
        btnEstadisticas = findViewById(R.id.btnEstadisticas)
        btnHome = findViewById(R.id.btnHome)
        btnAjustes = findViewById(R.id.btnAjustes)

        // Configurar listeners de clics
        btnHistorial.setOnClickListener {
            mostrarHistorialFragment()
        }

        btnEstadisticas.setOnClickListener {
            Toast.makeText(this, "Ya estás en la pantalla de Estadisticas", Toast.LENGTH_SHORT).show()
        }

        btnHome.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnAjustes.setOnClickListener {
            startActivity(Intent(this, ConfigActivity::class.java))
        }

        btnStats.setOnClickListener {
            mostrarEstadisticasFragment()
        }

        // Mostrar el fragmento de historial por defecto al iniciar la actividad
        mostrarHistorialFragment()
    }

    private fun mostrarHistorialFragment() {
        val fragment = HistorialFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun mostrarEstadisticasFragment() {
        val fragment = EstadisticasFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
