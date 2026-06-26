package com.appbodega.app

data class Producto (
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,
    val precio: Double,
    val imagenResId: Int
)