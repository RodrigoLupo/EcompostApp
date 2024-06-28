package com.example.ecompost

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerViewProductos: RecyclerView
    private lateinit var txtnombre: TextView
    private lateinit var textViewPuntos: TextView
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Recupera el token desde el intent o el almacenamiento compartido
        token = intent.getStringExtra("token") ?: PreferenceHelper.getAccessToken(this).orEmpty()

        if (token.isEmpty()) {
            Toast.makeText(this, "No se encontró token. Por favor inicie sesión.", Toast.LENGTH_SHORT).show()
            // Redirigir al usuario a la pantalla de inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos)
        txtnombre = findViewById(R.id.name_provee)
        textViewPuntos = findViewById(R.id.textViewPuntos)
        recyclerViewProductos.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            fetchProfile()
        }

        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnAjustes = findViewById<ImageButton>(R.id.btnAjustes)
        val btnEstadisticas = findViewById<ImageButton>(R.id.btnEstadisticas)

        btnHome.setOnClickListener {
            Toast.makeText(this, "Ya estás en la pantalla de perfil", Toast.LENGTH_SHORT).show()
        }

        btnAjustes.setOnClickListener {
            startActivity(Intent(this, ConfigActivity::class.java))
        }

        btnEstadisticas.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }

        fetchProfile()
    }

    private fun fetchProfile() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val call = apiService.getProfile("Bearer $token")

        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    val username = profile?.user_name?.capitalizeFirstLetter() ?: ""
                    val puntosAcumulados = profile?.proveedor?.puntos_acumulados ?: 0
                    val proveedorId = profile?.proveedor?.id ?: 0
                    val productos = profile?.productos ?: emptyList()
                    txtnombre.text = username
                    textViewPuntos.text = "Puntos acumulados: $puntosAcumulados"
                    val adapter = ProductoAdapter(this@ProfileActivity, productos, token, proveedorId)
                    recyclerViewProductos.adapter = adapter
                } else {
                    Toast.makeText(this@ProfileActivity, "Error al obtener perfil", Toast.LENGTH_SHORT).show()
                }
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Error al obtener perfil: ${t.message}", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    fun String.capitalizeFirstLetter(): String {
        return if (this.isNotEmpty()) {
            this[0].uppercaseChar() + this.substring(1)
        } else {
            this
        }
    }
}
