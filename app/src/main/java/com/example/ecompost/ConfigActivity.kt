package com.example.ecompost

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.ecompost.ProfileActivity
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfigActivity : AppCompatActivity() {

    private lateinit var btnEstadisticas: ImageButton
    private lateinit var btnHome: ImageButton
    private lateinit var btnAjustes: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        btnEstadisticas = findViewById(R.id.btnEstadisticas)
        btnHome = findViewById(R.id.btnHome)
        btnAjustes = findViewById(R.id.btnAjustes)

        btnEstadisticas.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }

        btnHome.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnAjustes.setOnClickListener {
            Toast.makeText(this, "Ya estás en la configuración", Toast.LENGTH_SHORT).show()
        }

        // Configuración de los clics en las flechas de cambiar nombre y contraseña Falta implementar Logica
        findViewById<AppCompatImageView>(R.id.arrow_cambiar_nombre).setOnClickListener {
            mostrarDialogoCambiarNombre()
        }

        findViewById<AppCompatImageView>(R.id.arrow_cambiar_contrasena).setOnClickListener {
            mostrarDialogoCambiarContrasena()
        }
    }

    private fun mostrarDialogoCambiarNombre() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cambiar_nombre, null)
        val editTextNuevoNombre = dialogView.findViewById<EditText>(R.id.editTextNuevoNombre)

        builder.setView(dialogView)
            .setPositiveButton("Guardar") { dialog, which ->
                val nuevoNombre = editTextNuevoNombre.text.toString()
                Toast.makeText(this, "Nuevo nombre guardado: $nuevoNombre", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun mostrarDialogoCambiarContrasena() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cambiar_contrasena, null)
        val editTextNuevaContraseña = dialogView.findViewById<EditText>(R.id.editTextNuevaContraseña)

        builder.setView(dialogView)
            .setPositiveButton("Cambiar") { dialog, which ->
                val nuevaContraseña = editTextNuevaContraseña.text.toString()
                Toast.makeText(this, "Nueva contraseña cambiada: $nuevaContraseña", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}
