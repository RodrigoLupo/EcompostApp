package com.example.ecompost

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val puntos_requeridos: Int,
    val imagen: String
)