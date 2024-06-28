package com.example.ecompost

data class HistorialItem(
    val nombre: String,
    val precio: String,
    val puntos: Int,
    val imagen: Int, // Recurso de imagen del producto
    val tipoPago: String // Puede ser "puntos" o "soles"
)
