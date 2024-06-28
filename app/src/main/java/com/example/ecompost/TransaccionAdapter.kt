package com.example.ecompost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TransaccionAdapter(
    private val context: Context,
    private val transacciones: List<Transaccion>
) : RecyclerView.Adapter<TransaccionAdapter.TransaccionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaccionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_historial, parent, false)
        return TransaccionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransaccionViewHolder, position: Int) {
        val transaccion = transacciones[position]
        holder.bind(transaccion)
    }

    override fun getItemCount(): Int {
        return transacciones.size
    }

    inner class TransaccionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewProducto: ImageView = itemView.findViewById(R.id.imageViewProducto)
        private val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        private val textViewPrecio: TextView = itemView.findViewById(R.id.textViewPrecio)
        private val textViewPuntos: TextView = itemView.findViewById(R.id.textViewPuntos)
        private val textViewPago: TextView = itemView.findViewById(R.id.textViewPago)

        fun bind(transaccion: Transaccion) {
            Glide.with(context).load(transaccion.producto.imagen)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(imageViewProducto)
            textViewNombre.text = transaccion.producto.nombre
            textViewPrecio.text = "Precio: ${transaccion.producto.precio}"
            textViewPuntos.text = "Puntos: ${transaccion.puntos_utilizados}"
            textViewPago.text = "Pago con: ${transaccion.tipo}"
        }
    }
}

