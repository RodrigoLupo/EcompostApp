package com.example.ecompost

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductoAdapter(
    private val context: Context,
    private val productos: List<Producto>
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.nombreTextView.text = producto.nombre
        holder.descripcionTextView.text = producto.descripcion
        holder.puntosTextView.text = "Puntos necesarios: ${producto.puntos_requeridos}"
        Log.d("ProductoAdapter", "Cargando imagen desde: ${producto.imagen}")
        Glide.with(context)
            .load("http://192.168.142.156:8000/${producto.imagen}")
            //.placeholder(R.drawable.placeholder) // Imagen de placeholder
            //.error(R.drawable.error) // Imagen de error
            .into(holder.imagenImageView)

        holder.itemView.setOnClickListener {
            showPopupMenu(it, producto)
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    private fun showPopupMenu(view: View, producto: Producto) {
        val popup = PopupMenu(context, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_producto, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            handleMenuItemClick(menuItem, producto)
            true
        }
        popup.show()
    }

    private fun handleMenuItemClick(menuItem: MenuItem, producto: Producto) {
        when (menuItem.itemId) {
            R.id.menu_canjear -> {
                // Handle canjear action
            }
            
        }
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombreProducto)
        val descripcionTextView: TextView = itemView.findViewById(R.id.textViewDescripcionProducto)
        val puntosTextView: TextView = itemView.findViewById(R.id.textViewPuntosProducto)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imageViewProducto)
    }
}
