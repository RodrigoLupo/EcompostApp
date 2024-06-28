package com.example.ecompost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistorialFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var transaccionAdapter: TransaccionAdapter
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historial, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewHistorial)
        recyclerView.layoutManager = LinearLayoutManager(context)

        token = PreferenceHelper.getAccessToken(requireContext()).orEmpty()

        if (token.isNotEmpty()) {
            fetchTransacciones()
        } else {
            // Manejar el caso donde no hay token disponible
        }

        return view
    }

    private fun fetchTransacciones() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val call = apiService.getCanjesPorProveedor("Bearer $token")

        call.enqueue(object : Callback<List<Transaccion>> {
            override fun onResponse(call: Call<List<Transaccion>>, response: Response<List<Transaccion>>) {
                if (response.isSuccessful) {
                    val transacciones = response.body() ?: emptyList()
                    transaccionAdapter = TransaccionAdapter(requireContext(), transacciones)
                    recyclerView.adapter = transaccionAdapter
                } else {
                    // Manejar error de la respuesta
                }
            }

            override fun onFailure(call: Call<List<Transaccion>>, t: Throwable) {
                // Manejar fallo de la llamada
            }
        })
    }
}
