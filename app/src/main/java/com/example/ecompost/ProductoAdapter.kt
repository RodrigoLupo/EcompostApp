package com.example.ecompost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductoAdapter(
    private val context: Context,
    private val productos: List<Producto>,
    private val token: String,
    private val proveedorId: Int
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombreTextView.text = producto.nombre
        holder.descripcionTextView.text = producto.descripcion
        holder.precioTextView.text = "Precio: ${producto.precio}"
        holder.puntosTextView.text = "Puntos: ${producto.puntos_requeridos}"

        Glide.with(context)
            .load("http://192.168.0.14:8000/${producto.imagen}")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(holder.imagenImageView)

        holder.itemView.setOnClickListener {
            // Mostrar diálogo de confirmación antes de canjear
            AlertDialog.Builder(context)
                .setTitle("Confirmar Canje")
                .setMessage("¿Estás seguro de que deseas canjear el producto '${producto.nombre}' por ${producto.puntos_requeridos} puntos?")
                .setPositiveButton("Sí") { dialog, which ->
                    val canjeRequest = CanjeRequest(proveedor_id = proveedorId, producto_id = producto.id)
                    canjearProducto(token, canjeRequest)
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun canjearProducto(token: String, canjeRequest: CanjeRequest) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val call = apiService.canjearPuntos("Bearer $token", canjeRequest)

        call.enqueue(object : retrofit2.Callback<CanjeResponse> {
            override fun onResponse(call: retrofit2.Call<CanjeResponse>, response: retrofit2.Response<CanjeResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Producto canjeado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(context, "Error al canjear producto: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<CanjeResponse>, t: Throwable) {
                Toast.makeText(context, "Error al canjear producto: ${t.message}", Toast.LENGTH_SHORT).show()
                println("Error al canjear producto: ${t.message}")
            }
        })
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombre)
        val descripcionTextView: TextView = itemView.findViewById(R.id.textViewDescripcion)
        val precioTextView: TextView = itemView.findViewById(R.id.textViewPrecio)
        val puntosTextView: TextView = itemView.findViewById(R.id.textViewPuntos)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imageViewProducto)
    }
}
