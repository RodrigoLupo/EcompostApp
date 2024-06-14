package com.example.ecompost

import android.os.Bundle
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
    private lateinit var textViewProveedorId: TextView
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        token = intent.getStringExtra("token") ?: ""
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos)
        txtnombre = findViewById(R.id.name_provee)
        textViewPuntos = findViewById(R.id.textViewPuntos)
        recyclerViewProductos.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            fetchProfile()
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
