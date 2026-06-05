package com.appbodega.app

data class venta(
    val codigo: String,
    val fecha: String,
    val cantidad: Int,
    val metodo: String,
    val categoria: String,
    val total: Double
)