package com.example.ecompost

data class ProfileResponse(
    val proveedor: Proveedor,
    val productos: List<Producto>
)

data class Proveedor(
    val puntos_acumulados: Int
)



