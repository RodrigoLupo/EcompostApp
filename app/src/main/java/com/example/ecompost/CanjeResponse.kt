package com.example.ecompost

data class CanjeResponse(
    val id: Int,
    val proveedor: Int,
    val producto: Int,
    val cantidad: Int,
    val puntos_utilizados: Int,
    val tipo: String
)
