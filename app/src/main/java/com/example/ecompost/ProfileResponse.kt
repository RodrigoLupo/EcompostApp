package com.example.ecompost

data class ProfileResponse(
    val user_name: String,
    val proveedor: Proveedor,
    val productos: List<Producto>
)

data class Proveedor(
    val id: Int,
    val user: Int,
    val puntos_acumulados: Int
)



