package com.example.ecompost

data class Transaccion(
    val id: Int,
    val proveedor: Int,
    val producto: Producto,
    val cantidad: Int,
    val puntos_utilizados: Int,
    val tipo: String
)