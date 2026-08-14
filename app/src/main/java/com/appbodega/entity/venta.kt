package com.appbodega.entity

data class venta(
    val id: String = "",
    val codigo: String = "",
    val fecha: String = "",
    val cantidad: Int = 0,
    val metodo: String = "",
    val categoria: String = "",
    val total: Double = 0.0,
    val productoId: String = ""
)