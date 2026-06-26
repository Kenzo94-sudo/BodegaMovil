package com.appbodega.entity

data class Producto (
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,
    val precio: Double,
    val imagenResId: Int
)