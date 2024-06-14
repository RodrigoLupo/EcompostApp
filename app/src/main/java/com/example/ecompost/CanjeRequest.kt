package com.example.ecompost

data class CanjeRequest(
    val proveedor_id: Int,
    val producto_id: Int,
    val cantidad: Int = 1
)
