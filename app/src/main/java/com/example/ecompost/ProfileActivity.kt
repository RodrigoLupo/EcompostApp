package com.example.ecompost

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val token = intent.getStringExtra("token") ?: ""
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val call = apiService.getProfile("Bearer $token")

        val textViewPuntos: TextView = findViewById(R.id.textViewPuntos)
        val recyclerViewProductos: RecyclerView = findViewById(R.id.recyclerViewProductos)
        recyclerViewProductos.layoutManager = LinearLayoutManager(this)

        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    val puntosAcumulados = profile?.proveedor?.puntos_acumulados ?: 0
                    val productos = profile?.productos ?: emptyList()

                    textViewPuntos.text = "Puntos acumulados: $puntosAcumulados"
                    val adapter = ProductoAdapter(this@ProfileActivity, productos)
                    recyclerViewProductos.adapter = adapter
                } else {
                    Toast.makeText(this@ProfileActivity, "Error al obtener perfil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Error al obtener perfil: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}