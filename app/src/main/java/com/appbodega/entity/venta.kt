package com.appbodega.entity

data class venta(
    val codigo: String,
    val fecha: String,
    val cantidad: Int,
    val metodo: String,
    val categoria: String,
    val total: Double
)